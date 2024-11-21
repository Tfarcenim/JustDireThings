package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.BaseMachineScreen;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.GrayscaleButton;
import com.direwolf20.justdirethings.common.blockentities.InventoryHolderBE;
import com.direwolf20.justdirethings.common.containers.InventoryHolderContainer;
import com.direwolf20.justdirethings.common.containers.slots.InventoryHolderSlot;
import com.direwolf20.justdirethings.common.network.data.InventoryHolderMoveItemsPayload;
import com.direwolf20.justdirethings.common.network.data.InventoryHolderSaveSlotPayload;
import com.direwolf20.justdirethings.common.network.data.InventoryHolderSettingsPayload;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class InventoryHolderScreen extends BaseMachineScreen<InventoryHolderContainer> {
    private InventoryHolderBE inventoryHolderBE;
    private boolean compareNBT;
    private boolean filtersOnly;
    private boolean compareCounts;
    private boolean automatedFiltersOnly;
    private boolean automatedCompareCounts;
    private boolean renderPlayer;
    private int renderedSlot;

    public InventoryHolderScreen(InventoryHolderContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        if (container.baseMachineBE instanceof InventoryHolderBE inventoryHolderBE) {
            this.inventoryHolderBE = inventoryHolderBE;
            this.compareNBT = inventoryHolderBE.compareNBT;
            this.filtersOnly = inventoryHolderBE.filtersOnly;
            this.compareCounts = inventoryHolderBE.compareCounts;
            this.automatedFiltersOnly = inventoryHolderBE.automatedFiltersOnly;
            this.automatedCompareCounts = inventoryHolderBE.automatedCompareCounts;
            this.renderPlayer = inventoryHolderBE.renderPlayer;
            this.renderedSlot = inventoryHolderBE.renderedSlot;
        }
    }

    @Override
    public void addTickSpeedButton() {
        //No-Op
    }

    @Override
    public void init() {
        super.init();
        addRenderableWidget(ToggleButtonFactory.FILTERONLYBUTTON(getGuiLeft() + 134, topSectionTop + 22, filtersOnly, b -> {
            filtersOnly = !filtersOnly;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.COMPARENBTBUTTON(getGuiLeft() + 152, topSectionTop + 22, compareNBT, b -> {
            compareNBT = !compareNBT;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.COMPARECOUNTSBUTTON(getGuiLeft() + 134, topSectionTop + 4, compareCounts, b -> {
            compareCounts = !compareCounts;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.SEND_INV_BUTTON(getGuiLeft() + 134, topSectionTop + 132, b -> {
            PacketDistributor.sendToServer(new InventoryHolderMoveItemsPayload(0));
        }));
        addRenderableWidget(ToggleButtonFactory.PULL_INV_BUTTON(getGuiLeft() + 26, topSectionTop + 132, b -> {
            PacketDistributor.sendToServer(new InventoryHolderMoveItemsPayload(1));
        }));
        addRenderableWidget(ToggleButtonFactory.SWAP_INV_BUTTON(getGuiLeft() + 152, topSectionTop + 132, b -> {
            PacketDistributor.sendToServer(new InventoryHolderMoveItemsPayload(2));
        }));
        addRenderableWidget(ToggleButtonFactory.FILTERONLYBUTTON(getGuiLeft() + 26, topSectionTop + 22, automatedFiltersOnly, b -> {
            automatedFiltersOnly = !automatedFiltersOnly;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.COMPARECOUNTSBUTTON(getGuiLeft() + 26, topSectionTop + 4, automatedCompareCounts, b -> {
            automatedCompareCounts = !automatedCompareCounts;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));

        addRenderableWidget(ToggleButtonFactory.SHOWFAKEPLAYERBUTTON(getGuiLeft() + 8, topSectionTop + 4, renderPlayer, b -> {
            renderPlayer = !renderPlayer;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
    }

    @Override
    public void setTopSection() {
        extraWidth = 0;
        extraHeight = 24;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        if (slot instanceof InventoryHolderSlot && slot.getItem().isEmpty() && !inventoryHolderBE.filterBasicHandler.getStackInSlot(slot.getSlotIndex()).isEmpty()) {
            ItemStack showStack = inventoryHolderBE.filterBasicHandler.getStackInSlot(slot.getSlotIndex());
            RenderSystem.enableBlend(); // Enable blending
            RenderSystem.defaultBlendFunc(); // Set default blend mode
            guiGraphics.renderFakeItem(showStack, slot.x, slot.y);
            guiGraphics.pose().pushPose();
            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, showStack, slot.x, slot.y);
            guiGraphics.pose().translate(0, 0, -200); //This was needed to make Damage Bars transparent
            guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), slot.x, slot.y, slot.x + 16, slot.y + 16, 0x80888888);
            guiGraphics.pose().popPose();
            RenderSystem.disableBlend();
        } else {
            super.renderSlot(guiGraphics, slot);
        }

        // Draw red border if this is the selected slot (renderedSlot)
        if (slot.getSlotIndex() == renderedSlot && slot instanceof InventoryHolderSlot) {
            // Top border
            guiGraphics.fill(slot.x, slot.y, slot.x + 16, slot.y + 1, 0xFFFF0000);
            // Bottom border
            guiGraphics.fill(slot.x, slot.y + 15, slot.x + 16, slot.y + 16, 0xFFFF0000);
            // Left border
            guiGraphics.fill(slot.x, slot.y, slot.x + 1, slot.y + 16, 0xFFFF0000);
            // Right border
            guiGraphics.fill(slot.x + 15, slot.y, slot.x + 16, slot.y + 16, 0xFFFF0000);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        Slot slot = this.hoveredSlot;
        if (slot instanceof InventoryHolderSlot && slot.getItem().isEmpty() && !inventoryHolderBE.filterBasicHandler.getStackInSlot(slot.getSlotIndex()).isEmpty()) {
            ItemStack itemstack = inventoryHolderBE.filterBasicHandler.getStackInSlot(slot.getSlotIndex());
            guiGraphics.renderTooltip(this.font, this.getTooltipFromContainerItem(itemstack), itemstack.getTooltipImage(), itemstack, x, y);
        } else {
            super.renderTooltip(guiGraphics, x, y);
        }
    }

    @Override
    public void saveSettings() {
        super.saveSettings();
        PacketDistributor.sendToServer(new InventoryHolderSettingsPayload(compareNBT, filtersOnly, compareCounts, automatedFiltersOnly, automatedCompareCounts, renderPlayer, renderedSlot));
    }

    public void renderInventorySection(GuiGraphics guiGraphics, int relX, int relY) {
        guiGraphics.blitSprite(SOCIALBACKGROUND, relX, relY + 83 - 8, this.imageWidth, this.imageHeight - 55); //Inventory Section
    }

    @Override
    public boolean mouseClicked(double x, double y, int btn) {
        if (btn == 0 && Screen.hasControlDown() && hoveredSlot != null && hoveredSlot instanceof InventoryHolderSlot) {
            if (Screen.hasShiftDown()) {
                if (hoveredSlot.getSlotIndex() >= 27 && hoveredSlot.getSlotIndex() <= 35) {
                    renderedSlot = hoveredSlot.getSlotIndex();
                    saveSettings();
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    return true;
                }
            } else {
                PacketDistributor.sendToServer(new InventoryHolderSaveSlotPayload(hoveredSlot.getSlotIndex()));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }
        return super.mouseClicked(x, y, btn);
    }
}
