package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SAreaAffectingPayload(
        double xRadius, double yRadius, double zRadius,
        int xOffset, int yOffset, int zOffset,
        boolean renderArea
) implements C2SModPacket {

    public C2SAreaAffectingPayload(FriendlyByteBuf buf) {
        this(buf.readDouble(),buf.readDouble(),buf.readDouble(),buf.readInt(),buf.readInt(),buf.readInt(),buf.readBoolean());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SAreaEffectingPayload(player, this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeDouble(xRadius);
        to.writeDouble(yRadius);
        to.writeDouble(zRadius);
        to.writeInt(xOffset);
        to.writeInt(yOffset);
        to.writeInt(zOffset);
        to.writeBoolean(renderArea);
    }
}
