package com.direwolf20.justdirethings.client.screens.basescreens;

import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import com.direwolf20.justdirethings.network.server.C2SBlockStateFilterPayload;
import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.MiscTools;
import com.direwolf20.justdirethings.util.SenseTarget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.HashMap;
import java.util.Map;

public abstract class SensorScreen<B extends SensorT1BE,C extends BaseMachineContainer<B>> extends BaseMachineScreen<C> implements SensorScreenInterface{
    public SenseTarget senseTarget;
    public boolean strongSignal;
    public boolean showBlockStates;
    public int blockStateSlot = -1;
    public ItemStack stateItemStack = ItemStack.EMPTY;
    public Map<Integer, Map<Property<?>, Comparable<?>>> blockStateProperties = new HashMap<>();
    public Map<Integer, ItemStack> itemStackCache = new HashMap<>();
    public SensorScreen(C container, Inventory pPlayerInventory, Component pTitle) {
        super(container, pPlayerInventory, pTitle);
    }

    public void saveBlockStateData(int slot) {
        if (!blockStateProperties.containsKey(slot)) return;
        Map<Property<?>, Comparable<?>> props = blockStateProperties.get(slot);
        CompoundTag tag = new CompoundTag();
        ListTag listTag = SensorT1BE.saveBlockStateProperty(props);
        tag.put("tagList", listTag);
        Services.PLATFORM.sendToServer(new C2SBlockStateFilterPayload(slot, tag));
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

    public void validateItemStackCache() {
        for (int i = 0; i < container.FILTER_SLOTS; i++) {
            ItemStack stack = container.filterHandler.getStackInSlot(i);
            ItemStack cachedStack = itemStackCache.get(i);
            if (!ItemStack.isSameItemSameTags(stack, cachedStack)) { //If the stack has changed, clear the props!
                clearStateProperties(i);
                saveBlockStateData(i);
                itemStackCache.put(i, stack);
            }
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
        if (showBlockStates && MiscTools.inBounds(topSectionLeft - 101, topSectionTop, 100, topSectionHeight, mouseX, mouseY))
            return false;
        return super.hasClickedOutside(mouseX, mouseY, guiLeftIn, guiTopIn, mouseButton);
    }

    @Override
    public boolean listVisible() {
        return showBlockStates;
    }

}
