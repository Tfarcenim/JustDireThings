package com.direwolf20.justdirethings.util;

import com.direwolf20.justdirethings.JustDireThings;
import net.minecraft.network.chat.Component;

public enum FillMode {
    NONE("none"),
    JDTONLY("jdtonly"),
    ALL("all");

    private final String baseName;

    FillMode(String baseName) {
        this.baseName = baseName;
    }

    public Component getTooltip() {
        return Component.translatable(JustDireThings.MODID + ".fillmode." + baseName);
    }

    public FillMode next() {
        return MiscHelpers.cycle(this);
    }
}
