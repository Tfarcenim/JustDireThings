package com.direwolf20.justdirethings.common.items.interfaces;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public interface PoweredItem {
    static int getAvailableEnergy(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return -1;
        }
        return energy.getEnergyStored();
    }

    default boolean isPowerBarVisible(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return false;
        }
        return (energy.getEnergyStored() < energy.getMaxEnergyStored());
    }

    default int getPowerBarWidth(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return 13;
        }
        return Math.min(13 * energy.getEnergyStored() / energy.getMaxEnergyStored(), 13);
    }

    default int getPowerBarColor(ItemStack stack) {
        var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return -1; //Tell caller to call super
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }

    default int getMaxEnergy() {
        return 10000;
    }

    static boolean hasEnoughEnergy(ItemStack stack, int requiredAmt) {
        return getAvailableEnergy(stack) >= requiredAmt;
    }

    static boolean consumeEnergy(ItemStack itemStack, int amt) {
        IEnergyStorage energyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energyStorage == null) return false;
        int amtExtracted = energyStorage.extractEnergy(amt, false);
        return amtExtracted == amt;
    }
}
