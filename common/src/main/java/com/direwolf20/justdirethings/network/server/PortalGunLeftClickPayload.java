package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record PortalGunLeftClickPayload(

) implements C2SModPacket {



    public static final PortalGunLeftClickPayload INSTANCE = new PortalGunLeftClickPayload();

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SPortalGunLeftClickPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {

    }
}

