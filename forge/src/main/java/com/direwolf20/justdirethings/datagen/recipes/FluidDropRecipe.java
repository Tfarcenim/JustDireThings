package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.Registration;
import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FluidDropRecipe implements CraftingRecipe {
    private final ResourceLocation id;
    protected final BlockState input;
    protected final BlockState output;
    protected final Item catalyst;

    public FluidDropRecipe(ResourceLocation id, BlockState input, BlockState output, Item catalyst) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.catalyst = catalyst;
    }

    public FluidDropRecipe(ResourceLocation id, BlockState input, BlockState output, Holder<Item> catalyst) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.catalyst = catalyst.value();
    }

    @Override
    public RecipeType<?> getType() {
        return Registration.FLUID_DROP_RECIPE_TYPE.get();
    }

    public boolean matches(BlockState blockState, ItemStack catalystStack) {
        if (!catalystStack.is(catalyst)) return false;
        return /*blockState.getBlock() instanceof LiquidBlock liquidBlock &&*/ blockState.getFluidState().is(input.getFluidState().getType()) && blockState.getFluidState().isSource();
    }

    public ResourceLocation getId() {
        return id;
    }

    public BlockState getOutput() {
        return output;
    }

    public BlockState getInput() {
        return input;
    }

    public Item getCatalyst() {
        return catalyst;
    }

    public Holder<Item> getCatalystHolder() {
        return catalyst == null ? null : catalyst.builtInRegistryHolder();
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }


    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
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
    public RecipeSerializer<?> getSerializer() {
        return Registration.FLUID_DROP_RECIPE_SERIALIZER.get();
    }


    public static class Serializer implements RecipeSerializer<FluidDropRecipe> {
        private static final ResourceLocation NAME = JustDireThings.id("fluiddrop");


        public static final StreamCodec<RegistryFriendlyByteBuf, FluidDropRecipe> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, FluidDropRecipe::getId,
                ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), FluidDropRecipe::getInput,
                ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), FluidDropRecipe::getOutput,
                ByteBufCodecs.holderRegistry(Registries.ITEM), FluidDropRecipe::getCatalystHolder,
                FluidDropRecipe::new
        );




        @Override
        public FluidDropRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new FluidDropRecipe(id,);
        }

        @Override
        public @Nullable FluidDropRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new FluidDropRecipe(id,buf.readById(Block.BLOCK_STATE_REGISTRY),buf.readById(Block.BLOCK_STATE_REGISTRY),buf.readById(BuiltInRegistries.ITEM));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FluidDropRecipe recipe) {
            buf.writeId(Block.BLOCK_STATE_REGISTRY, recipe.getInput());
            buf.writeId(Block.BLOCK_STATE_REGISTRY, recipe.getOutput());
            buf.writeId(BuiltInRegistries.ITEM, recipe.getCatalyst());
        }
    }
}
