package com.direwolf20.justdirethings.client.particles.alwaysvisibleparticle;

import com.direwolf20.justdirethings.client.particles.ModParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AlwaysVisibleParticleData implements ParticleOptions {
    public static final Codec<AlwaysVisibleParticleData> MAP_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ResourceLocation.CODEC.fieldOf("resourceLocation").forGetter(AlwaysVisibleParticleData::getResourceLocation)
                    )
                    .apply(instance, AlwaysVisibleParticleData::new)
    );

    ResourceLocation resourceLocation;

    public AlwaysVisibleParticleData(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeResourceLocation(resourceLocation);
    }

    @Override
    public String writeToString() {
        return "";//todo
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.ALWAYSVISIBLEPARTICLE.get();
    }

}
