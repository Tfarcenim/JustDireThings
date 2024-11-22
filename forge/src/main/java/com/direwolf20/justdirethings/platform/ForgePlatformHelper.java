package com.direwolf20.justdirethings.platform;

import com.direwolf20.justdirethings.common.PacketHandlerForge;
import com.direwolf20.justdirethings.common.blockentities.ClickerT1BE;
import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import com.direwolf20.justdirethings.network.client.S2CModPacket;
import com.direwolf20.justdirethings.network.server.C2SBlockStateFilterPayload;
import com.direwolf20.justdirethings.network.server.C2SAreaAffectingPayload;
import com.direwolf20.justdirethings.network.server.C2SClickerPayload;
import com.direwolf20.justdirethings.network.server.C2SModPacket;
import com.direwolf20.justdirethings.platform.services.IPlatformHelper;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.Map;
import java.util.function.Function;

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
}