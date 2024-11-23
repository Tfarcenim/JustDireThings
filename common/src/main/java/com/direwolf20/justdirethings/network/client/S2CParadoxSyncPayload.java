package com.direwolf20.justdirethings.network.client;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public record S2CParadoxSyncPayload(
        BlockPos blockPos,
        int runtime
) implements S2CModPacket {

    public S2CParadoxSyncPayload(FriendlyByteBuf buf) {
        this(buf.readBlockPos(),buf.readInt());
    }

    @Override
    public void handleClient() {
        Services.PLATFORM.handleS2CParadoxSyncPayload(this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBlockPos(blockPos);
        to.writeInt(runtime);
    }
}
