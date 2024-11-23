package com.direwolf20.justdirethings.common.network.handler;

import com.direwolf20.justdirethings.common.items.PortalGunV2;
import com.direwolf20.justdirethings.network.server.C2SPortalGunFavoriteChangePayload;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.direwolf20.justdirethings.util.NBTHelpers;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PortalGunFavoriteChangePacket {
    public static final PortalGunFavoriteChangePacket INSTANCE = new PortalGunFavoriteChangePacket();

    public static PortalGunFavoriteChangePacket get() {
        return INSTANCE;
    }

    public void handle(final C2SPortalGunFavoriteChangePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player sender = context.player();


        });
    }
}
