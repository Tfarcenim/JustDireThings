package com.direwolf20.justdirethings.common.network;

public class PacketHandler {
   /* public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(JustDireThings.MODID);

        //Going to Server
        registrar.playToServer(C2SAreaAffectingPayload.TYPE, C2SAreaAffectingPayload.STREAM_CODEC, AreaAffectingPacket.get()::handle);
        registrar.playToServer(C2SBlockStateFilterPayload.TYPE, C2SBlockStateFilterPayload.STREAM_CODEC, BlockStateFilterPacket.get()::handle);
        registrar.playToServer(C2SClickerPayload.TYPE, C2SClickerPayload.STREAM_CODEC, ClickerPacket.get()::handle);
        registrar.playToServer(C2SCopyMachineSettingsPayload.TYPE, C2SCopyMachineSettingsPayload.STREAM_CODEC, CopyMachineSettingsPacket.get()::handle);
        registrar.playToServer(C2SDirectionSettingPayload.TYPE, C2SDirectionSettingPayload.STREAM_CODEC, DirectionSettingPacket.get()::handle);
        registrar.playToServer(C2SDropperSettingPayload.TYPE, C2SDropperSettingPayload.STREAM_CODEC, DropperSettingPacket.get()::handle);
        registrar.playToServer(C2SEnergyTransmitterSettingPayload.TYPE, C2SEnergyTransmitterSettingPayload.STREAM_CODEC, EnergyTransmitterPacket.get()::handle);
        registrar.playToServer(C2SFilterSettingPayload.TYPE, C2SFilterSettingPayload.STREAM_CODEC, FilterSettingPacket.get()::handle);
        registrar.playToServer(C2SGhostSlotPayload.TYPE, C2SGhostSlotPayload.STREAM_CODEC, GhostSlotPacket.get()::handle);
        registrar.playToServer(C2SLeftClickPayload.TYPE, C2SLeftClickPayload.STREAM_CODEC, LeftClickPacket.get()::handle);
        registrar.playToServer(C2SPlayerAccessorPayload.TYPE, C2SPlayerAccessorPayload.STREAM_CODEC, PlayerAccessorPacket.get()::handle);
        registrar.playToServer(C2SPortalGunFavoritePayload.TYPE, C2SPortalGunFavoritePayload.STREAM_CODEC, PortalGunFavoritePacket.get()::handle);
        registrar.playToServer(C2SPortalGunFavoriteChangePayload.TYPE, C2SPortalGunFavoriteChangePayload.STREAM_CODEC, PortalGunFavoriteChangePacket.get()::handle);
        registrar.playToServer(PortalGunLeftClickPayload.TYPE, PortalGunLeftClickPayload.STREAM_CODEC, PortalGunLeftClickPacket.get()::handle);
        registrar.playToServer(RedstoneSettingPayload.TYPE, RedstoneSettingPayload.STREAM_CODEC, RedstoneSettingPacket.get()::handle);
        registrar.playToServer(SensorPayload.TYPE, SensorPayload.STREAM_CODEC, SensorPacket.get()::handle);
        registrar.playToServer(SwapperPayload.TYPE, SwapperPayload.STREAM_CODEC, SwapperPacket.get()::handle);
        registrar.playToServer(TickSpeedPayload.TYPE, TickSpeedPayload.STREAM_CODEC, TickSpeedPacket.get()::handle);
        registrar.playToServer(ToggleToolLeftRightClickPayload.TYPE, ToggleToolLeftRightClickPayload.STREAM_CODEC, ToggleToolLeftRightClickPacket.get()::handle);
        registrar.playToServer(ToggleToolPayload.TYPE, ToggleToolPayload.STREAM_CODEC, ToggleToolPacket.get()::handle);
        registrar.playToServer(ToggleToolSlotPayload.TYPE, ToggleToolSlotPayload.STREAM_CODEC, ToggleToolSlotPacket.get()::handle);
        registrar.playToServer(ToggleToolRefreshSlots.TYPE, ToggleToolRefreshSlots.STREAM_CODEC, ToggleToolRefreshSlotsPacket.get()::handle);
        registrar.playToServer(C2SParadoxMachineSnapshotPayload.TYPE, C2SParadoxMachineSnapshotPayload.STREAM_CODEC, ParadoxSnapshotPacket.get()::handle);
        registrar.playToServer(C2SParadoxRenderPayload.TYPE, C2SParadoxRenderPayload.STREAM_CODEC, ParadoxRenderPacket.get()::handle);
        registrar.playToServer(C2SInventoryHolderSaveSlotPayload.TYPE, C2SInventoryHolderSaveSlotPayload.STREAM_CODEC, InventoryHolderSaveSlotPacket.get()::handle);
        registrar.playToServer(C2SInventoryHolderSettingsPayload.TYPE, C2SInventoryHolderSettingsPayload.STREAM_CODEC, InventoryHolderSettingsPacket.get()::handle);
        registrar.playToServer(C2SInventoryHolderMoveItemsPayload.TYPE, C2SInventoryHolderMoveItemsPayload.STREAM_CODEC, InventoryHolderMoveItemsPacket.get()::handle);
        registrar.playToServer(C2SExperienceHolderPayload.TYPE, C2SExperienceHolderPayload.STREAM_CODEC, ExperienceHolderPacket.get()::handle);
        registrar.playToServer(C2SExperienceHolderSettingsPayload.TYPE, C2SExperienceHolderSettingsPayload.STREAM_CODEC, ExperienceHolderSettingsPacket.get()::handle);
        registrar.playToServer(ToolSettingsGUIPayload.TYPE, ToolSettingsGUIPayload.STREAM_CODEC, ToolSettingsGUIPacket.get()::handle);
        registrar.playToServer(C2SItemCollectorSettingsPayload.TYPE, C2SItemCollectorSettingsPayload.STREAM_CODEC, ItemCollectorSettingsPacket.get()::handle);

        //Going to Client
        registrar.playToClient(ClientSoundPayload.TYPE, ClientSoundPayload.STREAM_CODEC, ClientSoundPacket.get()::handle);
        registrar.playToClient(S2CParadoxSyncPayload.TYPE, S2CParadoxSyncPayload.STREAM_CODEC, ParadoxSyncPacket.get()::handle);
    }*/
}
