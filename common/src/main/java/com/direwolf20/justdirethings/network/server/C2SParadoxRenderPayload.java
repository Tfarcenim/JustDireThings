package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SParadoxRenderPayload(
        boolean renderParadox,
        int targetType
) implements C2SModPacket {

    public C2SParadoxRenderPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SParadoxRenderPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBoolean(renderParadox);
        to.writeInt(targetType);
    }
}
