package com.direwolf20.justdirethings.client.particles.alwaysvisibleparticle;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class AlwaysVisibleParticleType extends ParticleType<AlwaysVisibleParticleData> {

    private static final ParticleOptions.Deserializer<AlwaysVisibleParticleData> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public AlwaysVisibleParticleData fromCommand(ParticleType<AlwaysVisibleParticleData> p_123846_, StringReader p_123847_) {
            return null;
        }

        @Override
        public AlwaysVisibleParticleData fromNetwork(ParticleType<AlwaysVisibleParticleData> pParticleType, FriendlyByteBuf pBuffer) {
            return new AlwaysVisibleParticleData(pBuffer.readResourceLocation());
        }
    };
    
    public AlwaysVisibleParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter,DESERIALIZER);
    }

    public AlwaysVisibleParticleType getType() {
        return this;
    }

    @Override
    public Codec<AlwaysVisibleParticleData> codec() {
        return AlwaysVisibleParticleData.MAP_CODEC;
    }
}
