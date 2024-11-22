package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SDropperSettingPayload(
        int dropCount,
        int pickupDelay
) implements C2SModPacket {

    public C2SDropperSettingPayload(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SDropperSettingPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(dropCount);
        to.writeInt(pickupDelay);
    }
}
