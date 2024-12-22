package com.direwolf20.justdirethings.common.blocks.resources;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class GenericBlockItem<B extends Block> extends BlockItem {
    public GenericBlockItem(B pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }
}
