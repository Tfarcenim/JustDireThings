package com.direwolf20.justdirethings.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.function.Predicate;

public class JustDireFluidTank extends FluidTank implements INBTSerializable<CompoundTag> {
    public JustDireFluidTank(int capacity) {
        super(capacity);
    }

    public JustDireFluidTank(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
    }

    @Override
    public CompoundTag serializeNBT() {
        return super.writeToNBT(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        fluid = super.readFromNBT(nbt).getFluid();
    }
}
