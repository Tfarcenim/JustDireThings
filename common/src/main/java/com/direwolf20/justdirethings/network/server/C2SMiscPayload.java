package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public record C2SMiscPayload(
Action action
) implements C2SModPacket {

    public enum Action{
        LEFT_CLICK_PORTAL_GUN,TOOL_SETTINGS_GUI;
    }

    public C2SMiscPayload(FriendlyByteBuf buf) {
        this(buf.readEnum(Action.class));
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SMiscPayload(player,this);
    }

    public static void send(Action action) {
        Services.PLATFORM.sendToServer(new C2SMiscPayload(action));
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeEnum(action);
    }
}

