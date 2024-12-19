package com.direwolf20.justdirethings.common.items;

import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemstack;
import com.direwolf20.justdirethings.common.entities.PortalEntity;
import com.direwolf20.justdirethings.common.entities.PortalProjectile;
import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import com.direwolf20.justdirethings.common.items.interfaces.BasePoweredItem;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredItem;
import com.direwolf20.justdirethings.setup.Config;
import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PortalGunItem extends BasePoweredItem implements PoweredItem {
    public PortalGunItem() {
        super(new Properties()
                .stacksTo(1));
    }

    @Override
    public int getMaxEnergy() {
        return Config.PORTAL_GUN_V1_RF_CAPACITY.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        /*level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );*/
        if (!level.isClientSide) {
            if (!player.isShiftKeyDown())
                spawnProjectile(level, player, itemStack, false);
            else
                closeMyPortals((ServerLevel) level, itemStack, player);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    public static void closeMyPortals(ServerLevel level, ItemStack itemStack, Player player) {
        UUID portalGunUUID = getUUID(itemStack);
        MinecraftServer server = level.getServer();
        for (ServerLevel serverLevel : server.getAllLevels()) {
            List<? extends PortalEntity> customEntities = serverLevel.getEntities(Registration.PortalEntity.get(), k -> k.getOwner() == player.getUUID() || k.getPortalGunUUID().equals(portalGunUUID));

            for (PortalEntity entity : customEntities) {
                entity.setDying();
            }
        }
    }

    public static void spawnProjectile(Level level, Player player, ItemStack itemStack, boolean isPrimaryType) {
        if (!PoweredItem.consumeEnergy(itemStack, Config.PORTAL_GUN_V1_RF_COST.get())) {
            player.displayClientMessage(Component.translatable("justdirethings.lowenergy"), true);
            player.playNotifySound(SoundEvents.ALLAY_DEATH, SoundSource.PLAYERS, 1.0F, 1.0F);
            return;
        }
        PortalProjectile projectile = new PortalProjectile(level, player, getUUID(itemStack), isPrimaryType, false);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1F, 1.0F);
        level.addFreshEntity(projectile);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public static UUID setRandomUUID(ItemStack itemStack) {
        UUID uuid = UUID.randomUUID();
        JustDireDataComponents.setPortalgunUUID(itemStack,uuid);
        return uuid;
    }

    public static UUID getUUID(ItemStack itemStack) {
        UUID existing = JustDireDataComponents.getPortalgunUUID(itemStack);
        if (existing != null) return existing;
        return setRandomUUID(itemStack);
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
            if (cap == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> getEnergyStorage(stack)).cast();
            return LazyOptional.empty();
        }
    }
}
