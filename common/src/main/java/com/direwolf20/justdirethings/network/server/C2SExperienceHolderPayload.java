package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SExperienceHolderPayload(
        boolean add,
        int levels
) implements C2SModPacket {

    public C2SExperienceHolderPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SExperienceHolderPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBoolean(add);
        to.writeInt(levels);
    }
}
