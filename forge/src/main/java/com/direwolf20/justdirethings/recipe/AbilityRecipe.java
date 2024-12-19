package com.direwolf20.justdirethings.recipe;

import com.direwolf20.justdirethings.common.items.abilityupgrades.Upgrade;
import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableTool;
import com.direwolf20.justdirethings.setup.Config;
import com.direwolf20.justdirethings.setup.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.stream.Stream;

public class AbilityRecipe extends CustomSmithingTransformRecipe {

    public AbilityRecipe(SmithingTrimRecipe recipe) {
        super(recipe);
    }


    //template = 0, base = 1, addition = 2
    @Override
    public ItemStack assemble(Container container, RegistryAccess pRegistryAccess) {
        ItemStack base = container.getItem(1);
        ItemStack upgrade = container.getItem(2);
        if (isBaseIngredient(base) && base.getItem() instanceof ToggleableTool toggleableTool) {
            Ability ability = Ability.getAbilityFromUpgradeItem(upgrade.getItem());
            if (ability != null && toggleableTool.hasAbility(ability) && !ToggleableTool.hasUpgrade(base, ability) && Config.AVAILABLE_ABILITY_MAP.get(ability).get()) {
                ItemStack itemstack1 = base.copyWithCount(1);
                JustDireDataComponents.setAbilityUpgradeInstalled(itemstack1,true,ability);
                return itemstack1;
            }
        }
        return ItemStack.EMPTY;
    }



    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        ItemStack itemstack = new ItemStack(base.getItems()[0].getItem());
        Ability ability = Ability.getAbilityFromUpgradeItem(addition.getItems()[0].getItem());
        if (!Config.AVAILABLE_ABILITY_MAP.get(ability).get())
            return new ItemStack(Items.AIR);
        JustDireDataComponents.setAbilityUpgradeInstalled(itemstack,true,ability);

        return itemstack;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return stack.isEmpty();
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return stack.getItem() instanceof ToggleableTool;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return stack.getItem() instanceof Upgrade;
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.template, this.base, this.addition).anyMatch(Ingredient::isEmpty);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ABILITY_RECIPE_SERIALIZER.get();
    }


    public static class Serializer extends SmithingTrimRecipe.Serializer {
        @Override
        public SmithingTrimRecipe fromNetwork(ResourceLocation p_267117_, FriendlyByteBuf p_267316_) {
            return new AbilityRecipe(super.fromNetwork(p_267117_, p_267316_));
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_266746_, SmithingTrimRecipe p_266927_) {
            super.toNetwork(p_266746_, p_266927_);
        }

        @Override
        public SmithingTrimRecipe fromJson(ResourceLocation recipeLoc, JsonObject recipeJson) {
            return new AbilityRecipe(super.fromJson(recipeLoc, recipeJson));
        }
    }
}
