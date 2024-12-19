package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemstack;
import com.direwolf20.justdirethings.common.items.PortalGunItem;
import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredTool;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseBowItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CelestigemBowItem extends BaseBowItem implements PoweredTool {
    public CelestigemBowItem() {
        super(new Properties().durability(450).fireResistant(),3);
        registerAbility(Ability.POTIONARROW);
        registerAbility(Ability.SPLASH);
        registerAbility(Ability.LINGERING);
        registerAbility(Ability.HOMING);
    }

    public float getMaxDraw() {
        return 14;
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

    public EnergyStorageItemstack getEnergyStorage(ItemStack stack) {
        return new EnergyStorageItemstack(getMaxEnergy(), stack);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new Provider(stack);
    }

    public class Provider extends CapabilityProvider {
        public Provider(ItemStack stack) {
            super(stack);
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> getEnergyStorage(stack)).cast();
            return super.getCapability(cap,side);
        }
    }
}
