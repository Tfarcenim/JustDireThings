package com.direwolf20.justdirethings.client.screens.widgets;

import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory.TextureLocalization;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;

public class ToggleButton extends BaseButton {
    private List<TextureLocalization> textureLocalizations;
    private int texturePosition;
    private int maxValue = -1;

    public ToggleButton(int x, int y, int width, int height, List<TextureLocalization> textureLocalizations, int texturePosition, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, Button.DEFAULT_NARRATION);

        this.textureLocalizations = textureLocalizations;
        setTexturePosition(texturePosition);
    }

    public ToggleButton(int x, int y, int width, int height, List<TextureLocalization> textureLocalizations, int texturePosition, int maxValue, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, Button.DEFAULT_NARRATION);

        this.textureLocalizations = textureLocalizations;
        setTexturePosition(texturePosition);
        this.maxValue = maxValue;
    }

    public ToggleButton(int x, int y, int width, int height, List<TextureLocalization> textureLocalizations, boolean texturePosition, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress, Button.DEFAULT_NARRATION);

        this.textureLocalizations = textureLocalizations;
        setTexturePosition(texturePosition);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, textureLocalizations.get(getTexturePosition()).texture());
        guiGraphics.blit(textureLocalizations.get(getTexturePosition()).texture(), this.getX(), this.getY(), 0, 0, width, height, width, height);
    }


    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean flag = this.clicked(x, y);
                if (flag) {
                    this.playDownSound(Minecraft.getInstance().getSoundManager());

                    if (button == 1) previousTexturePosition();
                    else nextTexturePosition();

                    this.onClick(x, y);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isValidClickButton(int pButton) {
        return pButton == 0 || pButton == 1;
    }

    public int getTexturePosition() {
        return Mth.clamp(texturePosition,0,textureLocalizations.size() - 1);
    }

    public void setTexturePosition(boolean texturePosition) {
        setTexturePosition(texturePosition ? 1 : 0);
    }

    public void setTexturePosition(int texturePosition) {
        if (texturePosition >= textureLocalizations.size())
            this.texturePosition = textureLocalizations.size();
        else
            this.texturePosition = texturePosition;
    }

    public void nextTexturePosition() {
        if (maxValue == -1)
            texturePosition = (getTexturePosition() + 1) % textureLocalizations.size();
        else
            texturePosition = (getTexturePosition() + 1) % Math.min(textureLocalizations.size(), maxValue);
    }

    @Override
    public Component getLocalization() {
        return textureLocalizations.get(getTexturePosition()).localization();
    }

    public void previousTexturePosition() {
        if (maxValue == -1) {
            int size = textureLocalizations.size();
            texturePosition = (getTexturePosition() - 1 + size) % size;
        } else {
            int limit = Math.min(textureLocalizations.size(), maxValue); // Determine the effective limit
            texturePosition = (getTexturePosition() - 1 + limit) % limit;
        }
    }
}
