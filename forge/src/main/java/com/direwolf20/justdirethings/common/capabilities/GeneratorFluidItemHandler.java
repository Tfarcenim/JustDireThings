package com.direwolf20.justdirethings.common.capabilities;

import com.direwolf20.justdirethings.common.fluids.basefluids.RefinedFuel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemStackHandler;

public class GeneratorFluidItemHandler extends ItemStackHandler {
    public GeneratorFluidItemHandler() {
        super(1);
    }

    public GeneratorFluidItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;
        IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandlerItem == null)
            return false;
        FluidStack fluidStack = fluidHandlerItem.drain(1000, IFluidHandler.FluidAction.SIMULATE);
        if (fluidStack.getAmount() == 0)
            return false;
        if (!(fluidStack.getFluid() instanceof RefinedFuel))
            return false;
        return true;
    }
}
