package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SFilterSettingPayload(
        boolean allowList,
        boolean compareNBT,
        int blockItemFilter
) implements C2SModPacket {

    public C2SFilterSettingPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean(),buf.readBoolean(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SFilterSettingPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBoolean(allowList);
        to.writeBoolean(compareNBT);
        to.writeInt(blockItemFilter);
    }
}
