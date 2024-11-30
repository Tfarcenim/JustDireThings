package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.AbilityParams;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredTool;
import com.direwolf20.justdirethings.common.items.tools.basetools.BasePaxelItem;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import net.minecraft.world.item.ItemStack;

public class EclipseAlloyPaxel extends BasePaxelItem implements PoweredTool {
    public EclipseAlloyPaxel() {
        super(GooTier.ECLIPSEALLOY, 1, -2.8F, new Properties().fireResistant());
        registerAbility(Ability.OREXRAY);
        registerAbility(Ability.OREMINER);
        registerAbility(Ability.SKYSWEEPER);
        registerAbility(Ability.LAWNMOWER);
        registerAbility(Ability.TREEFELLER);
        registerAbility(Ability.LEAFBREAKER);
        registerAbility(Ability.SMELTER);
        registerAbility(Ability.HAMMER, new AbilityParams(3, 7, 2));
        registerAbility(Ability.DROPTELEPORT);
        registerAbility(Ability.INSTABREAK);
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

    @Override
    public int getMaxEnergy() {
        return 500000;
    }

}
