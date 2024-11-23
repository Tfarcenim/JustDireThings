package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SToggleToolPayload(
        String settingName
) implements C2SModPacket {


    public C2SToggleToolPayload(FriendlyByteBuf buf) {
        this(buf.readUtf());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SToolTogglePayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeUtf(settingName);
    }
}

