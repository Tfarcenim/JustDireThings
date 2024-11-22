package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SLeftClickPayload(
        int clickType, //0 for empty, 1 for block
        boolean mainHand,
        BlockPos blockPos,
        Direction direction,
        int inventorySlot,
        int keyCode, //-1 for left click
        boolean isMouse
) implements C2SModPacket {

    public C2SLeftClickPayload(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readBoolean(),buf.readBlockPos(),buf.readEnum(Direction.class),buf.readInt(),buf.readInt(),buf.readBoolean());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SLeftClickPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(clickType);
        to.writeBoolean(mainHand);
        to.writeBlockPos(blockPos);
        to.writeEnum(direction);
        to.writeInt(inventorySlot);
        to.writeInt(keyCode);
        to.writeBoolean(isMouse);
    }
}
