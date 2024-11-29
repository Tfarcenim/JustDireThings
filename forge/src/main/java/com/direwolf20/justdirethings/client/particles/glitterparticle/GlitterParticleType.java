package com.direwolf20.justdirethings.client.particles.glitterparticle;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class GlitterParticleType extends ParticleType<GlitterParticleData> {

    private static final ParticleOptions.Deserializer<GlitterParticleData> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public GlitterParticleData fromCommand(ParticleType<GlitterParticleData> p_123846_, StringReader p_123847_) {
            return null;
        }

        @Override
        public GlitterParticleData fromNetwork(ParticleType<GlitterParticleData> pParticleType, FriendlyByteBuf pBuffer) {
            return GlitterParticleData.playerparticle(pBuffer.readUtf(),new Vec3(pBuffer.readDouble(),pBuffer.readDouble(),pBuffer.readDouble()),pBuffer.readFloat(),
                    pBuffer.readFloat(),pBuffer.readFloat(),pBuffer.readFloat(),pBuffer.readFloat(),pBuffer.readFloat(),pBuffer.readBoolean());
        }
    };

    public GlitterParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter,DESERIALIZER);
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
