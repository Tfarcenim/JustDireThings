package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.BaseMachineScreen;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.GrayscaleButton;
import com.direwolf20.justdirethings.common.blockentities.ItemCollectorBE;
import com.direwolf20.justdirethings.common.containers.ItemCollectorContainer;
import com.direwolf20.justdirethings.network.server.C2SItemCollectorSettingsPayload;
import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ItemCollectorScreen extends BaseMachineScreen<ItemCollectorContainer> {
    public boolean respectPickupDelay = false;
    public boolean showParticles = true;
    public ItemCollectorScreen(ItemCollectorContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        respectPickupDelay = container.baseMachineBE.respectPickupDelay;
        showParticles = container.baseMachineBE.showParticles;
    }

    @Override
    public void init() {
        super.init();
        addRenderableWidget(ToggleButtonFactory.RESPECTPICKUPDELAY(getGuiLeft() + 116, topSectionTop + 62, respectPickupDelay, b -> {
            respectPickupDelay = !respectPickupDelay;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.SHOWPARTICLESBUTTON(getGuiLeft() + 98, topSectionTop + 62, showParticles, b -> {
            showParticles = !showParticles;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
    }

    @Override
    public void saveSettings() {
        super.saveSettings();
        Services.PLATFORM.sendToServer(new C2SItemCollectorSettingsPayload(respectPickupDelay, showParticles));
    }
}
