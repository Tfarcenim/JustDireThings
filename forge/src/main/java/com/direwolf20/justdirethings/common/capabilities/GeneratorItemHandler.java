package com.direwolf20.justdirethings.common.capabilities;

import com.direwolf20.justdirethings.common.MachineItemStackHandler;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

public class GeneratorItemHandler extends MachineItemStackHandler {

    public GeneratorItemHandler(int size, BaseMachineBE baseMachineBE) {
        super(size,baseMachineBE);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return ForgeHooks.getBurnTime(itemStack,RecipeType.SMELTING) > 0;
    }
}
