package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.SwapEntityType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SSwapperPayload(
        boolean swapBlocks,
        SwapEntityType swap_entity_type
) implements C2SModPacket {

    public C2SSwapperPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean(),buf.readEnum(SwapEntityType.class));
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SSwapperPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBoolean(swapBlocks);
        to.writeEnum(swap_entity_type);
    }
}
