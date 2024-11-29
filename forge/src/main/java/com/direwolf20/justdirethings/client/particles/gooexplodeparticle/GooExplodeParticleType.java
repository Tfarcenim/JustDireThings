package com.direwolf20.justdirethings.client.particles.gooexplodeparticle;


import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class GooExplodeParticleType extends ParticleType<GooExplodeParticleData> {

    private static final ParticleOptions.Deserializer<GooExplodeParticleData> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public GooExplodeParticleData fromCommand(ParticleType<GooExplodeParticleData> p_123846_, StringReader p_123847_) {
            return null;
        }

        @Override
        public GooExplodeParticleData fromNetwork(ParticleType<GooExplodeParticleData> pParticleType, FriendlyByteBuf pBuffer) {
            return new GooExplodeParticleData(pBuffer.readItem());
        }
    };
    
    public GooExplodeParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter,DESERIALIZER);
    }

    @Override
    public Codec<GooExplodeParticleData> codec() {
        return GooExplodeParticleData.MAP_CODEC;
    }

}
