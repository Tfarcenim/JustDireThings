package com.direwolf20.justdirethings.common.items;

import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemstack;
import com.direwolf20.justdirethings.common.entities.PortalProjectile;
import com.direwolf20.justdirethings.common.fluids.portalfluid.PortalFluidBlock;
import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import com.direwolf20.justdirethings.common.items.interfaces.BasePoweredItem;
import com.direwolf20.justdirethings.common.items.interfaces.FluidContainingItem;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredItem;
import com.direwolf20.justdirethings.setup.Config;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.FluidStackNBTHandler;
import com.direwolf20.justdirethings.util.MagicHelpers;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.direwolf20.justdirethings.util.NBTHelpers;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PortalGunV2Item extends BasePoweredItem implements PoweredItem, FluidContainingItem {
    public static final int MAX_FAVORITES = 12;
    public static final int maxMB = 8000;

    public PortalGunV2Item() {
        super(new Properties()
                .stacksTo(1));
    }

    @Override
    public int getMaxEnergy() {
        return Config.PORTAL_GUN_V2_RF_CAPACITY.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() == HitResult.Type.BLOCK) {
            if (pickupFluid(level, player, itemStack, blockhitresult))
                return InteractionResultHolder.fail(itemStack);
        }
        if (!level.isClientSide) {
            spawnProjectile(level, player, itemStack, true);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        if (level == null) {
            return;
        }
        IFluidHandlerItem fluidHandler = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandler == null) {
            return;
        }
        tooltip.add(Component.translatable("justdirethings.portalfluidamt", MagicHelpers.formatted(fluidHandler.getFluidInTank(0).getAmount()), MagicHelpers.formatted(maxMB)).withStyle(ChatFormatting.GREEN));
    }

    public static boolean pickupFluid(Level level, Player player, ItemStack itemStack, BlockHitResult blockhitresult) {
        BlockPos blockpos = blockhitresult.getBlockPos();
        BlockState blockstate1 = level.getBlockState(blockpos);
        if (blockstate1.getBlock() instanceof PortalFluidBlock portalFluidBlock) {
            IFluidHandlerItem fluidHandler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
            if (fluidHandler == null) return true;
            int filledAmt = fluidHandler.fill(new FluidStack(Registration.PORTAL_FLUID_SOURCE.get(), 1000), IFluidHandler.FluidAction.SIMULATE);
            if (filledAmt == 1000) {
                ItemStack itemstack2 = portalFluidBlock.pickupBlock(level, blockpos, blockstate1);
                fluidHandler.fill(new FluidStack(Registration.PORTAL_FLUID_SOURCE.get(), 1000), IFluidHandler.FluidAction.EXECUTE);
                portalFluidBlock.getPickupSound(blockstate1).ifPresent(p_150709_ -> player.playSound(p_150709_, 1.0F, 1.0F));
                if (!level.isClientSide) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack2);
                }
            }
            return true;
        }
        return false;
    }

    public static void spawnProjectile(Level level, Player player, ItemStack itemStack, boolean isPrimaryType) {
        NBTHelpers.PortalDestination portalDestination = player.isShiftKeyDown() ? getPrevious(itemStack) : getSelectedFavorite(itemStack);
        if (portalDestination == null || portalDestination.equals(NBTHelpers.PortalDestination.EMPTY)) return;
        int cost = calculateFluidCost((ServerLevel) level, player, portalDestination);
        if (!hasEnoughFluid(itemStack, cost)) {
            player.displayClientMessage(Component.translatable("justdirethings.lowportalfluid"), true);
            player.playNotifySound(SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return;
        }
        if (!PoweredItem.consumeEnergy(itemStack, Config.PORTAL_GUN_V2_RF_COST.get())) {
            player.displayClientMessage(Component.translatable("justdirethings.lowenergy"), true);
            player.playNotifySound(SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return;
        }
        PortalProjectile projectile = new PortalProjectile(level, player, PortalGunItem.getUUID(itemStack), isPrimaryType, true, portalDestination);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1F, 1.0F);
        level.addFreshEntity(projectile);
        consumeFluid(itemStack, cost);
        setPrevious(player, itemStack);
    }

    public static int calculateFluidCost(ServerLevel sourceLevel, Player player, NBTHelpers.PortalDestination portalDestination) {
        if (player.isCreative()) return 0;
        ServerLevel targetLevel = sourceLevel.getServer().getLevel(portalDestination.globalVec3().dimension());
        if (!targetLevel.equals(sourceLevel)) {
            return 100;
        }
        HitResult result = player.pick(5, 0f, false); //This will get the location the projectile will hit, or close to it probably
        Vec3 targetPosition = portalDestination.globalVec3().position();
        double distance = targetPosition.distanceTo(result.getLocation());
        return Math.min((int) Math.ceil(distance * 0.25), 100);
    }

    public static boolean hasEnoughFluid(ItemStack itemStack, int amt) {
        IFluidHandlerItem fluidHandler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandler == null) {
            return false;
        }
        return fluidHandler.getFluidInTank(0).getAmount() >= amt;
    }

    public static void consumeFluid(ItemStack itemStack, int amt) {
        IFluidHandlerItem fluidHandler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandler == null) {
            return;
        }
        fluidHandler.drain(amt, IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }



    public static NBTHelpers.PortalDestination getSelectedFavorite(ItemStack itemStack) {
        List<NBTHelpers.PortalDestination> favoritesList = new ArrayList<>(getFavorites(itemStack));
        if (favoritesList.isEmpty()) return null;
        return favoritesList.get(getFavoritePosition(itemStack));
    }

    public static NBTHelpers.PortalDestination getFavorite(ItemStack itemStack, int slot) {
        List<NBTHelpers.PortalDestination> favoritesList = new ArrayList<>(getFavorites(itemStack));
        if (favoritesList.isEmpty()) return null;
        return favoritesList.get(slot);
    }

    public static int getFavoritePosition(ItemStack itemStack) {
        Integer fav = JustDireDataComponents.getPortalgunFavorite(itemStack);
        return fav == null ? 0 : fav;
    }

    public static void setFavoritePosition(ItemStack itemStack, int favorite) {
        JustDireDataComponents.setPortalgunFavorite(itemStack,favorite);
    }

    public static List<NBTHelpers.PortalDestination> getFavorites(ItemStack itemStack) {
        return JustDireDataComponents.getPortalGunFavorites(itemStack);
    }

    public static void setFavorites(ItemStack itemStack, List<NBTHelpers.PortalDestination> favorites) {
        JustDireDataComponents.setPortalGunFavorites(itemStack,favorites);
    }

    public static void setPrevious(Player player, ItemStack itemStack) {
        Vec3 position = player.position();
        Direction facing = MiscHelpers.getFacingDirection(player);
        if (facing == Direction.DOWN) facing = Direction.NORTH; //Down is bad
        ResourceKey<Level> dimension = player.level().dimension();
        NBTHelpers.PortalDestination newDestination = new NBTHelpers.PortalDestination(new NBTHelpers.GlobalVec3(dimension, position), facing, "previous");
        JustDireDataComponents.setPortalGunPrevious(itemStack, newDestination);
    }

    public static NBTHelpers.PortalDestination getPrevious(ItemStack itemStack) {
        NBTHelpers.PortalDestination previous = JustDireDataComponents.getPortalGunPrevious(itemStack);
        return previous == null ? NBTHelpers.PortalDestination.EMPTY : previous;
    }

    public static void addFavorite(ItemStack itemStack, int position, NBTHelpers.PortalDestination portalDestination) {
        if (!itemStack.hasTag() ||!itemStack.getTag().contains(JustDireDataComponents.PORTAL_GUN_FAVORITES)) {
            List<NBTHelpers.PortalDestination> list = new ArrayList<>(MAX_FAVORITES);
            for (int i = 0; i < MAX_FAVORITES; i++) {
                list.add(NBTHelpers.PortalDestination.EMPTY); //Prefill List
            }
            setFavorites(itemStack, list);
        }
        List<NBTHelpers.PortalDestination> favoritesList = new ArrayList<>(getFavorites(itemStack));
        favoritesList.set(position, portalDestination);
        setFavorites(itemStack, favoritesList);
    }

    public static void removeFavorite(ItemStack itemStack, int position) {
        List<NBTHelpers.PortalDestination> favoritesList = new ArrayList<>(getFavorites(itemStack));
        if (favoritesList.isEmpty()) return;
        favoritesList.set(position, NBTHelpers.PortalDestination.EMPTY);
        setFavorites(itemStack, favoritesList);
    }

    public static boolean getStayOpen(ItemStack itemStack) {
        Boolean stayOpen = JustDireDataComponents.getPortalGunStayOpen(itemStack);
        return stayOpen != null && stayOpen;
    }

    public static ItemStack getPortalGunv2(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() instanceof PortalGunV2Item)
            return mainHand;
        ItemStack offHand = player.getOffhandItem();
        if (offHand.getItem() instanceof PortalGunV2Item)
            return offHand;
        return ItemStack.EMPTY;
    }

    public static int getFullness(ItemStack itemStack) {
        IFluidHandlerItem fluidHandler = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandler != null && !fluidHandler.getFluidInTank(0).isEmpty()) {
            float percentFull = ((float) fluidHandler.getFluidInTank(0).getAmount() / maxMB) * 100;
            if (percentFull > 0 && percentFull <= 33) {
                return 1;
            } else if (percentFull > 33 && percentFull <= 66) {
                return 2;
            } else if (percentFull > 66) {
                return 3;
            }
        }
        return 0;
    }

    public FluidStackNBTHandler getFluidHandler(ItemStack stack) {
        return new FluidStackNBTHandler(stack, PortalGunV2Item.maxMB, JustDireDataComponents.FLUID_CONTAINER) {
            @Override
            public boolean isFluidValid(int tank, FluidStack stack) {
                return stack.getFluid().getFluidType() == Registration.PORTAL_FLUID_TYPE.get();
            }

            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return fluid.getFluid().getFluidType() == Registration.PORTAL_FLUID_TYPE.get();
            }
        };
    }

    public EnergyStorageItemstack getEnergyStorage(ItemStack stack) {
        return new EnergyStorageItemstack(getMaxEnergy(), stack);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new Provider(stack);
    }

    public class Provider implements ICapabilityProvider {

        private final ItemStack stack;

        public Provider(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM) return LazyOptional.of(() -> getFluidHandler(stack)).cast();
            if (cap == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> getEnergyStorage(stack)).cast();
            return LazyOptional.empty();
        }
    }
}
