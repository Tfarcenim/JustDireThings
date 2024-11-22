package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public record C2SGhostSlotPayload(
        int slotNumber,
        ItemStack stack,
        int count,
        int mbAmt
) implements C2SModPacket {

    public C2SGhostSlotPayload(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readItem(),buf.readInt(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SGhostSlotPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(slotNumber);
        to.writeItem(stack);
        to.writeInt(count);
        to.writeInt(mbAmt);
    }
}
