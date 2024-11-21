package com.direwolf20.justdirethings.common.blockentities;

import com.direwolf20.justdirethings.client.particles.itemparticle.ItemFlowParticleData;
import com.direwolf20.justdirethings.common.blockentities.basebe.*;
import com.direwolf20.justdirethings.common.blocks.EnergyTransmitter;
import com.direwolf20.justdirethings.common.capabilities.EnergyStorageItemStackNoReceive;
import com.direwolf20.justdirethings.common.capabilities.TransmitterEnergyStorage;
import com.direwolf20.justdirethings.common.containers.handlers.FilterBasicHandler;
import com.direwolf20.justdirethings.common.items.PocketGenerator;
import com.direwolf20.justdirethings.setup.Config;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.interfacehelpers.AreaAffectingData;
import com.direwolf20.justdirethings.util.interfacehelpers.FilterData;
import com.direwolf20.justdirethings.util.interfacehelpers.RedstoneControlData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.*;
import java.util.stream.Collectors;

public class EnergyTransmitterBE extends BaseMachineBE implements RedstoneControlledBE, PoweredMachineBE, AreaAffectingBE, FilterableBE {
    public RedstoneControlData redstoneControlData = new RedstoneControlData();
    public final PoweredMachineContainerData poweredMachineData;
    private final Map<BlockPos, BlockCapabilityCache<IEnergyStorage, Direction>> energyHandlers = new HashMap<>();
    private final Map<BlockPos, BlockCapabilityCache<IEnergyStorage, Direction>> transmitterHandlers = new HashMap<>();
    private final Set<BlockPos> blocksToCharge = new HashSet<>();
    private final Set<BlockPos> transmitters = new HashSet<>();
    public AreaAffectingData areaAffectingData = new AreaAffectingData();
    public FilterData filterData = new FilterData();
    public boolean showParticles = true;

    public EnergyTransmitterBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        MACHINE_SLOTS = 1;
        poweredMachineData = new PoweredMachineContainerData(this);
        tickSpeed = 50; //We use this to check how often to rescan the area
    }

    public EnergyTransmitterBE(BlockPos pPos, BlockState pBlockState) {
        this(Registration.EnergyTransmitterBE.get(), pPos, pBlockState);
    }

    public void setEnergyTransmitterSettings(boolean showParticles) {
        this.showParticles = showParticles;
        markDirtyClient();
    }

    @Override
    public FilterBasicHandler getFilterHandler() {
        return getData(Registration.HANDLER_BASIC_FILTER);
    }

    @Override
    public FilterData getFilterData() {
        return filterData;
    }

    @Override
    public AreaAffectingData getAreaAffectingData() {
        return areaAffectingData;
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

    @Override
    public TransmitterEnergyStorage getEnergyStorage() {
        return getData(Registration.ENERGYSTORAGE_TRANSMITTERS);
    }

    @Override
    public int getEnergyStored() {
        return getEnergyStorage().getRealEnergyStored();
    }

    @Override
    public int getStandardEnergyCost() {
        return 0;
    }

    @Override
    public void tickClient() {
    }

    /**
     * Get an UPDATED and ACTIVE list of transmitters that are currently available (The cache'd cap still exists) via getTransmitterEnergyHandler
     */
    public Map<BlockPos, TransmitterEnergyStorage> getTransmitterEnergyStorages() {
        return transmitters.stream()
                .map(pos -> new AbstractMap.SimpleEntry<>(pos, getTransmitterEnergyHandler(pos)))
                .filter(entry -> entry.getValue() != null) // Filter out entries with null TransmitterEnergyStorages
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    /**
     * Balance the energy across the entire network of transmitters that THIS transmitter can see/access. Other transmitters will handle balancing amongst their own network
     */
    public void balanceEnergy() {
        if (transmitters.isEmpty() || transmitters.size() == 1) return;
        Map<BlockPos, TransmitterEnergyStorage> transmitterEnergyStorages = getTransmitterEnergyStorages();

        int totalEnergy = transmitterEnergyStorages.values().stream()
                .mapToInt(TransmitterEnergyStorage::getRealEnergyStored) // Convert each storage to an int representing its stored energy
                .sum(); // Sum up all the values
        int count = transmitterEnergyStorages.size();
        int averageEnergy = totalEnergy / count;
        int remainder = totalEnergy % count;

        // Check if already balanced
        if (isAlreadyBalanced(transmitterEnergyStorages, averageEnergy, remainder)) {
            return;
        }

        // Redistribution
        int i = 0;
        for (Map.Entry<BlockPos, TransmitterEnergyStorage> entry : transmitterEnergyStorages.entrySet()) {
            if (i < remainder) {
                entry.getValue().setEnergy(averageEnergy + 1);
            } else {
                entry.getValue().setEnergy(averageEnergy);
            }
            i++;
            doParticles(getBlockPos(), entry.getKey());
        }
    }

    /**
     * Checks to see if the rest of the network is balanced, within a reasonable margin of error (1 if theres a remainder value)
     */
    private boolean isAlreadyBalanced(Map<BlockPos, TransmitterEnergyStorage> transmitterEnergyStorages, int averageEnergy, int remainder) {
        int minEnergy = averageEnergy;
        int maxEnergy = averageEnergy + (remainder > 0 ? 1 : 0);

        return transmitterEnergyStorages.values().stream().allMatch((transmitterEnergyStorage) -> {
            int energy = transmitterEnergyStorage.getRealEnergyStored();
            return energy == minEnergy || energy == maxEnergy;
        });
    }

    /**
     * Get total energy stored across this transmitters entire network
     */
    public int getTotalEnergyStored() {
        return getTransmitterEnergyStorages().values().stream()
                .mapToInt(TransmitterEnergyStorage::getRealEnergyStored) // Convert each storage to an int representing its stored energy
                .sum();
    }

    /**
     * Get total energy possible to store across this transmitters entire network
     */
    public int getTotalMaxEnergyStored() {
        return getTransmitterEnergyStorages().values().stream()
                .mapToInt(TransmitterEnergyStorage::getRealMaxEnergyStored) // Convert each storage to an int representing its stored energy
                .sum();
    }


    /**
     * Add Energy to the network - called by TransmitterEnergyStorage.receiveEnergy
     */
    public int distributeEnergy(int energy) {
        int energyInserted = 0;
        for (TransmitterEnergyStorage transmitterEnergyStorage : getTransmitterEnergyStorages().values()) {
            int insertedEnergy = transmitterEnergyStorage.realReceiveEnergy(energy, false);
            energy = energy - insertedEnergy;
            energyInserted = energyInserted + insertedEnergy;
            if (energy <= 0) break;
        }
        //balanceEnergy();
        return energyInserted;
    }

    /**
     * Extract Energy from the network - called by TransmitterEnergyStorage.extractEnergy
     */
    public int extractEnergy(int energy) {
        int energyExtracted = 0;
        for (TransmitterEnergyStorage transmitterEnergyStorage : getTransmitterEnergyStorages().values()) {
            int extractedEnergy = transmitterEnergyStorage.realExtractEnergy(energy, false);
            energy = energy - extractedEnergy;
            energyExtracted = energyExtracted + extractedEnergy;
            if (energy <= 0) break;
        }
        //balanceEnergy();
        return energyExtracted;
    }

    @Override
    public void tickServer() {
        super.tickServer();
        if (isActiveRedstone()) {
            if (canRun())
                getBlocksToCharge();
            drainFromSlot();
            providePower();
        }
    }

    public void doParticles(BlockPos sourcePos, BlockPos targetPos) {
        if (!showParticles) return;
        Direction sourceFacing = level.getBlockState(sourcePos).getValue(BlockStateProperties.FACING);
        Vec3 sourceVec = new Vec3(sourcePos.getX() + 0.5f - (0.3 * sourceFacing.getStepX()), sourcePos.getY() + 0.5f - (0.3 * sourceFacing.getStepY()), sourcePos.getZ() + 0.5f - (0.3 * sourceFacing.getStepZ()));
        BlockState targetState = level.getBlockState(targetPos);
        Vec3 targetVec = new Vec3(0, 0, 0);
        if (targetState.getBlock() instanceof EnergyTransmitter) {
            Direction targetFacing = targetState.getValue(BlockStateProperties.FACING);
            targetVec = new Vec3(targetPos.getX() + 0.5f - (0.3 * targetFacing.getStepX()), targetPos.getY() + 0.5f - (0.3 * targetFacing.getStepY()), targetPos.getZ() + 0.5f - (0.3 * targetFacing.getStepZ()));
        } else {
            VoxelShape voxelShape = targetState.getShape(level, targetPos); //Todo maybe use this?
            targetVec = new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
        }

        ItemFlowParticleData data = new ItemFlowParticleData(new ItemStack(Items.YELLOW_CONCRETE), targetVec.x, targetVec.y, targetVec.z, 2);
        double d0 = sourceVec.x();
        double d1 = sourceVec.y();
        double d2 = sourceVec.z();
        ((ServerLevel) level).sendParticles(data, d0, d1, d2, 1, 0, 0, 0, 0);
    }

    /**
     * Allows filling the capacitor from another battery like device, or the Pocket Generator
     */
    public void drainFromSlot() {
        ItemStack itemStack = getMachineHandler().getStackInSlot(0);
        if (itemStack.isEmpty()) return;
        IEnergyStorage energyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energyStorage == null) return;
        if (itemStack.getItem() instanceof PocketGenerator pocketGenerator) {
            pocketGenerator.tryBurn((EnergyStorageItemStackNoReceive) energyStorage, itemStack);
        }
        if (getEnergyStorage().getEnergyStored() >= getEnergyStorage().getMaxEnergyStored())
            return; //Don't do anything if already full...
        transmitPower(energyStorage, getEnergyStorage(), fePerTick());
    }

    public IEnergyStorage getHandler(BlockPos blockPos) {
        var tempStorage = energyHandlers.get(blockPos);
        if (tempStorage == null) {
            boolean foundAcceptableSide = false;
            for (Direction direction : Direction.values()) {
                tempStorage = BlockCapabilityCache.create(
                        Capabilities.EnergyStorage.BLOCK, // capability to cache
                        (ServerLevel) level, // level
                        blockPos, // target position
                        direction // context (The side of the block we're trying to pull/push from?)
                );
                if (tempStorage.getCapability() != null && tempStorage.getCapability().canReceive()) {
                    energyHandlers.put(blockPos, tempStorage);
                    foundAcceptableSide = true;
                    break;
                }
            }
            if (!foundAcceptableSide)
                energyHandlers.put(blockPos, tempStorage); //Put the last one we checked, even if it can't receive!
        }
        return tempStorage.getCapability();
    }

    public TransmitterEnergyStorage getTransmitterEnergyHandler(BlockPos blockPos) {
        IEnergyStorage iEnergyStorage = getTransmitterHandler(blockPos);
        if (iEnergyStorage instanceof TransmitterEnergyStorage transmitterEnergyStorage)
            return transmitterEnergyStorage;
        return null;
    }

    public IEnergyStorage getTransmitterHandler(BlockPos blockPos) {
        var tempStorage = transmitterHandlers.get(blockPos);
        if (tempStorage == null) {
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(Registration.EnergyTransmitter.get())) {
                tempStorage = BlockCapabilityCache.create(
                        Capabilities.EnergyStorage.BLOCK, // capability to cache
                        (ServerLevel) level, // level
                        blockPos, // target position
                        blockState.getValue(BlockStateProperties.FACING) // context (The side of the block we're trying to pull/push from?)
                );
                if (tempStorage.getCapability() != null) { //This should always be true?
                    transmitterHandlers.put(blockPos, tempStorage);
                    return tempStorage.getCapability();
                }
            }
            energyHandlers.put(blockPos, tempStorage); //This should never ever run?
        }
        return tempStorage.getCapability();
    }

    public void providePower() {
        if (getEnergyStorage().getEnergyStored() <= 0) return; //Don't bother if we're empty!
        for (BlockPos blockPos : blocksToCharge) {
            IEnergyStorage iEnergyStorage = getHandler(blockPos);
            if (iEnergyStorage == null) continue;
            int sentAmt = transmitPowerWithLoss(getEnergyStorage(), iEnergyStorage, fePerTick(), blockPos);
            if (sentAmt > 0)
                doParticles(getBlockPos(), blockPos);
        }
        balanceEnergy();
    }

    public int calculateLoss(int amtToSend, BlockPos remotePosition) {
        double energyLoss = (Config.ENERGY_TRANSMITTER_T1_LOSS_PER_BLOCK.get() * Math.abs(getBlockPos().distManhattan(remotePosition))) / 100;
        //System.out.println("Distance: " + Math.abs(getBlockPos().distManhattan(remotePosition)) + ".  Energy Loss: " + energyLoss + ". Send vs receive: " + amtToSend + " : " + (amtToSend - (int) (Math.ceil(amtToSend * energyLoss))));
        return amtToSend - (int) (Math.floor(amtToSend * energyLoss));
    }

    public int transmitPowerWithLoss(IEnergyStorage sender, IEnergyStorage receiver, int amtToSend, BlockPos remotePosition) {
        int amtFit = receiver.receiveEnergy(amtToSend, true);
        if (amtFit <= 0) return 0;
        int extractAmt = sender.extractEnergy(amtFit, false);
        return receiver.receiveEnergy(calculateLoss(extractAmt, remotePosition), false);
    }

    public int transmitPower(IEnergyStorage sender, IEnergyStorage receiver, int amtToSend) {
        int amtFit = receiver.receiveEnergy(amtToSend, true);
        if (amtFit <= 0) return 0;
        int extractAmt = sender.extractEnergy(amtFit, false);
        return receiver.receiveEnergy(extractAmt, false);
    }

    /**
     * Discover nearby blocks that need charging - runs once every 2.5 seconds
     */
    public void getBlocksToCharge() {
        transmitters.clear();
        blocksToCharge.clear();
        transmitters.add(getBlockPos()); //Always add yourself
        AABB area = getAABB(getBlockPos());
        BlockPos.betweenClosedStream((int) area.minX, (int) area.minY, (int) area.minZ, (int) area.maxX - 1, (int) area.maxY - 1, (int) area.maxZ - 1)
                .map(BlockPos::immutable)
                .sorted(Comparator.comparingDouble(x -> x.distSqr(getBlockPos())))
                .forEach(blockPos -> {
                    if (blockPos.equals(getBlockPos())) return; //Already added above!
                    BlockState blockState = level.getBlockState(blockPos);
                    if (blockState.isAir() || level.getBlockEntity(blockPos) == null) return;

                    boolean foundAcceptableSide = false;
                    for (Direction direction : Direction.values()) {
                        var cap = level.getCapability(Capabilities.EnergyStorage.BLOCK, blockPos, direction);
                        if (cap != null && cap.canReceive()) {
                            foundAcceptableSide = true;
                            break;
                        }
                    }
                    if (!foundAcceptableSide)
                        return;

                    ItemStack blockItemStack = blockState.getBlock().getCloneItemStack(level, blockPos, blockState);
                    if (!isStackValidFilter(blockItemStack)) return;

                    if (blockState.getBlock() instanceof EnergyTransmitter)
                        transmitters.add(blockPos);
                    else
                        blocksToCharge.add(blockPos);
                });
        energyHandlers.entrySet().removeIf(entry -> !blocksToCharge.contains(entry.getKey()));
        transmitterHandlers.entrySet().removeIf(entry -> !transmitters.contains(entry.getKey()));
    }

    public int fePerTick() {
        return Config.ENERGY_TRANSMITTER_T1_RF_PER_TICK.get();
    }

    @Override
    public int getMaxEnergy() {
        return Config.ENERGY_TRANSMITTER_T1_MAX_RF.get();
    }

    @Override
    public boolean isDefaultSettings() {
        if (!super.isDefaultSettings())
            return false;
        if (!showParticles)
            return false;
        return true;
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putBoolean("showParticles", showParticles);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("showParticles"))
            showParticles = tag.getBoolean("showParticles");
    }
}
