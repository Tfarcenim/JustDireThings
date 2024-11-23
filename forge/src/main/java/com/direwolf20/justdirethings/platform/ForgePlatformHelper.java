package com.direwolf20.justdirethings.platform;

import com.direwolf20.justdirethings.common.PacketHandlerForge;
import com.direwolf20.justdirethings.common.blockentities.*;
import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.FilterableBE;
import com.direwolf20.justdirethings.common.containers.ExperienceHolderContainer;
import com.direwolf20.justdirethings.common.containers.InventoryHolderContainer;
import com.direwolf20.justdirethings.common.containers.ItemCollectorContainer;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import com.direwolf20.justdirethings.common.containers.slots.FilterBasicSlot;
import com.direwolf20.justdirethings.common.items.MachineSettingsCopier;
import com.direwolf20.justdirethings.common.items.PortalGun;
import com.direwolf20.justdirethings.common.items.PortalGunV2;
import com.direwolf20.justdirethings.common.items.interfaces.LeftClickableTool;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableItem;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableTool;
import com.direwolf20.justdirethings.network.client.S2CModPacket;
import com.direwolf20.justdirethings.network.client.S2CParadoxSyncPayload;
import com.direwolf20.justdirethings.network.server.*;
import com.direwolf20.justdirethings.platform.services.IPlatformHelper;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.direwolf20.justdirethings.util.NBTHelpers;
import com.direwolf20.justdirethings.util.interfacehelpers.FilterData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.Map;
import java.util.function.Function;

import static com.direwolf20.justdirethings.util.MiscTools.getHitResult;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    int i;

    @Override
    public <MSG extends S2CModPacket> void registerClientPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapS2C());
    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapC2S());
    }


    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {
        PacketHandlerForge.sendToClient(msg, player);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        PacketHandlerForge.sendToServer(msg);
    }

    @Override
    public void handleC2SAreaEffectingPayload(ServerPlayer player, C2SAreaAffectingPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof AreaAffectingBE areaAffectingBE) {
            areaAffectingBE.setAreaSettings(payload.xRadius(), payload.yRadius(), payload.zRadius(), payload.xOffset(), payload.yOffset(), payload.zOffset(), payload.renderArea());
        }
    }

    @Override
    public void handleC2SBlockStateFilterPayload(ServerPlayer player, C2SBlockStateFilterPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof SensorT1BE sensor) {
            ListTag listTag = payload.compoundTag().getList("tagList", 10); // 10 for CompoundTag type
            ItemStack stateStack = sensor.getFilterHandler().getStackInSlot(payload.slot());
            Map<Property<?>, Comparable<?>> propertiesList = SensorT1BE.loadBlockStateProperty(listTag, stateStack);
            sensor.addBlockStateProperty(payload.slot(), propertiesList);
        }
    }

    @Override
    public void handleC2SClickerPayload(ServerPlayer player, C2SClickerPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof ClickerT1BE clicker) {
            clicker.setClickerSettings(payload.clickType(), payload.clickTarget(), payload.sneaking(), payload.showFakePlayer(), payload.maxHoldTicks());
        }
    }

    @Override
    public void handleC2SLeftClickPayload(ServerPlayer player, C2SLeftClickPayload payload) {
        ItemStack toggleableItem = ItemStack.EMPTY;
        if (payload.inventorySlot() == -1)
            toggleableItem = ToggleableItem.getToggleableItem(player);
        else
            toggleableItem = player.getInventory().getItem(payload.inventorySlot());
        if (toggleableItem.getItem() instanceof LeftClickableTool && toggleableItem.getItem() instanceof ToggleableTool toggleableTool) {
            if (payload.keyCode() == -1) {//left Click
                InteractionHand hand = payload.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
                if (payload.clickType() == 0) { //Air
                    toggleableTool.useAbility(player.level(), player, hand, false);
                } else if (payload.clickType() == 1) { //Block
                    UseOnContext useoncontext = new UseOnContext(player.level(), player, hand, toggleableItem, new BlockHitResult(Vec3.atCenterOf(payload.blockPos()), payload.direction(), payload.blockPos(), false));
                    toggleableTool.useOnAbility(useoncontext, false);
                }
            } else { //Key Binding
                toggleableTool.useAbility(player.level(), player, toggleableItem, payload.keyCode(), payload.isMouse());
                BlockHitResult blockHitResult = getHitResult(player);
                if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                    UseOnContext useoncontext = new UseOnContext(player.level(), player, InteractionHand.MAIN_HAND, toggleableItem, blockHitResult);
                    toggleableTool.useOnAbility(useoncontext, toggleableItem, payload.keyCode(), payload.isMouse());
                }
            }
        }
    }

    @Override
    public void handleC2SCopyMachineSettingsPayload(ServerPlayer player, C2SCopyMachineSettingsPayload payload) {
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof MachineSettingsCopier))
            itemStack = player.getOffhandItem();
        if (!(itemStack.getItem() instanceof MachineSettingsCopier))
            return;

        MachineSettingsCopier.setSettings(itemStack, payload.area(), payload.offset(), payload.filter(), payload.redstone());
    }

    @Override
    public void handleC2SDirectionSettingPayload(ServerPlayer player, C2SDirectionSettingPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer) {
            baseMachineContainer.baseMachineBE.setDirection(payload.direction());
        }
    }

    @Override
    public void handleC2SDropperSettingPayload(ServerPlayer player, C2SDropperSettingPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof DropperT1BE dropperT1BE) {
            dropperT1BE.setDropperSettings(payload.dropCount(), payload.pickupDelay());
        }
    }

    @Override
    public void handleC2SExperienceHolderPayload(ServerPlayer player, C2SExperienceHolderPayload payload) {
        AbstractContainerMenu container = player.containerMenu;
        if (container instanceof ExperienceHolderContainer experienceHolderContainer && experienceHolderContainer.baseMachineBE instanceof ExperienceHolderBE experienceHolderBE) {
            if (payload.add())
                experienceHolderBE.storeExp(player, payload.levels());
            else
                experienceHolderBE.extractExp(player, payload.levels());
        }
    }

    @Override
    public void handleC2SExperienceHolderSettingsPayload(ServerPlayer player, C2SExperienceHolderSettingsPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof ExperienceHolderContainer experienceHolderContainer && experienceHolderContainer.baseMachineBE instanceof ExperienceHolderBE experienceHolderBE) {
            experienceHolderBE.changeSettings(player, payload.targetExp(), payload.ownerOnly(), payload.collectExp(), payload.showParticles());
        }
    }

    @Override
    public void handleC2SGhostSlotPayload(ServerPlayer player, C2SGhostSlotPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        Slot slot = container.slots.get(payload.slotNumber());
        ItemStack stack = payload.stack();
        stack.setCount(payload.count());
        if (slot instanceof FilterBasicSlot)
            slot.set(stack);

        if (container instanceof BaseMachineContainer baseMachineContainer)
            baseMachineContainer.baseMachineBE.markDirtyClient();
    }

    @Override
    public void handleC2SFilterSettingPayload(ServerPlayer player, C2SFilterSettingPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof FilterableBE filterableBE) {
            filterableBE.setFilterSettings(new FilterData(payload.allowList(), payload.compareNBT(), payload.blockItemFilter()));
        }
    }

    @Override
    public void handleInventoryHolderMoveItemsPayload(ServerPlayer player, C2SInventoryHolderMoveItemsPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof InventoryHolderContainer inventoryHolderContainer) {
            int moveType = payload.moveType();
            if (moveType == 0)
                inventoryHolderContainer.sendAllItemsToMachine();
            else if (moveType == 1)
                inventoryHolderContainer.sendAllItemsToPlayer();
            else if (moveType == 2)
                inventoryHolderContainer.swapItems();
        }
    }

    @Override
    public void handleInventoryHolderSaveSlotPayload(ServerPlayer player, C2SInventoryHolderSaveSlotPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof InventoryHolderBE inventoryHolderBE) {
            inventoryHolderBE.addSavedItem(payload.slot());
        }
    }

    @Override
    public void handleC2SInventoryHolderSettingsPayload(ServerPlayer player, C2SInventoryHolderSettingsPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof InventoryHolderBE inventoryHolderBE) {
            inventoryHolderBE.saveSettings(payload.compareNBT(), payload.filtersOnly(), payload.compareCounts(), payload.automatedFiltersOnly(), payload.automatedCompareCounts(), payload.renderPlayer(), payload.renderedSlot());
        }
    }

    @Override
    public void handleC2SItemCollectorSettingsPayload(ServerPlayer player, C2SItemCollectorSettingsPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof ItemCollectorContainer itemCollectorContainer && itemCollectorContainer.baseMachineBE instanceof ItemCollectorBE itemCollectorBE) {
            itemCollectorBE.setSettings(payload.respectPickupDelay(), payload.showParticles());
        }
    }

    @Override
    public void handleC2SParadoxRenderPayload(ServerPlayer player, C2SParadoxRenderPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof ParadoxMachineBE paradoxMachineBE) {
            paradoxMachineBE.setRenderParadox(payload.renderParadox(), payload.targetType());
        }
    }

    @Override
    public void handleS2CParadoxSyncPayload(S2CParadoxSyncPayload payload) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        BlockEntity blockEntity = level.getBlockEntity(payload.blockPos());
        if (blockEntity instanceof ParadoxMachineBE paradoxMachineBE) {
            paradoxMachineBE.receiveRunTime(payload.runtime());
        }
    }

    @Override
    public void handleC2SPlayerAccessorPayload(ServerPlayer player, C2SPlayerAccessorPayload payload) {
        AbstractContainerMenu container = player.containerMenu;

        if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof PlayerAccessorBE playerAccessorBE) {
            playerAccessorBE.updateSidedInventory(payload.direction(), payload.accessType());
        }
    }

    @Override
    public void handleC2SPortalGunFavoritePayload(ServerPlayer player, C2SPortalGunFavoritePayload payload) {
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof PortalGunV2))
            itemStack = player.getOffhandItem();
        if (!(itemStack.getItem() instanceof PortalGunV2))
            return;

        PortalGunV2.setFavoritePosition(itemStack, payload.favorite());
        PortalGunV2.setStayOpen(itemStack, payload.staysOpen());
    }

    @Override
    public void handleC2SPortalGunLeftClickPayload(ServerPlayer player, PortalGunLeftClickPayload portalGunLeftClickPayload) {
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof PortalGun))
            itemStack = player.getOffhandItem();
        if (!(itemStack.getItem() instanceof PortalGun))
            return;

        PortalGun.spawnProjectile(player.level(), player, itemStack, true);
    }

    @Override
    public void handleC2SPortalGunFavoriteChangePayload(ServerPlayer player, C2SPortalGunFavoriteChangePayload payload) {
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof PortalGunV2))
            itemStack = player.getOffhandItem();
        if (!(itemStack.getItem() instanceof PortalGunV2))
            return;

        Level level = player.level();
        if (!payload.add())
            PortalGunV2.removeFavorite(itemStack, payload.favorite());
        else {
            NBTHelpers.PortalDestination portalDestination = PortalGunV2.getFavorite(itemStack, payload.favorite());
            if (!payload.editing()) {
                Vec3 position = player.position();
                Direction facing = MiscHelpers.getFacingDirection(player);
                portalDestination = new NBTHelpers.PortalDestination(new NBTHelpers.GlobalVec3(level.dimension(), position), facing, payload.name());
                PortalGunV2.addFavorite(itemStack, payload.favorite(), portalDestination);
            } else {
                Vec3 position = payload.coordinates().equals(Vec3.ZERO) ? player.position() : payload.coordinates();
                Direction facing = portalDestination == null || portalDestination.equals(NBTHelpers.PortalDestination.EMPTY) ? MiscHelpers.getFacingDirection(player) : portalDestination.direction();
                ResourceKey<Level> dimension = portalDestination == null || portalDestination.equals(NBTHelpers.PortalDestination.EMPTY) ? level.dimension() : portalDestination.globalVec3().dimension();
                NBTHelpers.PortalDestination newDestination = new NBTHelpers.PortalDestination(new NBTHelpers.GlobalVec3(dimension, position), facing, payload.name());
                PortalGunV2.addFavorite(itemStack, payload.favorite(), newDestination);
            }
        }
    }
}