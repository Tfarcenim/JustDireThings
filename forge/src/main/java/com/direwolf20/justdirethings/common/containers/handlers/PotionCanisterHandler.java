package com.direwolf20.justdirethings.common.containers.handlers;

import com.direwolf20.justdirethings.common.items.PotionCanister;
import com.direwolf20.justdirethings.util.ItemStackNBTHandler;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;

public class PotionCanisterHandler extends ItemStackNBTHandler {
    private final ItemStack potionStack;

    public PotionCanisterHandler(ItemStack parent, String component, int size) {
        super(parent, component, size);
        potionStack = parent;
    }

    @Override
    protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
        if (!newStack.isEmpty() && newStack.getItem() instanceof PotionItem) {
            PotionCanister.attemptFill(potionStack);
        }
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.getItem() instanceof PotionItem || stack.getItem() instanceof BottleItem || stack.isEmpty();
    }
}
