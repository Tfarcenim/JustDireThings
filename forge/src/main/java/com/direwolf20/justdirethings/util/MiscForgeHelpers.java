package com.direwolf20.justdirethings.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class MiscForgeHelpers {
    public static IItemHandler getAttachedInventory(Level level, BlockPos blockPos, Direction side) {
        if (level == null) return null;
        BlockEntity be = level.getBlockEntity(blockPos);
        // if we have a TE and its an item handler, try extracting from that
        if (be != null) {
            IItemHandler handler = be.getCapability(ForgeCapabilities.ITEM_HANDLER,side).orElse(null);
            return handler;
        }
        return null;
    }
}
