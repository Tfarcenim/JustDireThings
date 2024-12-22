package com.direwolf20.justdirethings.common.containers.basecontainers;

import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class SensorContainer<B extends SensorT1BE> extends BaseMachineContainer<B> {
    public SensorContainer(@Nullable MenuType<?> menuType, int windowId, Inventory playerInventory, BlockPos blockPos) {
        super(menuType, windowId, playerInventory, blockPos);
    }
}
