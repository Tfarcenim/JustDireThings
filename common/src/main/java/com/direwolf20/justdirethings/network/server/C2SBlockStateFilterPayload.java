package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SBlockStateFilterPayload(
        int slot,
        CompoundTag compoundTag
) implements C2SModPacket {

    public C2SBlockStateFilterPayload(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readNbt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SBlockStateFilterPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(slot);
        to.writeNbt(compoundTag);
    }
}
