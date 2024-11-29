package com.direwolf20.justdirethings.client.particles.gooexplodeparticle;


import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class GooExplodeParticleType extends ParticleType<GooExplodeParticleData> {
    public GooExplodeParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter);
    }

    public GooExplodeParticleType getType() {
        return this;
    }

    @Override
    public Codec<GooExplodeParticleData> codec() {
        return GooExplodeParticleData.MAP_CODEC;
    }

}
