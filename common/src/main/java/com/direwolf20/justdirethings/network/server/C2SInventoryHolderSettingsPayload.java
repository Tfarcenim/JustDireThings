package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SInventoryHolderSettingsPayload(
        boolean compareNBT,
        boolean filtersOnly,
        boolean compareCounts,
        boolean automatedFiltersOnly,
        boolean automatedCompareCounts,
        boolean renderPlayer,
        int renderedSlot
) implements C2SModPacket {

    public C2SInventoryHolderSettingsPayload(FriendlyByteBuf buf) {
        this(buf.readBoolean(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SInventoryHolderSettingsPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(compareNBT);
        buf.writeBoolean(filtersOnly);
        buf.writeBoolean(compareCounts);
        buf.writeBoolean(automatedFiltersOnly);
        buf.writeBoolean(automatedCompareCounts);
        buf.writeBoolean(renderPlayer);
        buf.writeInt(renderedSlot);
    }
}
