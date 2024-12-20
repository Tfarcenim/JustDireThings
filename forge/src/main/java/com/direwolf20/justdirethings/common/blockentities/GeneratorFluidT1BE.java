package com.direwolf20.justdirethings.common.blockentities;

import com.direwolf20.justdirethings.common.blockentities.basebe.*;
import com.direwolf20.justdirethings.common.capabilities.EnergyStorageNoReceive;
import com.direwolf20.justdirethings.common.capabilities.GeneratorFluidItemHandler;
import com.direwolf20.justdirethings.common.capabilities.JustDireFluidTank;
import com.direwolf20.justdirethings.common.capabilities.MachineEnergyStorage;
import com.direwolf20.justdirethings.common.fluids.basefluids.RefinedFuel;
import com.direwolf20.justdirethings.setup.Config;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.interfacehelpers.RedstoneControlData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeneratorFluidT1BE extends BaseMachineBE implements RedstoneControlledBE, PoweredMachineBE, FluidMachineBE {
    public RedstoneControlData redstoneControlData = new RedstoneControlData();
    public final PoweredMachineContainerData poweredMachineData;
    public final FluidContainerData fluidContainerData;

    public GeneratorFluidT1BE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState, 1);
        fluidContainerData = new FluidContainerData(this);
        poweredMachineData = new PoweredMachineContainerData(this);
        generatorFluidItemHandler = new GeneratorFluidItemHandler(MACHINE_SLOTS);
    }

    public GeneratorFluidT1BE(BlockPos pPos, BlockState pBlockState) {
        this(Registration.GeneratorFluidT1BE.get(), pPos, pBlockState);
    }

    @Override
    public int getMaxMB() {
        return 4000;
    }

    JustDireFluidTank fluidTank = new JustDireFluidTank(getMaxMB(), fluidstack -> fluidstack.getFluid() instanceof RefinedFuel);

    public JustDireFluidTank getFluidTank() {
        return fluidTank;
    }

    @Override
    public ContainerData getFluidContainerData() {
        return fluidContainerData;
    }

    @Override
    public RedstoneControlData getRedstoneControlData() {
        return redstoneControlData;
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public ContainerData getContainerData() {
        return poweredMachineData;
    }

    protected MachineEnergyStorage energyStorage = new EnergyStorageNoReceive(getMaxEnergy());

    @Override
    public MachineEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public int getStandardEnergyCost() {
        return 0;
    }

    @Override
    public void tickClient() {
    }

    @Override
    public void tickServer() {
        super.tickServer();
        handleItemStack();
        doGenerate();
        providePowerAdjacent();
    }

    public ItemStack getItemStack() {
        return getMachineHandler().getStackInSlot(0);
    }

    protected final GeneratorFluidItemHandler generatorFluidItemHandler;
    @Override
    public ItemStackHandler getMachineHandler() {
        return generatorFluidItemHandler;
    }

    public boolean isStackValid(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;
        IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (fluidHandlerItem == null)
            return false;
        FluidStack fluidStack = fluidHandlerItem.drain(1000, IFluidHandler.FluidAction.SIMULATE);
        if (fluidStack.getAmount() == 0)
            return false;
        if (!getFluidStack().isEmpty() && !getFluidStack().isFluidEqual(fluidStack))
            return false;
        if (!(getFluidTank().isFluidValid(fluidStack)))
            return false;
        return true;
    }

    public void handleItemStack() {
        if (isFull()) return;
        ItemStack itemStack = getItemStack();
        if (!isStackValid(itemStack)) return;
        IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        FluidStack testExtract = fluidHandlerItem.drain(1000, IFluidHandler.FluidAction.SIMULATE);
        int insertAmt = getFluidTank().fill(testExtract, IFluidHandler.FluidAction.SIMULATE);
        if (insertAmt > 0) {
            FluidStack extractedStack = fluidHandlerItem.drain(insertAmt, IFluidHandler.FluidAction.EXECUTE);
            getFluidTank().fill(extractedStack, IFluidHandler.FluidAction.EXECUTE);
            if (itemStack.getItem() instanceof BucketItem)
                getMachineHandler().setStackInSlot(0, fluidHandlerItem.getContainer());
        }
    }

    @Override
    public int insertEnergy(int power, boolean simulate) {
        MachineEnergyStorage energyStorage = getEnergyStorage();
        if (energyStorage instanceof EnergyStorageNoReceive energyStorageNoReceive)
            return energyStorageNoReceive.forceReceiveEnergy(power, simulate);
        return 0;
    }

    public IEnergyStorage getHandler(Direction direction) {
        BlockPos targetPos = getBlockPos().relative(direction);
        BlockEntity blockEntity = level.getBlockEntity(targetPos);
        return blockEntity != null ? blockEntity.getCapability(ForgeCapabilities.ENERGY).orElse(null): null;
    }

    public void providePowerAdjacent() {
        if (getEnergyStorage().getEnergyStored() <= 0) return; //Don't bother if we're empty!
        for (Direction direction : Direction.values()) {
            IEnergyStorage iEnergyStorage = getHandler(direction);
            if (iEnergyStorage == null) continue;
            int amtFit = iEnergyStorage.receiveEnergy(getFEOutputPerTick() * 10, true);
            if (amtFit <= 0) continue;
            int extractAmt = extractEnergy(amtFit, false);
            iEnergyStorage.receiveEnergy(extractAmt, false);
        }
    }
    
    public void doGenerate() {
        if (!isActiveRedstone() || getFluidStack().isEmpty()) return;
        int fePerFuelTick = getFePerFuelTick();
        boolean canInsertEnergy = insertEnergy(fePerFuelTick, true) == fePerFuelTick;
        if (fePerFuelTick == 0 || !canInsertEnergy) return; //Don't burn if the buffer is full
        FluidStack extractedStack = getFluidTank().drain(1, IFluidHandler.FluidAction.EXECUTE);
        if (extractedStack.getAmount() == 0) return;

        //At this point we extracted some fuel!
        insertEnergy(fePerFuelTick, false); //Receive energy

        setChanged();
    }

    @Override
    public void handleTicks() {
        //NoOp
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return LazyOptional.of(() -> energyStorage).cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER) return LazyOptional.of(() -> fluidTank).cast();
        return cap == ForgeCapabilities.ITEM_HANDLER ? LazyOptional.of(() -> getMachineHandler()).cast() : super.getCapability(cap, side);
    }

    @Override
    public boolean canRun() {
        return true;
    }

    @Override
    public int getMaxEnergy() {
        return Config.GENERATOR_FLUID_T1_MAX_FE.get();
    }

    public int getFEOutputPerTick() {
        return Config.GENERATOR_FLUID_T1_FE_PER_TICK.get();
    }

    public int getFePerFuelTick() {
        return getFluidTank().getFluid().getFluid() instanceof RefinedFuel refinedFuel ? refinedFuel.fePerMb() : 0;
    }
}
