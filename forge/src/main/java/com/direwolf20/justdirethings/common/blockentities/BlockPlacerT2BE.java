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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BlockPlacerT2BE extends BlockPlacerT1BE implements PoweredMachineBE, AreaAffectingBE, FilterableBE {
    public FilterData filterData = new FilterData();
    public AreaAffectingData areaAffectingData = new AreaAffectingData(getBlockState().getValue(BlockStateProperties.FACING));
    public final PoweredMachineContainerData poweredMachineData;

    public BlockPlacerT2BE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.BlockPlacerT2BE.get(), pPos, pBlockState);
        poweredMachineData = new PoweredMachineContainerData(this);
    }

    @Override
    public PoweredMachineContainerData getContainerData() {
        return poweredMachineData;
    }

    protected MachineEnergyStorage energyStorage = new MachineEnergyStorage(getMaxEnergy());
    @Override
    public MachineEnergyStorage getEnergyStorage() {
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

    protected FilterBasicHandler filterBasicHandler = new FilterBasicHandler(9);
    @Override
    public FilterBasicHandler getFilterHandler() {
        return filterBasicHandler;
    }

    @Override
    public FilterData getFilterData() {
        return filterData;
    }

    @Override
    public boolean canPlace() {
        return hasEnoughPower(getStandardEnergyCost());
    }

    @Override
    public InteractionResult placeBlock(ItemStack itemStack, FakePlayer fakePlayer, BlockPos blockPos) {
        InteractionResult interactionResult = super.placeBlock(itemStack, fakePlayer, blockPos);
        if (interactionResult.equals(InteractionResult.CONSUME))
            extractEnergy(getStandardEnergyCost(), false);
        return interactionResult;
    }

    @Override
    public List<BlockPos> findSpotsToPlace(FakePlayer fakePlayer) {
        AABB area = getAABB(getBlockPos());
        return BlockPos.betweenClosedStream((int) area.minX, (int) area.minY, (int) area.minZ, (int) area.maxX - 1, (int) area.maxY - 1, (int) area.maxZ - 1)
                .filter(blockPos -> isBlockPosValid(fakePlayer, blockPos))
                .map(BlockPos::immutable)
                .sorted(Comparator.comparingDouble(x -> x.distSqr(getBlockPos())))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isBlockPosValid(FakePlayer fakePlayer, BlockPos blockPos) {
        if (!super.isBlockPosValid(fakePlayer, blockPos))
            return false; //Do the same checks as normal, then check the filters
        ItemStack blockItemStack = level.getBlockState(blockPos.relative(getDirectionValue())).getCloneItemStack(new BlockHitResult(Vec3.ZERO, getDirectionValue(), blockPos, false), level, blockPos, fakePlayer);
        return isStackValidFilter(blockItemStack);
    }

    LazyOptional<IItemHandler> itemOptional = LazyOptional.of(this::getMachineHandler);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> energyStorage).cast();
        return cap == ForgeCapabilities.ITEM_HANDLER ? itemOptional.cast() : super.getCapability(cap, side);
    }

}
