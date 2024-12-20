package com.direwolf20.justdirethings.client.screens;

import com.direwolf20.justdirethings.client.screens.basescreens.BaseMachineScreen;
import com.direwolf20.justdirethings.client.screens.standardbuttons.ToggleButtonFactory;
import com.direwolf20.justdirethings.client.screens.widgets.GrayscaleButton;
import com.direwolf20.justdirethings.client.screens.widgets.NumberButton;
import com.direwolf20.justdirethings.common.blockentities.ExperienceHolderBE;
import com.direwolf20.justdirethings.common.containers.ExperienceHolderContainer;
import com.direwolf20.justdirethings.network.server.C2SExperienceHolderPayload;
import com.direwolf20.justdirethings.network.server.C2SExperienceHolderSettingsPayload;
import com.direwolf20.justdirethings.platform.Services;
import com.direwolf20.justdirethings.util.ExperienceUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExperienceHolderScreen extends BaseMachineScreen<ExperienceHolderContainer> {
    private ExperienceHolderBE experienceHolderBE;
    private int exp;
    private int targetExp;
    private boolean ownerOnly;
    private boolean collectExp;
    public boolean showParticles = true;
    private static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = new ResourceLocation("hud/experience_bar_background");
    private static final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE = new ResourceLocation("hud/experience_bar_progress");

    public ExperienceHolderScreen(ExperienceHolderContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.experienceHolderBE = container.baseMachineBE;
        this.exp = container.baseMachineBE.exp;
        this.targetExp = container.baseMachineBE.targetExp;
        this.ownerOnly = container.baseMachineBE.ownerOnly;
        this.collectExp = container.baseMachineBE.collectExp;
        this.showParticles = container.baseMachineBE.showParticles;
    }

    @Override
    public void init() {
        super.init();
        addRenderableWidget(ToggleButtonFactory.STOREEXPBUTTON(topSectionLeft + (topSectionWidth / 2) + 15, topSectionTop + 62, true, b -> {
            int amt = 1;
            if (Screen.hasControlDown())
                amt = -1;
            else if (Screen.hasShiftDown())
                amt = amt * 10;
            Services.PLATFORM.sendToServer(new C2SExperienceHolderPayload(true, amt));
        }));
        addRenderableWidget(ToggleButtonFactory.EXTRACTEXPBUTTON(topSectionLeft + (topSectionWidth / 2) - 15 - 18, topSectionTop + 62, true, b -> {
            int amt = 1;
            if (Screen.hasControlDown())
                amt = -1;
            else if (Screen.hasShiftDown())
                amt = amt * 10;
            Services.PLATFORM.sendToServer(new C2SExperienceHolderPayload(false, amt));
        }));
        addRenderableWidget(ToggleButtonFactory.TARGETEXPBUTTON(topSectionLeft + (topSectionWidth / 2) - 15 - 42, topSectionTop + 64, targetExp, b -> {
            targetExp = ((NumberButton) b).getValue(); //The value is updated in the mouseClicked method below
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.OWNERONLYBUTTON(topSectionLeft + (topSectionWidth / 2) - 15 - 60, topSectionTop + 62, ownerOnly, b -> {
            ownerOnly = !ownerOnly;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.COLLECTEXPBUTTON(topSectionLeft + (topSectionWidth / 2) + 15, topSectionTop + 42, collectExp, b -> {
            collectExp = !collectExp;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
        addRenderableWidget(ToggleButtonFactory.SHOWPARTICLESBUTTON(topSectionLeft + (topSectionWidth / 2) + 31, topSectionTop + 42, showParticles, b -> {
            showParticles = !showParticles;
            ((GrayscaleButton) b).toggleActive();
            saveSettings();
        }));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        renderXPBar(guiGraphics, partialTicks, mouseX, mouseY);
    }

    public void renderXPBar(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int barX = topSectionLeft + (topSectionWidth / 2) - (182 / 2);  // Position for the XP bar in your GUI
        int barY = topSectionTop + topSectionHeight - 15;  // Y position for the XP bar

        // Bind the vanilla experience bar texture (this is the same texture used by the player XP bar)

        guiGraphics.blit(Gui.GUI_ICONS_LOCATION, barX, barY, 0, 64, 182, 5);//todo
        int partialAmount = (int) (ExperienceUtils.getProgressToNextLevel(experienceHolderBE.exp) * 183.0F);
        if (partialAmount > 0) {
            guiGraphics.blit(Gui.GUI_ICONS_LOCATION, 182, 5, 0, 0, barX, barY, partialAmount, 5);
        }
        String s = String.valueOf(ExperienceUtils.getLevelFromTotalExperience(experienceHolderBE.exp));
        int j = topSectionLeft + (topSectionWidth / 2) - font.width(s) / 2;
        int k = topSectionTop + 62 + (font.lineHeight / 2);
        guiGraphics.drawString(font, s, j + 1, k, 0, false);
        guiGraphics.drawString(font, s, j - 1, k, 0, false);
        guiGraphics.drawString(font, s, j, k + 1, 0, false);
        guiGraphics.drawString(font, s, j, k - 1, 0, false);
        guiGraphics.drawString(font, s, j, k, 8453920, false);
    }

    @Override
    public void saveSettings() {
        super.saveSettings();
        Services.PLATFORM.sendToServer(new C2SExperienceHolderSettingsPayload(targetExp, ownerOnly, collectExp, showParticles));
    }
}
