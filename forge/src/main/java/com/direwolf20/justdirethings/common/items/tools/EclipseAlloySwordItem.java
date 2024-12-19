package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemstack;
import com.direwolf20.justdirethings.common.items.PortalGunItem;
import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.AbilityParams;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredTool;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseSwordItem;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EclipseAlloySwordItem extends BaseSwordItem implements PoweredTool {
    public EclipseAlloySwordItem() {
        super(GooTier.ECLIPSEALLOY, 3, -2.0F ,new Properties() .fireResistant());
        registerAbility(Ability.GLOWING);
        registerAbility(Ability.CAUTERIZEWOUNDS, new AbilityParams(1, 1, 1, 1, 0, 1200));
        registerAbility(Ability.DROPTELEPORT);
        registerAbility(Ability.SMOKER);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return isPowerBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return getPowerBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int color = getPowerBarColor(stack);
        if (color == -1)
            return super.getBarColor(stack);
        return color;
    }

    @Override
    public int getMaxEnergy() {
        return 500000;
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
