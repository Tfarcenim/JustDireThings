package com.direwolf20.justdirethings.common.items;

import com.direwolf20.justdirethings.common.containers.PotionCanisterContainer;
import com.direwolf20.justdirethings.common.containers.handlers.PotionCanisterHandler;
import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import com.direwolf20.justdirethings.util.MagicHelpers;
import com.direwolf20.justdirethings.util.PotionContents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PotionCanisterItem extends Item {
    public PotionCanisterItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (level.isClientSide()) return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);

        NetworkHooks.openScreen((ServerPlayer) player,new SimpleMenuProvider(
                (windowId, playerInventory, playerEntity) -> new PotionCanisterContainer(windowId, playerInventory, player, itemstack), Component.empty()), (buf -> {
            buf.writeItem(itemstack);
        }));

        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
        super.appendHoverText(stack, level, tooltip, p_41424_);

        if (level == null) {
            return;
        }

        PotionContents potionContents = PotionCanisterItem.getPotionContents(stack);
        int potionAmt = PotionCanisterItem.getPotionAmount(stack);
        if (potionAmt == 0 || potionContents.equals(PotionContents.EMPTY)) return;

        tooltip.add(Component.literal(MagicHelpers.formatted(potionAmt) + "/" + MagicHelpers.formatted(PotionCanisterItem.getMaxMB())));
        potionContents.addPotionTooltip(tooltip, 1);
    }

    public static int getMaxMB() {
        return 1000;
    }

    public static PotionContents getPotionContents(ItemStack itemStack) {
        return JustDireDataComponents.getPotionContents(itemStack);
    }

    public static void setPotionContents(ItemStack itemStack, PotionContents potionContents) {
        JustDireDataComponents.setPotionContents(itemStack,potionContents);
    }

    public static void attemptFill(ItemStack canister) {
        if (!(canister.getItem() instanceof PotionCanisterItem)) return;
        PotionCanisterHandler handler = new PotionCanisterHandler(canister, "tool_contents", 1);
        ItemStack potion = handler.getStackInSlot(0);
        if (!(potion.getItem() instanceof PotionItem)) return;
        PotionContents currentContents = getPotionContents(canister);
        PotionContents newContents = PotionContents.fromItem(potion);
        if (currentContents.equals(PotionContents.EMPTY) || currentContents.equals(newContents)) {
            int currentAmt = getPotionAmount(canister);
            if (currentAmt + 250 <= getMaxMB()) {
                setPotionContents(canister, newContents);
                addPotionAmount(canister, 250);
                handler.setStackInSlot(0, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
    }

    public static int getPotionAmount(ItemStack itemStack) {
        Integer potionAmount = JustDireDataComponents.getPotionAmount(itemStack);
        return potionAmount == null ? 0 : potionAmount;
    }

    public static void addPotionAmount(ItemStack itemStack, int amt) {
        setPotionAmount(itemStack, getPotionAmount(itemStack) + amt);
    }

    public static void setPotionAmount(ItemStack itemStack, int amt) {
        JustDireDataComponents.setPotionAmount(itemStack, Math.max(0, Math.min(getMaxMB(), amt)));
        if (getPotionAmount(itemStack) == 0)
            setPotionContents(itemStack, PotionContents.EMPTY);
    }

    public static void reducePotionAmount(ItemStack itemStack, int amt) {
        setPotionAmount(itemStack, getPotionAmount(itemStack) - amt);
    }

    public static int getFullness(ItemStack itemStack) {
        int potionAmt = getPotionAmount(itemStack);
        if (potionAmt == 0) return 0;
        if (potionAmt > 0 && potionAmt <= 250) return 1;
        if (potionAmt > 250 && potionAmt <= 500) return 2;
        if (potionAmt > 500 && potionAmt <= 750) return 3;
        return 4;
    }

    public static int getPotionColor(ItemStack itemStack) {
        return getPotionContents(itemStack).getColor();
    }
}
