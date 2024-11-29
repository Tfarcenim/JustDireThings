package com.direwolf20.justdirethings.client;

import com.direwolf20.justdirethings.JustDireThings;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class TintedFluidTypeExtension implements IClientFluidTypeExtensions {
    public static final ResourceLocation UNDERWATER_LOCATION = new ResourceLocation("textures/misc/underwater.png");
    public static final ResourceLocation WATER_STILL = JustDireThings.id("block/fluid_source");
    public static final ResourceLocation WATER_FLOW = JustDireThings.id("block/fluid_flowing");
    public static final ResourceLocation WATER_OVERLAY = JustDireThings.id("block/fluid_overlay");
    private final int tint;

    public TintedFluidTypeExtension(int tint) {

        this.tint = tint;
    }

    @Override
    public ResourceLocation getStillTexture() {
        return WATER_STILL;
    }

    @Override
    public ResourceLocation getFlowingTexture() {
        return WATER_FLOW;
    }

    @Override
    public ResourceLocation getOverlayTexture() {
        return WATER_OVERLAY;
    }

    @Override
    public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
        return UNDERWATER_LOCATION;
    }

    @Override
    public int getTintColor() {
        return tint;
    }
}
