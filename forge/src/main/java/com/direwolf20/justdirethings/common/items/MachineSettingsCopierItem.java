package com.direwolf20.justdirethings.common.items;

import com.direwolf20.justdirethings.client.screens.ModScreens;
import com.direwolf20.justdirethings.common.blockentities.basebe.AreaAffectingBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.FilterableBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.RedstoneControlledBE;
import com.direwolf20.justdirethings.common.containers.handlers.FilterBasicHandler;
import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MachineSettingsCopierItem extends Item {
    public MachineSettingsCopierItem() {
        super(new Properties()
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide() || !player.isShiftKeyDown())
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);

        if (level.isClientSide)
            ModScreens.openMachineSettingsCopierScreen(itemstack);

        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;
        BlockEntity blockEntity = level.getBlockEntity(pContext.getClickedPos());

        if (!(blockEntity instanceof BaseMachineBE)) return InteractionResult.PASS;
        ItemStack itemStack = pContext.getItemInHand();

        Player player = pContext.getPlayer();

        if (player.isShiftKeyDown()) { //Copy
            saveSettings(level, blockEntity, itemStack);
            player.displayClientMessage(Component.translatable("justdirethings.settingscopied"), true);
            player.playNotifySound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundSource.PLAYERS, 1.0F, 1.0F);
        } else { //Paste
            loadSettings(level, blockEntity, itemStack);
            player.displayClientMessage(Component.translatable("justdirethings.settingspasted"), true);
            player.playNotifySound(SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    public void loadSettings(Level level, BlockEntity blockEntity, ItemStack itemStack) {
        CompoundTag copiedMachineData = itemStack.getTagElement("copied_machine_data");
        if (copiedMachineData == null || copiedMachineData.isEmpty()) return;
        if (copiedMachineData.isEmpty()) return;

        if (blockEntity instanceof AreaAffectingBE areaAffectingBE) {
            if (getCopyArea(itemStack))
                areaAffectingBE.loadAreaOnly(copiedMachineData);
            if (getCopyOffset(itemStack))
                areaAffectingBE.loadOffsetOnly(copiedMachineData);
        }

        if (getCopyFilter(itemStack) && blockEntity instanceof FilterableBE filterableBE) {
            filterableBE.loadFilterSettings(copiedMachineData);
            if (copiedMachineData.contains("filteredItems")) {
                CompoundTag filteredItems = copiedMachineData.getCompound("filteredItems");
                FilterBasicHandler filterBasicHandler = filterableBE.getFilterHandler();
                filterBasicHandler.deserializeNBT( filteredItems);
            }
        }

        if (getCopyRedstone(itemStack) && blockEntity instanceof RedstoneControlledBE redstoneControlledBE)
            redstoneControlledBE.loadRedstoneSettings(copiedMachineData);

        ((BaseMachineBE) blockEntity).markDirtyClient();
    }

    public void saveSettings(Level level, BlockEntity blockEntity, ItemStack itemStack) {
        CompoundTag compoundTag = new CompoundTag();
        if (blockEntity instanceof AreaAffectingBE areaAffectingBE) {
            if (getCopyArea(itemStack))
                areaAffectingBE.saveAreaOnly(compoundTag);
            if (getCopyOffset(itemStack))
                areaAffectingBE.saveOffsetOnly(compoundTag);
        }

        if (getCopyFilter(itemStack) && blockEntity instanceof FilterableBE filterableBE) {
            filterableBE.saveFilterSettings(compoundTag);
            FilterBasicHandler filterBasicHandler = filterableBE.getFilterHandler();
            compoundTag.put("filteredItems", filterBasicHandler.serializeNBT());
        }

        if (getCopyRedstone(itemStack) && blockEntity instanceof RedstoneControlledBE redstoneControlledBE)
            redstoneControlledBE.saveRedstoneSettings(compoundTag);

        if (!compoundTag.isEmpty())
            itemStack.getOrCreateTag().put("copied_machine_data",compoundTag);
    }

    public static void setSettings(ItemStack itemStack, boolean area, boolean offset, boolean filter, boolean redstone) {
        JustDireDataComponents.setCopyAreaSettings(itemStack,area);
        JustDireDataComponents.setCopyOffsetSettings(itemStack,offset);
        JustDireDataComponents.setCopyFilterSettings(itemStack,filter);
        JustDireDataComponents.setCopyRedstoneSettings(itemStack,redstone);
    }

    public static boolean getCopyArea(ItemStack itemStack) {
        Boolean area = JustDireDataComponents.getCopyAreaSettings(itemStack);
        return area == null || area;
    }

    public static boolean getCopyOffset(ItemStack itemStack) {
        Boolean offset = JustDireDataComponents.getCopyOffsetSettings(itemStack);
        return offset == null || offset;
    }

    public static boolean getCopyFilter(ItemStack itemStack) {
        Boolean filter = JustDireDataComponents.getCopyFilterSettings(itemStack);
        return filter == null || filter;
    }

    public static boolean getCopyRedstone(ItemStack itemStack) {
        Boolean redstone = JustDireDataComponents.getCopyRedstoneSettings(itemStack);
        return redstone == null  || redstone;
    }
}
