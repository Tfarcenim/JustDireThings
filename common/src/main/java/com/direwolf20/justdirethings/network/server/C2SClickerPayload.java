package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SClickerPayload(
        int clickType,
        int clickTarget,
        boolean sneaking,
        boolean showFakePlayer,
        int maxHoldTicks
) implements C2SModPacket {

    public C2SClickerPayload(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readInt(),buf.readBoolean(),buf.readBoolean(),buf.readInt());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SClickerPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(clickType);
        to.writeInt(clickTarget);
        to.writeBoolean(sneaking);
        to.writeBoolean(showFakePlayer);
        to.writeInt(maxHoldTicks);
    }
}
