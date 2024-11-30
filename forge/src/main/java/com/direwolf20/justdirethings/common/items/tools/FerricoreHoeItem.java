package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.tools.basetools.BaseHoeItem;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;

public class FerricoreHoeItem extends BaseHoeItem {
    public FerricoreHoeItem() {
        super(GooTier.FERRICORE, -2, -1.0F,new Item.Properties());
    }
}
