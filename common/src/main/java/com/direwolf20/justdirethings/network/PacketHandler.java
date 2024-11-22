package com.direwolf20.justdirethings.network;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.network.server.C2SBlockStateFilterPayload;
import com.direwolf20.justdirethings.network.server.C2SAreaAffectingPayload;
import com.direwolf20.justdirethings.network.server.C2SClickerPayload;
import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerServerPacket(C2SAreaAffectingPayload.class, C2SAreaAffectingPayload::new);
        Services.PLATFORM.registerServerPacket(C2SBlockStateFilterPayload.class, C2SBlockStateFilterPayload::new);
        Services.PLATFORM.registerServerPacket(C2SClickerPayload.class, C2SClickerPayload::new);
    }

    public static ResourceLocation packet(Class<?> clazz) {
        return JustDireThings.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
