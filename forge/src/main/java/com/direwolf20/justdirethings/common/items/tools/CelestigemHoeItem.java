package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.AbilityParams;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredTool;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseHoeItem;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;

public class CelestigemHoeItem extends BaseHoeItem implements PoweredTool {
    public CelestigemHoeItem() {
        super(GooTier.CELESTIGEM, -2, -1.0F, new Properties().fireResistant());
        registerAbility(Ability.DROPTELEPORT);
        registerAbility(Ability.HAMMER, new AbilityParams(3, 5, 2));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return isPowerBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return getPowerBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int color = getPowerBarColor(stack);
        if (color == -1)
            return super.getBarColor(stack);
        return color;
    }
}
