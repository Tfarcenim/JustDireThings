package com.direwolf20.justdirethings.common.blockentities;

import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.RedstoneControlledBE;
import com.direwolf20.justdirethings.datagen.JustDireBlockTags;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.FakePlayerUtil;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.direwolf20.justdirethings.util.UsefulFakePlayer;
import com.direwolf20.justdirethings.util.interfacehelpers.RedstoneControlData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.ArrayList;
import java.util.List;

public class ClickerT1BE extends BaseMachineBE implements RedstoneControlledBE {
    public RedstoneControlData redstoneControlData = new RedstoneControlData();
    protected List<BlockPos> positionsToClick = new ArrayList<>();
    protected List<? extends LivingEntity> entitiesToClick = new ArrayList<>();
    public int clickType = 0; //RightClick, 1 == Left Click
    public CLICK_TARGET clickTarget = CLICK_TARGET.BLOCK;
    public boolean sneaking = false;
    public boolean showFakePlayer = false;
    public int maxHoldTicks = 1;

    public enum CLICK_TARGET {
        BLOCK,
        AIR,
        HOSTILE,
        PASSIVE,
        ADULT,
        CHILD,
        PLAYER,
        LIVING;

        public CLICK_TARGET next() {
            CLICK_TARGET[] values = values();
            int nextOrdinal = (this.ordinal() + 1) % values.length;
            return values[nextOrdinal];
        }
    }

    public ClickerT1BE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        MACHINE_SLOTS = 1; //Slot for tool
    }

    public ClickerT1BE(BlockPos pPos, BlockState pBlockState) {
        this(Registration.ClickerT1BE.get(), pPos, pBlockState);
    }

    public void setClickerSettings(int clickType, int clickTarget, boolean sneaking, boolean showFakePlayer, int maxHoldTicks) {
        this.clickType = clickType;
        this.clickTarget = CLICK_TARGET.values()[clickTarget];
        this.sneaking = sneaking;
        this.showFakePlayer = showFakePlayer;
        this.maxHoldTicks = maxHoldTicks;
        positionsToClick.clear(); //Clear any clicks we have queue'd up
        entitiesToClick.clear();
        markDirtyClient();
    }

    @Override
    public RedstoneControlData getRedstoneControlData() {
        return redstoneControlData;
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public void tickClient() {
    }

    @Override
    public void tickServer() {
        super.tickServer();
        doClick();
    }

    public ItemStack getClickStack() {
        return getMachineHandler().getStackInSlot(0);
    }

    public void setClickStack(ItemStack stack) {
        getMachineHandler().setStackInSlot(0, stack);
    }

    public boolean isStackValid(ItemStack itemStack) {
        return true;
    }

    public boolean canClick() {
        return true;
    }

    public boolean clearTrackerIfNeeded(ItemStack itemStack) {
        if (positionsToClick.isEmpty() && entitiesToClick.isEmpty())
            return false;
        if (!isStackValid(itemStack))
            return true;
        if (!canClick())
            return true;
        if (!isActiveRedstone() && !redstoneControlData.redstoneMode.equals(MiscHelpers.RedstoneMode.PULSE))
            return true;
        return false;
    }

    @Override
    public boolean canRun() {
        return (super.canRun() || (clickType == 2 && getUsefulFakePlayer((ServerLevel) level).isUsingItem()));
    }

    public void doClick() {
        ItemStack placeStack = getClickStack();
        if (clearTrackerIfNeeded(placeStack)) {
            positionsToClick.clear();
            entitiesToClick.clear();
            return;
        }
        if (!canClick()) return;
        UsefulFakePlayer fakePlayer = getUsefulFakePlayer((ServerLevel) level);
        if ((clickTarget.equals(CLICK_TARGET.BLOCK) || clickTarget.equals(CLICK_TARGET.AIR))) {
            if (isActiveRedstone() && canRun() && positionsToClick.isEmpty())
                positionsToClick = findSpotsToClick(fakePlayer);
            if (positionsToClick.isEmpty())
                return;
            if (canRun()) {
                //System.out.println("Running");
                BlockPos blockPos = positionsToClick.remove(0);
                clickBlock(placeStack, fakePlayer, blockPos);
            }
        } else {
            if (isActiveRedstone() && canRun() && entitiesToClick.isEmpty())
                entitiesToClick = findEntitiesToClick(getAABB());
            if (entitiesToClick.isEmpty())
                return;
            if (canRun() || (clickType == 2 && fakePlayer.isUsingItem())) {
                LivingEntity entity = entitiesToClick.remove(0);
                clickEntity(placeStack, fakePlayer, entity);
            }
        }
    }

    public InteractionResult clickEntity(ItemStack itemStack, UsefulFakePlayer fakePlayer, LivingEntity entity) {
        fakePlayer.setReach(0.9);
        Direction placing = Direction.values()[direction];
        FakePlayerUtil.setupFakePlayerForUse(fakePlayer, entity.blockPosition(), placing, entity.position(), itemStack, sneaking);
        if (showFakePlayer && level instanceof ServerLevel serverLevel && !itemStack.isEmpty()) {
            fakePlayer.drawParticles(serverLevel, itemStack);
        }
        FakePlayerUtil.FakePlayerResult fakePlayerResult;
        fakePlayerResult = FakePlayerUtil.clickEntityInDirection(fakePlayer, level, entity, clickType, maxHoldTicks);
        setClickStack(fakePlayerResult.returnStack());
        FakePlayerUtil.cleanupFakePlayerFromUse(fakePlayer, fakePlayer.getMainHandItem());
        return fakePlayerResult.interactionResult();
    }

    public InteractionResult clickBlock(ItemStack itemStack, UsefulFakePlayer fakePlayer, BlockPos blockPos) {
        Direction placing = Direction.values()[direction];
        FakePlayerUtil.setupFakePlayerForUse(fakePlayer, blockPos, placing, itemStack, sneaking);
        if (showFakePlayer && level instanceof ServerLevel serverLevel) {
            ItemStack showStack = itemStack.isEmpty() ? new ItemStack(Items.COBBLESTONE) : itemStack;
            fakePlayer.drawParticles(serverLevel, showStack);
        }
        FakePlayerUtil.FakePlayerResult fakePlayerResult = new FakePlayerUtil.FakePlayerResult(InteractionResult.FAIL, itemStack);
        if (!level.getBlockState(blockPos).isAir() && clickTarget.equals(CLICK_TARGET.BLOCK)) {
            fakePlayer.setReach(0.9);
            fakePlayerResult = FakePlayerUtil.clickBlockInDirection(fakePlayer, level, clickType, maxHoldTicks);
        } else if (level.getBlockState(blockPos).isAir() && clickTarget.equals(CLICK_TARGET.AIR)) {
            fakePlayer.setReach(1);
            fakePlayerResult = FakePlayerUtil.rightClickAirInDirection(fakePlayer, level, clickType, maxHoldTicks);
        }
        setClickStack(fakePlayerResult.returnStack());
        FakePlayerUtil.cleanupFakePlayerFromUse(fakePlayer, fakePlayer.getMainHandItem());
        return fakePlayerResult.interactionResult();
    }

    public boolean isBlockPosValid(FakePlayer fakePlayer, BlockPos blockPos) {
        if (!level.mayInteract(fakePlayer, blockPos))
            return false;
        if (level.getBlockState(blockPos).isAir() && clickTarget.equals(CLICK_TARGET.BLOCK))
            return false;
        if (!level.getBlockState(blockPos).isAir() && clickTarget.equals(CLICK_TARGET.AIR))
            return false;
        if (level.getBlockState(blockPos).is(JustDireBlockTags.NO_AUTO_CLICK))
            return false;
        if (!canPlaceAt(level, blockPos, fakePlayer))
            return false;
        return true;
    }

    public List<BlockPos> findSpotsToClick(FakePlayer fakePlayer) {
        List<BlockPos> returnList = new ArrayList<>();
        BlockPos blockPos = getBlockPos().relative(getBlockState().getValue(BlockStateProperties.FACING));
        if (isBlockPosValid(fakePlayer, blockPos))
            returnList.add(blockPos);
        return returnList;
    }

    public AABB getAABB() {
        return new AABB(getBlockPos().relative(getBlockState().getValue(BlockStateProperties.FACING)));
    }

    public List<? extends LivingEntity> findEntitiesToClick(AABB aabb) {
        List<LivingEntity> returnList = new ArrayList<>(level.getEntitiesOfClass(LivingEntity.class, aabb, this::isValidEntity));

        return returnList;
    }

    public boolean isValidEntity(Entity entity) {
        if (clickTarget.equals(CLICK_TARGET.HOSTILE) && !(entity instanceof Monster))
            return false;
        if (((clickTarget.equals(CLICK_TARGET.PASSIVE)) || (clickTarget.equals(CLICK_TARGET.ADULT)) || (clickTarget.equals(CLICK_TARGET.CHILD))) && !(entity instanceof Animal))
            return false;
        if (clickTarget.equals(CLICK_TARGET.ADULT) && (entity instanceof Animal animal) && (animal.isBaby()))
            return false;
        if (clickTarget.equals(CLICK_TARGET.CHILD) && (entity instanceof Animal animal) && !(animal.isBaby()))
            return false;
        if (clickTarget.equals(CLICK_TARGET.PLAYER) && !(entity instanceof Player))
            return false;
        return true;
    }

    @Override
    public boolean isDefaultSettings() {
        if (!super.isDefaultSettings())
            return false;
        if (clickType != 0)
            return false;
        if (!clickTarget.equals(CLICK_TARGET.BLOCK))
            return false;
        if (sneaking)
            return false;
        if (showFakePlayer)
            return false;
        if (maxHoldTicks != 1)
            return false;
        return true;
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("clickType", clickType);
        tag.putInt("clickTarget", clickTarget.ordinal());
        tag.putBoolean("sneaking", sneaking);
        tag.putBoolean("showFakePlayer", showFakePlayer);
        tag.putInt("maxHoldTicks", maxHoldTicks);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        this.clickType = tag.getInt("clickType");
        this.clickTarget = CLICK_TARGET.values()[tag.getInt("clickTarget")];
        this.sneaking = tag.getBoolean("sneaking");
        this.showFakePlayer = tag.getBoolean("showFakePlayer");
        if (tag.contains("maxHoldTicks"))
            maxHoldTicks = tag.getInt("maxHoldTicks");
        super.loadAdditional(tag, provider);
    }
}
