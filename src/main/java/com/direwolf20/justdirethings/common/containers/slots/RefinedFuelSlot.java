package com.direwolf20.justdirethings.common.containers.slots;

import com.direwolf20.justdirethings.common.fluids.basefluids.RefinedFuel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class RefinedFuelSlot extends SlotItemHandler {
    public RefinedFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;
        IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(Capabilities.FluidHandler.ITEM);
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
