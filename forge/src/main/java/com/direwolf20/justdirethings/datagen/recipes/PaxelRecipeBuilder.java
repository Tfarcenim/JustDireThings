package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.ModRecipes;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.SmithingTrimRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Consumer;


public class PaxelRecipeBuilder extends SmithingTrimRecipeBuilder {


    public PaxelRecipeBuilder(RecipeSerializer<?> pType, RecipeCategory pCategory, Ingredient pTemplate, Ingredient pBase, Ingredient pAddition) {
        super(pType, pCategory, pTemplate, pBase, pAddition);
    }

    public static SmithingTransformRecipeBuilder paxel(Ingredient pTemplate, Ingredient pBase, Ingredient pAddition, Item pResult) {
        return new SmithingTransformRecipeBuilder(ModRecipes.PAXEL_RECIPE_SERIALIZER.get(), pTemplate, pBase, pAddition,RecipeCategory.TOOLS, pResult);
    }


    public static PaxelRecipeBuilder ability(Ingredient pTemplate, Ingredient pBase, Ingredient pAddition) {
        return new PaxelRecipeBuilder(ModRecipes.ABILITY_RECIPE_SERIALIZER.get(), RecipeCategory.TOOLS,pTemplate, pBase, pAddition);
    }

    @Override
    public PaxelRecipeBuilder unlocks(String pKey, CriterionTriggerInstance pCriterion) {
        return (PaxelRecipeBuilder) super.unlocks(pKey, pCriterion);
    }

    public void save(Consumer<FinishedRecipe> pRecipeOutput) {
        String armorName = BuiltInRegistries.ITEM.getKey(this.base.getItems()[0].getItem()).getPath();
        String abilityItemPath = BuiltInRegistries.ITEM.getKey(this.addition.getItems()[0].getItem()).getPath();
        String abilityName = abilityItemPath.substring(abilityItemPath.indexOf("_") + 1);
        this.save(pRecipeOutput, JustDireThings.id( armorName + "-" + abilityName));
    }

}
