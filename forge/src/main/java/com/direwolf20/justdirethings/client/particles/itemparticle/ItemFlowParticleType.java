package com.direwolf20.justdirethings.client.particles.itemparticle;


import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class ItemFlowParticleType extends ParticleType<ItemFlowParticleData> {

    private static final ParticleOptions.Deserializer<ItemFlowParticleData> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public ItemFlowParticleData fromCommand(ParticleType<ItemFlowParticleData> p_123846_, StringReader p_123847_) {
            return null;
        }

        @Override
        public ItemFlowParticleData fromNetwork(ParticleType<ItemFlowParticleData> pParticleType, FriendlyByteBuf pBuffer) {
            return new ItemFlowParticleData(pBuffer.readItem(),pBuffer.readDouble(),pBuffer.readDouble(),pBuffer.readDouble(),pBuffer.readInt());
        }
    };
    
    public ItemFlowParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter,DESERIALIZER);
    }

    @Override
    public Codec<ItemFlowParticleData> codec() {
        return ItemFlowParticleData.MAP_CODEC;
    }

}
