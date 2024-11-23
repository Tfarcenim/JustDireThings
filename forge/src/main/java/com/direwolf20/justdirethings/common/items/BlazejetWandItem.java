package com.direwolf20.justdirethings.common.items;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.AbilityParams;
import com.direwolf20.justdirethings.common.items.interfaces.BaseToggleableToolItem;
import com.direwolf20.justdirethings.common.items.interfaces.LeftClickableTool;

public class BlazejetWandItem extends BaseToggleableToolItem implements LeftClickableTool {
    public BlazejetWandItem() {
        super(new Properties()
                .fireResistant()
                .durability(200));
        registerAbility(Ability.LAVAREPAIR);
        registerAbility(Ability.AIRBURST, new AbilityParams(1, 2, 1, 2));
    }
}
