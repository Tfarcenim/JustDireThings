package com.direwolf20.justdirethings.common.capabilities;


import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineBE;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.EnergyStorage;

public class MachineEnergyStorage<B extends BlockEntity & PoweredMachineBE> extends EnergyStorage {

    final B be;

    public MachineEnergyStorage(int capacity, B be) {
        super(capacity);
        this.be = be;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int i = super.receiveEnergy(maxReceive, simulate);
        if (i > 0 && !simulate) {
            be.setChanged();
        }
        return i;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int i = super.extractEnergy(maxExtract, simulate);
        if (i > 0 && !simulate) {
            be.setChanged();
        }
        return i;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        be.setChanged();
    }
}