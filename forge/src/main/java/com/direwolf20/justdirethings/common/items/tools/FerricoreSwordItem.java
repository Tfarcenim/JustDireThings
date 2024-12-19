package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseSwordItem;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class FerricoreSwordItem extends BaseSwordItem {
    public FerricoreSwordItem() {
        super(GooTier.FERRICORE, 3, -2.0F,new Item.Properties());
        registerAbility(Ability.MOBSCANNER);
    }
}
