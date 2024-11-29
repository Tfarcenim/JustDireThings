package com.direwolf20.justdirethings.client.particles.glitterparticle;

import com.mojang.serialization.Codec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class GlitterParticleType extends ParticleType<GlitterParticleData> {


    public GlitterParticleType(boolean pOverrideLimiter, ParticleOptions.Deserializer<GlitterParticleData> pDeserializer) {
        super(pOverrideLimiter, pDeserializer);
    }

    public GlitterParticleType getType() {
        return this;
    }

    @Override
    public Codec<GlitterParticleData> codec() {
        return GlitterParticleData.CODEC;
    }

    public static class FACTORY implements ParticleProvider<GlitterParticleData> {
        private final SpriteSet sprites;

        public FACTORY(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(GlitterParticleData data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new GlitterParticle(world, x, y, z, data.target, xSpeed, ySpeed, zSpeed, data.size, data.r, data.g, data.b, data.a, data.depthTest, data.maxAgeMul, data.partType, this.sprites);
        }
    }
}
