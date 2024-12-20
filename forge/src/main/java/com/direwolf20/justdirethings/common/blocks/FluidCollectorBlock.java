package com.direwolf20.justdirethings.common.blocks;

import com.direwolf20.justdirethings.common.blockentities.FluidCollectorT1BE;
import com.direwolf20.justdirethings.common.blocks.baseblocks.BaseMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class FluidCollectorBlock extends BaseMachineBlock {
    public FluidCollectorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(hand);

        IFluidHandlerItem fluidHandlerItem = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandlerItem != null) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (!(blockEntity instanceof FluidCollectorT1BE fluidPlacerT1BE)) return InteractionResult.PASS;
            IFluidHandler cap = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, blockHitResult.getDirection()).orElse(null);
            if (cap == null) return InteractionResult.PASS;
            if (fluidHandlerItem.getFluidInTank(0).isEmpty()) {
                if (!level.isClientSide) {
                    FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(player.getMainHandItem(), fluidPlacerT1BE.getFluidTank(),
                            new InvWrapper(player.getInventory()), Integer.MAX_VALUE, player, true);
                    if (fluidActionResult.isSuccess()) {
                        player.setItemInHand(InteractionHand.MAIN_HAND, fluidActionResult.getResult());
                    }
                }
            } else {
                if (!level.isClientSide) {
                    FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainerAndStow(stack, fluidPlacerT1BE.getFluidTank(),
                            new InvWrapper(player.getInventory()), Integer.MAX_VALUE, player, true);
                    if (fluidActionResult.isSuccess()) {
                        player.setItemInHand(hand, fluidActionResult.getResult());
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, blockPos, player, hand, blockHitResult);
    }

}
