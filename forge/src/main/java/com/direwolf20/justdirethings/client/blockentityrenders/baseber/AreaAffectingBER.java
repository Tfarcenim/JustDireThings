package com.direwolf20.justdirethings.client.blockentityrenders.baseber;

import com.direwolf20.justdirethings.client.renderers.RenderHelpers;
import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;

import java.awt.*;

public class AreaAffectingBER<B extends BlockEntity & AreaAffectingBE> implements BlockEntityRenderer<B> {

    @Override
    public void render(B blockentity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightsIn, int combinedOverlayIn) {
        Matrix4f matrix4f = matrixStackIn.last().pose();
        if (blockentity.getAreaAffectingData().renderArea) {
            RenderHelpers.renderLines(matrixStackIn, blockentity.getAABB(BlockPos.ZERO), Color.GREEN, bufferIn);
            RenderHelpers.renderBoxSolid(matrixStackIn, matrix4f, bufferIn, blockentity.getAABB(BlockPos.ZERO), 1, 0, 0, 0.125f);
            if (blockentity.getAreaAffectingData().xRadius > 0 || blockentity.getAreaAffectingData().yRadius > 0 || blockentity.getAreaAffectingData().zRadius > 0) {
                RenderHelpers.renderLines(matrixStackIn, blockentity.getAABBOffsetOnly(BlockPos.ZERO), Color.WHITE, bufferIn);
                RenderHelpers.renderBoxSolid(matrixStackIn, matrix4f, bufferIn, blockentity.getAABBOffsetOnly(BlockPos.ZERO), 0, 0, 1, 0.125f);
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(B pBlockEntity) {
        return true;
    }
}
