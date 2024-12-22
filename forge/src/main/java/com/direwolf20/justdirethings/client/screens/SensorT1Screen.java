package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.SensorScreen;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.BlockStateScrollList;
import com.direwolf20.justdirethings.client.screens.widgets.ToggleButton;
import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.blockentities.basebe.FilterableBE;
import com.direwolf20.justdirethings.common.containers.SensorT1Container;
import com.direwolf20.justdirethings.common.containers.slots.FilterBasicSlot;
import com.direwolf20.justdirethings.network.server.C2SSensorPayload;
import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.MiscTools;
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

    private BlockStateScrollList scrollPanel;


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

        initList(false);
    }

    protected void initList(boolean refresh) {
        if (refresh) {
            removeWidget(scrollPanel);
        }
        this.scrollPanel = new BlockStateScrollList(this, topSectionLeft - 95, topSectionTop + 5, 90,90);
        addRenderableWidget(scrollPanel);
    }

    @Override
    public void setTopSection() {
        extraWidth = 0;
        extraHeight = 0;
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
        if (showBlockStates && MiscTools.inBounds(topSectionLeft - 101, topSectionTop, 100, topSectionHeight, mouseX, mouseY))
            return false;
        return super.hasClickedOutside(mouseX, mouseY, guiLeftIn, guiTopIn, mouseButton);
    }

    @Override
    public void saveSettings() {
        super.saveSettings();
        Services.PLATFORM.sendToServer(new C2SSensorPayload(senseTarget, strongSignal, 0, 0));
    }



    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        validateItemStackCache();
        if (showBlockStates) {
            //guiGraphics.blit(SOCIALBACKGROUND, topSectionLeft - 100, topSectionTop,0,0, 100, topSectionHeight);
            guiGraphics.blitNineSlicedSized(SOCIALBACKGROUND, topSectionLeft - 100, topSectionTop,100, topSectionHeight,4,236,34,0,0,236,34);
            if (blockStateSlot != -1 && !container.filterHandler.getStackInSlot(blockStateSlot).equals(scrollPanel.getStateStack()))
                refreshStateWindow();
        }
    }

    public void refreshStateWindow() {
        if (!showBlockStates || blockStateSlot == -1) return;
        stateItemStack = container.filterHandler.getStackInSlot(blockStateSlot);
        scrollPanel.setStateStack(stateItemStack);
        scrollPanel.refreshList();
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

    @Override
    public boolean mouseClicked(double x, double y, int btn) {
        if (baseMachineBE instanceof FilterableBE) {
            if (hoveredSlot != null && (hoveredSlot instanceof FilterBasicSlot)) {
                if (btn == 1) {
                    if (showBlockStates) {
                        blockStateSlot = -1;
                        stateItemStack = ItemStack.EMPTY;
                        scrollPanel.setStateStack(ItemStack.EMPTY);
                        this.removeWidget(scrollPanel);
                    } else {
                        blockStateSlot = hoveredSlot.getSlotIndex();
                        stateItemStack = hoveredSlot.getItem();
                        scrollPanel.setStateStack(stateItemStack);
                        this.addRenderableWidget(scrollPanel);
                    }
                    showBlockStates = !showBlockStates;
                    this.scrollPanel.refreshList();
                    return true;
                }
            }
        }
        return super.mouseClicked(x, y, btn);
    }
}
