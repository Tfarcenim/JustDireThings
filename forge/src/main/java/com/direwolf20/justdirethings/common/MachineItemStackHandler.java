package com.direwolf20.justdirethings.common;

import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import net.minecraftforge.items.ItemStackHandler;

public class MachineItemStackHandler extends ItemStackHandler {

    private final BaseMachineBE baseMachineBE;

    public MachineItemStackHandler(int slots, BaseMachineBE baseMachineBE) {
        super(slots);
        this.baseMachineBE = baseMachineBE;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (!baseMachineBE.getLevel().isClientSide) {
            baseMachineBE.setChanged();
        }
    }
}
