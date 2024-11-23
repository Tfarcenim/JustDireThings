package com.direwolf20.justdirethings.client.events;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.client.KeyBindings;
import com.direwolf20.justdirethings.client.screens.AdvPortalRadialMenu;
import com.direwolf20.justdirethings.common.items.PortalGunV2;
import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.LeftClickableTool;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableItem;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableTool;
import com.direwolf20.justdirethings.network.server.C2SLeftClickPayload;
import com.direwolf20.justdirethings.network.server.C2SMiscPayload;
import com.direwolf20.justdirethings.network.server.C2SToggleToolPayload;
import com.direwolf20.justdirethings.platform.Services;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.direwolf20.justdirethings.util.MiscTools.getHitResult;

@Mod.EventBusSubscriber(modid = JustDireThings.MODID, value = Dist.CLIENT)
public class EventKeyInput {

    @SubscribeEvent
    public static void handleEventInput(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;

        if (KeyBindings.toolUI.consumeClick()) {
            C2SMiscPayload.send(C2SMiscPayload.Action.TOOL_SETTINGS_GUI);
        }

        KeyMapping keyMapping = KeyBindings.toggleTool;
        ItemStack portalGun = PortalGunV2.getPortalGunv2(mc.player);
        if (!portalGun.isEmpty()) {
            if (!(mc.screen instanceof AdvPortalRadialMenu) && keyMapping.consumeClick() && ((keyMapping.getKeyModifier() == KeyModifier.NONE
                    && KeyModifier.getActiveModifier() == KeyModifier.NONE) || keyMapping.getKeyModifier() != KeyModifier.NONE)) {
                mc.setScreen(new AdvPortalRadialMenu(portalGun));
            }
        }

        ItemStack toggleableItem = ToggleableItem.getToggleableItem(mc.player);
        if (!toggleableItem.isEmpty()) {
            if (KeyBindings.toggleTool.consumeClick()) {
                Services.PLATFORM.sendToServer(new C2SToggleToolPayload("enabled"));
            }
        }
    }

    // Handling key presses
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || mc.screen != null)
            return;
        Player player = mc.player;
        if (event.getAction() == InputConstants.PRESS) {
            for (int i = 0; i < mc.player.getInventory().items.size(); i++) {
                ItemStack itemStack = mc.player.getInventory().getItem(i);
                if (itemStack.getItem() instanceof ToggleableTool toggleableTool && itemStack.getItem() instanceof LeftClickableTool) {
                    activateAbilities(itemStack, event.getKey(), toggleableTool, player, i, false);
                }
            }
            for (int i = mc.player.getInventory().items.size(); i < mc.player.getInventory().items.size() + mc.player.getInventory().armor.size(); i++) {
                ItemStack itemStack = mc.player.getInventory().getItem(i);
                if (itemStack.getItem() instanceof ToggleableTool toggleableTool && itemStack.getItem() instanceof LeftClickableTool) {
                    activateAbilities(itemStack, event.getKey(), toggleableTool, player, i, false);
                }
            }
            for (int i = mc.player.getInventory().items.size() + mc.player.getInventory().armor.size(); i < mc.player.getInventory().items.size() + mc.player.getInventory().armor.size() + mc.player.getInventory().offhand.size(); i++) {
                ItemStack itemStack = mc.player.getInventory().getItem(i);
                if (itemStack.getItem() instanceof ToggleableTool toggleableTool && itemStack.getItem() instanceof LeftClickableTool) {
                    activateAbilities(itemStack, event.getKey(), toggleableTool, player, i, false);
                }
            }

        }
    }

    // Handling mouse clicks
    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || mc.screen != null || event.getButton() == 0 || event.getButton() == 1 || event.getAction() != InputConstants.PRESS)
            return;
        Player player = mc.player;
        for (int i = 0; i < mc.player.getInventory().items.size(); i++) {
            ItemStack itemStack = mc.player.getInventory().getItem(i);
            if (itemStack.getItem() instanceof ToggleableTool toggleableTool && itemStack.getItem() instanceof LeftClickableTool) {
                activateAbilities(itemStack, event.getButton(), toggleableTool, player, i, true);
            }
        }
    }

    private static void activateAbilities(ItemStack itemStack, int key, ToggleableTool toggleableTool, Player player, int invSlot, boolean isMouse) {
        List<Ability> abilities = LeftClickableTool.getCustomBindingListFor(itemStack, key, isMouse, player);
        if (!abilities.isEmpty()) {
            //Do them client side and Server side, since some abilities (like ore scanner) are client side activated.
            toggleableTool.useAbility(player.level(), player, itemStack, key, isMouse);
            BlockHitResult blockHitResult = getHitResult(player);
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                UseOnContext useoncontext = new UseOnContext(player.level(), player, InteractionHand.MAIN_HAND, itemStack, blockHitResult);
                toggleableTool.useOnAbility(useoncontext, itemStack, key, isMouse);
            }
            Services.PLATFORM.sendToServer(new C2SLeftClickPayload(0, false, BlockPos.ZERO, Direction.UP, invSlot, key, isMouse)); //Type 0 == air
        }
    }
}
