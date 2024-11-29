package com.direwolf20.justdirethings.common.events;

import com.direwolf20.justdirethings.common.items.tools.basetools.BaseBowItem;
import com.direwolf20.justdirethings.datagen.recipes.FluidDropRecipe;
import com.direwolf20.justdirethings.setup.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EntityEvents {
    public record FluidInputs(BlockState blockState, Item item) {
    }

    static Map<FluidInputs, BlockState> fluidCraftCache = new HashMap<>();

    @SubscribeEvent
    public static void livingUseItem(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity() instanceof Player && event.getItem().getItem() instanceof BaseBowItem baseBowItem) {
            event.setDuration(event.getDuration() - (20 - (int) baseBowItem.getMaxDraw()));
        }
    }

    //@SubscribeEvent
    public static void entityTick(ItemEntity e) {
        Level level = e.level();
        BlockState blockState = e.getBlockStateOn();
        if (!(blockState.getBlock() instanceof LiquidBlock)) return;
        BlockState fluidDropOutput = findRecipe(blockState, e);
        if (!fluidDropOutput.isAir()) {
            BlockPos blockPos = e.blockPosition();
            if (level.setBlockAndUpdate(blockPos, fluidDropOutput)) {
                e.getItem().shrink(1);
                FluidStack fluidStack = new FluidStack(level.getFluidState(blockPos).getType(), 1000);
                FluidType fluidType = fluidStack.getFluid().getFluidType();
                if (fluidType.isVaporizedOnPlacement(level, blockPos, fluidStack)) {
                    level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    fluidType.onVaporize(null, level, blockPos, fluidStack);
                } else {
                    if (!level.isClientSide)
                        level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
    }

    @Nullable
    private static BlockState findRecipe(BlockState blockState, ItemEntity entity) {
        FluidInputs fluidInputs = new FluidInputs(blockState, entity.getItem().getItem());
        if (fluidCraftCache.containsKey(fluidInputs))
            return fluidCraftCache.get(fluidInputs);
        RecipeManager recipeManager = entity.level().getRecipeManager();

        for (FluidDropRecipe recipe : recipeManager.getAllRecipesFor(ModRecipes.FLUID_DROP_RECIPE_TYPE.get())) {
            if (recipe.matches(blockState, entity.getItem())) {
                fluidCraftCache.put(fluidInputs, recipe.getOutput());
                break;
            }
        }
        if (!fluidCraftCache.containsKey(fluidInputs))
            fluidCraftCache.put(fluidInputs, Blocks.AIR.defaultBlockState());
        return fluidCraftCache.get(fluidInputs);
    }

    private static void clearCache() {
        fluidCraftCache.clear();
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent e) {
        clearCache();
    }

    @SubscribeEvent
    public static void onReloadServerResources(AddReloadListenerEvent e) {
        clearCache();
    }

    @SubscribeEvent
    public static void onClientRecipesUpdated(RecipesUpdatedEvent e) {
        clearCache();
    }
}
