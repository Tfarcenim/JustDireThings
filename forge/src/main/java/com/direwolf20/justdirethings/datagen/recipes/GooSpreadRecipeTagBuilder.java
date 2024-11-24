package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.function.Consumer;


public class GooSpreadRecipeTagBuilder implements RecipeBuilder {
    @Nullable
    private String group;

    protected final TagKey<Block> input;
    protected final BlockState output;
    protected final int tierRequirement;
    protected final int craftingDuration;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public GooSpreadRecipeTagBuilder(TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration) {
        this.input = input;
        this.output = output;
        this.tierRequirement = tierRequirement;
        this.craftingDuration = craftingDuration;
    }

    public static GooSpreadRecipeTagBuilder shapeless(TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration) {
        return new GooSpreadRecipeTagBuilder(input, output, tierRequirement, craftingDuration);
    }

    @Override
    public RecipeBuilder unlockedBy(String p_176496_, CriterionTriggerInstance p_176497_) {
        return this;
    }

    public GooSpreadRecipeTagBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return ItemStack.EMPTY.getItem();
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



    public static class Result implements FinishedRecipe {

        private final ResourceLocation pId;
        private final TagKey<Block> input;
        private final BlockState output;
        private final int tierRequirement;
        private final int craftingDuration;
        private final String group;
        private final Advancement.Builder build;
        private final ResourceLocation location;

        public Result(ResourceLocation pId, TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration,String group, Advancement.Builder build, ResourceLocation location) {

            this.pId = pId;
            this.input = input;
            this.output = output;
            this.tierRequirement = tierRequirement;
            this.craftingDuration = craftingDuration;
            this.group = group;
            this.build = build;
            this.location = location;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.addProperty("input", input.location().toString());
            json.add("output", MiscHelpers.serializeBlockState(output));
            json.addProperty("tierRequirement",tierRequirement);
        }

        @Override
        public ResourceLocation getId() {
            return pId;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return Registration.GOO_SPREAD_RECIPE_SERIALIZER_TAG.get();
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public JsonObject serializeAdvancement() {
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
