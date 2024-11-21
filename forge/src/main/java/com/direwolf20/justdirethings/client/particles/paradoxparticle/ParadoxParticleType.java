package com.direwolf20.justdirethings.client.particles.paradoxparticle;


import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class ParadoxParticleType extends ParticleType<ParadoxParticleData> {

    private static final ParticleOptions.Deserializer<ParadoxParticleType> DESERIALIZER = new ParticleOptions.Deserializer<ParadoxParticleData>() {
        public ParadoxParticleType fromCommand(ParticleType<ParadoxParticleData> p_123846_, StringReader p_123847_) {
            return (ParadoxParticleData)p_123846_;
        }

        public SimpleParticleType fromNetwork(ParticleType<SimpleParticleType> p_123849_, FriendlyByteBuf p_123850_) {
            return (SimpleParticleType)p_123849_;
        }
    };

    public ParadoxParticleType(boolean p_123740_) {
        super(p_123740_,DESERIALIZER);
    }

    public ParadoxParticleType getType() {
        return this;
    }

    @Override
    public Codec<ParadoxParticleData> codec() {
        return null;//ParadoxParticleData.MAP_CODEC;todo
    }


}
