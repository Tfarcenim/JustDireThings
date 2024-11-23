package com.direwolf20.justdirethings.platform.services;

import com.direwolf20.justdirethings.network.client.S2CModPacket;
import com.direwolf20.justdirethings.network.client.S2CParadoxSyncPayload;
import com.direwolf20.justdirethings.network.server.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Function;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    <MSG extends S2CModPacket> void registerClientPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf,MSG> reader);
    <MSG extends C2SModPacket> void registerServerPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf,MSG> reader);
    void sendToClient(S2CModPacket msg, ServerPlayer player);
    void sendToServer(C2SModPacket msg);

    void handleC2SAreaEffectingPayload(ServerPlayer player,C2SAreaAffectingPayload payload);

    void handleC2SBlockStateFilterPayload(ServerPlayer player, C2SBlockStateFilterPayload blockStateFilterPayload);

    void handleC2SClickerPayload(ServerPlayer player, C2SClickerPayload c2SClickerPayload);

    void handleC2SLeftClickPayload(ServerPlayer player, C2SLeftClickPayload c2SLeftClickPayload);

    void handleC2SCopyMachineSettingsPayload(ServerPlayer player, C2SCopyMachineSettingsPayload c2SCopyMachineSettingsPayload);

    void handleC2SDirectionSettingPayload(ServerPlayer player, C2SDirectionSettingPayload c2SDirectionSettingPayload);

    void handleC2SDropperSettingPayload(ServerPlayer player, C2SDropperSettingPayload c2SDropperSettingPayload);

    void handleC2SExperienceHolderPayload(ServerPlayer player, C2SExperienceHolderPayload c2SExperienceHolderPayload);

    void handleC2SExperienceHolderSettingsPayload(ServerPlayer player, C2SExperienceHolderSettingsPayload c2SExperienceHolderSettingsPayload);

    void handleC2SGhostSlotPayload(ServerPlayer player, C2SGhostSlotPayload c2SGhostSlotPayload);

    void handleC2SFilterSettingPayload(ServerPlayer player, C2SFilterSettingPayload c2SFilterSettingPayload);

    void handleInventoryHolderMoveItemsPayload(ServerPlayer player, C2SInventoryHolderMoveItemsPayload c2SInventoryHolderMoveItemsPayload);

    void handleInventoryHolderSaveSlotPayload(ServerPlayer player, C2SInventoryHolderSaveSlotPayload c2SInventoryHolderSaveSlotPayload);

    void handleC2SInventoryHolderSettingsPayload(ServerPlayer player, C2SInventoryHolderSettingsPayload c2SInventoryHolderSettingsPayload);

    void handleC2SItemCollectorSettingsPayload(ServerPlayer player, C2SItemCollectorSettingsPayload c2SItemCollectorSettingsPayload);

    void handleC2SParadoxRenderPayload(ServerPlayer player, C2SParadoxRenderPayload c2SParadoxRenderPayload);

    void handleS2CParadoxSyncPayload(S2CParadoxSyncPayload s2CParadoxSyncPayload);

    void handleC2SPlayerAccessorPayload(ServerPlayer player, C2SPlayerAccessorPayload c2SPlayerAccessorPayload);

    void handleC2SPortalGunFavoritePayload(ServerPlayer player, C2SPortalGunFavoritePayload c2SPortalGunFavoritePayload);

    void handleC2SMiscPayload(ServerPlayer player, C2SMiscPayload miscPayload);

    void handleC2SPortalGunFavoriteChangePayload(ServerPlayer player, C2SPortalGunFavoriteChangePayload c2SPortalGunFavoriteChangePayload);

    void handleC2SRedstoneSettingPayload(ServerPlayer player, C2SRedstoneSettingPayload redstoneSettingPayload);

    void handleC2SSensorPayload(ServerPlayer player, C2SSensorPayload c2SSensorPayload);

    void handleTickSpeedPayload(ServerPlayer player, C2STickSpeedPayload c2STickSpeedPayload);

    void handleC2SSwapperPayload(ServerPlayer player, C2SSwapperPayload c2SSwapperPayload);

    void handleC2SToggleToolLeftRightClickPayload(ServerPlayer player, C2SToggleToolLeftRightClickPayload c2SToggleToolLeftRightClickPayload);

    void handleC2SToolTogglePayload(ServerPlayer player, C2SToggleToolPayload c2SToggleToolPayload);

    void handleC2SToggleToolRefreshSlotPayload(ServerPlayer player, C2SToggleToolRefreshSlotPayload c2SToggleToolRefreshSlotPayload);

    void handleC2SToggleToolSlotPayload(ServerPlayer player, C2SToggleToolSlotPayload c2SToggleToolSlotPayload);
}