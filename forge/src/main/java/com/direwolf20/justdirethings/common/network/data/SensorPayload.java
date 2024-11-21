package com.direwolf20.justdirethings.common.network.data;

import com.direwolf20.justdirethings.JustDireThings;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SensorPayload(
        int senseTarget,
        boolean strongSignal,
        int senseCount,
        int equality
) implements CustomPacketPayload {
    public static final Type<SensorPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(JustDireThings.MODID, "sensor_packet"));

    @Override
    public Type<SensorPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, SensorPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SensorPayload::senseTarget,
            ByteBufCodecs.BOOL, SensorPayload::strongSignal,
            ByteBufCodecs.VAR_INT, SensorPayload::senseCount,
            ByteBufCodecs.VAR_INT, SensorPayload::equality,
            SensorPayload::new
    );
}
