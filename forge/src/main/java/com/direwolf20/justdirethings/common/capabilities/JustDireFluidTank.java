package com.direwolf20.justdirethings.common.capabilities;

import com.direwolf20.justdirethings.common.blockentities.basebe.FluidMachineBE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.function.Predicate;

public class JustDireFluidTank<B extends BlockEntity & FluidMachineBE> extends FluidTank implements INBTSerializable<CompoundTag> {
    private final B fluidMachineBE;

    public JustDireFluidTank(int capacity, B fluidMachineBE) {
        this(capacity,e -> true,fluidMachineBE);
    }

    public JustDireFluidTank(int capacity, Predicate<FluidStack> validator,B fluidMachineBE) {
        super(capacity, validator);
        this.fluidMachineBE = fluidMachineBE;
    }

    @Override
    protected void onContentsChanged() {
        super.onContentsChanged();
        if (!fluidMachineBE.getLevel().isClientSide) {
            fluidMachineBE.setChanged();
        }
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
