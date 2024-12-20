package com.direwolf20.justdirethings.common.blockentities.basebe;

import com.direwolf20.justdirethings.common.blocks.BlockBreakerT1Block;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.direwolf20.justdirethings.util.interfacehelpers.RedstoneControlData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface RedstoneControlledBE {
    RedstoneControlData getRedstoneControlData();

    BlockEntity getBlockEntity();

    default void setRedstoneSettings(MiscHelpers.RedstoneMode redstoneMode) {
        getRedstoneControlData().redstoneMode = redstoneMode;
        if (getBlockEntity() instanceof BaseMachineBE baseMachineBE)
            baseMachineBE.markDirtyClient();
        BlockState blockState = getBlockEntity().getBlockState();
        if (blockState.hasProperty(BlockBreakerT1Block.ACTIVE)) {
            getBlockEntity().getLevel().setBlockAndUpdate(getBlockEntity().getBlockPos(), blockState.setValue(BlockBreakerT1Block.ACTIVE, isActiveRedstoneTestOnly()));
        }
    }

    default void evaluateRedstone() {
        if (!getRedstoneControlData().checkedRedstone) {
            boolean newRedstoneSignal = getBlockEntity().getLevel().hasNeighborSignal(getBlockEntity().getBlockPos());
            if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.PULSE) && !getRedstoneControlData().receivingRedstone && newRedstoneSignal)
                getRedstoneControlData().pulsed = true;
            getRedstoneControlData().receivingRedstone = newRedstoneSignal;
            getRedstoneControlData().checkedRedstone = true;
            BlockState blockState = getBlockEntity().getBlockState();
            if (blockState.hasProperty(BlockBreakerT1Block.ACTIVE)) {
                getBlockEntity().getLevel().setBlockAndUpdate(getBlockEntity().getBlockPos(), blockState.setValue(BlockBreakerT1Block.ACTIVE, isActiveRedstoneTestOnly()));
            }
        }
    }

    default boolean isActiveRedstoneTestOnly() {
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.IGNORED))
            return true;
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.LOW))
            return !getRedstoneControlData().receivingRedstone;
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.HIGH))
            return getRedstoneControlData().receivingRedstone;
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.PULSE) && getRedstoneControlData().pulsed) {
            return true;
        }
        return false;
    }

    default boolean isActiveRedstone() {
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.IGNORED))
            return true;
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.LOW))
            return !getRedstoneControlData().receivingRedstone;
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.HIGH))
            return getRedstoneControlData().receivingRedstone;
        if (getRedstoneControlData().redstoneMode.equals(MiscHelpers.RedstoneMode.PULSE) && getRedstoneControlData().pulsed) {
            getRedstoneControlData().pulsed = false;
            return true;
        }
        return false;
    }

    default void saveRedstoneSettings(CompoundTag tag) {
       getRedstoneControlData().redstoneMode.setMode(tag);
        tag.putBoolean("pulsed", getRedstoneControlData().pulsed);
        tag.putBoolean("receivingRedstone", getRedstoneControlData().receivingRedstone);
    }

    default void loadRedstoneSettings(CompoundTag tag) {
        if (tag.contains(MiscHelpers.RedstoneMode.KEY)) { //Assume all the others are there too...
            getRedstoneControlData().redstoneMode = MiscHelpers.RedstoneMode.getMode(tag);
            getRedstoneControlData().pulsed = tag.getBoolean("pulsed");
            getRedstoneControlData().receivingRedstone = tag.getBoolean("receivingRedstone");
        }
    }
}
