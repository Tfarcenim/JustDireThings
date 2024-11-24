package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.function.Consumer;


public class FluidDropRecipeBuilder implements RecipeBuilder {
    private String group = "";

    protected final BlockState input;
    protected final BlockState output;
    protected final Item catalyst;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();


    public FluidDropRecipeBuilder(BlockState input, BlockState output, Item catalyst) {
        this.input = input;
        this.output = output;
        this.catalyst = catalyst;
    }

    public static FluidDropRecipeBuilder shapeless(BlockState input, BlockState output, Item catalyst) {
        return new FluidDropRecipeBuilder(input, output, catalyst);
    }

    public FluidDropRecipeBuilder unlockedBy(String pName, CriterionTriggerInstance pCriterion) {
        this.advancement.addCriterion(pName,pCriterion);
        return this;
    }

    public FluidDropRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    public void save(Consumer<FinishedRecipe> pRecipeOutput) {
        this.save(pRecipeOutput, JustDireThings.id(BuiltInRegistries.BLOCK.getKey(this.output.getBlock()).getPath() + "-fluiddrop"));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pRecipeOutput, ResourceLocation pId) {
        //this.ensureValid(pId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId)).rewards(AdvancementRewards.Builder.recipe(pId)).requirements(RequirementsStrategy.OR);
        pRecipeOutput.accept(new Result(pId, input,output,catalyst,group, advancement,pId.withPrefix("recipes/" + RecipeCategory.MISC.getFolderName() + "/")));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        final BlockState input;
        final BlockState output;
        final Item catalyst;
        private final String group;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation p_249007_, BlockState input, BlockState output, Item p_248667_, String p_248592_,  Advancement.Builder p_249909_, ResourceLocation p_249109_) {
            this.id = p_249007_;
            this.input = input;
            this.output = output;
            this.catalyst = p_248667_;
            this.group = p_248592_;
            this.advancement = p_249909_;
            this.advancementId = p_249109_;
        }

        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.add("input", MiscHelpers.serializeBlockState(input));
            json.add("output", MiscHelpers.serializeBlockState(output));
            json.addProperty("catalyst",BuiltInRegistries.ITEM.getKey(catalyst).toString());
        }

        public RecipeSerializer<?> getType() {
            return Registration.FLUID_DROP_RECIPE_SERIALIZER.get();
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }


}
