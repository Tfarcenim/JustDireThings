package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.BaseMachineScreen;
import com.direwolf20.justdirethings.common.containers.FluidCollectorT2Container;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FluidCollectorT2Screen extends BaseMachineScreen<FluidCollectorT2Container> {
    public FluidCollectorT2Screen(FluidCollectorT2Container container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void setTopSection() {
        extraWidth = 60;
        extraHeight = 0;
    }

    public int getFluidBarOffset() {
        return 204;
    }
}
