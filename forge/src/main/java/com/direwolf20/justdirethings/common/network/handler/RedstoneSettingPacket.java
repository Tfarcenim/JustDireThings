package com.direwolf20.justdirethings.common.network.handler;

import com.direwolf20.justdirethings.common.blockentities.basebe.RedstoneControlledBE;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import com.direwolf20.justdirethings.common.network.data.RedstoneSettingPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RedstoneSettingPacket {
    public static final RedstoneSettingPacket INSTANCE = new RedstoneSettingPacket();

    public static RedstoneSettingPacket get() {
        return INSTANCE;
    }

    public void handle(final RedstoneSettingPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player sender = context.player();
            AbstractContainerMenu container = sender.containerMenu;

            if (container instanceof BaseMachineContainer baseMachineContainer && baseMachineContainer.baseMachineBE instanceof RedstoneControlledBE redstoneControlledBE) {
                redstoneControlledBE.setRedstoneSettings(payload.redstoneMode());
            }
        });
    }
}
