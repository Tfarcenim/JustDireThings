package com.direwolf20.justdirethings.datagen.recipes;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GooSpreadRecipeTag extends AbstractGooSpreadRecipe<TagKey<Block>> {


    public GooSpreadRecipeTag(ResourceLocation id, TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration) {
        super(id, input, output, tierRequirement, craftingDuration);
    }


    @Override
    public RecipeType<?> getType() {
        return Registration.GOO_SPREAD_RECIPE_TYPE_TAG.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.GOO_SPREAD_RECIPE_SERIALIZER_TAG.get();
    }


    public static class Serializer extends GooSerializer<TagKey<Block>,GooSpreadRecipeTag> {

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
        protected GooSpreadRecipeTag compose(ResourceLocation id, TagKey<Block> input, BlockState output, int tierRequirement, int craftingDuration) {
            return new GooSpreadRecipeTag(id, input, output, tierRequirement, craftingDuration);
        }

        @Override
        protected void writeInput(FriendlyByteBuf buf, TagKey<Block> value) {
            buf.writeResourceLocation(value.location());
        }

        @Override
        protected TagKey<Block> readInput(FriendlyByteBuf buf) {
            return TagKey.create(Registries.BLOCK,buf.readResourceLocation());
        }

        @Override
        protected TagKey<Block> readInput(JsonObject json) {
            return TagKey.create(Registries.BLOCK,new ResourceLocation(GsonHelper.getAsString(json,"input")));
        }
    }
}
