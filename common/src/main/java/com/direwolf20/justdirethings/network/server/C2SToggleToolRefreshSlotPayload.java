package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SToggleToolRefreshSlotPayload(
        int slot
) implements C2SModPacket {

    public C2SToggleToolRefreshSlotPayload(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SToggleToolRefreshSlotPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(slot);
    }
}

