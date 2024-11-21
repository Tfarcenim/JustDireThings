package com.direwolf20.justdirethings.common.network.data;

import com.direwolf20.justdirethings.JustDireThingsForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ItemCollectorSettingsPayload(
        boolean respectPickupDelay,
        boolean showParticles
) implements CustomPacketPayload {
    public static final Type<ItemCollectorSettingsPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(JustDireThingsForge.MODID, "item_collector_settings"));

    @Override
    public Type<ItemCollectorSettingsPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, ItemCollectorSettingsPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ItemCollectorSettingsPayload::respectPickupDelay,
            ByteBufCodecs.BOOL, ItemCollectorSettingsPayload::showParticles,
            ItemCollectorSettingsPayload::new
    );
}
