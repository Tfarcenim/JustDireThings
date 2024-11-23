package com.direwolf20.justdirethings.common.containers.slots;

import com.direwolf20.justdirethings.util.ItemHandlerCopySlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class FuelSlot extends ItemHandlerCopySlot {
    public FuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return stack.getBurnTime(RecipeType.SMELTING) > 0;
    }
}
