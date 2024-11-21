package com.direwolf20.justdirethings.common.blockentities;

import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.FilterableBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineContainerData;
import com.direwolf20.justdirethings.common.capabilities.MachineEnergyStorage;
import com.direwolf20.justdirethings.common.containers.handlers.FilterBasicHandler;
import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.Helpers;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableTool;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.interfacehelpers.AreaAffectingData;
import com.direwolf20.justdirethings.util.interfacehelpers.FilterData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BlockBreakerT2BE extends BlockBreakerT1BE implements PoweredMachineBE, AreaAffectingBE, FilterableBE {
    public FilterData filterData = new FilterData(false, false, 0);
    public AreaAffectingData areaAffectingData = new AreaAffectingData(getBlockState().getValue(BlockStateProperties.FACING));
    public final PoweredMachineContainerData poweredMachineData;

    public BlockBreakerT2BE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.BlockBreakerT2BE.get(), pPos, pBlockState);
        poweredMachineData = new PoweredMachineContainerData(this);
    }

    @Override
    public PoweredMachineContainerData getContainerData() {
        return poweredMachineData;
    }

    @Override
    public MachineEnergyStorage getEnergyStorage() {
        return getData(Registration.ENERGYSTORAGE_MACHINES);
    }

    @Override
    public int getStandardEnergyCost() {
        return 500;
    }

    @Override
    public AreaAffectingData getAreaAffectingData() {
        return areaAffectingData;
    }

    @Override
    public FilterBasicHandler getFilterHandler() {
        return getData(Registration.HANDLER_BASIC_FILTER);
    }

    @Override
    public FilterData getFilterData() {
        return filterData;
    }

    @Override
    public FilterData getDefaultFilterData() {
        return new FilterData(false, false, 0);
    }

    @Override
    public void tickServer() {
        super.tickServer();
        chargeItemStack(getMachineHandler().getStackInSlot(0));
    }

    @Override
    public boolean canMine() {
        return hasEnoughPower(getStandardEnergyCost());
    }

    @Override
    public boolean tryBreakBlock(ItemStack tool, FakePlayer fakePlayer, BlockPos breakPos, BlockState blockState) {
        if (extractEnergy(getStandardEnergyCost(), false) < getStandardEnergyCost())
            return false;
        return super.tryBreakBlock(tool, fakePlayer, breakPos, blockState);
    }

    public Direction getFacing() {
        return getDirectionValue();
    }

    @Override
    public List<BlockPos> findBlocksToMine(FakePlayer fakePlayer) {
        AABB area = getAABB(getBlockPos());
        List<BlockPos> blockPositions = new ArrayList<>();
        BlockPos originPos = getBlockPos();

        // Use BlockPos.betweenClosed() to get a stream of positions within the area
        Iterable<BlockPos> positions = BlockPos.betweenClosed(
                (int) area.minX, (int) area.minY, (int) area.minZ,
                (int) area.maxX - 1, (int) area.maxY - 1, (int) area.maxZ - 1
        );

        // Iterate through the positions
        for (BlockPos blockPos : positions) {
            if (isBlockValid(fakePlayer, blockPos)) {
                blockPositions.add(blockPos.immutable());
            }
        }

        // Sort the positions based on distance from the origin position
        blockPositions.sort(Comparator.comparingDouble(pos -> pos.distSqr(originPos)));
        return blockPositions;
    }

    public boolean isBlockValid(FakePlayer fakePlayer, BlockPos blockPos) {
        if (!super.isBlockValid(fakePlayer, blockPos))
            return false; //Do the same checks as normal, then check the filters
        if (filterData.blockItemFilter == 0) { //Block Comparison
            ItemStack blockItemStack = level.getBlockState(blockPos).getCloneItemStack(new BlockHitResult(Vec3.ZERO, Direction.UP, blockPos, false), level, blockPos, fakePlayer);
            return isStackValidFilter(blockItemStack);
        } else { //Item Drop Comparison
            ItemStack tool = getTool();
            List<ItemStack> drops = Block.getDrops(level.getBlockState(blockPos), (ServerLevel) level, blockPos, level.getBlockEntity(blockPos), fakePlayer, tool);
            for (ItemStack drop : drops) {
                if (tool.getItem() instanceof ToggleableTool toggleableTool && toggleableTool.canUseAbility(tool, Ability.SMELTER)) {
                    if (isStackValidFilter(Helpers.getSmeltedItem(level, drop))) return true;
                } else {
                    if (isStackValidFilter(drop)) return true;
                }
            }
        }
        return false;
    }
}
