package com.direwolf20.justdirethings.common.items.resources;

import net.minecraft.world.item.Item;

public class TieredCoalItem extends Item {
    final int mult;
    public TieredCoalItem(int mult) {
        super(new Properties());
        this.mult = mult;
    }

    public int getBurnSpeedMultiplier() {
        return mult;
    }
}
