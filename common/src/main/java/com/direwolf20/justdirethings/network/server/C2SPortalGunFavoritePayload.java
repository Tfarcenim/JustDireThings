package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SPortalGunFavoritePayload(
        int favorite,
        boolean staysOpen
) implements C2SModPacket{

    public C2SPortalGunFavoritePayload(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readBoolean());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SPortalGunFavoritePayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(favorite);
        to.writeBoolean(staysOpen);
    }
}
