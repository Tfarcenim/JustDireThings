package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SInventoryHolderMoveItemsPayload(
        int moveType
) implements C2SModPacket {

    public C2SInventoryHolderMoveItemsPayload(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleInventoryHolderMoveItemsPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(moveType);
    }
}
