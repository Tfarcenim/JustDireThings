package com.direwolf20.justdirethings.client.screens.basescreens;

import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SensorScreen<B extends SensorT1BE,C extends BaseMachineContainer<B>> extends BaseMachineScreen<C> {
    public SensorScreen(C container, Inventory pPlayerInventory, Component pTitle) {
        super(container, pPlayerInventory, pTitle);
    }
}
