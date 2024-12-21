package com.direwolf20.justdirethings.common.blockentities.basebe;

import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface FluidMachineBE {
    default int getMaxMB() {
        return 8000;
    }

    ContainerData getFluidContainerData();

    FluidTank getFluidTank();

    default FluidStack getFluidStack() {
        return getFluidTank().getFluid();
    }

    default void setFluidStack(Fluid fluid, int amt) {
        getFluidTank().setFluid(new FluidStack(fluid, amt));
    }

    default int getAmountStored() {
        return getFluidTank().getFluidAmount();
    }

    default void setAmountStored(int value) {
        FluidStack fluidStack = getFluidTank().getFluid();
        if (!fluidStack.isEmpty()) {
            fluidStack.setAmount(value);
        }
    }

    default boolean isFull() {
        return getFluidStack().getAmount() >= getMaxMB();
    }
}
