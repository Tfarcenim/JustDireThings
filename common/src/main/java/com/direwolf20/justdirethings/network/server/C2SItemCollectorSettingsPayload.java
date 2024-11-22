package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SItemCollectorSettingsPayload(
        boolean respectPickupDelay,
        boolean showParticles
) implements C2SModPacket {

    public C2SItemCollectorSettingsPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean(),buf.readBoolean());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SItemCollectorSettingsPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBoolean(respectPickupDelay);
        to.writeBoolean(showParticles);
    }
}
