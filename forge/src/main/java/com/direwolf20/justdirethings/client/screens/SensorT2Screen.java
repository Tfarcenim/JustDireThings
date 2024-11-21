package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.BaseMachineScreen;
import com.direwolf20.justdirethings.client.screens.basescreens.SensorScreenInterface;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.BlockStateScrollList;
import com.direwolf20.justdirethings.client.screens.widgets.NumberButton;
import com.direwolf20.justdirethings.client.screens.widgets.ToggleButton;
import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.blockentities.SensorT2BE;
import com.direwolf20.justdirethings.common.blockentities.basebe.FilterableBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineBE;
import com.direwolf20.justdirethings.common.containers.SensorT2Container;
import com.direwolf20.justdirethings.common.containers.slots.FilterBasicSlot;
import com.direwolf20.justdirethings.common.network.data.BlockStateFilterPayload;
import com.direwolf20.justdirethings.common.network.data.SensorPayload;
import com.direwolf20.justdirethings.util.MagicHelpers;
import com.direwolf20.justdirethings.util.MiscTools;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class SensorT2Screen extends BaseMachineScreen<SensorT2Container> implements SensorScreenInterface {
    public int senseTarget;
    public boolean strongSignal;
    public boolean showBlockStates;
    public int blockStateSlot = -1;
    private BlockStateScrollList scrollPanel;
    public ItemStack stateItemStack = ItemStack.EMPTY;
    public Map<Integer, Map<Property<?>, Comparable<?>>> blockStateProperties = new HashMap<>();
    public Map<Integer, ItemStack> itemStackCache = new HashMap<>();
    public int senseAmount;
    public int equality;
    public SensorT2BE sensorT2BE;

    public SensorT2Screen(SensorT2Container container, Inventory inv, Component name) {
        super(container, inv, name);
        if (baseMachineBE instanceof SensorT2BE sensor) {
            this.sensorT2BE = sensor;
            senseTarget = sensor.sense_target.ordinal();
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
        addRenderableWidget(ToggleButtonFactory.SENSORTARGETBUTTON(getGuiLeft() + 26, topSectionTop + 62, senseTarget, b -> {
            senseTarget = ((ToggleButton) b).getTexturePosition();
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

        this.scrollPanel = new BlockStateScrollList(this, topSectionLeft - 95, 90, topSectionTop + 5, topSectionTop + topSectionHeight - 10);
    }

    @Override
    public void setTopSection() {
        extraWidth = 60;
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
        PacketDistributor.sendToServer(new SensorPayload(senseTarget, strongSignal, senseAmount, equality));
    }

    public Comparable<?> getValue(Property<?> property) {
        if (!blockStateProperties.containsKey(blockStateSlot)) return null;
        Map<Property<?>, Comparable<?>> props = blockStateProperties.get(blockStateSlot);
        if (!props.containsKey(property)) return null;
        return props.get(property);
    }

    public void setPropertyValue(Property<?> property, Comparable<?> comparable, boolean isAny) {
        Map<Property<?>, Comparable<?>> props = blockStateProperties.getOrDefault(blockStateSlot, new HashMap<>());
        if (isAny) {
            props.remove(property);
        } else {
            props.put(property, comparable);
        }
        blockStateProperties.put(blockStateSlot, props);
        saveBlockStateData(blockStateSlot);
    }

    public void clearStateProperties(int slot) {
        blockStateProperties.put(slot, new HashMap<>());
    }

    public void populateItemStackCache() {
        for (int i = 0; i < container.FILTER_SLOTS; i++) {
            ItemStack stack = container.filterHandler.getStackInSlot(i);
            this.itemStackCache.put(i, stack);
        }
    }

    public void validateItemStackCache() {
        for (int i = 0; i < container.FILTER_SLOTS; i++) {
            ItemStack stack = container.filterHandler.getStackInSlot(i);
            ItemStack cachedStack = itemStackCache.get(i);
            if (!ItemStack.isSameItemSameComponents(stack, cachedStack)) { //If the stack has changed, clear the props!
                clearStateProperties(i);
                saveBlockStateData(i);
                itemStackCache.put(i, stack);
            }
        }
    }

    public void saveBlockStateData(int slot) {
        if (!blockStateProperties.containsKey(slot)) return;
        Map<Property<?>, Comparable<?>> props = blockStateProperties.get(slot);
        CompoundTag tag = new CompoundTag();
        ListTag listTag = SensorT1BE.saveBlockStateProperty(props);
        tag.put("tagList", listTag);
        PacketDistributor.sendToServer(new BlockStateFilterPayload(slot, tag));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        validateItemStackCache();
        if (showBlockStates) {
            guiGraphics.blitSprite(SOCIALBACKGROUND, topSectionLeft - 100, topSectionTop, 100, topSectionHeight);
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
        if (baseMachineBE instanceof FilterableBE filterableBE) {
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
