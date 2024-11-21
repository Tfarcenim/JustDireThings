package com.direwolf20.justdirethings.common.blockentities.basebe;

import com.direwolf20.justdirethings.common.capabilities.MachineEnergyStorage;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public interface PoweredMachineBE {
    default int getMaxEnergy() {
        return 100000;
    }

    ContainerData getContainerData();

    MachineEnergyStorage getEnergyStorage();

    default int getEnergyStored() {
        return getEnergyStorage().getEnergyStored();
    }

    default void setEnergyStored(int value) {
        getEnergyStorage().setEnergy(value);
    }

    int getStandardEnergyCost();

    default boolean hasEnoughPower(int power) {
        return getEnergyStorage().extractEnergy(power, true) >= power;
    }

    default int insertEnergy(int power, boolean simulate) {
        return getEnergyStorage().receiveEnergy(power, simulate);
    }

    default int extractEnergy(int power, boolean simulate) {
        return getEnergyStorage().extractEnergy(power, simulate);
    }

    default void chargeItemStack(ItemStack itemStack) {
        itemStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
            int acceptedEnergy = iEnergyStorage.receiveEnergy(5000, true);
            if (acceptedEnergy > 0) {
                int extractedEnergy = getEnergyStorage().extractEnergy(acceptedEnergy, false);
                iEnergyStorage.receiveEnergy(extractedEnergy, false);
            }
        });
    }
}
