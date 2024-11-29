package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;


public class GooSpreadRecipeTagBuilder extends AbstractGooRecipeBuilder<TagKey<Block>> {


      public GooSpreadRecipeTagBuilder(TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration) {
        super(input, output, tierRequirement, craftingDuration);
    }

    public static GooSpreadRecipeTagBuilder shapeless(TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration) {
        return new GooSpreadRecipeTagBuilder(input, output, tierRequirement, craftingDuration);
    }

    public void save(Consumer<FinishedRecipe> pRecipeOutput) {
        this.save(pRecipeOutput, JustDireThings.id(BuiltInRegistries.BLOCK.getKey(this.output.getBlock()).getPath() + "-goospread_tag"));
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

    public static class Result extends AbstractGooRecipeBuilder.Result<TagKey<Block>> {

        public Result(ResourceLocation pId, TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration, String group, Advancement.Builder build, ResourceLocation location) {
            super(pId, input, output, tierRequirement, craftingDuration, group, build, location);
        }

        @Override
        protected void serializeInput(JsonObject json) {
            json.addProperty("input", input.location().toString());
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.GOO_SPREAD_RECIPE_SERIALIZER_TAG.get();
        }

    }
}
