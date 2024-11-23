package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.common.containers.PotionCanisterContainer;
import com.direwolf20.justdirethings.common.items.PotionCanisterItem;
import com.direwolf20.justdirethings.util.MagicHelpers;
import com.direwolf20.justdirethings.util.MiscTools;
import com.direwolf20.justdirethings.util.PotionContents;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PotionCanisterScreen extends AbstractContainerScreen<PotionCanisterContainer> {
    private final ResourceLocation GUI = JustDireThings.id("textures/gui/fuelcanister.png");
    protected final ResourceLocation FLUIDBAR = JustDireThings.id("textures/gui/fluidbar.png");
    ResourceLocation potionOverlay = new ResourceLocation("item/potion_overlay");


    protected final PotionCanisterContainer container;
    private ItemStack potionCanister;

    public PotionCanisterScreen(PotionCanisterContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.container = container;
        this.potionCanister = container.potionCanister;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        fluidBarTooltip(pGuiGraphics, pX, pY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        //super.renderLabels(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        int offset = 5;
        guiGraphics.blit(FLUIDBAR, getGuiLeft() + offset, getGuiTop() + 5, 0, 0, 18, 72, 36, 72);
        int maxMB = PotionCanisterItem.getMaxMB(), height = 70;
        if (maxMB > 0) {
            int remaining = (PotionCanisterItem.getPotionAmount(potionCanister) * height) / maxMB;
            renderFluid(guiGraphics, getGuiLeft() + offset + 1, getGuiTop() + 5 + 72 - 1, 16, remaining, potionCanister);
        }
        guiGraphics.blit(FLUIDBAR, getGuiLeft() + offset, getGuiTop() + 5, 18, 0, 18, 72, 36, 72);
    }

    public void renderFluid(GuiGraphics guiGraphics, int startX, int startY, int width, int height, ItemStack potionCanister) {
        PotionContents potionContents = PotionCanisterItem.getPotionContents(potionCanister);
        if (potionContents.equals(PotionContents.EMPTY) || PotionCanisterItem.getPotionAmount(potionCanister) <= 0) return;
        ResourceLocation fluidStill = IClientFluidTypeExtensions.of(Fluids.WATER).getStillTexture();
        TextureAtlasSprite fluidStillSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
        int fluidColor = potionContents.getColor();

        float red = (float) (fluidColor >> 16 & 255) / 255.0F;
        float green = (float) (fluidColor >> 8 & 255) / 255.0F;
        float blue = (float) (fluidColor & 255) / 255.0F;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        RenderSystem.setShaderColor(red, green, blue, 1.0f);

        int zLevel = 0;
        float uMin = fluidStillSprite.getU0();
        float uMax = fluidStillSprite.getU1();
        float vMin = fluidStillSprite.getV0();
        float vMax = fluidStillSprite.getV1();
        int textureWidth = fluidStillSprite.contents().width();
        int textureHeight = fluidStillSprite.contents().height();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder vertexBuffer = tesselator.getBuilder();
        vertexBuffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        int yOffset = 0;
        while (yOffset < height) {
            int drawHeight = Math.min(textureHeight, height - yOffset);
            int drawY = startY - yOffset - drawHeight; // Adjust for bottom-to-top drawing

            float vMaxAdjusted = vMin + (vMax - vMin) * ((float) drawHeight / textureHeight);

            int xOffset = 0;
            while (xOffset < width) {
                int drawWidth = Math.min(textureWidth, width - xOffset);

                float uMaxAdjusted = uMin + (uMax - uMin) * ((float) drawWidth / textureWidth);

                vertexBuffer.vertex(poseStack.last().pose(), startX + xOffset, drawY + drawHeight, zLevel).uv(uMin, vMaxAdjusted);
                vertexBuffer.vertex(poseStack.last().pose(), startX + xOffset + drawWidth, drawY + drawHeight, zLevel).uv(uMaxAdjusted, vMaxAdjusted);
                vertexBuffer.vertex(poseStack.last().pose(), startX + xOffset + drawWidth, drawY, zLevel).uv(uMaxAdjusted, vMin);
                vertexBuffer.vertex(poseStack.last().pose(), startX + xOffset, drawY, zLevel).uv(uMin, vMin);

                xOffset += drawWidth;
            }
            yOffset += drawHeight;
        }

        BufferUploader.drawWithShader(vertexBuffer.end());
        poseStack.popPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.applyModelViewMatrix();
    }

    public void fluidBarTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        PotionContents potionContents = PotionCanisterItem.getPotionContents(potionCanister);
        int potionAmt = PotionCanisterItem.getPotionAmount(potionCanister);
        if (potionAmt == 0 || potionContents.equals(PotionContents.EMPTY)) return;
        if (MiscTools.inBounds(getGuiLeft() + 5, getGuiTop() + 5, 18, 72, pX, pY)) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal(MagicHelpers.formatted(potionAmt) + "/" + MagicHelpers.formatted(PotionCanisterItem.getMaxMB())));
            potionContents.addPotionTooltip(components, 1);
            pGuiGraphics.renderTooltip(font, components, Optional.empty(), pX, pY);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        InputConstants.Key mouseKey = InputConstants.getKey(p_keyPressed_1_, p_keyPressed_2_);
        if (p_keyPressed_1_ == 256 || minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            onClose();

            return true;
        }

        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public boolean mouseClicked(double x, double y, int btn) {
        return super.mouseClicked(x, y, btn);
    }

    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
        return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
    }


    private static MutableComponent getTrans(String key, Object... args) {
        return Component.translatable(JustDireThings.MODID + "." + key, args);
    }

}
