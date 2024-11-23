package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2STickSpeedPayload(
        int tickSpeed
) implements C2SModPacket {

    public C2STickSpeedPayload(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleTickSpeedPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(tickSpeed);
    }
}
