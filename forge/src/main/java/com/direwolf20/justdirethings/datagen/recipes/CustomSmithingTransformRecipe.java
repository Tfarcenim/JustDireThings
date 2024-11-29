package com.direwolf20.justdirethings.datagen.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

public class CustomSmithingTransformRecipe extends SmithingTransformRecipe {
    public CustomSmithingTransformRecipe(ResourceLocation pId, Ingredient pTemplate, Ingredient pBase, Ingredient pAddition, ItemStack pResult) {
        super(pId, pTemplate, pBase, pAddition, pResult);
    }

    public CustomSmithingTransformRecipe(SmithingTransformRecipe recipe) {
        this(recipe.id,recipe.template,recipe.base,recipe.addition,recipe.result);
    }

}
