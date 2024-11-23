package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SPlayerAccessorPayload(
        Direction direction,
        int accessType
) implements C2SModPacket {

    public C2SPlayerAccessorPayload(FriendlyByteBuf buf) {
        this(buf.readEnum(Direction.class),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SPlayerAccessorPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(direction);
        to.writeInt(accessType);
    }
}
