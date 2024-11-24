package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.common.blockentities.basebe.GooBlockBE_Base;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GooSpreadRecipeTag implements CraftingRecipe {
    private final ResourceLocation id;
    protected final TagKey<Block> input;
    protected final BlockState output;
    protected int tierRequirement;
    protected int craftingDuration;

    public GooSpreadRecipeTag(ResourceLocation id, TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.tierRequirement = tierRequirement;
        this.craftingDuration = craftingDuration;
    }

    @Override
    public RecipeType<?> getType() {
        return Registration.GOO_SPREAD_RECIPE_TYPE_TAG.get();
    }

    public boolean matches(GooBlockBE_Base gooBlockBE_base, BlockState sourceState) {
        return sourceState.is(input) && gooBlockBE_base.getTier() >= tierRequirement;
    }

    @Override
    public boolean matches(CraftingContainer p_44002_, Level p_44003_) {
        return false;
    }

    public BlockState getOutput() {
        return output;
    }

    public TagKey<Block> getInput() {
        return input;
    }

    public int getTierRequirement() {
        return tierRequirement;
    }

    public int getCraftingDuration() {
        return craftingDuration;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public ItemStack assemble(CraftingContainer p_44001_, RegistryAccess p_267165_) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return ItemStack.EMPTY;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.GOO_SPREAD_RECIPE_SERIALIZER_TAG.get();
    }


    public static class Serializer implements RecipeSerializer<GooSpreadRecipeTag> {
        private static final ResourceLocation NAME = JustDireThings.id("goospread_tag");
        private static final MapCodec<GooSpreadRecipeTag> CODEC = RecordCodecBuilder.mapCodec(
                p_311734_ -> p_311734_.group(
                                ResourceLocation.CODEC.fieldOf("id").forGetter(p_301134_ -> p_301134_.id),
                                TagKey.codec(Registries.BLOCK).fieldOf("input").forGetter(p_301135_ -> p_301135_.input),
                                BlockState.CODEC.fieldOf("output").forGetter(p_301136_ -> p_301136_.output),
                                Codec.INT.fieldOf("tierRequirement").forGetter(p_301137_ -> p_301137_.tierRequirement),
                                Codec.INT.fieldOf("craftingDuration").forGetter(p_301138_ -> p_301138_.craftingDuration)
                        )
                        .apply(p_311734_, GooSpreadRecipeTag::new)
        );


        @Override
        public GooSpreadRecipeTag fromJson(ResourceLocation id, JsonObject json) {
            return new GooSpreadRecipeTag(id, TagKey.create(Registries.BLOCK,new ResourceLocation(GsonHelper.getAsString(json,"input"))),
                    MiscHelpers.loadBlockState(GsonHelper.getNonNull(json,"output")),
                    GsonHelper.getAsInt(json,"tierRequirement"),
                    GsonHelper.getAsInt(json,"craftingDuration")
                    );
        }

        @Override
        public @Nullable GooSpreadRecipeTag fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf pBuffer) {
            ResourceLocation resourceLocation = pBuffer.readResourceLocation();
            ResourceLocation tagLocation = pBuffer.readResourceLocation(); // Read the tag's ResourceLocation
            TagKey<Block> inputIngredient = TagKey.create(Registries.BLOCK, tagLocation); // Create the BlockTagIngredient
            BlockState outputState = Block.stateById(pBuffer.readInt());
            int tierRequirement = pBuffer.readInt();
            int craftingDuration = pBuffer.readInt();
            return new GooSpreadRecipeTag(resourceLocation, inputIngredient, outputState, tierRequirement, craftingDuration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, GooSpreadRecipeTag pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.id);
            pBuffer.writeResourceLocation(pRecipe.input.location()); // Write the tag's ResourceLocation
            pBuffer.writeInt(Block.getId(pRecipe.output));
            pBuffer.writeInt(pRecipe.tierRequirement);
            pBuffer.writeInt(pRecipe.craftingDuration);
        }
    }
}
