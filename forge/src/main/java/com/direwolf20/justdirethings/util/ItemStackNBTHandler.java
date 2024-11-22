package com.direwolf20.justdirethings.util;

import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemStackNBTHandler implements IItemHandlerModifiable {

    private final ItemStack stack;
    private final String target;
    private final int size;

    public ItemStackNBTHandler(ItemStack stack, String target, int size) {
        this.stack = stack;
        this.target = target;
        this.size = size;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {

    }

    public List<ItemStack> getContents() {
        List<ItemStack> stacks = JustDireDataComponents.getItems(stack,target);
        return stacks;
    }

    @Override
    public int getSlots() {
        return size;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return getContents().get(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return null;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return null;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 0;
    }

    protected void updateContents(List<ItemStack> contents, ItemStack stack, int slot) {
        this.validateSlotIndex(slot);
        // Use the max of the contents slots and the capability slots to avoid truncating
        NonNullList<ItemStack> list = NonNullList.withSize(Math.max(contents.getSlots(), this.getSlots()), ItemStack.EMPTY);
        contents.copyInto(list);
        ItemStack oldStack = list.get(slot);
        list.set(slot, stack);
        this.parent.set(this.component, ItemContainerContents.fromItems(list));
        this.onContentsChanged(slot, oldStack, stack);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.getItem().canFitInsideContainerItems();
    }

    /**
     * Throws {@link UnsupportedOperationException} if the provided slot index is invalid.
     */
    protected final void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= getSlots()) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + getSlots() + ")");
        }
    }
}
