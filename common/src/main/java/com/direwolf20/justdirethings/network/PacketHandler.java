package com.direwolf20.justdirethings.network;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.network.client.S2CParadoxSyncPayload;
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
        Services.PLATFORM.registerServerPacket(C2SDirectionSettingPayload.class, C2SDirectionSettingPayload::new);
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
        Services.PLATFORM.registerServerPacket(C2SMiscPayload.class,C2SMiscPayload::new);
        Services.PLATFORM.registerServerPacket(C2SParadoxRenderPayload.class,C2SParadoxRenderPayload::new);
        Services.PLATFORM.registerServerPacket(C2SPlayerAccessorPayload.class,C2SPlayerAccessorPayload::new);
        Services.PLATFORM.registerServerPacket(C2SPortalGunFavoriteChangePayload.class,C2SPortalGunFavoriteChangePayload::new);
        Services.PLATFORM.registerServerPacket(C2SPortalGunFavoritePayload.class,C2SPortalGunFavoritePayload::new);
        Services.PLATFORM.registerServerPacket(C2SRedstoneSettingPayload.class,C2SRedstoneSettingPayload::new);
        Services.PLATFORM.registerServerPacket(C2SSensorPayload.class,C2SSensorPayload::new);
        Services.PLATFORM.registerServerPacket(C2SSwapperPayload.class,C2SSwapperPayload::new);
        Services.PLATFORM.registerServerPacket(C2STickSpeedPayload.class,C2STickSpeedPayload::new);
        Services.PLATFORM.registerServerPacket(C2SToggleToolLeftRightClickPayload.class,C2SToggleToolLeftRightClickPayload::new);
        Services.PLATFORM.registerServerPacket(C2SToggleToolPayload.class,C2SToggleToolPayload::new);
        Services.PLATFORM.registerServerPacket(C2SToggleToolRefreshSlotPayload.class,C2SToggleToolRefreshSlotPayload::new);
        Services.PLATFORM.registerServerPacket(C2SToggleToolSlotPayload.class,C2SToggleToolSlotPayload::new);

        Services.PLATFORM.registerClientPacket(S2CParadoxSyncPayload.class,S2CParadoxSyncPayload::new);

    }

    public static ResourceLocation packet(Class<?> clazz) {
        return JustDireThings.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}
