package com.direwolf20.justdirethings.common.containers.handlers;

import net.minecraftforge.items.ItemStackHandler;

public class FilterBasicHandler extends ItemStackHandler {
    public FilterBasicHandler(int size) {
        super(size);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
