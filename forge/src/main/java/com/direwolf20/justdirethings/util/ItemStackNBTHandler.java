package com.direwolf20.justdirethings.util;

import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemStackNBTHandler implements IItemHandlerModifiable {

    private final ItemStack holder;
    private final String target;
    private final int size;

    public ItemStackNBTHandler(ItemStack holder, String target, int size) {
        this.holder = holder;
        this.target = target;
        this.size = size;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        validateSlotIndex(slot);
        if (!this.isItemValid(slot, stack)) {
            throw new RuntimeException("Invalid stack " + stack + " for slot " + slot + ")");
        }
        List<ItemStack> contents = this.getContents();
        ItemStack existing = contents.get(slot);
        if (!ItemStack.matches(stack, existing)) {
            this.updateContents(contents, stack, slot);
        }
    }

    public List<ItemStack> getContents() {
        List<ItemStack> stacks = JustDireDataComponents.getItems(holder,target);
        if (stacks == null) {
            return NonNullList.withSize(size,ItemStack.EMPTY);
        }
        return stacks;
    }

    @Override
    public int getSlots() {
        return size;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        List<ItemStack> contents = getContents();
        return contents.get(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack toInsert, boolean simulate) {
        this.validateSlotIndex(slot);

        if (toInsert.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (!this.isItemValid(slot, toInsert)) {
            return toInsert;
        }

        List<ItemStack> contents = this.getContents();
        ItemStack existing = contents.get(slot);
        // Max amount of the stack that could be inserted
        int insertLimit = Math.min(this.getSlotLimit(slot), toInsert.getMaxStackSize());

        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameTags(toInsert, existing)) {
                return toInsert;
            }

            insertLimit -= existing.getCount();
        }

        if (insertLimit <= 0) {
            return toInsert;
        }

        int inserted = Math.min(insertLimit, toInsert.getCount());

        if (!simulate) {
            this.updateContents(contents, toInsert.copyWithCount(existing.getCount() + inserted), slot);
        }

        return toInsert.copyWithCount(toInsert.getCount() - inserted);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        this.validateSlotIndex(slot);

        if (amount == 0) {
            return ItemStack.EMPTY;
        }

        List<ItemStack> contents = this.getContents();
        ItemStack existing = contents.get(slot);

        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (!simulate) {
            this.updateContents(contents, existing.copyWithCount(existing.getCount() - toExtract), slot);
        }

        return existing.copyWithCount(toExtract);
    }


    @Override
    public int getSlotLimit(int slot) {
        return Item.MAX_STACK_SIZE;
    }

    protected void updateContents(List<ItemStack> contents, ItemStack stack, int slot) {
        this.validateSlotIndex(slot);
        // Use the max of the contents slots and the capability slots to avoid truncating
        NonNullList<ItemStack> list = NonNullList.withSize(Math.max(contents.size(), this.getSlots()), ItemStack.EMPTY);
        for (int i = 0; i < list.size();i++) {
            list.set(i,contents.get(i));
        }

        ItemStack oldStack = list.get(slot);
        list.set(slot, stack);
        JustDireDataComponents.setItems(holder,list,target);
        this.onContentsChanged(slot, oldStack, stack);
    }

    protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack stack) {

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
