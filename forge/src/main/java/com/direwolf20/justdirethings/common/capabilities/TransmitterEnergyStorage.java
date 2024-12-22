package com.direwolf20.justdirethings.common.capabilities;

import com.direwolf20.justdirethings.common.blockentities.EnergyTransmitterBE;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class TransmitterEnergyStorage extends MachineEnergyStorage<EnergyTransmitterBE> implements INBTSerializable<Tag> {

    public TransmitterEnergyStorage(int capacity, EnergyTransmitterBE energyTransmitterBE) {
        super(capacity,energyTransmitterBE);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(getMaxEnergyStored() - getEnergyStored(), Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            be.distributeEnergy(energyReceived);
            be.setChanged();
            //energy += energyReceived;
        }
        return energyReceived;
    }

    public int realReceiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += energyReceived;
            be.setChanged();
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(getEnergyStored(), Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            be.setChanged();
        }
        return energyExtracted;
    }

    public int realExtractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            be.setChanged();
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return be.getTotalEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return be.getTotalMaxEnergyStored();
    }

    public int getRealEnergyStored() {
        return energy;
    }

    public int getRealMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public Tag serializeNBT() {
        return IntTag.valueOf(this.getRealEnergyStored());
    }

    @Override
    public void deserializeNBT( Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        this.energy = intNbt.getAsInt();
    }
}