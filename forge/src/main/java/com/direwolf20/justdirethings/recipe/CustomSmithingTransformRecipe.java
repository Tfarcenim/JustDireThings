package com.direwolf20.justdirethings.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;

public class CustomSmithingTransformRecipe extends SmithingTrimRecipe {
    public CustomSmithingTransformRecipe(ResourceLocation pId, Ingredient pTemplate, Ingredient pBase, Ingredient pAddition) {
        super(pId, pTemplate, pBase, pAddition);
    }

    public CustomSmithingTransformRecipe(SmithingTrimRecipe recipe) {
        this(recipe.id,recipe.template,recipe.base,recipe.addition);
    }

}
