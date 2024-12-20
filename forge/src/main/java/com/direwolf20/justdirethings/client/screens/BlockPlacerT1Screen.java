package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.BaseMachineScreen;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.ToggleButton;
import com.direwolf20.justdirethings.common.containers.BlockPlacerT1Container;
import com.direwolf20.justdirethings.network.server.C2SDirectionSettingPayload;
import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.MiscHelpers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BlockPlacerT1Screen extends BaseMachineScreen<BlockPlacerT1Container> {
    public BlockPlacerT1Screen(BlockPlacerT1Container container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void init() {
        super.init();
        addRenderableWidget(ToggleButtonFactory.DIRECTIONBUTTON(getGuiLeft() + 122, topSectionTop + 38, direction, b -> {
            direction = ((ToggleButton) b).getTexturePosition();
            Services.PLATFORM.sendToServer(new C2SDirectionSettingPayload(direction));
        }));
    }

    @Override
    public void setTopSection() {
        extraWidth = 0;
        extraHeight = 0;
    }

    @Override
    public void addRedstoneButtons() {
        addRenderableWidget(ToggleButtonFactory.redstoneButton(getGuiLeft() + 104, topSectionTop + 38, redstoneMode.ordinal(), b -> {
            redstoneMode = MiscHelpers.RedstoneMode.values()[((ToggleButton) b).getTexturePosition()];
            saveSettings();
        }));
    }
}
