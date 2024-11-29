package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.setup.ModRecipes;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;


public class PaxelRecipeBuilder extends SmithingTransformRecipeBuilder {


    public PaxelRecipeBuilder(RecipeSerializer<?> pType, Ingredient pTemplate, Ingredient pBase, Ingredient pAddition, RecipeCategory pCategory, Item pResult) {
        super(pType, pTemplate, pBase, pAddition, pCategory, pResult);
    }

    public static SmithingTransformRecipeBuilder paxel(Ingredient pTemplate, Ingredient pBase, Ingredient pAddition, Item pResult) {
        return new SmithingTransformRecipeBuilder(ModRecipes.PAXEL_RECIPE_SERIALIZER.get(), pTemplate, pBase, pAddition,RecipeCategory.TOOLS, pResult);
    }


    public static SmithingTransformRecipeBuilder ability(Ingredient pTemplate, Ingredient pBase, Ingredient pAddition) {
        return new SmithingTransformRecipeBuilder(ModRecipes.ABILITY_RECIPE_SERIALIZER.get(), pTemplate, pBase, pAddition,RecipeCategory.TOOLS, Items.AIR);
    }

}
