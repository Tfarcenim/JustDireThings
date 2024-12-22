package com.direwolf20.justdirethings.mixin;

import net.minecraftforge.client.model.generators.ModelBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = ModelBuilder.class,remap = false)
public interface ModelBuilderMixin {
    @Accessor
    Map<String, String> getTextures();
}
