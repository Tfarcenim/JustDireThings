package com.direwolf20.justdirethings.common.capabilities.providers;

import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemstack;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyItemProvider extends EnergyStorageItemstack implements ICapabilityProvider {
    public EnergyItemProvider(ItemStack itemStack) {
        super(startingCapacity(itemStack), itemStack);
    }

    public static int startingCapacity(ItemStack stack) {
        if (stack.getItem() instanceof PoweredItem poweredItem) {
            return poweredItem.getMaxEnergy();
        }
        return 100000;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> this).cast();
        return LazyOptional.empty();
    }
}
