package com.direwolf20.justdirethings.common.containers;

import com.direwolf20.justdirethings.common.blockentities.EnergyTransmitterBE;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class EnergyTransmitterContainer extends BaseMachineContainer<EnergyTransmitterBE> {

    public EnergyTransmitterContainer(int windowId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(windowId, playerInventory, extraData.readBlockPos());
    }

    public EnergyTransmitterContainer(int windowId, Inventory playerInventory, BlockPos blockPos) {
        super(Registration.EnergyTransmitter_Container.get(), windowId, playerInventory, blockPos);
        addPlayerSlots(player.getInventory());
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, Registration.EnergyTransmitter.get());
    }

    @Override
    public boolean clickMenuButton(Player player, int value) {
        Button button = Button.values()[value];
        switch (button) {
            case TOGGLE_PARTICLES ->  {
                baseMachineBE.showParticles = !baseMachineBE.showParticles;
                baseMachineBE.setChanged();
                return true;
            }
        }
        return false;
    }


    public enum Button {
        TOGGLE_PARTICLES
    }
}
