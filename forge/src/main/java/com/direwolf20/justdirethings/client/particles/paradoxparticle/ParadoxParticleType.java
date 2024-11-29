package com.direwolf20.justdirethings.client.particles.paradoxparticle;


import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class ParadoxParticleType extends ParticleType<ParadoxParticleData> {

    private static final ParticleOptions.Deserializer<ParadoxParticleData> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public ParadoxParticleData fromCommand(ParticleType<ParadoxParticleData> p_123846_, StringReader p_123847_) {
            return null;
        }

        @Override
        public ParadoxParticleData fromNetwork(ParticleType<ParadoxParticleData> pParticleType, FriendlyByteBuf pBuffer) {
            return new ParadoxParticleData(pBuffer.readItem(),pBuffer.readDouble(),pBuffer.readDouble(),pBuffer.readDouble(),pBuffer.readInt(),pBuffer.readUUID());
        }
    };

    public ParadoxParticleType(boolean p_123740_) {
        super(p_123740_,DESERIALIZER);
    }

    @Override
    public Codec<ParadoxParticleData> codec() {
        return ParadoxParticleData.MAP_CODEC;//todo
    }


}
