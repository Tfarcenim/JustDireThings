package com.direwolf20.justdirethings.network;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.network.server.*;
import com.direwolf20.justdirethings.platform.Services;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {
        Services.PLATFORM.registerServerPacket(C2SAreaAffectingPayload.class, C2SAreaAffectingPayload::new);
        Services.PLATFORM.registerServerPacket(C2SBlockStateFilterPayload.class, C2SBlockStateFilterPayload::new);
        Services.PLATFORM.registerServerPacket(C2SClickerPayload.class, C2SClickerPayload::new);
        Services.PLATFORM.registerServerPacket(C2SCopyMachineSettingsPayload.class, C2SCopyMachineSettingsPayload::new);
        Services.PLATFORM.registerServerPacket(C2SDropperSettingPayload.class, C2SDropperSettingPayload::new);
        Services.PLATFORM.registerServerPacket(C2SExperienceHolderPayload.class, C2SExperienceHolderPayload::new);
        Services.PLATFORM.registerServerPacket(C2SExperienceHolderSettingsPayload.class, C2SExperienceHolderSettingsPayload::new);
        Services.PLATFORM.registerServerPacket(C2SFilterSettingPayload.class, C2SFilterSettingPayload::new);
        Services.PLATFORM.registerServerPacket(C2SGhostSlotPayload.class, C2SGhostSlotPayload::new);
        Services.PLATFORM.registerServerPacket(C2SInventoryHolderMoveItemsPayload.class, C2SInventoryHolderMoveItemsPayload::new);
        Services.PLATFORM.registerServerPacket(C2SInventoryHolderSaveSlotPayload.class, C2SInventoryHolderSaveSlotPayload::new);
        Services.PLATFORM.registerServerPacket(C2SInventoryHolderSettingsPayload.class, C2SInventoryHolderSettingsPayload::new);
        Services.PLATFORM.registerServerPacket(C2SItemCollectorSettingsPayload.class,C2SItemCollectorSettingsPayload::new);
        Services.PLATFORM.registerServerPacket(C2SLeftClickPayload.class, C2SLeftClickPayload::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return JustDireThings.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
