package com.direwolf20.justdirethings.client.jei;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.datagen.recipes.GooSpreadRecipeTag;
import com.direwolf20.justdirethings.setup.Registration;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class GooSpreadRecipeTagCategory implements IRecipeCategory<GooSpreadRecipeTag> {
    public static final RecipeType<GooSpreadRecipeTag> TYPE =
            RecipeType.create(JustDireThings.MODID, "goo_spread_recipe_tag", GooSpreadRecipeTag.class);

    public static final int width = 120;
    public static final int height = 40;

    private final IDrawable background;
    private final IDrawable slot;
    private final IDrawable icon;
    private final Component localizedName;
    private final IDrawableStatic arrow;

    public GooSpreadRecipeTagCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(width, height);
        slot = guiHelper.getSlotDrawable();
        icon = guiHelper.createDrawableItemStack(new ItemStack(Registration.GooBlock_Tier1.get()));
        localizedName = Component.translatable("justdirethings.goospreadrecipetag.title");
        this.arrow = guiHelper.getRecipeArrow();
    }

    @Override
    public RecipeType<GooSpreadRecipeTag> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(GooSpreadRecipeTag recipe, IRecipeSlotsView slotsView, GuiGraphics gui, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        arrow.draw(gui, 54, 12);
        background.draw(gui, 17, 0);
        RenderSystem.disableBlend();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GooSpreadRecipeTag recipe, IFocusGroup focuses) {
        TagKey<Block> input = recipe.getInput();
        IRecipeSlotBuilder inputSlotBuilder = builder.addSlot(RecipeIngredientRole.INPUT, 9, 12);
        List<ItemStack> itemstacks = new ArrayList<>();
        BuiltInRegistries.BLOCK.getTag(input).get().forEach(blockHolder -> {
            itemstacks.add(blockHolder.value().asItem().getDefaultInstance());
        });
        inputSlotBuilder.addItemStacks(itemstacks);
        /*if (input.getBlock().asItem() != Items.AIR) {
            inputSlotBuilder
                    .addItemStack(new ItemStack(input.getBlock()));
        } else if (input.getBlock() instanceof LiquidBlock liquidBlock) {
            inputSlotBuilder
                    .addFluidStack(liquidBlock.fluid, 1000);
        }*/
        List<ItemStack> catalystlist = new ArrayList<>();

        if (recipe.getTierRequirement() <= 1)
            catalystlist.add(new ItemStack(Registration.GooBlock_Tier1.get()));
        if (recipe.getTierRequirement() <= 2)
            catalystlist.add(new ItemStack(Registration.GooBlock_Tier2.get()));
        if (recipe.getTierRequirement() <= 3)
            catalystlist.add(new ItemStack(Registration.GooBlock_Tier3.get()));
        if (recipe.getTierRequirement() <= 4)
            catalystlist.add(new ItemStack(Registration.GooBlock_Tier4.get()));
        builder.addSlot(RecipeIngredientRole.CATALYST, 29, 12)
                .addItemStacks(catalystlist);

        BlockState output = recipe.getOutput();
        if (output.getBlock().asItem() != Items.AIR) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 12)
                    .addItemStack(new ItemStack(output.getBlock()));
        } else if (output.getBlock() instanceof LiquidBlock liquidBlock) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 12)
                    .addFluidStack(liquidBlock.getFluid(), 1000);
        }
    }
}
