package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseAxeItem;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public class FerricoreAxeItem extends BaseAxeItem {
    public FerricoreAxeItem() {
        super(GooTier.FERRICORE, 7.0F, -2.5F,new Item.Properties());
        registerAbility(Ability.TREEFELLER);
        registerAbility(Ability.LEAFBREAKER);
    }
}
