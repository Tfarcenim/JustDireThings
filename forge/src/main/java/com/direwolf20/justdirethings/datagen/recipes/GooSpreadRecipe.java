package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.ModRecipes;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GooSpreadRecipe extends AbstractGooSpreadRecipe<BlockState> {


    public GooSpreadRecipe(ResourceLocation id, BlockState input, BlockState output, int tierRequirement, int craftingDuration) {
        super(id, input, output, tierRequirement, craftingDuration);
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GOO_SPREAD_RECIPE_TYPE.get();
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GOO_SPREAD_RECIPE_SERIALIZER.get();
    }


    public static class Serializer extends GooSerializer<BlockState,GooSpreadRecipe> {
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
        protected BlockState readInput(JsonObject buf) {
            return MiscHelpers.loadBlockState(buf.get("input"));
        }

        @Override
        protected GooSpreadRecipe compose(ResourceLocation id, BlockState input, BlockState output, int tierRequirement, int craftingDuration) {
            return new GooSpreadRecipe(id, input, output, tierRequirement, craftingDuration);
        }

        @Override
        protected void writeInput(FriendlyByteBuf buf, BlockState value) {
            buf.writeId(Block.BLOCK_STATE_REGISTRY,value);
        }

        @Override
        protected BlockState readInput(FriendlyByteBuf buf) {
            return buf.readById(Block.BLOCK_STATE_REGISTRY);
        }
    }
}
