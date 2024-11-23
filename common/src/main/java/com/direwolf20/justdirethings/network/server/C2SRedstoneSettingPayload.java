package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.MiscHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SRedstoneSettingPayload(
        MiscHelpers.RedstoneMode redstoneMode
) implements C2SModPacket {

    public C2SRedstoneSettingPayload(FriendlyByteBuf buf) {
        this(buf.readEnum(MiscHelpers.RedstoneMode.class));
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SRedstoneSettingPayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(redstoneMode);
    }
}
