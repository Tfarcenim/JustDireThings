package com.direwolf20.justdirethings.util;

public enum SwapEntityType {
    NONE,
    HOSTILE,
    PASSIVE,
    ADULT,
    CHILD,
    PLAYER,
    LIVING,
    ITEM,
    ALL;

    public SwapEntityType next() {
       return MiscHelpers.cycle(this);
    }
}
