package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SCopyMachineSettingsPayload(
        boolean area, boolean offset,
        boolean filter, boolean redstone
) implements C2SModPacket {

    public C2SCopyMachineSettingsPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SCopyMachineSettingsPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBoolean(area);
        to.writeBoolean(offset);
        to.writeBoolean(filter);
        to.writeBoolean(redstone);
    }
}
