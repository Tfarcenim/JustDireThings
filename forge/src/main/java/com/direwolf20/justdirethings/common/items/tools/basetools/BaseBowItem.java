package com.direwolf20.justdirethings.common.items.tools.basetools;

import com.direwolf20.justdirethings.common.entities.JustDireArrow;
import com.direwolf20.justdirethings.common.items.PotionCanisterItem;
import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import com.direwolf20.justdirethings.common.items.interfaces.*;
import com.direwolf20.justdirethings.setup.Config;
import com.direwolf20.justdirethings.util.ItemStackNBTHandler;
import com.direwolf20.justdirethings.util.PotionContents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.direwolf20.justdirethings.util.TooltipHelpers.*;

public class BaseBowItem extends BowItem implements ToggleableTool, LeftClickableTool {
    protected final EnumSet<Ability> abilities = EnumSet.noneOf(Ability.class);
    protected final Map<Ability, AbilityParams> abilityParams = new EnumMap<>(Ability.class);
    private final int slots;

    public BaseBowItem(Properties properties,int slots) {
        super(properties);
        this.slots = slots;
    }

    public float getMaxDraw() {
        return 20;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.pass(stack);
        if (player.isShiftKeyDown()) {
            openSettings(player);
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        if (stack.getItem() instanceof PoweredTool poweredTool && PoweredItem.getAvailableEnergy(stack) < poweredTool.getBlockBreakFECost())
            return InteractionResultHolder.pass(stack);
        return super.use(level, player, hand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if ((!getPassiveTickAbilities(itemStack).isEmpty() || !getCooldownAbilities().isEmpty()) && entity instanceof Player player) {
            ToggleableTool.tickCooldowns(level, itemStack, player);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack ammo = pEntityLiving.getProjectile(pStack);
            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !ammo.isEmpty() || flag);
            if (i < 0) return;
            float f = getPowerForTime(i);

            if (!pLevel.isClientSide) {
                AbstractArrow arrow = createProjectile(pLevel, pEntityLiving, pStack, ammo, f >= 1);
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                pLevel.addFreshEntity(arrow);
            }
        }
    }//todo

    protected AbstractArrow createProjectile(Level level, LivingEntity livingEntity, ItemStack weapon, ItemStack ammo, boolean crit) {
        ArrowItem arrowitem = ammo.getItem() instanceof ArrowItem arrowitem1 ? arrowitem1 : (ArrowItem) Items.ARROW;
        ToggleableTool toggleableTool = (ToggleableTool) weapon.getItem();
        JustDireArrow justDireArrow = new JustDireArrow(level, livingEntity);
        if (arrowitem.equals(Items.ARROW)) {
            if (crit) {
                justDireArrow.setCritArrow(true);
            }
            Boolean e = JustDireDataComponents.isEpicArrow(weapon);
            if (e != null && e) {
                justDireArrow.setEpicArrow(true);
                justDireArrow.setBaseDamage(20d);
                AbilityParams abilityParams = toggleableTool.getAbilityParams(Ability.EPICARROW);
                ToggleableTool.addCooldown(weapon, Ability.EPICARROW, abilityParams.cooldown, false);
                JustDireDataComponents.setEpicArrow(weapon,false);
            }

            if (!toggleableTool.getEnabled(weapon))
                return customArrow(justDireArrow);

            if (canUseAbilityAndDurability(weapon, Ability.PHASE)) {
                justDireArrow.setPhase(true);
                Helpers.damageTool(weapon, livingEntity, Ability.PHASE);
            }

            if (canUseAbilityAndDurability(weapon, Ability.HOMING)) {
                justDireArrow.setHoming(true);
                Helpers.damageTool(weapon, livingEntity, Ability.HOMING);
                boolean hostileOnly = ToggleableTool.getCustomSetting(weapon, Ability.HOMING.getName()) == 0;
                LivingEntity aimedAtEntity = findAimedAtEntity(livingEntity, hostileOnly);
                if (aimedAtEntity != null)
                    justDireArrow.setTargetEntity(aimedAtEntity);
                justDireArrow.setHostileOnly(hostileOnly);
            }

            if (noPotionAbilitiesActive(weapon))
                return customArrow(justDireArrow);

            IItemHandler itemHandler = weapon.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
            if (itemHandler instanceof ItemStackNBTHandler componentItemHandler) {
                for (int slot = 0; slot < componentItemHandler.getSlots(); slot++) {
                    ItemStack potionCanister = componentItemHandler.getStackInSlot(slot);
                    if (potionCanister.getItem() instanceof PotionCanisterItem) {
                        int potionAmt = PotionCanisterItem.getPotionAmount(potionCanister);
                        PotionContents slotPotionContents = PotionCanisterItem.getPotionContents(potionCanister);
                        if (!slotPotionContents.equals(PotionContents.EMPTY)) {
                            int neededAmt = 0;
                            if (canUseAbilityAndDurability(weapon, Ability.POTIONARROW))
                                neededAmt = neededAmt + 25;
                            if (canUseAbilityAndDurability(weapon, Ability.SPLASH))
                                neededAmt = neededAmt + 25;
                            if (canUseAbilityAndDurability(weapon, Ability.LINGERING))
                                neededAmt = neededAmt + 50;
                            if (potionAmt >= neededAmt) {
                                PotionCanisterItem.setPotionAmount(potionCanister, potionAmt - neededAmt);
                                componentItemHandler.setStackInSlot(slot, potionCanister);
                                if (canUseAbilityAndDurability(weapon, Ability.POTIONARROW)) {
                                    justDireArrow.setPotionArrow(true);
                                    Helpers.damageTool(weapon, livingEntity, Ability.POTIONARROW);
                                }
                                if (canUseAbilityAndDurability(weapon, Ability.SPLASH)) {
                                    justDireArrow.setSplash(true);
                                    Helpers.damageTool(weapon, livingEntity, Ability.SPLASH);
                                }
                                if (canUseAbilityAndDurability(weapon, Ability.LINGERING)) {
                                    justDireArrow.setLingering(true);
                                    Helpers.damageTool(weapon, livingEntity, Ability.LINGERING);
                                }
                                justDireArrow.setEffectsFromPotions(slotPotionContents);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return customArrow(justDireArrow);
    }

    public LivingEntity findAimedAtEntity(LivingEntity livingEntity, boolean onlyHostile) {
        double range = 50;
        Vec3 startVec = livingEntity.getEyePosition(1.0F);
        Vec3 lookVec = livingEntity.getViewVector(1.0F);
        Vec3 endVec = startVec.add(lookVec.scale(range));

        // Perform the ray trace
        HitResult hitResult = livingEntity.level().clip(new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, livingEntity));

        if (hitResult.getType() != HitResult.Type.MISS) {
            endVec = hitResult.getLocation();
        }

        AABB boundingBox = new AABB(startVec, endVec).inflate(1.0D);
        List<Entity> entities = livingEntity.level().getEntities(livingEntity, boundingBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE);

        LivingEntity closestEntity = null;
        double closestDistance = range * range;

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity1) {
                if (onlyHostile && !JustDireArrow.isHostileEntity(livingEntity1)) {
                    continue;  // Skip non-hostile entities if onlyHostile is true
                }
                AABB entityBoundingBox = entity.getBoundingBox().inflate(entity.getPickRadius());
                Vec3 entityHitVec = entityBoundingBox.clip(startVec, endVec).orElse(null);

                if (entityBoundingBox.contains(startVec)) {
                    if (closestDistance >= 0.0D) {
                        closestEntity = livingEntity1;
                        closestDistance = 0.0D;
                    }
                } else if (entityHitVec != null) {
                    double distanceToHit = startVec.distanceToSqr(entityHitVec);

                    if (distanceToHit < closestDistance) {
                        closestEntity = livingEntity1;
                        closestDistance = distanceToHit;
                    }
                }
            }
        }
        return closestEntity;
    }

    public boolean noPotionAbilitiesActive(ItemStack itemStack) {
        if (canUseAbilityAndDurability(itemStack, Ability.POTIONARROW))
            return false;
        if (canUseAbilityAndDurability(itemStack, Ability.SPLASH))
            return false;
        if (canUseAbilityAndDurability(itemStack, Ability.LINGERING))
            return false;
        return true;

    }

  //  @Override
    protected int getDurabilityUse(ItemStack itemStack) {
        return this instanceof PoweredTool poweredTool ? poweredTool.getBlockBreakFECost() : 1;
    }



    @Override
    public EnumSet<Ability> getAllAbilities() {
        return abilities;
    }

    @Override
    public EnumSet<Ability> getAbilities() {
        return abilities.stream()
                .filter(ability -> Config.AVAILABLE_ABILITY_MAP.get(ability).get())
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Ability.class)));
    }

    @Override
    public Map<Ability, AbilityParams> getAbilityParamsMap() {
        return abilityParams;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        if (level == null) {
            return;
        }

        boolean sneakPressed = Screen.hasShiftDown();
        appendFEText(stack, tooltip);
        if (sneakPressed) {
            appendToolEnabled(stack, tooltip);
            appendAbilityList(stack, tooltip);
        } else {
            appendToolEnabled(stack, tooltip);
            appendShiftForInfo(stack, tooltip);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<T> onBroken) {
        if (stack.getItem() instanceof PoweredTool poweredTool) {
            IEnergyStorage energyStorage = stack.getCapability(ForgeCapabilities.ENERGY).orElse(null);
            if (energyStorage == null) return amount;
            double reductionFactor = 0;
            if (entity != null) {
                int unbreakingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING,stack);
                reductionFactor = Math.min(1.0, unbreakingLevel * 0.1);
            }
            int finalEnergyCost = (int) Math.max(0, amount - (amount * reductionFactor));
            energyStorage.extractEnergy(finalEnergyCost, false);
            return 0;
        }
        return amount;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public IItemHandler getItemHandler(ItemStack stack) {
        return new ItemStackNBTHandler(stack,JustDireDataComponents.TOOL_CONTENTS,slots);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapabilityProvider(stack);
    }

    public class CapabilityProvider implements ICapabilityProvider {

        protected final ItemStack stack;
        private final LazyOptional<IItemHandler> holder;

        public CapabilityProvider(ItemStack stack) {
            this.stack = stack;
            holder = LazyOptional.of(() -> getItemHandler(stack));
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, this.holder);
        }
    }

}
