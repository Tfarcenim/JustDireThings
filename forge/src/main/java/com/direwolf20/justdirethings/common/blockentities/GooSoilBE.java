package com.direwolf20.justdirethings.common.blockentities;

import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.NBTHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class GooSoilBE extends BlockEntity {
    private NBTHelpers.BoundInventory boundInventory;
    protected IItemHandler attachedInventory;

    public GooSoilBE(BlockPos pos, BlockState state) {
        super(Registration.GooSoilBE.get(), pos, state);
    }

    public void bindInventory(NBTHelpers.BoundInventory boundInventory) {
        this.boundInventory = boundInventory;
        this.setChanged();
    }

    public IItemHandler getAttachedInventory(ServerLevel serverLevel) {
        if (boundInventory == null) return null;
        if (attachedInventory == null) {
            ServerLevel boundLevel = serverLevel.getServer().getLevel(boundInventory.globalPos().dimension());
            if (boundLevel == null) return null;
            BlockEntity attachedBE = boundLevel.getBlockEntity(boundInventory.globalPos().pos());
            if (attachedBE == null) return null;

            attachedInventory = attachedBE.getCapability(ForgeCapabilities.ITEM_HANDLER,boundInventory.direction()).orElse(null);
        }
        return attachedInventory;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (boundInventory != null) {
            tag.put("boundinventory", boundInventory.toNBT());
        }
    }

    public void load(CompoundTag tag) {
        if (tag.contains("boundinventory"))
            boundInventory = NBTHelpers.BoundInventory.fromNBT(tag.getCompound("boundinventory"));
        super.load(tag);
    }
}
