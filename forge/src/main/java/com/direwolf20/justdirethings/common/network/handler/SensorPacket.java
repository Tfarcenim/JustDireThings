package com.direwolf20.justdirethings.common.network.handler;

import com.direwolf20.justdirethings.common.blockentities.SensorT1BE;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import com.direwolf20.justdirethings.common.network.data.SensorPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SensorPacket {
    public static final SensorPacket INSTANCE = new SensorPacket();

    public static SensorPacket get() {
        return INSTANCE;
    }

    public void handle(final SensorPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player sender = context.player();
            AbstractContainerMenu container = sender.containerMenu;

            if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof SensorT1BE sensor) {
                sensor.setSensorSettings(payload.senseTarget(), payload.strongSignal(), payload.senseCount(), payload.equality());
            }
        });
    }
}
