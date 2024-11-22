package com.direwolf20.justdirethings.common.blockentities;

import com.direwolf20.justdirethings.client.particles.itemparticle.ItemFlowParticleData;
import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.FilterableBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.RedstoneControlledBE;
import com.direwolf20.justdirethings.common.containers.handlers.FilterBasicHandler;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.interfacehelpers.AreaAffectingData;
import com.direwolf20.justdirethings.util.interfacehelpers.FilterData;
import com.direwolf20.justdirethings.util.interfacehelpers.RedstoneControlData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

import static net.minecraft.world.entity.Entity.RemovalReason.DISCARDED;

public class ItemCollectorBE extends BaseMachineBE implements FilterableBE, AreaAffectingBE, RedstoneControlledBE {
    protected IItemHandler attachedInventory;
    public FilterData filterData = new FilterData();
    public AreaAffectingData areaAffectingData = new AreaAffectingData(getBlockState().getValue(BlockStateProperties.FACING).getOpposite());
    public RedstoneControlData redstoneControlData = new RedstoneControlData();
    public boolean respectPickupDelay = false;
    public boolean showParticles = true;

    public ItemCollectorBE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.ItemCollectorBE.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public FilterData getFilterData() {
        return filterData;
    }

    @Override
    public RedstoneControlData getRedstoneControlData() {
        return redstoneControlData;
    }

    @Override
    public AreaAffectingData getAreaAffectingData() {
        return areaAffectingData;
    }

    public void tickClient() {
    }

    public void tickServer() {
        super.tickServer();
        findItemsAndStore();
    }

    public void setSettings(boolean respectPickupDelay, boolean showParticles) {
        this.respectPickupDelay = respectPickupDelay;
        this.showParticles = showParticles;
        markDirtyClient();
    }

    @Override
    public FilterBasicHandler getFilterHandler() {
        return getData(Registration.HANDLER_BASIC_FILTER);
    }

    public void doParticles(ItemStack itemStack, Vec3 sourcePos) {
        if (!showParticles)
            return;
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        BlockPos blockPos = getBlockPos();
        ItemFlowParticleData data = new ItemFlowParticleData(itemStack, blockPos.getX() + 0.5f - (0.3 * direction.getStepX()), blockPos.getY() + 0.5f - (0.3 * direction.getStepY()), blockPos.getZ() + 0.5f - (0.3 * direction.getStepZ()), 5);
        double d0 = sourcePos.x();
        double d1 = sourcePos.y();
        double d2 = sourcePos.z();
        ((ServerLevel) level).sendParticles(data, d0, d1, d2, 10, 0, 0, 0, 0);
    }

    private void findItemsAndStore() {
        if (!isActiveRedstone() || !canRun()) return;
        assert level != null;
        AABB searchArea = getAABB(getBlockPos());

        List<ItemEntity> entityList = level.getEntitiesOfClass(ItemEntity.class, searchArea, entity -> true)
                .stream().toList();

        if (entityList.isEmpty()) return;

        IItemHandler handler = getAttachedInventory();

        if (handler == null) return;

        for (ItemEntity itemEntity : entityList) {
            if (respectPickupDelay && itemEntity.hasPickUpDelay())
                continue;
            ItemStack stack = itemEntity.getItem();
            if (stack.isEmpty() || !isStackValidFilter(stack)) continue;
            ItemStack leftover = ItemHandlerHelper.insertItemStacked(handler, stack, false);
            if (leftover.isEmpty()) {
                // If the stack is now empty, remove the ItemEntity from the collection
                doParticles(itemEntity.getItem(), itemEntity.getPosition(0));
                itemEntity.remove(DISCARDED);
            } else {
                // Otherwise, update the ItemEntity with the modified stack
                itemEntity.setItem(leftover);
            }
        }
    }

    private IItemHandler getAttachedInventory() {
        if (attachedInventory == null) {
            BlockState state = level.getBlockState(getBlockPos());
            Direction facing = state.getValue(BlockStateProperties.FACING);
            BlockPos inventoryPos = getBlockPos().relative(facing);
            BlockEntity attachedBE = level.getBlockEntity(inventoryPos);
            if (attachedBE == null)return null;
            attachedInventory = attachedBE.getCapability(ForgeCapabilities.ITEM_HANDLER,
                    facing.getOpposite() // context (The side of the block we're trying to pull/push from?)
            ).orElse(null);
        }
        return attachedInventory;
    }

    @Override
    public AreaAffectingData getDefaultAreaData(AreaAffectingBE areaAffectingBE) {
        return areaAffectingBE.getDefaultAreaData(getBlockState().getValue(BlockStateProperties.FACING).getOpposite());
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("respectPickupDelay", respectPickupDelay);
        tag.putBoolean("showParticles", showParticles);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        respectPickupDelay = tag.getBoolean("respectPickupDelay");
        showParticles = tag.getBoolean("showParticles");
    }
}
