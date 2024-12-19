package com.direwolf20.justdirethings.setup;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.datagen.recipes.*;
import com.direwolf20.justdirethings.recipe.AbilityRecipe;
import com.direwolf20.justdirethings.recipe.PaxelRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.direwolf20.justdirethings.JustDireThings.MODID;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);
   // public static final Supplier<RecipeType<PaxelRecipe>> PAXEL_RECIPE_TYPE = RECIPE_TYPES.register("paxelrecipe", () -> RecipeType.simple(JustDireThings.id("paxelrecipe")));
    //public static final Supplier<RecipeType<AbilityRecipe>> ABILITY_RECIPE_TYPE = RECIPE_TYPES.register("abilityrecipe", () -> RecipeType.simple(JustDireThings.id("abilityrecipe")));
    public static final Supplier<RecipeType<FluidDropRecipe>> FLUID_DROP_RECIPE_TYPE = RECIPE_TYPES.register("fluiddroprecipe", () -> RecipeType.simple(JustDireThings.id("fluiddroprecipe")));
    public static final Supplier<RecipeType<GooSpreadRecipeTag>> GOO_SPREAD_RECIPE_TYPE_TAG = RECIPE_TYPES.register("goospreadrecipe_tag", () -> RecipeType.simple(JustDireThings.id("goospreadrecipe_tag")));
    public static final Supplier<RecipeType<GooSpreadRecipe>> GOO_SPREAD_RECIPE_TYPE = RECIPE_TYPES.register("goospreadrecipe", () -> RecipeType.simple(JustDireThings.id("goospreadrecipe")));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, JustDireThings.MODID);
    public static final Supplier<PaxelRecipe.Serializer> PAXEL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("paxel", PaxelRecipe.Serializer::new);
    public static final Supplier<AbilityRecipe.Serializer> ABILITY_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("ability", AbilityRecipe.Serializer::new);
    public static final Supplier<FluidDropRecipe.Serializer> FLUID_DROP_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("fluiddrop", FluidDropRecipe.Serializer::new);
    public static final Supplier<GooSpreadRecipeTag.Serializer> GOO_SPREAD_RECIPE_SERIALIZER_TAG = RECIPE_SERIALIZERS.register("goospread_tag", GooSpreadRecipeTag.Serializer::new);
    public static final Supplier<GooSpreadRecipe.Serializer> GOO_SPREAD_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("goospread", GooSpreadRecipe.Serializer::new);
}
