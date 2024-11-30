package com.direwolf20.justdirethings.common.capabilities;

import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageItemstack extends EnergyStorage {
    protected final ItemStack itemStack;

    public EnergyStorageItemstack(int capacity, ItemStack itemStack) {
        super(capacity, capacity, capacity, 0);
        this.itemStack = itemStack;
        this.energy = getEnergyStored();
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        JustDireDataComponents.setForgeEnergy(itemStack,energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += energyReceived;
            JustDireDataComponents.setForgeEnergy(itemStack,energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            JustDireDataComponents.setForgeEnergy(itemStack,energy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        Integer i = JustDireDataComponents.getForgeEnergy(itemStack);
        return i == null ? 0 : i;
    }

    @Override
    public int getMaxEnergyStored() {
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
        return IntTag.valueOf(this.getEnergyStored());
    }

    @Override
    public void deserializeNBT( Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        this.energy = intNbt.getAsInt();
    }
}
