package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SToggleToolLeftRightClickPayload(
        int slot,
        String abilityName,
        int button,
        int keyCode,
        boolean isMouse,
        boolean requireEquipped
) implements C2SModPacket {

    public C2SToggleToolLeftRightClickPayload(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readUtf(),buf.readInt(),buf.readInt(),buf.readBoolean(),buf.readBoolean());
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SToggleToolLeftRightClickPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(slot);
        to.writeUtf(abilityName);
        to.writeInt(button);
        to.writeInt(keyCode);
        to.writeBoolean(isMouse);
        to.writeBoolean(requireEquipped);
    }
}
