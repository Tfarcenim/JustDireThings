package com.direwolf20.justdirethings.client.jei;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.client.jei.ghostfilters.GhostFilterBasic;
import com.direwolf20.justdirethings.client.screens.basescreens.BaseScreen;
import com.direwolf20.justdirethings.datagen.recipes.*;
import com.direwolf20.justdirethings.recipe.AbilityRecipe;
import com.direwolf20.justdirethings.recipe.PaxelRecipe;
import com.direwolf20.justdirethings.setup.ModRecipes;
import com.direwolf20.justdirethings.setup.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.IExtendableSmithingRecipeCategory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIIntegration implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return JustDireThings.id("jei_plugin");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeRegistry = jeiRuntime.getRecipeManager();
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<CraftingRecipe> hiddenRecipes = new ArrayList<>();
       /* for (var sidedBlock : Registration.SIDEDBLOCKS.getEntries()) {
            if (sidedBlock.get() instanceof BaseMachineBlock baseMachineBlock) {
                Optional<? extends Recipe<?>> recipe = recipeManager.byKey(new ResourceLocation(sidedBlock.getId() + "_nbtclear"));
                recipe.ifPresent(recipeHolder -> hiddenRecipes.add(recipeHolder));
            }
        }
        for (var sidedBlock : Registration.BLOCKS.getEntries()) {
            if (sidedBlock.get() instanceof BaseMachineBlock baseMachineBlock) {
                Optional<RecipeHolder<?>> recipe = recipeManager.byKey(new ResourceLocation(sidedBlock.getId() + "_nbtclear"));
                recipe.ifPresent(recipeHolder -> hiddenRecipes.add(recipeHolder));
            }
        }*/


        recipeRegistry.hideRecipes(RecipeTypes.CRAFTING, hiddenRecipes);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(
                new GooSpreadRecipeCategory(guiHelper),
                new GooSpreadRecipeTagCategory(guiHelper),
                new FluidDropRecipeCategory(guiHelper)
        );
        registration.addRecipeCategories(new OreToResourceCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<GooSpreadRecipe> goospreadrecipes = new ArrayList<>(recipeManager.getAllRecipesFor(ModRecipes.GOO_SPREAD_RECIPE_TYPE.get()));

        registration.addRecipes(GooSpreadRecipeCategory.TYPE, goospreadrecipes);

        List<GooSpreadRecipeTag> goospreadtagrecipes = new ArrayList<>(recipeManager.getAllRecipesFor(ModRecipes.GOO_SPREAD_RECIPE_TYPE_TAG.get()));

        registration.addRecipes(GooSpreadRecipeTagCategory.TYPE, goospreadtagrecipes);

        List<FluidDropRecipe> fluidDropRecipes = new ArrayList<>(recipeManager.getAllRecipesFor(ModRecipes.FLUID_DROP_RECIPE_TYPE.get()));

        registration.addRecipes(FluidDropRecipeCategory.TYPE, fluidDropRecipes);

        //Ore to Resources
        registration.addRecipes(new RecipeType<>(OreToResourceCategory.UID, OreToResourceRecipe.class),
                List.of(new OreToResourceRecipe(Registration.RawFerricoreOre.get(), new ItemStack(Registration.RawFerricore.get())),
                        new OreToResourceRecipe(Registration.RawBlazegoldOre.get(), new ItemStack(Registration.RawBlazegold.get())),
                        new OreToResourceRecipe(Registration.RawCelestigemOre.get(), new ItemStack(Registration.Celestigem.get())),
                        new OreToResourceRecipe(Registration.RawEclipseAlloyOre.get(), new ItemStack(Registration.RawEclipseAlloy.get()))));

    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        IExtendableSmithingRecipeCategory smithingCategory = registration.getSmithingCategory();
        smithingCategory.addExtension(AbilityRecipe.class, new AbilityRecipeCategory());
        smithingCategory.addExtension(PaxelRecipe.class, new PaxelRecipeCategory());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier1.get()), GooSpreadRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier2.get()), GooSpreadRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier3.get()), GooSpreadRecipeCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier4.get()), GooSpreadRecipeCategory.TYPE);

        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier1.get()), GooSpreadRecipeTagCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier2.get()), GooSpreadRecipeTagCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier3.get()), GooSpreadRecipeTagCategory.TYPE);
        registry.addRecipeCatalyst(new ItemStack(Registration.GooBlock_Tier4.get()), GooSpreadRecipeTagCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(BaseScreen.class, new GhostFilterBasic());
    }

}
