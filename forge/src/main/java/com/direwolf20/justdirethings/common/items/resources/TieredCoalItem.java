package com.direwolf20.justdirethings.common.items.resources;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class TieredCoalItem extends Item {
    final int mult;
    final int burnTime;
    public TieredCoalItem(int tier) {
        super(new Properties());
        this.mult = (int) Math.pow(2,tier);
        burnTime = (int) (1600 * Math.pow(3,tier));
    }

    //            .add(Registration.Coal_T1.getId(), new FurnaceFuel(4800), false)
    //                .add(Registration.CoalBlock.getId(), new FurnaceFuel(48000), false)
    //                .add(Registration.Coal_T2.getId(), new FurnaceFuel(14400), false)
    //                .add(Registration.CoalBlock_T2.getId(), new FurnaceFuel(144000), false)
    //                .add(Registration.Coal_T3.getId(), new FurnaceFuel(43200), false)
    //                .add(Registration.CoalBlock_T3.getId(), new FurnaceFuel(432000), false)
    //                .add(Registration.Coal_T4.getId(), new FurnaceFuel(129600), false)
    //                .add(Registration.CoalBlock_T4.getId(), new FurnaceFuel(1296000), false)
    //                .add(Registration.CharcoalBlock.getId(), new FurnaceFuel(16000), false);
    //        ;


    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    public int getBurnSpeedMultiplier() {
        return mult;
    }
}
