package com.direwolf20.justdirethings.client.particles.glitterparticle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import java.util.Random;

public class GlitterParticle extends TextureSheetParticle {
    private double sourceX;
    private double sourceY;
    private double sourceZ;
    private Vec3 target;
    private int speedModifier;
    private String particleType;
    private Random rand = new Random();
    private int particlePicker;
    protected final SpriteSet spriteSet;

    public GlitterParticle(ClientLevel world, double sourceX, double sourceY, double sourceZ, Vec3 target, double xSpeed, double ySpeed, double zSpeed,
                           float size, float red, float green, float blue, float alpha, boolean collide, float maxAge, String particleType, SpriteSet sprite) {
        super(world, sourceX, sourceY, sourceZ);
        xd = xSpeed;
        yd = ySpeed;
        zd = zSpeed;
        rCol = red;
        gCol = green;
        bCol = blue;
        this.alpha = alpha;
        gravity = 0;
        this.lifetime = Math.round(maxAge);

        setSize(0.001F, 0.001F);

        xo = x;
        yo = y;
        zo = z;
        this.quadSize = size;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.sourceZ = sourceZ;

        this.target = target;
        this.hasPhysics = collide;
        this.particleType = particleType;
        this.setGravity(0f);
        particlePicker = rand.nextInt(3) + 1;
        this.spriteSet = sprite;
        this.setSprite(sprite.get(particlePicker, 4));
    }

    @Override
    public void render(VertexConsumer p_225606_1_, Camera p_225606_2_, float p_225606_3_) {
        super.render(p_225606_1_, p_225606_2_, p_225606_3_);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    // [VanillaCopy] of super, without drag when onGround is true
    @Override
    public void tick() {
        //System.out.println("I exist!" + posX+":"+posY+":"+posZ +"....."+targetX+":"+targetY+":"+targetZ);
        double moveX;
        double moveY;
        double moveZ;

        //Just in case something goes weird, we remove the particle if its been around too long.
        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        //prevPos is used in the render. if you don't do this your particle rubber bands (Like lag in an MMO).
        //This is used because ticks are 20 per second, and FPS is usually 60 or higher.
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        Vec3 sourcePos = new Vec3(sourceX, sourceY, sourceZ);

        //Get the current position of the particle, and figure out the vector of where it's going
        Vec3 partPos = new Vec3(this.x, this.y, this.z);
        Vec3 targetDirection = new Vec3(target.x() - this.x, target.y() - this.y, target.z() - this.z);

        //The total distance between the particle and target
        double totalDistance = target.distanceTo(partPos);
        if (totalDistance < 0.1)
            this.remove();

        double speedAdjust = 20;

        moveX = (target.x - this.x) / speedAdjust;
        moveY = (target.y - this.y) / speedAdjust;
        moveZ = (target.z - this.z) / speedAdjust;

        //This does not seem to be used but I will leave it here for now anyways
        BlockPos nextPos = new BlockPos((int) this.x + (int) moveX, (int) this.y + (int) moveY, (int) this.z + (int) moveZ);

        if (age > 40)
            this.hasPhysics = false;
        this.move(moveX, moveY, moveZ);
    }

    public void setGravity(float value) {
        gravity = value;
    }

    public void setSpeed(float mx, float my, float mz) {
        xd = mx;
        yd = my;
        zd = mz;
    }

}
