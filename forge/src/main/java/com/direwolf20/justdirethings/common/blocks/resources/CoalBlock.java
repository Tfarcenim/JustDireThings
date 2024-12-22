package com.direwolf20.justdirethings.common.blocks.resources;

import net.minecraft.world.level.block.Block;

public class CoalBlock extends Block {
    private final int mult;

    public CoalBlock(int mult) {
        super(Properties.of()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F)
        );
        this.mult = mult;
    }

    public int getBurnSpeedMultiplier() {
        return mult;
    }
}
