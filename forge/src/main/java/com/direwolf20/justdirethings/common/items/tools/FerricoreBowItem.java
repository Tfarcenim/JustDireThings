package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.tools.basetools.BaseBowItem;

public class FerricoreBowItem extends BaseBowItem {
    public FerricoreBowItem() {
        super(new Properties().durability(250));
        registerAbility(Ability.POTIONARROW);
    }
}
