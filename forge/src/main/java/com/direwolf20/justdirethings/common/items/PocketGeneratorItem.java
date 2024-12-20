package com.direwolf20.justdirethings.common.items;

import com.direwolf20.justdirethings.common.blocks.resources.CoalBlock_T1;
import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemStackNoReceive;
import com.direwolf20.justdirethings.common.containers.PocketGeneratorContainer;
import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import com.direwolf20.justdirethings.common.items.interfaces.PoweredItem;
import com.direwolf20.justdirethings.common.items.interfaces.ToggleableItem;
import com.direwolf20.justdirethings.common.items.resources.TieredCoalItem;
import com.direwolf20.justdirethings.setup.Config;
import com.direwolf20.justdirethings.util.ItemStackNBTHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.direwolf20.justdirethings.util.TooltipHelpers.*;

public class PocketGeneratorItem extends Item implements PoweredItem, ToggleableItem {
    public PocketGeneratorItem() {
        super(new Properties()
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (level.isClientSide()) return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);

        if (!player.isShiftKeyDown()) {
            NetworkHooks.openScreen((ServerPlayer) player,new SimpleMenuProvider(
                    (windowId, playerInventory, playerEntity) -> new PocketGeneratorContainer(windowId, playerInventory, player, itemstack), Component.empty()), buf -> buf.writeItem(itemstack));
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if (world.isClientSide) return;
        if (entity instanceof Player player && itemStack.getItem() instanceof ToggleableItem toggleableItem && toggleableItem.getEnabled(itemStack)) {
            IEnergyStorage energyStorage = itemStack.getCapability(ForgeCapabilities.ENERGY).orElse(null);
            if (energyStorage == null) return;
            if (energyStorage instanceof EnergyStorageItemStackNoReceive EnergyStorageItemStackNoReceive) {
                tryBurn(EnergyStorageItemStackNoReceive, itemStack);
                if (energyStorage.getEnergyStored() >= (getFEPerTick() / 10)) { //If we have 1/10th the max transfer speed, go ahead and let it rip
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack slotStack = player.getInventory().getItem(i);
                        IEnergyStorage slotEnergy = slotStack.getCapability(ForgeCapabilities.ENERGY).orElse(null);
                        if (slotEnergy != null) {
                            int acceptedEnergy = slotEnergy.receiveEnergy(getFEPerTick(), true);
                            if (acceptedEnergy > 0) {
                                int extractedEnergy = energyStorage.extractEnergy(acceptedEnergy, false);
                                slotEnergy.receiveEnergy(extractedEnergy, false);
                            }
                        }
                    }
                }
            }
        }
    }

    private int fePerTick(ItemStack itemStack) {
        return (getFePerFuelTick() * getBurnSpeedMultiplier(itemStack));
    }

    public void tryBurn(EnergyStorageItemStackNoReceive energyStorage, ItemStack itemStack) {
        boolean canInsertEnergy = energyStorage.forceReceiveEnergy(fePerTick(itemStack), true) > 0;

        Integer c = JustDireDataComponents.getPocketgenCounter(itemStack);
        int counter = c == null ? 0 : c;
        if (counter > 0 && canInsertEnergy) {
            burn(energyStorage, itemStack);
        } else if (canInsertEnergy) {
            if (initBurn(itemStack))
                burn(energyStorage, itemStack);
        }
    }


    private void burn(EnergyStorageItemStackNoReceive energyStorage, ItemStack itemStack) {
        energyStorage.forceReceiveEnergy(fePerTick(itemStack), false);
        Integer c = JustDireDataComponents.getPocketgenCounter(itemStack);
        int counter = c == null ? 0 : c;
        counter--;
        JustDireDataComponents.setPocketgenCounter(itemStack,counter);
        if (counter == 0) {
            JustDireDataComponents.setPocketgenMaxburn(itemStack,0);
            initBurn(itemStack);
        }
    }

    private boolean initBurn(ItemStack itemStack) {
        ItemStackNBTHandler handler = new ItemStackNBTHandler(itemStack, JustDireDataComponents.ITEMSTACK_HANDLER, 1);
        ItemStack fuelStack = handler.getStackInSlot(0);

        int burnTime = ForgeHooks.getBurnTime(fuelStack,RecipeType.SMELTING);
        if (burnTime > 0) {
            if (fuelStack.getItem() instanceof TieredCoalItem direCoal) {
                setFuelMultiplier(itemStack, direCoal.getBurnSpeedMultiplier());
            } else if (fuelStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CoalBlock_T1 coalBlock) {
                setFuelMultiplier(itemStack, coalBlock.getBurnSpeedMultiplier());
            } else if (fuelStack.getItem() instanceof FuelCanisterItem) {
                setFuelMultiplier(itemStack, FuelCanisterItem.getBurnSpeedMultiplier(fuelStack));
            } else {
                setFuelMultiplier(itemStack, 1);
            }
            if (fuelStack.hasCraftingRemainingItem())
                handler.setStackInSlot(0, fuelStack.getCraftingRemainingItem());
            else {
                fuelStack.shrink(1);
                handler.setStackInSlot(0, fuelStack);
            }


            int counter = (int) (Math.floor(burnTime) / getBurnSpeedMultiplier(itemStack));
            int maxBurn = counter;
            JustDireDataComponents.setPocketgenCounter(itemStack, counter);
            JustDireDataComponents.setPocketgenMaxburn(itemStack, maxBurn);
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        if (level == null) {
            return;
        }
        appendFEText(stack, tooltip);
        appendToolEnabled(stack, tooltip);
        appendGeneratorDetails(stack, tooltip);
        appendShiftForInfo(stack, tooltip);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return isPowerBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return getPowerBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int color = getPowerBarColor(stack);
        if (color == -1)
            return super.getBarColor(stack);
        return color;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public void setFuelMultiplier(ItemStack itemStack, int amount) {
        JustDireDataComponents.setPocketgenFuelmult(itemStack, amount);
    }

    public int getFuelMultiplier(ItemStack itemStack) {
        Integer fuelMulti = JustDireDataComponents.getPocketgenFuelmult(itemStack);
        return fuelMulti == null ? 1 : fuelMulti;
    }

    @Override
    public int getMaxEnergy() {
        return Config.POCKET_GENERATOR_MAX_FE.get();
    }

    public int getFEPerTick() {
        return Config.POCKET_GENERATOR_FE_PER_TICK.get();
    }

    public int getFePerFuelTick() {
        return Config.POCKET_GENERATOR_FE_PER_FUEL_TICK.get();
    }

    public int getBurnSpeedMultiplier(ItemStack itemStack) {
        return Config.POCKET_GENERATOR_BURN_SPEED_MULTIPLIER.get() * getFuelMultiplier(itemStack);
    }

    public IItemHandler getItemHandler(ItemStack stack) {
        return new ItemStackNBTHandler(stack,JustDireDataComponents.ITEMSTACK_HANDLER,1);
    }

    public EnergyStorage getEnergyStorage(ItemStack stack) {
        int capacity = 1000000; //Default
        if (stack.getItem() instanceof PoweredItem poweredItem) {
            capacity = poweredItem.getMaxEnergy();
        }
        return new EnergyStorageItemStackNoReceive(capacity, stack);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new Provider(stack);
    }

    class Provider implements ICapabilityProvider {

        private final ItemStack stack;

        public Provider(ItemStack stack) {
            this.stack = stack;
            itemHolder = LazyOptional.of(() -> getItemHandler(this.stack));
            energyHolder = LazyOptional.of(() -> getEnergyStorage(stack));
        }

        private final LazyOptional<IItemHandler> itemHolder;
        private final LazyOptional<IEnergyStorage> energyHolder;

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == ForgeCapabilities.ITEM_HANDLER) {
                return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, this.itemHolder);
            } else {
                return ForgeCapabilities.ENERGY.orEmpty(cap,energyHolder);
            }
        }
    }
}
