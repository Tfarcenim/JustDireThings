package com.direwolf20.justdirethings.common.containers;

import com.direwolf20.justdirethings.common.blockentities.BlockSwapperT2BE;
import com.direwolf20.justdirethings.common.containers.basecontainers.BaseMachineContainer;
import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class BlockSwapperT2Container extends BaseMachineContainer<BlockSwapperT2BE> {
    public ContainerData swapperData;

    public BlockSwapperT2Container(int windowId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(windowId, playerInventory, extraData.readBlockPos());
    }

    public BlockSwapperT2Container(int windowId, Inventory playerInventory, BlockPos blockPos) {
        super(Registration.BlockSwapperT2_Container.get(), windowId, playerInventory, blockPos);
        addPlayerSlots(player.getInventory());
        swapperData = baseMachineBE.swapperData;
        addDataSlots(swapperData);
    }

    public int getPartnerExists() {
        return this.swapperData == null ? 0 : this.swapperData.get(0);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, Registration.BlockSwapperT2.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return super.quickMoveStack(playerIn, index); //Only does filter slots!
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
    }
}
