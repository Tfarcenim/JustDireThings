package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredTool;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseAxe;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;

public class CelestigemAxe extends BaseAxe implements PoweredTool {
    public CelestigemAxe() {
        super(GooTier.CELESTIGEM, new Properties()
                .attributes(AxeItem.createAttributes(GooTier.CELESTIGEM, 7.0F, -2.5F))
                .fireResistant());
        registerAbility(Ability.TREEFELLER);
        registerAbility(Ability.LEAFBREAKER);
        registerAbility(Ability.SMELTER);
        registerAbility(Ability.DROPTELEPORT);
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
