package com.direwolf20.justdirethings.common.blocks;

import com.direwolf20.justdirethings.common.blockentities.ParadoxMachineBE;
import com.direwolf20.justdirethings.common.blocks.baseblocks.BaseMachineBlock;
import com.direwolf20.justdirethings.common.containers.ParadoxMachineContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ParadoxMachineBlock extends BaseMachineBlock {
    public ParadoxMachineBlock() {
        super(Properties.of()
                .sound(SoundType.METAL)
                .strength(2.0f)
                .isRedstoneConductor(BaseMachineBlock::never)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ParadoxMachineBE(pos, state);
    }

    @Override
    public void openMenu(ServerPlayer player, BlockPos blockPos) {
        NetworkHooks.openScreen(player,new SimpleMenuProvider(
                (windowId, playerInventory, playerEntity) -> new ParadoxMachineContainer(windowId, playerInventory, blockPos), Component.empty()),blockPos);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandlerItem != null) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (!(blockEntity instanceof ParadoxMachineBE fluidPlacerT1BE)) return InteractionResult.PASS;
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
                    FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainerAndStow(itemStack, fluidPlacerT1BE.getFluidTank(),
                            new InvWrapper(player.getInventory()), Integer.MAX_VALUE, player, true);
                    if (fluidActionResult.isSuccess()) {
                        player.setItemInHand(hand, fluidActionResult.getResult());
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, hand, blockHitResult);
    }

    @Override
    public boolean isValidBE(BlockEntity blockEntity) {
        return blockEntity instanceof ParadoxMachineBE;
    }
}
