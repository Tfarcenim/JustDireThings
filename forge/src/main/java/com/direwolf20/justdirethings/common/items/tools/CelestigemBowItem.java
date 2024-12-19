package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredTool;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseBowItem;
import net.minecraft.world.item.ItemStack;

public class CelestigemBowItem extends BaseBowItem implements PoweredTool {
    public CelestigemBowItem() {
        super(new Properties().durability(450).fireResistant(),3);
        registerAbility(Ability.POTIONARROW);
        registerAbility(Ability.SPLASH);
        registerAbility(Ability.LINGERING);
        registerAbility(Ability.HOMING);
    }

    public float getMaxDraw() {
        return 14;
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
