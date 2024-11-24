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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GooSpreadRecipe implements CraftingRecipe {
    private final ResourceLocation id;
    protected final BlockState input;
    protected final BlockState output;
    protected int tierRequirement;
    protected int craftingDuration;

    public GooSpreadRecipe(ResourceLocation id, BlockState input, BlockState output, int tierRequirement, int craftingDuration) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.tierRequirement = tierRequirement;
        this.craftingDuration = craftingDuration;
    }

    @Override
    public RecipeType<?> getType() {
        return Registration.GOO_SPREAD_RECIPE_TYPE.get();
    }

    public boolean matches(GooBlockBE_Base gooBlockBE_base, BlockState sourceState) {
        return sourceState.equals(input) && gooBlockBE_base.getTier() >= tierRequirement;
    }

    public BlockState getOutput() {
        return output;
    }

    public BlockState getInput() {
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
        return null;
    }


    @Override
    public boolean matches(CraftingContainer p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer p_44001_, RegistryAccess p_267165_) {
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
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.GOO_SPREAD_RECIPE_SERIALIZER.get();
    }


    public static class Serializer implements RecipeSerializer<GooSpreadRecipe> {
        private static final net.minecraft.resources.ResourceLocation NAME = JustDireThings.id("goospread");
        private static final MapCodec<GooSpreadRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_311734_ -> p_311734_.group(
                                ResourceLocation.CODEC.fieldOf("id").forGetter(p_301134_ -> p_301134_.id),
                                BlockState.CODEC.fieldOf("input").forGetter(p_301135_ -> p_301135_.input),
                                BlockState.CODEC.fieldOf("output").forGetter(p_301136_ -> p_301136_.output),
                                Codec.INT.fieldOf("tierRequirement").forGetter(p_301137_ -> p_301137_.tierRequirement),
                                Codec.INT.fieldOf("craftingDuration").forGetter(p_301138_ -> p_301138_.craftingDuration)
                        )
                        .apply(p_311734_, GooSpreadRecipe::new)
        );

        @Override
        public GooSpreadRecipe fromJson(ResourceLocation p_44103_, JsonObject json) {
            BlockState input = MiscHelpers.loadBlockState(GsonHelper.getNonNull(json,"input"));
            BlockState output = MiscHelpers.loadBlockState(GsonHelper.getNonNull(json,"output"));
            int tierRequirement = GsonHelper.getAsInt(json,"tierRequirement");
            int craftingDuration = GsonHelper.getAsInt(json,"craftingDuration");
            return new GooSpreadRecipe(p_44103_,input,output,tierRequirement,craftingDuration);
        }

        @Override
        public @Nullable GooSpreadRecipe fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf pBuffer) {
            ResourceLocation resourceLocation = pBuffer.readResourceLocation();
            BlockState inputState = Block.stateById(pBuffer.readInt());
            BlockState outputState = Block.stateById(pBuffer.readInt());
            int tierRequirement = pBuffer.readInt();
            int craftingDuration = pBuffer.readInt();

            return new GooSpreadRecipe(resourceLocation, inputState, outputState, tierRequirement, craftingDuration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, GooSpreadRecipe pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.id);
            pBuffer.writeInt(Block.getId(pRecipe.input));
            pBuffer.writeInt(Block.getId(pRecipe.output));
            pBuffer.writeInt(pRecipe.tierRequirement);
            pBuffer.writeInt(pRecipe.craftingDuration);
        }
    }
}
