package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.SenseTarget;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SSensorPayload(
        SenseTarget senseTarget,
        boolean strongSignal,
        int senseCount,
        int equality
) implements C2SModPacket {

    public C2SSensorPayload(FriendlyByteBuf buf) {
        this(buf.readEnum(SenseTarget.class),buf.readBoolean(),buf.readInt(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SSensorPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(senseTarget);
        to.writeBoolean(strongSignal);
        to.writeInt(senseCount);
        to.writeInt(equality);
    }
}
