package com.direwolf20.justdirethings.common.blocks.baseblocks;

import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.RedstoneControlledBE;
import com.direwolf20.justdirethings.common.items.FerricoreWrenchItem;
import com.direwolf20.justdirethings.common.items.MachineSettingsCopierItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseMachineBlock extends Block implements EntityBlock {
    public BaseMachineBlock(Properties properties) {
        super(properties);
    }

    public static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(world, pos, state, entity, stack);
        if (!world.isClientSide && entity instanceof Player player) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BaseMachineBE baseMachineBE) {
                CompoundTag customData1 = stack.getTagElement("custom_data_1");
                if (customData1!=null) {
                        blockEntity.load(customData1);
                }
                baseMachineBE.setPlacedBy(player.getUUID());
            }
        }
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level level, BlockPos blockPos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof MachineSettingsCopierItem || itemStack.getItem() instanceof FerricoreWrenchItem)
            return InteractionResult.PASS;

        BlockEntity te = level.getBlockEntity(blockPos);
        if (!isValidBE(te))
            return InteractionResult.FAIL;

        openMenu((ServerPlayer) player, blockPos);

        return InteractionResult.SUCCESS;
    }

    public abstract void openMenu(ServerPlayer player, BlockPos blockPos);

    public abstract boolean isValidBE(BlockEntity blockEntity);

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return (lvl, pos, blockState, t) -> {
                if (t instanceof BaseMachineBE tile) {
                    tile.tickClient();
                }
            };
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof BaseMachineBE tile) {
                tile.tickServer();
            }
        };
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(blockState, level, pos, blockIn, fromPos, isMoving);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof RedstoneControlledBE redstoneControlledBE) {
            redstoneControlledBE.getRedstoneControlData().checkedRedstone = false;
        }
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @javax.annotation.Nullable Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof RedstoneControlledBE) {
            return true;
        }
        return super.canConnectRedstone(state, level, pos, direction);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (newState.getBlock() != this) {
            BlockEntity blockEntity = worldIn.getBlockEntity(pos);
            if (blockEntity instanceof BaseMachineBE baseMachineBE) {
                IItemHandler iItemHandler = baseMachineBE.getMachineHandler();
                for (int i = 0; i < iItemHandler.getSlots(); ++i) {
                    Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), iItemHandler.getStackInSlot(i));
                }
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder); // Get default drops
        BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);

        if (blockEntity instanceof BaseMachineBE baseMachineBE && !baseMachineBE.isDefaultSettings()) {
            ItemStack itemStack = new ItemStack(Item.byBlock(this));
            CompoundTag compoundTag = new CompoundTag();
            ((BaseMachineBE) blockEntity).saveAdditional(compoundTag);
            if (!compoundTag.isEmpty()) {
                itemStack.getOrCreateTag().put("custom_data_1",compoundTag);
            }
            drops.clear(); // Clear any default drops
            drops.add(itemStack); // Add your custom item stack with NBT data
        }

        return drops;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.FACING, rotation.rotate(state.getValue(BlockStateProperties.FACING)));
    }

    public BlockState direRotate(BlockState blockState, LevelAccessor level, BlockPos pos, Rotation direction) {
        BlockState newState = direRotate(blockState, direction);
        if (level.getBlockEntity(pos) instanceof AreaAffectingBE areaAffectingBE) {
            areaAffectingBE.handleRotate(blockState.getValue(BlockStateProperties.FACING), newState.getValue(BlockStateProperties.FACING));
        }
        return newState;
    }

    public BlockState direRotate(BlockState state, Rotation rotation) {
        DirectionProperty prop = BlockStateProperties.FACING;
        Comparable<?> currentValue = state.getValue(prop);
        List<Direction> directions = prop.getPossibleValues().stream().toList();
        int currentDirectionIndex = directions.indexOf(currentValue);
        int nextDirectionIndex = (currentDirectionIndex + 1) % directions.size();
        Direction nextDirection = directions.get(nextDirectionIndex);
        return state.setValue(prop, nextDirection);

    }
}
