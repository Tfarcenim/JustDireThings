package com.direwolf20.justdirethings.common.blocks;

import com.direwolf20.justdirethings.common.blockentities.FluidCollectorT2BE;
import com.direwolf20.justdirethings.common.blocks.baseblocks.BaseMachineBlock;
import com.direwolf20.justdirethings.common.containers.FluidCollectorT2Container;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;

public class FluidCollectorT2 extends BaseMachineBlock {
    public FluidCollectorT2() {
        super(Properties.of()
                .sound(SoundType.METAL)
                .strength(2.0f)
                .isRedstoneConductor(BaseMachineBlock::never)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FluidCollectorT2BE(pos, state);
    }

    @Override
    public void openMenu(Player player, BlockPos blockPos) {
        player.openMenu(new SimpleMenuProvider(
                (windowId, playerInventory, playerEntity) -> new FluidCollectorT2Container(windowId, playerInventory, blockPos), Component.empty()));
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResult.PASS;
        IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandlerItem != null) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity == null) return InteractionResult.PASS;
            IFluidHandler cap = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, blockHitResult.getDirection()).orElse(null);
            if (cap == null) return InteractionResult.PASS;
            if (fluidHandlerItem.getFluidInTank(0).getAmount() < fluidHandlerItem.getTankCapacity(0) && !cap.getFluidInTank(0).isEmpty()) {
                FluidStack testStack = cap.drain(fluidHandlerItem.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE);
                if (testStack.getAmount() > 0) {
                    int amtFit = fluidHandlerItem.fill(testStack, IFluidHandler.FluidAction.SIMULATE);
                    if (amtFit > 0) {
                        FluidStack extractedStack = cap.drain(amtFit, IFluidHandler.FluidAction.EXECUTE);
                        fluidHandlerItem.fill(extractedStack, IFluidHandler.FluidAction.EXECUTE);
                        if (itemStack.getItem() instanceof BucketItem)
                            player.setItemSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, fluidHandlerItem.getContainer());
                        level.playSound(null, blockPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1F, 1.0F);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
                int insertAmt = cap.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE);
                if (insertAmt > 0) {
                    FluidStack extractedStack = fluidHandlerItem.drain(insertAmt, IFluidHandler.FluidAction.EXECUTE);
                    if (!extractedStack.isEmpty()) {
                        cap.fill(extractedStack, IFluidHandler.FluidAction.EXECUTE);
                        if (itemStack.getItem() instanceof BucketItem)
                            player.setItemSlot(hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, fluidHandlerItem.getContainer());
                        level.playSound(null, blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1F, 1.0F);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isValidBE(BlockEntity blockEntity) {
        return blockEntity instanceof FluidCollectorT2BE;
    }
}
