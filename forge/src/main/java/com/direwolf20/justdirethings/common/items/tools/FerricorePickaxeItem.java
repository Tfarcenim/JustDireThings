package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.tools.basetools.BasePickaxeItem;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

public class FerricorePickaxeItem extends BasePickaxeItem {
    public FerricorePickaxeItem() {
        super(GooTier.FERRICORE, 1, -2.8F, new Item.Properties());
        registerAbility(Ability.ORESCANNER);
        registerAbility(Ability.OREMINER);
    }
}
