package com.direwolf20.justdirethings.common.capabilities;

import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineBE;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnergyStorageNoReceive<B extends BlockEntity & PoweredMachineBE> extends MachineEnergyStorage<B> {

    public EnergyStorageNoReceive(int capacity,B be) {
        super(capacity,be);
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    /**
     * Pocket Generators need to be able to power themselves, but not receive energy from anything else. This is used to do this.
     */
    public int forceReceiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }
}
