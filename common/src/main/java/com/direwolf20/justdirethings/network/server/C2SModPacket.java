package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.network.ModPacket;
import net.minecraft.server.level.ServerPlayer;

public interface C2SModPacket extends ModPacket {

    void handleServer(ServerPlayer player);

}
