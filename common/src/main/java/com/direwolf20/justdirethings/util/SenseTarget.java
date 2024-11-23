package com.direwolf20.justdirethings.util;

public enum SenseTarget {
    BLOCK,
    AIR,
    HOSTILE,
    PASSIVE,
    ADULT,
    CHILD,
    PLAYER,
    LIVING,
    ITEM;

    public SenseTarget next() {
        return MiscHelpers.cycle(this);
    }
}
