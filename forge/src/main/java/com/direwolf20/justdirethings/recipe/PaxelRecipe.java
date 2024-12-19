package com.direwolf20.justdirethings.recipe;

import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableTool;
import com.direwolf20.justdirethings.setup.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.*;

public class PaxelRecipe extends CustomSmithingTransformRecipe {


    public PaxelRecipe(SmithingTrimRecipe recipe) {
        super(recipe);
    }

    //template = 0, base = 1, addition = 2
    public ItemStack assemble(Container container, RegistryAccess provider) {
        ItemStack pickaxe = container.getItem(0);
        ItemStack axe = container.getItem(1);
        ItemStack shovel = container.getItem(2);
        ItemStack result = getResultItem(provider);

        if (isTemplateIngredient(pickaxe) && pickaxe.getItem() instanceof ToggleableTool pickaxetoggleableTool) {
            for (Ability ability : pickaxetoggleableTool.getAbilities()) {
                if (ToggleableTool.hasUpgrade(pickaxe, ability))
                    JustDireDataComponents.setAbilityUpgradeInstalled(result,true,ability);
            }

        } else {
            return ItemStack.EMPTY;
        }
        if (isBaseIngredient(axe) && axe.getItem() instanceof ToggleableTool axetoggleableTool) {
            for (Ability ability : axetoggleableTool.getAbilities()) {
                if (ToggleableTool.hasUpgrade(axe, ability))
                    JustDireDataComponents.setAbilityUpgradeInstalled(result,true,ability);
            }
        } else {
            return ItemStack.EMPTY;
        }
        if (isAdditionIngredient(shovel) && shovel.getItem() instanceof ToggleableTool shoveltoggleableTool) {
            for (Ability ability : shoveltoggleableTool.getAbilities()) {
                if (ToggleableTool.hasUpgrade(shovel, ability))
                    JustDireDataComponents.setAbilityUpgradeInstalled(result,true,ability);
            }
        } else {
            return ItemStack.EMPTY;
        }

        // Transfer and combine enchantments from pickaxe, axe, and shovel
        Map<Enchantment,Integer> pickaxeEnchantments = pickaxe.getAllEnchantments();
        Map<Enchantment,Integer> axeEnchantments = axe.getAllEnchantments();
        Map<Enchantment,Integer> shovelEnchantments = shovel.getAllEnchantments();

        Map<Enchantment,Integer> combinedEnchantments = new HashMap<>();

        combine(combinedEnchantments,pickaxeEnchantments);
        combine(combinedEnchantments,axeEnchantments);
        combine(combinedEnchantments,shovelEnchantments);

        EnchantmentHelper.setEnchantments(combinedEnchantments,result);

        return result;
    }

    static void combine(Map<Enchantment,Integer> existing,Map<Enchantment,Integer> add) {
        for (Map.Entry<Enchantment,Integer> entry : add.entrySet()) {
            Enchantment addKey = entry.getKey();
            if (existing.containsKey(addKey)) {
                int addValue = entry.getValue();
                int existingValue = existing.get(addKey);
                if (addValue > existingValue) {
                    existing.put(addKey,addValue);
                }
            } else {
                boolean conflicts = false;
                for (Enchantment key : existing.keySet()) {
                    if (!addKey.isCompatibleWith(key)) {
                        conflicts = true;
                        break;
                    }
                }
                if (!conflicts) {
                    existing.put(addKey,entry.getValue());
                }
            }
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.PAXEL_RECIPE_SERIALIZER.get();
    }


    public static class Serializer extends SmithingTrimRecipe.Serializer {
        @Override
        public SmithingTrimRecipe fromNetwork(ResourceLocation p_267117_, FriendlyByteBuf p_267316_) {
            return new PaxelRecipe(super.fromNetwork(p_267117_, p_267316_));
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_266746_, SmithingTrimRecipe p_266927_) {
            super.toNetwork(p_266746_, p_266927_);
        }

        @Override
        public SmithingTrimRecipe fromJson(ResourceLocation recipeLoc, JsonObject recipeJson) {
            return new PaxelRecipe(super.fromJson(recipeLoc, recipeJson));
        }
    }
}
