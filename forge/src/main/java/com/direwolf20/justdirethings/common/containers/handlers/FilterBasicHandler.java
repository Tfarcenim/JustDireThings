package com.direwolf20.justdirethings.common.containers.handlers;

import com.direwolf20.justdirethings.common.MachineItemStackHandler;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;

public class FilterBasicHandler extends MachineItemStackHandler {
    public FilterBasicHandler(int size, BaseMachineBE baseMachineBE) {
        super(size,baseMachineBE);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
