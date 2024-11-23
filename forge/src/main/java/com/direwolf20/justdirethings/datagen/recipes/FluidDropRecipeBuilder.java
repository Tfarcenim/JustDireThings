package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.datagen.JustDireRecipes;
import com.direwolf20.justdirethings.setup.Registration;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;


public class FluidDropRecipeBuilder implements RecipeBuilder {
    @Nullable
    private String group;

    protected final BlockState input;
    protected final BlockState output;
    protected final Item catalyst;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();


    public FluidDropRecipeBuilder(BlockState input, BlockState output, Item catalyst) {
        this.input = input;
        this.output = output;
        this.catalyst = catalyst;
    }

    public static FluidDropRecipeBuilder shapeless(BlockState input, BlockState output, Item catalyst) {
        return new FluidDropRecipeBuilder(input, output, catalyst);
    }

    public FluidDropRecipeBuilder requires(TagKey<Item> pTag) {
        return this.requires(Ingredient.of(pTag));
    }

    public FluidDropRecipeBuilder requires(ItemLike pItem) {
        return this.requires(pItem, 1);
    }

    public FluidDropRecipeBuilder requires(ItemLike pItem, int pQuantity) {
        for (int i = 0; i < pQuantity; ++i) {
            this.requires(Ingredient.of(pItem));
        }

        return this;
    }

    public FluidDropRecipeBuilder requires(Ingredient pIngredient) {
        return this.requires(pIngredient, 1);
    }

    public FluidDropRecipeBuilder requires(Ingredient pIngredient, int pQuantity) {
        for (int i = 0; i < pQuantity; ++i) {
            this.ingredients.add(pIngredient);
        }

        return this;
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

        public void serializeRecipeData(JsonObject p_126230_) {
            if (!this.group.isEmpty()) {
                p_126230_.addProperty("group", this.group);
            }

            JsonObject jsonobject = new JsonObject();


            p_126230_.add("result", jsonobject);
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
