package com.direwolf20.justdirethings.common.capabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;

public class GeneratorItemHandler extends ItemStackHandler {
    public GeneratorItemHandler() {
        super(1);
    }

    public GeneratorItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return ForgeHooks.getBurnTime(itemStack,RecipeType.SMELTING) > 0;
    }
}
