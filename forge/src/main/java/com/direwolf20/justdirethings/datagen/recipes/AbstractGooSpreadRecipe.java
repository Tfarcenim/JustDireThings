package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.common.blockentities.basebe.GooBlockBE_Base;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractGooSpreadRecipe<T> implements Recipe<CraftingContainer> {

    protected final ResourceLocation id;
    protected final T input;
    protected final BlockState output;
    protected final int tierRequirement;
    protected final int craftingDuration;

    public AbstractGooSpreadRecipe(ResourceLocation id, T input, BlockState output, int tierRequirement, int craftingDuration) {

        this.id = id;
        this.input = input;
        this.output = output;
        this.tierRequirement = tierRequirement;
        this.craftingDuration = craftingDuration;
    }

    @Override
    public final boolean matches(CraftingContainer p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public final ResourceLocation getId() {
        return id;
    }

    @Override
    public final boolean isSpecial() {
        return true;
    }

    @Override
    public final ItemStack assemble(CraftingContainer p_44001_, RegistryAccess p_267165_) {
        return ItemStack.EMPTY;
    }

    @Override
    public final boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public final ItemStack getResultItem(RegistryAccess p_267052_) {
        return ItemStack.EMPTY;
    }

    public final boolean matches(GooBlockBE_Base gooBlockBE_base, BlockState sourceState) {
        return sourceState.equals(input) && gooBlockBE_base.getTier() >= tierRequirement;
    }

    public final BlockState getOutput() {
        return output;
    }

    public final T getInput() {
        return input;
    }

    public final int getTierRequirement() {
        return tierRequirement;
    }

    public final int getCraftingDuration() {
        return craftingDuration;
    }

    public static abstract class GooSerializer<T,G extends AbstractGooSpreadRecipe<T>> implements RecipeSerializer<G> {

        protected abstract G compose(ResourceLocation id, T input, BlockState output, int tierRequirement, int craftingDuration);

        @Override
        public final void toNetwork(FriendlyByteBuf pBuffer, G pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.id);
            writeInput(pBuffer,pRecipe.input);
            pBuffer.writeInt(Block.getId(pRecipe.output));
            pBuffer.writeInt(pRecipe.tierRequirement);
            pBuffer.writeInt(pRecipe.craftingDuration);
        }

        @Override
        public final @Nullable G fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf pBuffer) {
            ResourceLocation resourceLocation = pBuffer.readResourceLocation();
            T inputState = readInput(pBuffer);
            BlockState outputState = Block.stateById(pBuffer.readInt());
            int tierRequirement = pBuffer.readInt();
            int craftingDuration = pBuffer.readInt();
            return compose(resourceLocation,inputState,outputState,tierRequirement,craftingDuration);
        }

        @Override
        public final G fromJson(ResourceLocation id, JsonObject json) {
            return compose(id, readInput(json),
                    MiscHelpers.loadBlockState(GsonHelper.getNonNull(json,"output")),
                    GsonHelper.getAsInt(json,"tierRequirement"),
                    GsonHelper.getAsInt(json,"craftingDuration")
            );
        }

        protected abstract T readInput(JsonObject json);

        protected abstract void writeInput(FriendlyByteBuf buf,T value);
        protected abstract T readInput(FriendlyByteBuf buf);

    }

}
