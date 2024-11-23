package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SToggleToolSlotPayload(
        String settingName,
        int slot,
        int typeTool,
        int value
) implements C2SModPacket {

    public C2SToggleToolSlotPayload(FriendlyByteBuf buf) {
        this(buf.readUtf(),buf.readInt(),buf.readInt(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SToggleToolSlotPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {

    }
}

