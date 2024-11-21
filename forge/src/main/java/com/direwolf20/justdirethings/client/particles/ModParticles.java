package com.direwolf20.justdirethings.client.particles;


import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.client.particles.alwaysvisibleparticle.AlwaysVisibleParticleType;
import com.direwolf20.justdirethings.client.particles.glitterparticle.GlitterParticleType;
import com.direwolf20.justdirethings.client.particles.gooexplodeparticle.GooExplodeParticleType;
import com.direwolf20.justdirethings.client.particles.itemparticle.ItemFlowParticleType;
import com.direwolf20.justdirethings.client.particles.paradoxparticle.ParadoxParticleData;
import com.direwolf20.justdirethings.client.particles.paradoxparticle.ParadoxParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, JustDireThings.MODID);
    public static final RegistryObject<ParticleType<?>> GOOEXPLODEPARTICLE = PARTICLE_TYPES.register("gooexplodeparticle", () -> new GooExplodeParticleType(false));
    public static final RegistryObject<ParticleType<?>> ITEMFLOWPARTICLE = PARTICLE_TYPES.register("itemflowparticle", () -> new ItemFlowParticleType(false));
    public static final RegistryObject<ParticleType<?>> ALWAYSVISIBLEPARTICLE = PARTICLE_TYPES.register("alwaysvisibleparticle", () -> new AlwaysVisibleParticleType(false));
    public static final RegistryObject<ParticleType<?>> GLITTERPARTICLE = PARTICLE_TYPES.register("glitter", () -> new GlitterParticleType(false));
    public static final RegistryObject<ParticleType<ParadoxParticleData>> PARADOXPARTICLE = PARTICLE_TYPES.register("paradox", () -> new ParadoxParticleType(false));
}