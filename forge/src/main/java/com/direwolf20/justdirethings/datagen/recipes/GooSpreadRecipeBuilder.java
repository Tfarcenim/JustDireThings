package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;


public class GooSpreadRecipeBuilder implements RecipeBuilder {
    @Nullable
    private String group;

    private final ResourceLocation id;
    protected final BlockState input;
    protected final BlockState output;
    protected final int tierRequirement;
    protected final int craftingDuration;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public GooSpreadRecipeBuilder(ResourceLocation id, BlockState input, BlockState output, int tierRequirement, int craftingDuration) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.tierRequirement = tierRequirement;
        this.craftingDuration = craftingDuration;
    }

    public static GooSpreadRecipeBuilder shapeless(ResourceLocation id, BlockState input, BlockState output, int tierRequirement, int craftingDuration) {
        return new GooSpreadRecipeBuilder(id, input, output, tierRequirement, craftingDuration);
    }

    public GooSpreadRecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        this.criteria.put(pName, pCriterion);
        return this;
    }

    public GooSpreadRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pRecipeOutput) {
        this.save(pRecipeOutput, JustDireThings.id(BuiltInRegistries.BLOCK.getKey(this.output.getBlock()).getPath() + "-goospread"));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pRecipeOutput, ResourceLocation pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        GooSpreadRecipe shapelessrecipe = new GooSpreadRecipe(
                this.id,
                this.input,
                this.output,
                this.tierRequirement,
                this.craftingDuration
        );
        pRecipeOutput.accept(pId, shapelessrecipe, advancement$builder.build(pId.withPrefix("recipes/" + RecipeCategory.MISC.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject p_125967_) {

        }

        @Override
        public ResourceLocation getId() {
            return null;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return null;
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
