package com.direwolf20.justdirethings.client.particles;


import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.client.particles.alwaysvisibleparticle.AlwaysVisibleParticle;
import com.direwolf20.justdirethings.client.particles.glitterparticle.GlitterParticleType;
import com.direwolf20.justdirethings.client.particles.gooexplodeparticle.GooExplodeParticle;
import com.direwolf20.justdirethings.client.particles.itemparticle.ItemFlowParticle;
import com.direwolf20.justdirethings.client.particles.paradoxparticle.ParadoxParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JustDireThings.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRenderDispatcher {

    @SubscribeEvent
    public static void registerProviders(RegisterParticleProvidersEvent evt) {
        evt.registerSpecial(ModParticles.GOOEXPLODEPARTICLE.get(), GooExplodeParticle.FACTORY);
        evt.registerSpecial(ModParticles.ITEMFLOWPARTICLE.get(), ItemFlowParticle.FACTORY);
        evt.registerSpecial(ModParticles.ALWAYSVISIBLEPARTICLE.get(), AlwaysVisibleParticle.FACTORY);
        evt.registerSpriteSet(ModParticles.GLITTERPARTICLE.get(), GlitterParticleType.FACTORY::new);
        evt.registerSpecial(ModParticles.PARADOXPARTICLE.get(), ParadoxParticle.FACTORY);
    }
}
