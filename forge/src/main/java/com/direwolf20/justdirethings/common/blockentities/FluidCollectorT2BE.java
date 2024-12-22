package com.direwolf20.justdirethings.common.blockentities;

import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.FilterableBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineContainerData;
import com.direwolf20.justdirethings.common.capabilities.MachineEnergyStorage;
import com.direwolf20.justdirethings.common.containers.handlers.FilterBasicHandler;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.interfacehelpers.AreaAffectingData;
import com.direwolf20.justdirethings.util.interfacehelpers.FilterData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FluidCollectorT2BE extends FluidCollectorT1BE implements PoweredMachineBE, AreaAffectingBE, FilterableBE {
    public FilterData filterData = new FilterData();
    public AreaAffectingData areaAffectingData = new AreaAffectingData(getBlockState().getValue(BlockStateProperties.FACING));
    public final PoweredMachineContainerData poweredMachineData;

    public FluidCollectorT2BE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.FluidCollectorT2BE.get(), pPos, pBlockState);
        poweredMachineData = new PoweredMachineContainerData(this);
    }

    @Override
    public int getMaxMB() {
        return 32000;
    }

    @Override
    public PoweredMachineContainerData getContainerData() {
        return poweredMachineData;
    }

    protected MachineEnergyStorage<FluidCollectorT2BE> energyStorage = new MachineEnergyStorage<>(getMaxEnergy(),this);

    @Override
    public MachineEnergyStorage<FluidCollectorT2BE> getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public int getStandardEnergyCost() {
        return 500;
    }

    @Override
    public AreaAffectingData getAreaAffectingData() {
        return areaAffectingData;
    }

    protected FilterBasicHandler filterBasicHandler = new FilterBasicHandler(9,this);

    @Override
    public FilterBasicHandler getFilterHandler() {
        return filterBasicHandler;
    }

    @Override
    public FilterData getFilterData() {
        return filterData;
    }

    @Override
    public boolean canCollect() {
        return hasEnoughPower(getStandardEnergyCost());
    }

    @Override
    public boolean collectFluid(BlockPos blockPos) {
        boolean success = super.collectFluid(blockPos);
        if (success)
            extractEnergy(getStandardEnergyCost(), false);
        return success;
    }

    @Override
    public List<BlockPos> findSpotsToCollect(FakePlayer fakePlayer) {
        AABB area = getAABB(getBlockPos());
        return BlockPos.betweenClosedStream((int) area.minX, (int) area.minY, (int) area.minZ, (int) area.maxX - 1, (int) area.maxY - 1, (int) area.maxZ - 1)
                .filter(blockPos -> isBlockPosValid(blockPos, fakePlayer))
                .map(BlockPos::immutable)
                .sorted(Comparator.comparingDouble(x -> x.distSqr(getBlockPos())))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isBlockPosValid(BlockPos blockPos, FakePlayer fakePlayer) {
        if (!super.isBlockPosValid(blockPos, fakePlayer))
            return false; //Do the same checks as normal, then check the filters
        if (!(level.getBlockState(blockPos).getBlock() instanceof LiquidBlock liquidBlock))
            return false;
        return isStackValidFilter(liquidBlock);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return LazyOptional.of(() -> machineHandler).cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER) return LazyOptional.of(() -> fluidTank).cast();
        if (cap == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> energyStorage).cast();
        return super.getCapability(cap, side);
    }
}
