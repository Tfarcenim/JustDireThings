package com.direwolf20.justdirethings.common.items;

import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemstack;
import com.direwolf20.justdirethings.common.items.interfaces.*;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EclipsegateWandItem extends BaseToggleableToolItem implements PoweredItem, LeftClickableTool {
    public EclipsegateWandItem() {
        super(new Properties()
                .durability(200)
                .fireResistant());
        registerAbility(Ability.AIRBURST, new AbilityParams(1, 8, 1, 8));
        registerAbility(Ability.VOIDSHIFT, new AbilityParams(1, 30, 1, 30));
        registerAbility(Ability.ECLIPSEGATE, new AbilityParams(1, 20, 1, 20));
    }

    @Override
    public int getMaxEnergy() {
        return 100000;
    }

    public EnergyStorageItemstack getEnergyStorage(ItemStack stack) {
        return new EnergyStorageItemstack(getMaxEnergy(), stack);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new Provider(stack);
    }

    public class Provider implements ICapabilityProvider {

        private final ItemStack stack;

        public Provider(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> getEnergyStorage(stack)).cast();
            return LazyOptional.empty();
        }
    }
}
