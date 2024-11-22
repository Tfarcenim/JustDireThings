package com.direwolf20.justdirethings.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SParadoxMachineSnapshotPayload(

) implements C2SModPacket {


    @Override
    public void handleServer(ServerPlayer player) {

    }

    @Override
    public void write(FriendlyByteBuf to) {

    }
}

