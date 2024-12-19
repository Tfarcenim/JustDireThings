package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class AbstractGooRecipeBuilder<T> implements RecipeBuilder {

    @Nullable
    protected String group;

    protected final T input;
    protected final BlockState output;
    protected final int tierRequirement;
    protected final int craftingDuration;
    protected final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public AbstractGooRecipeBuilder(T input, BlockState output, int tierRequirement, int craftingDuration) {
        this.input = input;
        this.output = output;
        this.tierRequirement = tierRequirement;
        this.craftingDuration = craftingDuration;
    }

    @Override
    public final RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public final AbstractGooRecipeBuilder<T> group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public final Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    public static abstract class Result<T> implements FinishedRecipe {

        private final ResourceLocation pId;
        protected final T input;
        private final BlockState output;
        private final int tierRequirement;
        private final int craftingDuration;
        private final String group;
        private final Advancement.Builder advancement;
        private final ResourceLocation location;

        public Result(ResourceLocation pId, T input, BlockState output, int tierRequirement, int craftingDuration, String group, Advancement.Builder advancement, ResourceLocation location) {

            this.pId = pId;
            this.input = input;
            this.output = output;
            this.tierRequirement = tierRequirement;
            this.craftingDuration = craftingDuration;
            this.group = group;
            this.advancement = advancement;
            this.location = location;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            serializeInput(json);
            json.add("output", MiscHelpers.serializeBlockState(output));
            json.addProperty("tierRequirement",tierRequirement);
            json.addProperty("craftingDuration",craftingDuration);
        }

        protected abstract void serializeInput(JsonObject object);

        @Override
        public final ResourceLocation getId() {
            return pId;
        }

        @Override
        public final JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public final ResourceLocation getAdvancementId() {
            return location;
        }
    }
}
