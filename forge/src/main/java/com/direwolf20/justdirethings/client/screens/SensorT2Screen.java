package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.SensorScreen;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.NumberButton;
import com.direwolf20.justdirethings.client.screens.widgets.ToggleButton;
import com.direwolf20.justdirethings.common.blockentities.SensorT2BE;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineBE;
import com.direwolf20.justdirethings.common.containers.SensorT2Container;
import com.direwolf20.justdirethings.network.server.C2SSensorPayload;
import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.MagicHelpers;
import com.direwolf20.justdirethings.util.MiscTools;
import com.direwolf20.justdirethings.util.SenseTarget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.*;

public class SensorT2Screen extends SensorScreen<SensorT2BE,SensorT2Container> {
    public int senseAmount;
    public int equality;
    public SensorT2BE sensorT2BE;

    public SensorT2Screen(SensorT2Container container, Inventory inv, Component name) {
        super(container, inv, name);
        if (baseMachineBE instanceof SensorT2BE sensor) {
            this.sensorT2BE = sensor;
            senseTarget = sensor.sense_target;
            strongSignal = sensor.strongSignal;
            blockStateProperties = sensor.blockStateProperties;
            this.senseAmount = sensor.senseAmount;
            this.equality = sensor.equality;
            populateItemStackCache();
        }
    }

    @Override
    public void addFilterButtons() {
        addRenderableWidget(ToggleButtonFactory.ALLOWLISTBUTTON(getGuiLeft() + 8, topSectionTop + 62, filterData.allowlist, b -> {
            filterData.allowlist = !filterData.allowlist;
            saveSettings();
        }));
    }

    @Override
    public void init() {
        super.init();
        addRenderableWidget(ToggleButtonFactory.SENSORTARGETBUTTON(getGuiLeft() + 26, topSectionTop + 62, senseTarget.ordinal(), b -> {

            senseTarget = SenseTarget.values()[((ToggleButton) b).getTexturePosition()];
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.STRONGWEAKREDSTONEBUTTON(getGuiLeft() + 44, topSectionTop + 62, strongSignal ? 1 : 0, b -> {
            strongSignal = ((ToggleButton) b).getTexturePosition() == 1;
            saveSettings();
        }));

        addRenderableWidget(new NumberButton(getGuiLeft() + 122, topSectionTop + 64, 24, 12, senseAmount, 0, 9999, Component.translatable("justdirethings.screen.senseamount"), b -> {
            senseAmount = ((NumberButton) b).getValue(); //The value is updated in the mouseClicked method below
            saveSettings();
        }));

        addRenderableWidget(ToggleButtonFactory.EQUALSBUTTON(getGuiLeft() + 104, topSectionTop + 62, equality, b -> {

            equality = ((ToggleButton) b).getTexturePosition();
            saveSettings();
        }));
    }

    @Override
    public void setTopSection() {
        extraWidth = 60;
        extraHeight = 0;
    }

    @Override
    public void saveSettings() {
        super.saveSettings();
        Services.PLATFORM.sendToServer(new C2SSensorPayload(senseTarget, strongSignal, senseAmount, equality));
    }


    @Override
    public void powerBarTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        if (baseMachineBE instanceof PoweredMachineBE poweredMachineBE) {
            if (MiscTools.inBounds(topSectionLeft + 5, topSectionTop + 5, 18, 72, pX, pY)) {
                if (hasShiftDown())
                    pGuiGraphics.renderTooltip(font, Language.getInstance().getVisualOrder(Arrays.asList(
                            Component.translatable("justdirethings.screen.energy", MagicHelpers.formatted(this.container.getEnergy()), MagicHelpers.formatted(poweredMachineBE.getMaxEnergy())),
                            Component.translatable("justdirethings.screen.energycost", MagicHelpers.withSuffix(sensorT2BE.getEnergyCost()))
                    )), pX, pY);
                else
                    pGuiGraphics.renderTooltip(font, Language.getInstance().getVisualOrder(Arrays.asList(
                            Component.translatable("justdirethings.screen.energy", MagicHelpers.withSuffix(this.container.getEnergy()), MagicHelpers.withSuffix(poweredMachineBE.getMaxEnergy())),
                            Component.translatable("justdirethings.screen.energycost", MagicHelpers.withSuffix(sensorT2BE.getEnergyCost()))
                    )), pX, pY);
            }
        }
    }
}
