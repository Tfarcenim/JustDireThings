package com.direwolf20.justdirethings.common.blocks;

import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.blocks.baseblocks.BaseMachineBlock;
import com.direwolf20.justdirethings.common.containers.SensorT1Container;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;

public class SensorT1 extends BaseMachineBlock {
    public SensorT1() {
        super(Properties.of()
                .sound(SoundType.METAL)
                .strength(2.0f)
                .isRedstoneConductor(BaseMachineBlock::never)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SensorT1BE(pos, state);
    }

    @Override
    public void openMenu(Player player, BlockPos blockPos) {
        player.openMenu(new SimpleMenuProvider(
                (windowId, playerInventory, playerEntity) -> new SensorT1Container(windowId, playerInventory, blockPos), Component.translatable("")), (buf -> {
            buf.writeBlockPos(blockPos);
        }));
    }

    @Override
    public boolean isValidBE(BlockEntity blockEntity) {
        return blockEntity instanceof SensorT1BE;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @javax.annotation.Nullable Direction direction) {
        if (direction == (state.getValue(BlockStateProperties.FACING).getOpposite()))
            return false; //Don't emit on facing side
        return true;
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (side == (blockState.getValue(BlockStateProperties.FACING).getOpposite()))
            return 0; //Don't emit on facing side
        BlockEntity blockEntity = blockAccess.getBlockEntity(pos);
        if (blockEntity instanceof SensorT1BE sensorT1BE) {
            return sensorT1BE.emitRedstone ? 15 : 0; // Emit full power if true, no power if false
        }
        return 0;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (side == (blockState.getValue(BlockStateProperties.FACING).getOpposite()))
            return 0; //Don't emit on facing side
        BlockEntity blockEntity = blockAccess.getBlockEntity(pos);
        if (blockEntity instanceof SensorT1BE sensorT1BE && sensorT1BE.strongSignal) {
            return getSignal(blockState, blockAccess, pos, side);
        }
        return 0;
    }
}
