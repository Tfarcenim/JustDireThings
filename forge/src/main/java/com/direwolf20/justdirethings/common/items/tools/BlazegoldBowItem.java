package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.Helpers;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseBowItem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class BlazegoldBowItem extends BaseBowItem {
    public BlazegoldBowItem() {
        super(new Properties().durability(450).fireResistant(),2);
        registerAbility(Ability.POTIONARROW);
        registerAbility(Ability.SPLASH);
        registerAbility(Ability.LAVAREPAIR);
    }

    public float getMaxDraw() {
        return 17;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (canUseAbility(stack, Ability.LAVAREPAIR))
            return Helpers.doLavaRepair(stack, entity);
        return false;
    }
}
