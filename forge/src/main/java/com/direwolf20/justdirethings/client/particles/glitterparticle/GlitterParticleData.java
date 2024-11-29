package com.direwolf20.justdirethings.client.particles.glitterparticle;

import com.direwolf20.justdirethings.client.particles.ModParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class GlitterParticleData implements ParticleOptions {
    public static final Codec<GlitterParticleData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("partType").forGetter(p -> p.partType),
                    Vec3.CODEC.fieldOf("target").forGetter(p -> p.target),
                  //  Codec.DOUBLE.fieldOf("targetX").forGetter(p -> p.targetX),
                 //   Codec.DOUBLE.fieldOf("targetY").forGetter(p -> p.targetY),
                //    Codec.DOUBLE.fieldOf("targetZ").forGetter(p -> p.targetZ),
                    Codec.FLOAT.fieldOf("size").forGetter(p -> p.size),
                    Codec.FLOAT.fieldOf("r").forGetter(p -> p.r),
                    Codec.FLOAT.fieldOf("g").forGetter(p -> p.g),
                    Codec.FLOAT.fieldOf("b").forGetter(p -> p.b),
                    Codec.FLOAT.fieldOf("a").forGetter(p -> p.a),
                    Codec.FLOAT.fieldOf("maxAgeMul").forGetter(p -> p.maxAgeMul),
                    Codec.BOOL.fieldOf("depthTest").forGetter(p -> p.depthTest)
            ).apply(instance, GlitterParticleData::new));

    public final float size;
    public final float r, g, b, a;
    public final float maxAgeMul;
    public final boolean depthTest;
    public final Vec3 target;
    public final String partType;

    public static GlitterParticleData playerparticle(String type, Vec3 target, float size, float r, float g, float b) {
        return playerparticle(type, target, size, r, g, b, 1);
    }

    public static GlitterParticleData playerparticle(String type, Vec3 target, float size, float r, float g, float b, float maxAgeMul) {
        return playerparticle(type, target, size, r, g, b, 1f, maxAgeMul, true);
    }

    public static GlitterParticleData playerparticle(String type, Vec3 target, float size, float r, float g, float b, boolean depth) {
        return playerparticle(type, target, size, r, g, b, 1f, 1, depth);
    }

    public static GlitterParticleData playerparticle(String type, Vec3 target, float size, float r, float g, float b, float a, float maxAgeMul, boolean depthTest) {
        return new GlitterParticleData(type, target, size, r, g, b, a, maxAgeMul, depthTest);
    }

    private GlitterParticleData(String type, Vec3 target, float size, float r, float g, float b, float a, float maxAgeMul, boolean depthTest) {
        this.target = target;
        this.size = size;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.maxAgeMul = maxAgeMul;
        this.depthTest = depthTest;
        this.partType = type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeUtf(partType);
        pBuffer.writeDouble(target.x);
        pBuffer.writeDouble(target.y);
        pBuffer.writeDouble(target.z);
        pBuffer.writeFloat(size);
        pBuffer.writeFloat(r);
        pBuffer.writeFloat(g);
        pBuffer.writeFloat(b);
        pBuffer.writeFloat(a);
        pBuffer.writeFloat(maxAgeMul);
        pBuffer.writeBoolean(depthTest);
    }

    @Override
    public String writeToString() {//todo
        return "";
    }

    @Nonnull
    @Override
    public ParticleType<GlitterParticleData> getType() {
        return ModParticles.GLITTERPARTICLE.get();
    }

    public float getSize() {
        return size;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getA() {
        return a;
    }

    public float getMaxAgeMul() {
        return maxAgeMul;
    }

    public boolean isDepthTest() {
        return depthTest;
    }

    public Vec3 getTarget() {
        return target;
    }

    public String getPartType() {
        return partType;
    }
}
