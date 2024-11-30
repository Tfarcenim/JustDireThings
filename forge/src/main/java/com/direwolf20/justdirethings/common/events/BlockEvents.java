package com.direwolf20.justdirethings.common.events;

import com.direwolf20.justdirethings.common.items.interfaces.ToggleableItem;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableTool;
import com.direwolf20.justdirethings.common.items.tools.BlazegoldHoeItem;
import com.direwolf20.justdirethings.common.items.tools.CelestigemHoeItem;
import com.direwolf20.justdirethings.common.items.tools.EclipseAlloyHoeItem;
import com.direwolf20.justdirethings.common.items.tools.FerricoreHoeItem;
import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockEvents {
    public static boolean alreadyBreaking = false;
    public static BlockPos spawnDropsAtPos = BlockPos.ZERO;

    @SubscribeEvent
    public static void BlockToolModificationEvent(BlockEvent.BlockToolModificationEvent event) {
        ItemStack heldItem = event.getHeldItemStack();
        if (event.getToolAction().equals(ToolActions.HOE_TILL) && heldItem.getItem() instanceof ToggleableItem toggleableItem) {
            if (heldItem.getItem() instanceof FerricoreHoeItem && toggleableItem.getEnabled(heldItem)) {
                BlockState modifiedState = event.getState().getBlock().getToolModifiedState(event.getState(), event.getContext(), event.getToolAction(), true);
                if (modifiedState != null && modifiedState.is(Blocks.FARMLAND))
                    event.setFinalState(Registration.GooSoil_Tier1.get().defaultBlockState());
            } else if (heldItem.getItem() instanceof BlazegoldHoeItem && toggleableItem.getEnabled(heldItem)) {
                BlockState modifiedState = event.getState().getBlock().getToolModifiedState(event.getState(), event.getContext(), event.getToolAction(), true);
                if (modifiedState != null && modifiedState.is(Blocks.FARMLAND))
                    event.setFinalState(Registration.GooSoil_Tier2.get().defaultBlockState());
            } else if (heldItem.getItem() instanceof CelestigemHoeItem && toggleableItem.getEnabled(heldItem)) {
                BlockState modifiedState = event.getState().getBlock().getToolModifiedState(event.getState(), event.getContext(), event.getToolAction(), true);
                if (modifiedState != null && modifiedState.is(Blocks.FARMLAND))
                    event.setFinalState(Registration.GooSoil_Tier3.get().defaultBlockState());
            } else if (heldItem.getItem() instanceof EclipseAlloyHoeItem && toggleableItem.getEnabled(heldItem)) {
                BlockState modifiedState = event.getState().getBlock().getToolModifiedState(event.getState(), event.getContext(), event.getToolAction(), true);
                if (modifiedState != null && modifiedState.is(Blocks.FARMLAND))
                    event.setFinalState(Registration.GooSoil_Tier4.get().defaultBlockState());
            }
        }
    }

    @SubscribeEvent
    public static void BlockBreakEvent(BlockEvent.BreakEvent event) {
        ItemStack itemStack = event.getPlayer().getMainHandItem();
        if (!alreadyBreaking && itemStack.getItem() instanceof ToggleableTool toggleableTool && itemStack.isCorrectToolForDrops(event.getState())) {
            alreadyBreaking = true;
            toggleableTool.mineBlocksAbility(itemStack, event.getPlayer().level(), event.getPos(), event.getPlayer());
            alreadyBreaking = false;
            event.setCanceled(true); //Cancel the original block broken, since its handled in the mineBlocksAbility
        }
    }

    /*@SubscribeEvent
    public static void BlockDrops(BlockDropsEvent event) {
        ItemStack itemStack = event.getTool();
        Entity breaker = event.getBreaker();
        if (alreadyBreaking && itemStack.getItem() instanceof ToggleableTool toggleableTool && breaker instanceof Player player) {
            ServerLevel serverLevel = event.getLevel();
            BlockPos breakPos = event.getPos();
            List<ItemStack> newDrops = new ArrayList<>();
            for (ItemEntity drop : event.getDrops()) {
                newDrops.add(drop.getItem());
            }
            BlockPos spawnAt = spawnDropsAtPos == null || spawnDropsAtPos.equals(BlockPos.ZERO) ? player.blockPosition() : spawnDropsAtPos;
            AbilityMethods.handleDrops(itemStack, serverLevel, spawnAt, player, breakPos, newDrops, event.getState(), event.getDroppedExperience());
            event.getState().spawnAfterBreak(event.getLevel(), event.getPos(), itemStack, false);
            event.setCanceled(true);
        }
    }*///todo
}
