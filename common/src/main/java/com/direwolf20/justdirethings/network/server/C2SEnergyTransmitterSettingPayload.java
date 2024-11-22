package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.JustDireThings;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record C2SEnergyTransmitterSettingPayload(
        boolean showParticles
) implements C2SModPacket {



    @Override
    public void handleServer(ServerPlayer player) {

    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeBoolean(showParticles);
    }
}
