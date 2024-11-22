package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SExperienceHolderSettingsPayload(
        int targetExp,
        boolean ownerOnly,
        boolean collectExp,
        boolean showParticles
) implements C2SModPacket{
public C2SExperienceHolderSettingsPayload(FriendlyByteBuf buf) {
    this(buf.readInt(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean());
}

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SExperienceHolderSettingsPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(targetExp);
        to.writeBoolean(ownerOnly);
        to.writeBoolean(collectExp);
        to.writeBoolean(showParticles);
    }
}
