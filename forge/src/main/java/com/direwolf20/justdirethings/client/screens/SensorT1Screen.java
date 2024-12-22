package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.SensorScreen;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.ToggleButton;
import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.containers.SensorT1Container;
import com.direwolf20.justdirethings.common.containers.slots.FilterBasicSlot;
import com.direwolf20.justdirethings.network.server.C2SSensorPayload;
import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.SenseTarget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SensorT1Screen extends SensorScreen<SensorT1BE,SensorT1Container>  {



    public SensorT1Screen(SensorT1Container container, Inventory inv, Component name) {
        super(container, inv, name);
        senseTarget = container.baseMachineBE.sense_target;
        strongSignal = container.baseMachineBE.strongSignal;
        blockStateProperties = container.baseMachineBE.blockStateProperties;
        populateItemStackCache();
    }

    @Override
    public void addFilterButtons() {
        addRenderableWidget(ToggleButtonFactory.ALLOWLISTBUTTON(getGuiLeft() + 38, topSectionTop + 38, filterData.allowlist, b -> {
            filterData.allowlist = !filterData.allowlist;
            saveSettings();
        }));
    }

    @Override
    public void init() {
        super.init();
        addRenderableWidget(ToggleButtonFactory.SENSORTARGETBUTTON(getGuiLeft() + 56, topSectionTop + 38, senseTarget.ordinal(), b -> {
            senseTarget = SenseTarget.values()[((ToggleButton) b).getTexturePosition()];
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.STRONGWEAKREDSTONEBUTTON(getGuiLeft() + 20, topSectionTop + 38, strongSignal ? 1 : 0, b -> {
            strongSignal = ((ToggleButton) b).getTexturePosition() == 1;
            saveSettings();
        }));
    }

    @Override
    public void setTopSection() {
        extraWidth = 0;
        extraHeight = 0;
    }

    @Override
    public void saveSettings() {
        super.saveSettings();
        Services.PLATFORM.sendToServer(new C2SSensorPayload(senseTarget, strongSignal, 0, 0));
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        if (hoveredSlot != null && (hoveredSlot instanceof FilterBasicSlot)) {
            if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                List<Component> components = new ArrayList<>();
                ItemStack itemstack = this.hoveredSlot.getItem();
                components.add(Component.translatable("justdirethings.screen.rightclicksettings").withStyle(ChatFormatting.RED));
                components.addAll(this.getTooltipFromContainerItem(itemstack));
                pGuiGraphics.renderTooltip(this.font, components, itemstack.getTooltipImage(), itemstack, pX, pY);
            } else {
                List<FormattedText> components = new ArrayList<>();
                components.add(Component.translatable("justdirethings.screen.rightclicksettings").withStyle(ChatFormatting.RED));
                pGuiGraphics.renderTooltip(this.font, Language.getInstance().getVisualOrder(components), pX, pY);
            }
        } else {
            super.renderTooltip(pGuiGraphics, pX, pY);
        }
    }
}
