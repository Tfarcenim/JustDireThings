package com.direwolf20.justdirethings.client.renderers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.Mth;
import net.minecraftforge.client.model.pipeline.VertexConsumerWrapper;


public class DireVertexConsumer extends VertexConsumerWrapper {
    private float alpha;
    private float red = -1;
    private float green = -1;
    private float blue = -1;

    public DireVertexConsumer(VertexConsumer parent, float alpha) {
        super(parent);
        this.alpha = alpha;
    }

    public DireVertexConsumer(VertexConsumer parent, float alpha, float red, float green, float blue) {
        super(parent);
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public VertexConsumer color(int r, int g, int b, int a) {
        if (red == -1)
            parent.color(r, g, b, Math.round((float) 255 * alpha));
        else {
            int rCol = (int) Mth.lerp(red, 0, r);
            int gCol = (int) Mth.lerp(green, 0, g);
            int bCol = (int) Mth.lerp(blue, 0, b);
            parent.color(rCol, gCol, bCol, Math.round((float) 255 * alpha));
        }
        return this;
    }
}
