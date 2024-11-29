package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.ModRecipes;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;


public class GooSpreadRecipeBuilder extends AbstractGooRecipeBuilder<BlockState> {

    public GooSpreadRecipeBuilder(BlockState input, BlockState output, int tierRequirement, int craftingDuration) {
        super(input, output, tierRequirement, craftingDuration);
    }

    public static GooSpreadRecipeBuilder shapeless(BlockState input, BlockState output, int tierRequirement, int craftingDuration) {
        return new GooSpreadRecipeBuilder(input, output, tierRequirement, craftingDuration);
    }



    @Override
    public void save(Consumer<FinishedRecipe> pRecipeOutput) {
        this.save(pRecipeOutput, JustDireThings.id(BuiltInRegistries.BLOCK.getKey(this.output.getBlock()).getPath() + "-goospread"));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pRecipeOutput, ResourceLocation pId) {
        //    this.ensureValid(pId);

        pRecipeOutput.accept(new Result(pId,
                this.input,
                this.output,
                this.tierRequirement,
                this.craftingDuration,group,advancement,pId.withPrefix("recipes/" + RecipeCategory.MISC.getFolderName() + "/")));
    }

    public static class Result extends AbstractGooRecipeBuilder.Result<BlockState> {

        public Result(ResourceLocation pId, BlockState input, BlockState output, int tierRequirement, int craftingDuration, String group, Advancement.Builder advancement, ResourceLocation location) {
            super(pId, input, output, tierRequirement, craftingDuration, group, advancement, location);
        }

        @Override
        protected void serializeInput(JsonObject object) {
            object.add("input",MiscHelpers.serializeBlockState(input));
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.GOO_SPREAD_RECIPE_SERIALIZER_TAG.get();
        }
    }
}
