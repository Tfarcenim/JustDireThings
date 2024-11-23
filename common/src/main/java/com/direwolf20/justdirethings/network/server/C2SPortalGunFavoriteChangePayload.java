package com.direwolf20.justdirethings.network.server;

import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public record C2SPortalGunFavoriteChangePayload(
        int favorite,
        boolean add,
        String name,
        boolean editing,
        Vec3 coordinates
) implements C2SModPacket {

    public C2SPortalGunFavoriteChangePayload(FriendlyByteBuf buf){
        this(buf.readInt(),buf.readBoolean(),buf.readUtf(),buf.readBoolean(),new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble()));
    }

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handleC2SPortalGunFavoriteChangePayload(player,this);
    }

    @Override
    public void write(FriendlyByteBuf to) {
        to.writeInt(favorite);
        to.writeBoolean(add);
        to.writeUtf(name);
        to.writeBoolean(editing);
        to.writeDouble(coordinates.x);
        to.writeDouble(coordinates.y);
        to.writeDouble(coordinates.z);
    }
}
