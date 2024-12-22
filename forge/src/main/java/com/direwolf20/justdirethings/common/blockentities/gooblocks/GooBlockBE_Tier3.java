package com.direwolf20.justdirethings.common.blockentities.gooblocks;

import com.direwolf20.justdirethings.common.blockentities.basebe.GooBlockBE;
import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GooBlockBE_Tier3 extends GooBlockBE {
    public GooBlockBE_Tier3(BlockPos pos, BlockState state) {
        super(Registration.GooBlockBE_Tier3.get(), pos, state);
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public int counterReducer() {
        return 5;
    }
}
