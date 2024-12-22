package com.direwolf20.justdirethings.setup;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.common.blockentities.*;
import com.direwolf20.justdirethings.common.blockentities.gooblocks.GooBlockBE_Tier1;
import com.direwolf20.justdirethings.common.blockentities.gooblocks.GooBlockBE_Tier2;
import com.direwolf20.justdirethings.common.blockentities.gooblocks.GooBlockBE_Tier3;
import com.direwolf20.justdirethings.common.blockentities.gooblocks.GooBlockBE_Tier4;
import com.direwolf20.justdirethings.common.blocks.*;
import com.direwolf20.justdirethings.common.blocks.gooblocks.*;
import com.direwolf20.justdirethings.common.blocks.resources.*;
import com.direwolf20.justdirethings.common.blocks.soil.GooSoilTier1;
import com.direwolf20.justdirethings.common.blocks.soil.GooSoilTier2;
import com.direwolf20.justdirethings.common.blocks.soil.GooSoilTier3;
import com.direwolf20.justdirethings.common.blocks.soil.GooSoilTier4;
import com.direwolf20.justdirethings.common.containers.*;
import com.direwolf20.justdirethings.common.entities.*;
import com.direwolf20.justdirethings.common.fluids.polymorphicfluid.PolymorphicFluid;
import com.direwolf20.justdirethings.common.fluids.polymorphicfluid.PolymorphicFluidBlock;
import com.direwolf20.justdirethings.common.fluids.polymorphicfluid.PolymorphicFluidType;
import com.direwolf20.justdirethings.common.fluids.portalfluid.PortalFluid;
import com.direwolf20.justdirethings.common.fluids.portalfluid.PortalFluidBlock;
import com.direwolf20.justdirethings.common.fluids.portalfluid.PortalFluidType;
import com.direwolf20.justdirethings.common.fluids.refinedt2fuel.RefinedT2Fuel;
import com.direwolf20.justdirethings.common.fluids.refinedt2fuel.RefinedT2FuelBlock;
import com.direwolf20.justdirethings.common.fluids.refinedt2fuel.RefinedT2FuelType;
import com.direwolf20.justdirethings.common.fluids.refinedt3fuel.RefinedT3Fuel;
import com.direwolf20.justdirethings.common.fluids.refinedt3fuel.RefinedT3FuelBlock;
import com.direwolf20.justdirethings.common.fluids.refinedt3fuel.RefinedT3FuelType;
import com.direwolf20.justdirethings.common.fluids.refinedt4fuel.RefinedT4Fuel;
import com.direwolf20.justdirethings.common.fluids.refinedt4fuel.RefinedT4FuelBlock;
import com.direwolf20.justdirethings.common.fluids.refinedt4fuel.RefinedT4FuelType;
import com.direwolf20.justdirethings.common.fluids.timefluid.TimeFluid;
import com.direwolf20.justdirethings.common.fluids.timefluid.TimeFluidBlock;
import com.direwolf20.justdirethings.common.fluids.timefluid.TimeFluidType;
import com.direwolf20.justdirethings.common.fluids.unrefinedt2fuel.UnrefinedT2Fuel;
import com.direwolf20.justdirethings.common.fluids.unrefinedt2fuel.UnrefinedT2FuelBlock;
import com.direwolf20.justdirethings.common.fluids.unrefinedt2fuel.UnrefinedT2FuelType;
import com.direwolf20.justdirethings.common.fluids.unrefinedt3fuel.UnrefinedT3Fuel;
import com.direwolf20.justdirethings.common.fluids.unrefinedt3fuel.UnrefinedT3FuelBlock;
import com.direwolf20.justdirethings.common.fluids.unrefinedt3fuel.UnrefinedT3FuelType;
import com.direwolf20.justdirethings.common.fluids.unrefinedt4fuel.UnrefinedT4Fuel;
import com.direwolf20.justdirethings.common.fluids.unrefinedt4fuel.UnrefinedT4FuelBlock;
import com.direwolf20.justdirethings.common.fluids.unrefinedt4fuel.UnrefinedT4FuelType;
import com.direwolf20.justdirethings.common.fluids.unstableportalfluid.UnstablePortalFluid;
import com.direwolf20.justdirethings.common.fluids.unstableportalfluid.UnstablePortalFluidBlock;
import com.direwolf20.justdirethings.common.fluids.unstableportalfluid.UnstablePortalFluidType;
import com.direwolf20.justdirethings.common.fluids.xpfluid.XPFluid;
import com.direwolf20.justdirethings.common.fluids.xpfluid.XPFluidBlock;
import com.direwolf20.justdirethings.common.fluids.xpfluid.XPFluidType;
import com.direwolf20.justdirethings.common.items.*;
import com.direwolf20.justdirethings.common.items.abilityupgrades.Upgrade;
import com.direwolf20.justdirethings.common.items.abilityupgrades.UpgradeBlank;
import com.direwolf20.justdirethings.common.items.abilityupgrades.UpgradeTemplate;
import com.direwolf20.justdirethings.common.items.armors.*;
import com.direwolf20.justdirethings.common.items.resources.*;
import com.direwolf20.justdirethings.common.items.tools.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.direwolf20.justdirethings.JustDireThings.MODID;
import static com.direwolf20.justdirethings.client.particles.ModParticles.PARTICLE_TYPES;

public class Registration {
    //public static final TicketController TICKET_CONTROLLER = new TicketController(JustDireThings.id("chunk_loader"), null);

    //TieredCoalItem.getId(), new FurnaceFuel(4800), false)
    //                .add(Registration.CoalBlock.getId(), new FurnaceFuel(48000), false)
    //                .add(Registration.TieredCoal_T2Item.getId(), new FurnaceFuel(14400), false)
    //                .add(Registration.CoalBlock_T2.getId(), new FurnaceFuel(144000), false)
    //                .add(Registration.TieredCoal_T3Item.getId(), new FurnaceFuel(43200), false)
    //                .add(Registration.CoalBlock_T3.getId(), new FurnaceFuel(432000), false)
    //                .add(Registration.TieredCoal_T4Item.getId(), new FurnaceFuel(129600), false)
    //                .add(Registration.CoalBlock_T4.getId(), new FurnaceFuel(1296000), false)
    //                .add(Registration.CharcoalBlock.getId(), new FurnaceFuel(16000), false);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, MODID);
    public static final DeferredRegister<Block> SIDEDBLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> BUCKET_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> TOOLS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> BOWS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> ARMORS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Item> UPGRADES = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, MODID);
    // private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MODID);
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, MODID);


    private static final DeferredRegister<SoundEvent> SOUND_REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, JustDireThings.MODID);
    public static final Supplier<SoundEvent> BEEP = SOUND_REGISTRY.register("beep", () -> SoundEvent.createVariableRangeEvent(JustDireThings.id( "beep")));
    public static final Supplier<SoundEvent> PORTAL_GUN_CLOSE = SOUND_REGISTRY.register("portal_gun_close", () -> SoundEvent.createVariableRangeEvent(JustDireThings.id( "portal_gun_close")));
    public static final Supplier<SoundEvent> PORTAL_GUN_OPEN = SOUND_REGISTRY.register("portal_gun_open", () -> SoundEvent.createVariableRangeEvent(JustDireThings.id( "portal_gun_open")));
    public static final Supplier<SoundEvent> PARADOX_AMBIENT = SOUND_REGISTRY.register("paradox_ambient", () -> SoundEvent.createVariableRangeEvent(JustDireThings.id( "paradox_ambient")));


    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        FLUID_BLOCKS.register(eventBus);
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
        SIDEDBLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        BUCKET_ITEMS.register(eventBus);
        TOOLS.register(eventBus);
        BOWS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
        //  ATTACHMENT_TYPES.register(eventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(eventBus);
        ModRecipes.RECIPE_TYPES.register(eventBus);
        PARTICLE_TYPES.register(eventBus);
        ENTITY_TYPES.register(eventBus);
        ARMORS.register(eventBus);
        SOUND_REGISTRY.register(eventBus);
        ATTRIBUTES.register(eventBus);
        UPGRADES.register(eventBus);
    }

    //Gooblocks
    public static final RegistryObject<GooBlock_Tier1> GooBlock_Tier1 = BLOCKS.register("gooblock_tier1", GooBlock_Tier1::new);
    public static final RegistryObject<BlockItem> GooBlock_Tier1_ITEM = ITEMS.register("gooblock_tier1", () -> new GooBlock_Item(GooBlock_Tier1.get(), new Item.Properties()));
    public static final RegistryObject<GooBlock_Tier2> GooBlock_Tier2 = BLOCKS.register("gooblock_tier2", GooBlock_Tier2::new);
    public static final RegistryObject<BlockItem> GooBlock_Tier2_ITEM = ITEMS.register("gooblock_tier2", () -> new GooBlock_Item(GooBlock_Tier2.get(), new Item.Properties()));
    public static final RegistryObject<GooBlock_Tier3> GooBlock_Tier3 = BLOCKS.register("gooblock_tier3", GooBlock_Tier3::new);
    public static final RegistryObject<BlockItem> GooBlock_Tier3_ITEM = ITEMS.register("gooblock_tier3", () -> new GooBlock_Item(GooBlock_Tier3.get(), new Item.Properties()));
    public static final RegistryObject<GooBlock_Tier4> GooBlock_Tier4 = BLOCKS.register("gooblock_tier4", GooBlock_Tier4::new);
    public static final RegistryObject<BlockItem> GooBlock_Tier4_ITEM = ITEMS.register("gooblock_tier4", () -> new GooBlock_Item(GooBlock_Tier4.get(), new Item.Properties()));

    public static final RegistryObject<GooPatternBlock> GooPatternBlock = BLOCKS.register("goopatternblock", GooPatternBlock::new);


    //Blocks
    public static final RegistryObject<GooSoilTier1> GooSoil_Tier1 = BLOCKS.register("goosoil_tier1", GooSoilTier1::new);
    public static final RegistryObject<BlockItem> GooSoil_ITEM_Tier1 = ITEMS.register("goosoil_tier1", () -> new BlockItem(GooSoil_Tier1.get(), new Item.Properties()));
    public static final RegistryObject<GooSoilTier2> GooSoil_Tier2 = BLOCKS.register("goosoil_tier2", GooSoilTier2::new);
    public static final RegistryObject<BlockItem> GooSoil_ITEM_Tier2 = ITEMS.register("goosoil_tier2", () -> new BlockItem(GooSoil_Tier2.get(), new Item.Properties()));
    public static final RegistryObject<GooSoilTier3> GooSoil_Tier3 = BLOCKS.register("goosoil_tier3", GooSoilTier3::new);
    public static final RegistryObject<BlockItem> GooSoil_ITEM_Tier3 = ITEMS.register("goosoil_tier3", () -> new BlockItem(GooSoil_Tier3.get(), new Item.Properties()));
    public static final RegistryObject<GooSoilTier4> GooSoil_Tier4 = BLOCKS.register("goosoil_tier4", GooSoilTier4::new);
    public static final RegistryObject<BlockItem> GooSoil_ITEM_Tier4 = ITEMS.register("goosoil_tier4", () -> new BlockItem(GooSoil_Tier4.get(), new Item.Properties()));
    public static final RegistryObject<EclipseGateBlock> EclipseGateBlock = BLOCKS.register("eclipsegateblock", EclipseGateBlock::new);

    //Fluids
    //Polymorphic Fluid
    public static final RegistryObject<FluidType> POLYMORPHIC_FLUID_TYPE = FLUID_TYPES.register("polymorphic_fluid_type",
            PolymorphicFluidType::new);
    public static final RegistryObject<PolymorphicFluid> POLYMORPHIC_FLUID_FLOWING = FLUIDS.register("polymorphic_fluid_flowing",
            PolymorphicFluid.Flowing::new);
    public static final RegistryObject<PolymorphicFluid> POLYMORPHIC_FLUID_SOURCE = FLUIDS.register("polymorphic_fluid_source",
            PolymorphicFluid.Source::new);
    public static final RegistryObject<LiquidBlock> POLYMORPHIC_FLUID_BLOCK = FLUID_BLOCKS.register("polymorphic_fluid_block",
            PolymorphicFluidBlock::new);
    public static final RegistryObject<BucketItem> POLYMORPHIC_FLUID_BUCKET = BUCKET_ITEMS.register("polymorphic_fluid_bucket",
            () -> new BucketItem(Registration.POLYMORPHIC_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Portal Fluid
    public static final RegistryObject<FluidType> PORTAL_FLUID_TYPE = FLUID_TYPES.register("portal_fluid_type",
            PortalFluidType::new);
    public static final RegistryObject<PortalFluid> PORTAL_FLUID_FLOWING = FLUIDS.register("portal_fluid_flowing",
            PortalFluid.Flowing::new);
    public static final RegistryObject<PortalFluid> PORTAL_FLUID_SOURCE = FLUIDS.register("portal_fluid_source",
            PortalFluid.Source::new);
    public static final RegistryObject<LiquidBlock> PORTAL_FLUID_BLOCK = FLUID_BLOCKS.register("portal_fluid_block",
            PortalFluidBlock::new);
    public static final RegistryObject<BucketItem> PORTAL_FLUID_BUCKET = BUCKET_ITEMS.register("portal_fluid_bucket",
            () -> new BucketItem(Registration.PORTAL_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Time Fluid
    public static final RegistryObject<FluidType> TIME_FLUID_TYPE = FLUID_TYPES.register("time_fluid_type",
            TimeFluidType::new);
    public static final RegistryObject<TimeFluid> TIME_FLUID_FLOWING = FLUIDS.register("time_fluid_flowing",
            TimeFluid.Flowing::new);
    public static final RegistryObject<TimeFluid> TIME_FLUID_SOURCE = FLUIDS.register("time_fluid_source",
            TimeFluid.Source::new);
    public static final RegistryObject<LiquidBlock> TIME_FLUID_BLOCK = FLUID_BLOCKS.register("time_fluid_block",
            TimeFluidBlock::new);
    public static final RegistryObject<BucketItem> TIME_FLUID_BUCKET = BUCKET_ITEMS.register("time_fluid_bucket",
            () -> new BucketItem(Registration.TIME_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Unstable Portal Fluid
    public static final RegistryObject<FluidType> UNSTABLE_PORTAL_FLUID_TYPE = FLUID_TYPES.register("unstable_portal_fluid_type",
            UnstablePortalFluidType::new);
    public static final RegistryObject<UnstablePortalFluid> UNSTABLE_PORTAL_FLUID_FLOWING = FLUIDS.register("unstable_portal_fluid_flowing",
            UnstablePortalFluid.Flowing::new);
    public static final RegistryObject<UnstablePortalFluid> UNSTABLE_PORTAL_FLUID_SOURCE = FLUIDS.register("unstable_portal_fluid_source",
            UnstablePortalFluid.Source::new);
    public static final RegistryObject<LiquidBlock> UNSTABLE_PORTAL_FLUID_BLOCK = FLUID_BLOCKS.register("unstable_portal_fluid_block",
            UnstablePortalFluidBlock::new);
    public static final RegistryObject<BucketItem> UNSTABLE_PORTAL_FLUID_BUCKET = BUCKET_ITEMS.register("unstable_portal_fluid_bucket",
            () -> new BucketItem(Registration.UNSTABLE_PORTAL_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Unrefined T2 Fuel
    public static final RegistryObject<FluidType> UNREFINED_T2_FLUID_TYPE = FLUID_TYPES.register("unrefined_t2_fluid_type",
            UnrefinedT2FuelType::new);
    public static final RegistryObject<UnrefinedT2Fuel> UNREFINED_T2_FLUID_FLOWING = FLUIDS.register("unrefined_t2_fluid_flowing",
            UnrefinedT2Fuel.Flowing::new);
    public static final RegistryObject<UnrefinedT2Fuel> UNREFINED_T2_FLUID_SOURCE = FLUIDS.register("unrefined_t2_fluid_source",
            UnrefinedT2Fuel.Source::new);
    public static final RegistryObject<LiquidBlock> UNREFINED_T2_FLUID_BLOCK = FLUID_BLOCKS.register("unrefined_t2_fluid_block",
            UnrefinedT2FuelBlock::new);
    public static final RegistryObject<BucketItem> UNREFINED_T2_FLUID_BUCKET = BUCKET_ITEMS.register("unrefined_t2_fluid_bucket",
            () -> new BucketItem(Registration.UNREFINED_T2_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Refined T2 Fuel
    public static final RegistryObject<FluidType> REFINED_T2_FLUID_TYPE = FLUID_TYPES.register("refined_t2_fluid_type",
            RefinedT2FuelType::new);
    public static final RegistryObject<RefinedT2Fuel> REFINED_T2_FLUID_FLOWING = FLUIDS.register("refined_t2_fluid_flowing",
            RefinedT2Fuel.Flowing::new);
    public static final RegistryObject<RefinedT2Fuel> REFINED_T2_FLUID_SOURCE = FLUIDS.register("refined_t2_fluid_source",
            RefinedT2Fuel.Source::new);
    public static final RegistryObject<LiquidBlock> REFINED_T2_FLUID_BLOCK = FLUID_BLOCKS.register("refined_t2_fluid_block",
            RefinedT2FuelBlock::new);
    public static final RegistryObject<BucketItem> REFINED_T2_FLUID_BUCKET = BUCKET_ITEMS.register("refined_t2_fluid_bucket",
            () -> new BucketItem(Registration.REFINED_T2_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Unrefined T3 Fuel
    public static final RegistryObject<FluidType> UNREFINED_T3_FLUID_TYPE = FLUID_TYPES.register("unrefined_t3_fluid_type",
            UnrefinedT3FuelType::new);
    public static final RegistryObject<UnrefinedT3Fuel> UNREFINED_T3_FLUID_FLOWING = FLUIDS.register("unrefined_t3_fluid_flowing",
            UnrefinedT3Fuel.Flowing::new);
    public static final RegistryObject<UnrefinedT3Fuel> UNREFINED_T3_FLUID_SOURCE = FLUIDS.register("unrefined_t3_fluid_source",
            UnrefinedT3Fuel.Source::new);
    public static final RegistryObject<LiquidBlock> UNREFINED_T3_FLUID_BLOCK = FLUID_BLOCKS.register("unrefined_t3_fluid_block",
            UnrefinedT3FuelBlock::new);
    public static final RegistryObject<BucketItem> UNREFINED_T3_FLUID_BUCKET = BUCKET_ITEMS.register("unrefined_t3_fluid_bucket",
            () -> new BucketItem(Registration.UNREFINED_T3_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Refined T3 Fuel
    public static final RegistryObject<FluidType> REFINED_T3_FLUID_TYPE = FLUID_TYPES.register("refined_t3_fluid_type",
            RefinedT3FuelType::new);
    public static final RegistryObject<RefinedT3Fuel> REFINED_T3_FLUID_FLOWING = FLUIDS.register("refined_t3_fluid_flowing",
            RefinedT3Fuel.Flowing::new);
    public static final RegistryObject<RefinedT3Fuel> REFINED_T3_FLUID_SOURCE = FLUIDS.register("refined_t3_fluid_source",
            RefinedT3Fuel.Source::new);
    public static final RegistryObject<LiquidBlock> REFINED_T3_FLUID_BLOCK = FLUID_BLOCKS.register("refined_t3_fluid_block",
            RefinedT3FuelBlock::new);
    public static final RegistryObject<BucketItem> REFINED_T3_FLUID_BUCKET = BUCKET_ITEMS.register("refined_t3_fluid_bucket",
            () -> new BucketItem(Registration.REFINED_T3_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Unrefined T4 Fuel
    public static final RegistryObject<FluidType> UNREFINED_T4_FLUID_TYPE = FLUID_TYPES.register("unrefined_t4_fluid_type",
            UnrefinedT4FuelType::new);
    public static final RegistryObject<UnrefinedT4Fuel> UNREFINED_T4_FLUID_FLOWING = FLUIDS.register("unrefined_t4_fluid_flowing",
            UnrefinedT4Fuel.Flowing::new);
    public static final RegistryObject<UnrefinedT4Fuel> UNREFINED_T4_FLUID_SOURCE = FLUIDS.register("unrefined_t4_fluid_source",
            UnrefinedT4Fuel.Source::new);
    public static final RegistryObject<LiquidBlock> UNREFINED_T4_FLUID_BLOCK = FLUID_BLOCKS.register("unrefined_t4_fluid_block",
            UnrefinedT4FuelBlock::new);
    public static final RegistryObject<BucketItem> UNREFINED_T4_FLUID_BUCKET = BUCKET_ITEMS.register("unrefined_t4_fluid_bucket",
            () -> new BucketItem(Registration.UNREFINED_T4_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Refined T4 Fuel
    public static final RegistryObject<FluidType> REFINED_T4_FLUID_TYPE = FLUID_TYPES.register("refined_t4_fluid_type",
            RefinedT4FuelType::new);
    public static final RegistryObject<RefinedT4Fuel> REFINED_T4_FLUID_FLOWING = FLUIDS.register("refined_t4_fluid_flowing",
            RefinedT4Fuel.Flowing::new);
    public static final RegistryObject<RefinedT4Fuel> REFINED_T4_FLUID_SOURCE = FLUIDS.register("refined_t4_fluid_source",
            RefinedT4Fuel.Source::new);
    public static final RegistryObject<LiquidBlock> REFINED_T4_FLUID_BLOCK = FLUID_BLOCKS.register("refined_t4_fluid_block",
            RefinedT4FuelBlock::new);
    public static final RegistryObject<BucketItem> REFINED_T4_FLUID_BUCKET = BUCKET_ITEMS.register("refined_t4_fluid_bucket",
            () -> new BucketItem(Registration.REFINED_T4_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //XP Fluid
    public static final RegistryObject<FluidType> XP_FLUID_TYPE = FLUID_TYPES.register("xp_fluid_type",
            XPFluidType::new);
    public static final RegistryObject<XPFluid> XP_FLUID_FLOWING = FLUIDS.register("xp_fluid_flowing",
            XPFluid.Flowing::new);
    public static final RegistryObject<XPFluid> XP_FLUID_SOURCE = FLUIDS.register("xp_fluid_source",
            XPFluid.Source::new);
    public static final RegistryObject<LiquidBlock> XP_FLUID_BLOCK = FLUID_BLOCKS.register("xp_fluid_block",
            XPFluidBlock::new);
    public static final RegistryObject<BucketItem> XP_FLUID_BUCKET = BUCKET_ITEMS.register("xp_fluid_bucket",
            () -> new BucketItem(Registration.XP_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


    //Machines
    public static final RegistryObject<ItemCollectorBlock> ItemCollector = BLOCKS.register("itemcollector", ItemCollectorBlock::new);
    public static final RegistryObject<BlockItem> ItemCollector_ITEM = ITEMS.register("itemcollector", () -> new BlockItem(ItemCollector.get(), new Item.Properties()));
    public static final RegistryObject<BlockBreakerT1Block> BlockBreakerT1 = SIDEDBLOCKS.register("blockbreakert1", BlockBreakerT1Block::new);
    public static final RegistryObject<BlockItem> BlockBreakerT1_ITEM = ITEMS.register("blockbreakert1", () -> new BlockItem(BlockBreakerT1.get(), new Item.Properties()));
    public static final RegistryObject<BlockBreakerT2Block> BlockBreakerT2 = SIDEDBLOCKS.register("blockbreakert2", BlockBreakerT2Block::new);
    public static final RegistryObject<BlockItem> BlockBreakerT2_ITEM = ITEMS.register("blockbreakert2", () -> new BlockItem(BlockBreakerT2.get(), new Item.Properties()));
    public static final RegistryObject<BlockPlacerT1Block> BlockPlacerT1 = SIDEDBLOCKS.register("blockplacert1", BlockPlacerT1Block::new);
    public static final RegistryObject<BlockItem> BlockPlacerT1_ITEM = ITEMS.register("blockplacert1", () -> new BlockItem(BlockPlacerT1.get(), new Item.Properties()));
    public static final RegistryObject<BlockPlacerT2Block> BlockPlacerT2 = SIDEDBLOCKS.register("blockplacert2", BlockPlacerT2Block::new);
    public static final RegistryObject<BlockItem> BlockPlacerT2_ITEM = ITEMS.register("blockplacert2", () -> new BlockItem(BlockPlacerT2.get(), new Item.Properties()));
    public static final RegistryObject<ClickerT1Block> ClickerT1 = SIDEDBLOCKS.register("clickert1", ClickerT1Block::new);
    public static final RegistryObject<BlockItem> ClickerT1_ITEM = ITEMS.register("clickert1", () -> new BlockItem(ClickerT1.get(), new Item.Properties()));
    public static final RegistryObject<ClickerT2Block> ClickerT2 = SIDEDBLOCKS.register("clickert2", ClickerT2Block::new);
    public static final RegistryObject<BlockItem> ClickerT2_ITEM = ITEMS.register("clickert2", () -> new BlockItem(ClickerT2.get(), new Item.Properties()));
    public static final RegistryObject<SensorT1> SensorT1 = SIDEDBLOCKS.register("sensort1", SensorT1::new);
    public static final RegistryObject<BlockItem> SensorT1_ITEM = ITEMS.register("sensort1", () -> new BlockItem(SensorT1.get(), new Item.Properties()));
    public static final RegistryObject<SensorT2Block> SensorT2 = SIDEDBLOCKS.register("sensort2", SensorT2Block::new);
    public static final RegistryObject<BlockItem> SensorT2_ITEM = ITEMS.register("sensort2", () -> new BlockItem(SensorT2.get(), new Item.Properties()));
    public static final RegistryObject<DropperT1Block> DropperT1 = SIDEDBLOCKS.register("droppert1", DropperT1Block::new);
    public static final RegistryObject<BlockItem> DropperT1_ITEM = ITEMS.register("droppert1", () -> new BlockItem(DropperT1.get(), new Item.Properties()));
    public static final RegistryObject<DropperT2Block> DropperT2 = SIDEDBLOCKS.register("droppert2", DropperT2Block::new);
    public static final RegistryObject<BlockItem> DropperT2_ITEM = ITEMS.register("droppert2", () -> new BlockItem(DropperT2.get(), new Item.Properties()));
    public static final RegistryObject<BlockSwapperT1> BlockSwapperT1 = SIDEDBLOCKS.register("blockswappert1", BlockSwapperT1::new);
    public static final RegistryObject<BlockItem> BlockSwapperT1_ITEM = ITEMS.register("blockswappert1", () -> new BlockItem(BlockSwapperT1.get(), new Item.Properties()));
    public static final RegistryObject<BlockSwapperT2Block> BlockSwapperT2 = SIDEDBLOCKS.register("blockswappert2", BlockSwapperT2Block::new);
    public static final RegistryObject<BlockItem> BlockSwapperT2_ITEM = ITEMS.register("blockswappert2", () -> new BlockItem(BlockSwapperT2.get(), new Item.Properties()));
    public static final RegistryObject<PlayerAccessorBlock> PlayerAccessor = BLOCKS.register("playeraccessor", PlayerAccessorBlock::new);
    public static final RegistryObject<BlockItem> PlayerAccessor_ITEM = ITEMS.register("playeraccessor", () -> new BlockItem(PlayerAccessor.get(), new Item.Properties()));
    public static final RegistryObject<FluidPlacerT1Block> FluidPlacerT1 = SIDEDBLOCKS.register("fluidplacert1", FluidPlacerT1Block::new);
    public static final RegistryObject<BlockItem> FluidPlacerT1_ITEM = ITEMS.register("fluidplacert1", () -> new BlockItem(FluidPlacerT1.get(), new Item.Properties()));
    public static final RegistryObject<FluidPlacerT2Block> FluidPlacerT2 = SIDEDBLOCKS.register("fluidplacert2", FluidPlacerT2Block::new);
    public static final RegistryObject<BlockItem> FluidPlacerT2_ITEM = ITEMS.register("fluidplacert2", () -> new BlockItem(FluidPlacerT2.get(), new Item.Properties()));
    public static final RegistryObject<FluidCollectorT1Block> FluidCollectorT1 = SIDEDBLOCKS.register("fluidcollectort1", FluidCollectorT1Block::new);
    public static final RegistryObject<BlockItem> FluidCollectorT1_ITEM = ITEMS.register("fluidcollectort1", () -> new BlockItem(FluidCollectorT1.get(), new Item.Properties()));
    public static final RegistryObject<FluidCollectorT2Block> FluidCollectorT2 = SIDEDBLOCKS.register("fluidcollectort2", FluidCollectorT2Block::new);
    public static final RegistryObject<BlockItem> FluidCollectorT2_ITEM = ITEMS.register("fluidcollectort2", () -> new BlockItem(FluidCollectorT2.get(), new Item.Properties()));
    public static final RegistryObject<ParadoxMachineBlock> ParadoxMachine = SIDEDBLOCKS.register("paradoxmachine", ParadoxMachineBlock::new);
    public static final RegistryObject<BlockItem> ParadoxMachine_ITEM = ITEMS.register("paradoxmachine", () -> new BlockItem(ParadoxMachine.get(), new Item.Properties()));
    public static final RegistryObject<InventoryHolderBlock> InventoryHolder = BLOCKS.register("inventory_holder", InventoryHolderBlock::new);
    public static final RegistryObject<BlockItem> InventoryHolder_ITEM = ITEMS.register("inventory_holder", () -> new BlockItem(InventoryHolder.get(), new Item.Properties()));
    public static final RegistryObject<ExperienceHolderBlock> ExperienceHolder = BLOCKS.register("experienceholder", ExperienceHolderBlock::new);
    public static final RegistryObject<BlockItem> ExperienceHolder_ITEM = ITEMS.register("experienceholder", () -> new BlockItem(ExperienceHolder.get(), new Item.Properties()));


    //Power Machines
    public static final RegistryObject<GeneratorT1Block> GeneratorT1 = BLOCKS.register("generatort1", GeneratorT1Block::new);
    public static final RegistryObject<GeneratorFluidT1Block> GeneratorFluidT1 = BLOCKS.register("generatorfluidt1", GeneratorFluidT1Block::new);
    public static final RegistryObject<BlockItem> GeneratorT1_ITEM = ITEMS.register("generatort1", () -> new BlockItem(GeneratorT1.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> GeneratorFluidT1_ITEM = ITEMS.register("generatorfluidt1", () -> new BlockItem(GeneratorFluidT1.get(), new Item.Properties()));
    public static final RegistryObject<EnergyTransmitterBlock> EnergyTransmitter = BLOCKS.register("energytransmitter", EnergyTransmitterBlock::new);
    public static final RegistryObject<BlockItem> EnergyTransmitter_ITEM = ITEMS.register("energytransmitter", () -> new BlockItem(EnergyTransmitter.get(), new Item.Properties()));

    //Blocks - Raw Resources
    public static final RegistryObject<RawFerricoreOre> RawFerricoreOre = BLOCKS.register("raw_ferricore_ore", RawFerricoreOre::new);
    public static final RegistryObject<BlockItem> RawFerricoreOre_ITEM = ITEMS.register("raw_ferricore_ore", () -> new BlockItem(RawFerricoreOre.get(), new Item.Properties()));
    public static final RegistryObject<RawBlazegoldOre> RawBlazegoldOre = BLOCKS.register("raw_blazegold_ore", RawBlazegoldOre::new);
    public static final RegistryObject<BlockItem> RawBlazegoldOre_ITEM = ITEMS.register("raw_blazegold_ore", () -> new BlockItem(RawBlazegoldOre.get(), new Item.Properties()));
    public static final RegistryObject<RawCelestigemOre> RawCelestigemOre = BLOCKS.register("raw_celestigem_ore", RawCelestigemOre::new);
    public static final RegistryObject<BlockItem> RawCelestigemOre_ITEM = ITEMS.register("raw_celestigem_ore", () -> new BlockItem(RawCelestigemOre.get(), new Item.Properties()));
    public static final RegistryObject<RawEclipseAlloyOre> RawEclipseAlloyOre = BLOCKS.register("raw_eclipsealloy_ore", RawEclipseAlloyOre::new);
    public static final RegistryObject<BlockItem> RawEclipseAlloyOre_ITEM = ITEMS.register("raw_eclipsealloy_ore", () -> new BlockItem(RawEclipseAlloyOre.get(), new Item.Properties()));
    public static final RegistryObject<RawCoal> RawCoal_T1 = BLOCKS.register("raw_coal_t1_ore", RawCoal::new);
    public static final RegistryObject<BlockItem> RawCoal_T1_ITEM = ITEMS.register("raw_coal_t1_ore", () -> new BlockItem(RawCoal_T1.get(), new Item.Properties()));
    public static final RegistryObject<RawCoal> RawCoal_T2 = BLOCKS.register("raw_coal_t2_ore", RawCoal::new);
    public static final RegistryObject<BlockItem> RawCoal_T2_ITEM = ITEMS.register("raw_coal_t2_ore", () -> new BlockItem(RawCoal_T2.get(), new Item.Properties()));
    public static final RegistryObject<RawCoal> RawCoal_T3 = BLOCKS.register("raw_coal_t3_ore", RawCoal::new);
    public static final RegistryObject<BlockItem> RawCoal_T3_ITEM = ITEMS.register("raw_coal_t3_ore", () -> new BlockItem(RawCoal_T3.get(), new Item.Properties()));
    public static final RegistryObject<RawCoal> RawCoal_T4 = BLOCKS.register("raw_coal_t4_ore", RawCoal::new);
    public static final RegistryObject<BlockItem> RawCoal_T4_ITEM = ITEMS.register("raw_coal_t4_ore", () -> new BlockItem(RawCoal_T4.get(), new Item.Properties()));
    public static final RegistryObject<TimeCrystalBlock> TimeCrystalBlock = BLOCKS.register("time_crystal_block", TimeCrystalBlock::new);
    public static final RegistryObject<BlockItem> TimeCrystalBlock_ITEM = ITEMS.register("time_crystal_block", () -> new BlockItem(TimeCrystalBlock.get(), new Item.Properties()));
    public static final RegistryObject<TimeCrystalBuddingBlock> TIME_CRYSTAL_BUDDING_BLOCK = BLOCKS.register("time_crystal_budding_block", TimeCrystalBuddingBlock::new);
    public static final RegistryObject<BlockItem> TimeCrystalBuddingBlock_ITEM = ITEMS.register("time_crystal_budding_block", () -> new BlockItem(TIME_CRYSTAL_BUDDING_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<TimeCrystalClusterBlock> TimeCrystalCluster = BLOCKS.register("time_crystal_cluster", () -> new TimeCrystalClusterBlock(
            7,
            3,
            BlockBehaviour.Properties.of()
                    .forceSolidOn()
                    .noOcclusion()
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .strength(1.5F)
                    .lightLevel(p_152632_ -> 5)
                    .pushReaction(PushReaction.DESTROY)
    ));
    public static final RegistryObject<TimeCrystalClusterBlock> TimeCrystalCluster_Small = BLOCKS.register("time_crystal_cluster_small", () -> new TimeCrystalClusterBlock(
            3, 4, BlockBehaviour.Properties.copy(TimeCrystalCluster.get()).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel(p_187409_ -> 1)
    ));
    public static final RegistryObject<TimeCrystalClusterBlock> TimeCrystalCluster_Medium = BLOCKS.register("time_crystal_cluster_medium", () -> new TimeCrystalClusterBlock(
            4, 3, BlockBehaviour.Properties.copy(TimeCrystalCluster.get()).sound(SoundType.MEDIUM_AMETHYST_BUD).lightLevel(p_152617_ -> 2)
    ));
    public static final RegistryObject<TimeCrystalClusterBlock> TimeCrystalCluster_Large = BLOCKS.register("time_crystal_cluster_large", () -> new TimeCrystalClusterBlock(
            5, 3, BlockBehaviour.Properties.copy(TimeCrystalCluster.get()).sound(SoundType.LARGE_AMETHYST_BUD).lightLevel(p_152629_ -> 4)
    ));


    //Blocks Consolidated Resources
    public static final RegistryObject<FerricoreBlock> FerricoreBlock = BLOCKS.register("ferricore_block", FerricoreBlock::new);
    public static final RegistryObject<BlockItem> FerricoreBlock_ITEM = ITEMS.register("ferricore_block", () -> new BlockItem(FerricoreBlock.get(), new Item.Properties()));
    public static final RegistryObject<BlazeGoldBlock> BlazeGoldBlock = BLOCKS.register("blazegold_block", BlazeGoldBlock::new);
    public static final RegistryObject<BlockItem> BlazeGoldBlock_ITEM = ITEMS.register("blazegold_block", () -> new BlockItem(BlazeGoldBlock.get(), new Item.Properties()));
    public static final RegistryObject<CelestigemBlock> CelestigemBlock = BLOCKS.register("celestigem_block", CelestigemBlock::new);
    public static final RegistryObject<BlockItem> CelestigemBlock_ITEM = ITEMS.register("celestigem_block", () -> new BlockItem(CelestigemBlock.get(), new Item.Properties()));
    public static final RegistryObject<EclipseAlloyBlock> EclipseAlloyBlock = BLOCKS.register("eclipsealloy_block", EclipseAlloyBlock::new);
    public static final RegistryObject<BlockItem> EclipseAlloyBlock_ITEM = ITEMS.register("eclipsealloy_block", () -> new BlockItem(EclipseAlloyBlock.get(), new Item.Properties()));
    public static final RegistryObject<CoalBlock> CoalBlock_T1 = BLOCKS.register("coalblock_t1", () -> new CoalBlock(2));
    public static final RegistryObject<BlockItem> CoalBlock_T1_ITEM = ITEMS.register("coalblock_t1", () -> new CoalBlockItem(CoalBlock_T1.get(), new Item.Properties(),48000));
    public static final RegistryObject<CoalBlock> CoalBlock_T2 = BLOCKS.register("coalblock_t2",() -> new CoalBlock(4));
    public static final RegistryObject<BlockItem> CoalBlock_T2_ITEM = ITEMS.register("coalblock_t2", () -> new CoalBlockItem(CoalBlock_T2.get(), new Item.Properties(),144000));
    public static final RegistryObject<CoalBlock> CoalBlock_T3 = BLOCKS.register("coalblock_t3",() -> new CoalBlock(8));
    public static final RegistryObject<BlockItem> CoalBlock_T3_ITEM = ITEMS.register("coalblock_t3", () -> new CoalBlockItem(CoalBlock_T3.get(), new Item.Properties(),432000));
    public static final RegistryObject<CoalBlock> CoalBlock_T4 = BLOCKS.register("coalblock_t4",() -> new CoalBlock(16));
    public static final RegistryObject<BlockItem> CoalBlock_T4_ITEM = ITEMS.register("coalblock_t4", () -> new CoalBlockItem(CoalBlock_T4.get(), new Item.Properties(),1296000));
    public static final RegistryObject<CharcoalBlock> CharcoalBlock = BLOCKS.register("charcoal", CharcoalBlock::new);
    public static final RegistryObject<BlockItem> CharcoalBlock_ITEM = ITEMS.register("charcoal", () -> new FuelBlockItem(CharcoalBlock.get(), new Item.Properties(),16000));

    //BlockEntities (Not TileEntities - Honest)
    public static final RegistryObject<BlockEntityType<GooBlockBE_Tier1>> GooBlockBE_Tier1 = BLOCK_ENTITIES.register("gooblock_tier1", () -> BlockEntityType.Builder.of(GooBlockBE_Tier1::new, GooBlock_Tier1.get()).build(null));
    public static final RegistryObject<BlockEntityType<GooBlockBE_Tier2>> GooBlockBE_Tier2 = BLOCK_ENTITIES.register("gooblock_tier2", () -> BlockEntityType.Builder.of(GooBlockBE_Tier2::new, GooBlock_Tier2.get()).build(null));
    public static final RegistryObject<BlockEntityType<GooBlockBE_Tier3>> GooBlockBE_Tier3 = BLOCK_ENTITIES.register("gooblock_tier3", () -> BlockEntityType.Builder.of(GooBlockBE_Tier3::new, GooBlock_Tier3.get()).build(null));
    public static final RegistryObject<BlockEntityType<GooBlockBE_Tier4>> GooBlockBE_Tier4 = BLOCK_ENTITIES.register("gooblock_tier4", () -> BlockEntityType.Builder.of(GooBlockBE_Tier4::new, GooBlock_Tier4.get()).build(null));
    public static final RegistryObject<BlockEntityType<GooSoilBE>> GooSoilBE = BLOCK_ENTITIES.register("goosoilbe", () -> BlockEntityType.Builder.of(GooSoilBE::new, GooSoil_Tier3.get(), GooSoil_Tier4.get()).build(null));
    public static final RegistryObject<BlockEntityType<ItemCollectorBE>> ItemCollectorBE = BLOCK_ENTITIES.register("itemcollectorbe", () -> BlockEntityType.Builder.of(ItemCollectorBE::new, ItemCollector.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockBreakerT1BE>> BlockBreakerT1BE = BLOCK_ENTITIES.register("blockbreakert1", () -> BlockEntityType.Builder.of(BlockBreakerT1BE::new, BlockBreakerT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockBreakerT2BE>> BlockBreakerT2BE = BLOCK_ENTITIES.register("blockbreakert2", () -> BlockEntityType.Builder.of(BlockBreakerT2BE::new, BlockBreakerT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockPlacerT1BE>> BlockPlacerT1BE = BLOCK_ENTITIES.register("blockplacert1", () -> BlockEntityType.Builder.of(BlockPlacerT1BE::new, BlockPlacerT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockPlacerT2BE>> BlockPlacerT2BE = BLOCK_ENTITIES.register("blockplacert2", () -> BlockEntityType.Builder.of(BlockPlacerT2BE::new, BlockPlacerT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<ClickerT1BE>> ClickerT1BE = BLOCK_ENTITIES.register("clickert1", () -> BlockEntityType.Builder.of(ClickerT1BE::new, ClickerT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<ClickerT2BE>> ClickerT2BE = BLOCK_ENTITIES.register("clickert2", () -> BlockEntityType.Builder.of(ClickerT2BE::new, ClickerT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<SensorT1BE>> SensorT1BE = BLOCK_ENTITIES.register("sensort1be", () -> BlockEntityType.Builder.of(SensorT1BE::new, SensorT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<SensorT2BE>> SensorT2BE = BLOCK_ENTITIES.register("sensort2be", () -> BlockEntityType.Builder.of(SensorT2BE::new, SensorT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<DropperT1BE>> DropperT1BE = BLOCK_ENTITIES.register("droppert1", () -> BlockEntityType.Builder.of(DropperT1BE::new, DropperT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<DropperT2BE>> DropperT2BE = BLOCK_ENTITIES.register("droppert2", () -> BlockEntityType.Builder.of(DropperT2BE::new, DropperT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorT1BE>> GeneratorT1BE = BLOCK_ENTITIES.register("generatort1", () -> BlockEntityType.Builder.of(GeneratorT1BE::new, GeneratorT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorFluidT1BE>> GeneratorFluidT1BE = BLOCK_ENTITIES.register("generatorfluidt1", () -> BlockEntityType.Builder.of(GeneratorFluidT1BE::new, GeneratorFluidT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyTransmitterBE>> EnergyTransmitterBE = BLOCK_ENTITIES.register("energytransmitter", () -> BlockEntityType.Builder.of(EnergyTransmitterBE::new, EnergyTransmitter.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockSwapperT1BE>> BlockSwapperT1BE = BLOCK_ENTITIES.register("blockswappert1", () -> BlockEntityType.Builder.of(BlockSwapperT1BE::new, BlockSwapperT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockSwapperT2BE>> BlockSwapperT2BE = BLOCK_ENTITIES.register("blockswappert2", () -> BlockEntityType.Builder.of(BlockSwapperT2BE::new, BlockSwapperT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<PlayerAccessorBE>> PlayerAccessorBE = BLOCK_ENTITIES.register("playeraccessorbe", () -> BlockEntityType.Builder.of(PlayerAccessorBE::new, PlayerAccessor.get()).build(null));
    public static final RegistryObject<BlockEntityType<EclipseGateBE>> EclipseGateBE = BLOCK_ENTITIES.register("eclipsegatebe", () -> BlockEntityType.Builder.of(EclipseGateBE::new, EclipseGateBlock.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidPlacerT1BE>> FluidPlacerT1BE = BLOCK_ENTITIES.register("fluidplacert1", () -> BlockEntityType.Builder.of(FluidPlacerT1BE::new, FluidPlacerT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidPlacerT2BE>> FluidPlacerT2BE = BLOCK_ENTITIES.register("fluidplacert2", () -> BlockEntityType.Builder.of(FluidPlacerT2BE::new, FluidPlacerT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidCollectorT1BE>> FluidCollectorT1BE = BLOCK_ENTITIES.register("fluidcollectort1", () -> BlockEntityType.Builder.of(FluidCollectorT1BE::new, FluidCollectorT1.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidCollectorT2BE>> FluidCollectorT2BE = BLOCK_ENTITIES.register("fluidcollectort2", () -> BlockEntityType.Builder.of(FluidCollectorT2BE::new, FluidCollectorT2.get()).build(null));
    public static final RegistryObject<BlockEntityType<ParadoxMachineBE>> ParadoxMachineBE = BLOCK_ENTITIES.register("paradoxmachine", () -> BlockEntityType.Builder.of(ParadoxMachineBE::new, ParadoxMachine.get()).build(null));
    public static final RegistryObject<BlockEntityType<InventoryHolderBE>> InventoryHolderBE = BLOCK_ENTITIES.register("inventory_holder", () -> BlockEntityType.Builder.of(InventoryHolderBE::new, InventoryHolder.get()).build(null));
    public static final RegistryObject<BlockEntityType<ExperienceHolderBE>> ExperienceHolderBE = BLOCK_ENTITIES.register("experienceholder", () -> BlockEntityType.Builder.of(ExperienceHolderBE::new, ExperienceHolder.get()).build(null));

    //Items - Raw Resources
    public static final RegistryObject<Item> RawFerricore = ITEMS.register("raw_ferricore", Registration::basic);
    public static final RegistryObject<Item> RawBlazegold = ITEMS.register("raw_blazegold",Registration::basic);
    public static final RegistryObject<Item> RawEclipseAlloy = ITEMS.register("raw_eclipsealloy", Registration::basic);

    public static Item basic() {
        return new Item(new Item.Properties());
    }

    //Items - Resources
    public static final RegistryObject<Item> FerricoreIngot = ITEMS.register("ferricore_ingot", Registration::basic);
    public static final RegistryObject<BlazeGoldIngot> BlazegoldIngot = ITEMS.register("blazegold_ingot", BlazeGoldIngot::new);
    public static final RegistryObject<Celestigem> Celestigem = ITEMS.register("celestigem", Celestigem::new);
    public static final RegistryObject<Item> EclipseAlloyIngot = ITEMS.register("eclipsealloy_ingot", Registration::basic);
    public static final RegistryObject<TieredCoalItem> Coal_T1 = ITEMS.register("coal_t1", () -> new TieredCoalItem(1));
    public static final RegistryObject<TieredCoalItem> Coal_T2 = ITEMS.register("coal_t2", () -> new TieredCoalItem(2));
    public static final RegistryObject<TieredCoalItem> Coal_T3 = ITEMS.register("coal_t3",() -> new TieredCoalItem(3));
    public static final RegistryObject<TieredCoalItem> Coal_T4 = ITEMS.register("coal_t4", () -> new TieredCoalItem(4));
    public static final RegistryObject<PolymorphicCatalyst> PolymorphicCatalyst = ITEMS.register("polymorphic_catalyst", PolymorphicCatalyst::new);
    public static final RegistryObject<Item> PortalFluidCatalyst = ITEMS.register("portal_fluid_catalyst", () -> new Item(new Item.Properties()));
    public static final RegistryObject<TimeCrystal> TimeCrystal = ITEMS.register("time_crystal", TimeCrystal::new);

    //Items
    public static final RegistryObject<FuelCanisterItem> Fuel_Canister = ITEMS.register("fuel_canister", FuelCanisterItem::new);
    public static final RegistryObject<PocketGeneratorItem> Pocket_Generator = ITEMS.register("pocket_generator", PocketGeneratorItem::new);
    public static final RegistryObject<FerricoreWrenchItem> FerricoreWrench = ITEMS.register("ferricore_wrench", FerricoreWrenchItem::new);
    public static final RegistryObject<TotemOfDeathRecallItem> TotemOfDeathRecall = ITEMS.register("totem_of_death_recall", TotemOfDeathRecallItem::new);
    public static final RegistryObject<BlazejetWandItem> BlazejetWand = ITEMS.register("blazejet_wand", BlazejetWandItem::new);
    public static final RegistryObject<VoidshiftWandItem> VoidshiftWand = ITEMS.register("voidshift_wand", VoidshiftWandItem::new);
    public static final RegistryObject<EclipsegateWandItem> EclipsegateWand = ITEMS.register("eclipsegate_wand", EclipsegateWandItem::new);
    public static final RegistryObject<TimeWandItem> TimeWand = ITEMS.register("time_wand", TimeWandItem::new);
    public static final RegistryObject<CreatureCatcherItem> CREATURECATCHER = ITEMS.register("creaturecatcher", CreatureCatcherItem::new);
    public static final RegistryObject<MachineSettingsCopierItem> MachineSettingsCopier = ITEMS.register("machinesettingscopier", MachineSettingsCopierItem::new);
    public static final RegistryObject<PortalGunItem> PortalGun = ITEMS.register("portalgun", PortalGunItem::new);
    public static final RegistryObject<PortalGunV2Item> PortalGunV2 = ITEMS.register("portalgun_v2", PortalGunV2Item::new);
    public static final RegistryObject<FluidCanisterItem> FluidCanister = ITEMS.register("fluid_canister", FluidCanisterItem::new);
    public static final RegistryObject<PotionCanisterItem> PotionCanister = ITEMS.register("potion_canister", PotionCanisterItem::new);
    public static final RegistryObject<FerricoreBowItem> FerricoreBow = BOWS.register("bow_ferricore", FerricoreBowItem::new);
    public static final RegistryObject<BlazegoldBowItem> BlazegoldBow = BOWS.register("bow_blazegold", BlazegoldBowItem::new);
    public static final RegistryObject<CelestigemBowItem> CelestigemBow = BOWS.register("bow_celestigem", CelestigemBowItem::new);
    public static final RegistryObject<EclipseAlloyBowItem> EclipseAlloyBow = BOWS.register("bow_eclipsealloy", EclipseAlloyBowItem::new);

    //Items - Tools
    public static final RegistryObject<FerricoreSwordItem> FerricoreSword = TOOLS.register("ferricore_sword", FerricoreSwordItem::new);
    public static final RegistryObject<FerricorePickaxeItem> FerricorePickaxe = TOOLS.register("ferricore_pickaxe", FerricorePickaxeItem::new);
    public static final RegistryObject<FerricoreShovel> FerricoreShovel = TOOLS.register("ferricore_shovel", FerricoreShovel::new);
    public static final RegistryObject<FerricoreAxeItem> FerricoreAxe = TOOLS.register("ferricore_axe", FerricoreAxeItem::new);
    public static final RegistryObject<FerricoreHoeItem> FerricoreHoe = TOOLS.register("ferricore_hoe", FerricoreHoeItem::new);
    public static final RegistryObject<BlazegoldSwordItem> BlazegoldSword = TOOLS.register("blazegold_sword", BlazegoldSwordItem::new);
    public static final RegistryObject<BlazegoldPickaxeItem> BlazegoldPickaxe = TOOLS.register("blazegold_pickaxe", BlazegoldPickaxeItem::new);
    public static final RegistryObject<BlazegoldShovel> BlazegoldShovel = TOOLS.register("blazegold_shovel", BlazegoldShovel::new);
    public static final RegistryObject<BlazegoldAxeItem> BlazegoldAxe = TOOLS.register("blazegold_axe", BlazegoldAxeItem::new);
    public static final RegistryObject<BlazegoldHoeItem> BlazegoldHoe = TOOLS.register("blazegold_hoe", BlazegoldHoeItem::new);
    public static final RegistryObject<CelestigemSwordItem> CelestigemSword = TOOLS.register("celestigem_sword", CelestigemSwordItem::new);
    public static final RegistryObject<CelestigemPickaxeItem> CelestigemPickaxe = TOOLS.register("celestigem_pickaxe", CelestigemPickaxeItem::new);
    public static final RegistryObject<CelestigemShovelItem> CelestigemShovel = TOOLS.register("celestigem_shovel", CelestigemShovelItem::new);
    public static final RegistryObject<CelestigemAxeItem> CelestigemAxe = TOOLS.register("celestigem_axe", CelestigemAxeItem::new);
    public static final RegistryObject<CelestigemHoeItem> CelestigemHoe = TOOLS.register("celestigem_hoe", CelestigemHoeItem::new);
    public static final RegistryObject<CelestigemPaxelItem> CelestigemPaxel = TOOLS.register("celestigem_paxel", CelestigemPaxelItem::new);
    public static final RegistryObject<EclipseAlloySwordItem> EclipseAlloySword = TOOLS.register("eclipsealloy_sword", EclipseAlloySwordItem::new);
    public static final RegistryObject<EclipseAlloyPickaxeItem> EclipseAlloyPickaxe = TOOLS.register("eclipsealloy_pickaxe", EclipseAlloyPickaxeItem::new);
    public static final RegistryObject<EclipseAlloyShovelItem> EclipseAlloyShovel = TOOLS.register("eclipsealloy_shovel", EclipseAlloyShovelItem::new);
    public static final RegistryObject<EclipseAlloyAxeItem> EclipseAlloyAxe = TOOLS.register("eclipsealloy_axe", EclipseAlloyAxeItem::new);
    public static final RegistryObject<EclipseAlloyHoeItem> EclipseAlloyHoe = TOOLS.register("eclipsealloy_hoe", EclipseAlloyHoeItem::new);
    public static final RegistryObject<EclipseAlloyPaxelItem> EclipseAlloyPaxel = TOOLS.register("eclipsealloy_paxel", EclipseAlloyPaxelItem::new);

    //Items - Armor
    public static final RegistryObject<FerricoreHelmet> FerricoreHelmet = ARMORS.register("ferricore_helmet", FerricoreHelmet::new);
    public static final RegistryObject<FerricoreChestplate> FerricoreChestplate = ARMORS.register("ferricore_chestplate", FerricoreChestplate::new);
    public static final RegistryObject<FerricoreLeggings> FerricoreLeggings = ARMORS.register("ferricore_leggings", FerricoreLeggings::new);
    public static final RegistryObject<FerricoreBoots> FerricoreBoots = ARMORS.register("ferricore_boots", FerricoreBoots::new);

    public static final RegistryObject<BlazegoldHelmet> BlazegoldHelmet = ARMORS.register("blazegold_helmet", BlazegoldHelmet::new);
    public static final RegistryObject<BlazegoldChestplate> BlazegoldChestplate = ARMORS.register("blazegold_chestplate", BlazegoldChestplate::new);
    public static final RegistryObject<BlazegoldLeggings> BlazegoldLeggings = ARMORS.register("blazegold_leggings", BlazegoldLeggings::new);
    public static final RegistryObject<BlazegoldBoots> BlazegoldBoots = ARMORS.register("blazegold_boots", BlazegoldBoots::new);

    public static final RegistryObject<CelestigemHelmet> CelestigemHelmet = ARMORS.register("celestigem_helmet", CelestigemHelmet::new);
    public static final RegistryObject<CelestigemChestplate> CelestigemChestplate = ARMORS.register("celestigem_chestplate", CelestigemChestplate::new);
    public static final RegistryObject<CelestigemLeggings> CelestigemLeggings = ARMORS.register("celestigem_leggings", CelestigemLeggings::new);
    public static final RegistryObject<CelestigemBoots> CelestigemBoots = ARMORS.register("celestigem_boots", CelestigemBoots::new);

    public static final RegistryObject<EclipseAlloyHelmet> EclipseAlloyHelmet = ARMORS.register("eclipsealloy_helmet", EclipseAlloyHelmet::new);
    public static final RegistryObject<EclipseAlloyChestplate> EclipseAlloyChestplate = ARMORS.register("eclipsealloy_chestplate", EclipseAlloyChestplate::new);
    public static final RegistryObject<EclipseAlloyLeggings> EclipseAlloyLeggings = ARMORS.register("eclipsealloy_leggings", EclipseAlloyLeggings::new);
    public static final RegistryObject<EclipseAlloyBoots> EclipseAlloyBoots = ARMORS.register("eclipsealloy_boots", EclipseAlloyBoots::new);

    //Items - Ability Upgrades
    public static final RegistryObject<UpgradeTemplate> TEMPLATE_FERRICORE = ITEMS.register("template_ferricore", UpgradeTemplate::new);
    public static final RegistryObject<UpgradeTemplate> TEMPLATE_BLAZEGOLD = ITEMS.register("template_blazegold", UpgradeTemplate::new);
    public static final RegistryObject<UpgradeTemplate> TEMPLATE_CELESTIGEM = ITEMS.register("template_celestigem", UpgradeTemplate::new);
    public static final RegistryObject<UpgradeTemplate> TEMPLATE_ECLIPSEALLOY = ITEMS.register("template_eclipsealloy", UpgradeTemplate::new);

    public static final RegistryObject<UpgradeBlank> UPGRADE_BASE = UPGRADES.register("upgrade_blank", UpgradeBlank::new);


    //Tier 1 Abilities
    public static final RegistryObject<Upgrade> UPGRADE_MOBSCANNER = UPGRADES.register("upgrade_mobscanner", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_OREMINER = UPGRADES.register("upgrade_oreminer", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_ORESCANNER = UPGRADES.register("upgrade_orescanner", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_LAWNMOWER = UPGRADES.register("upgrade_lawnmower", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_SKYSWEEPER = UPGRADES.register("upgrade_skysweeper", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_TREEFELLER = UPGRADES.register("upgrade_treefeller", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_LEAFBREAKER = UPGRADES.register("upgrade_leafbreaker", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_RUNSPEED = UPGRADES.register("upgrade_runspeed", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_WALKSPEED = UPGRADES.register("upgrade_walkspeed", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_STEPHEIGHT = UPGRADES.register("upgrade_stepheight", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_JUMPBOOST = UPGRADES.register("upgrade_jumpboost", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_MINDFOG = UPGRADES.register("upgrade_mindfog", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_INVULNERABILITY = UPGRADES.register("upgrade_invulnerability", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_POTIONARROW = UPGRADES.register("upgrade_potionarrow", Upgrade::new);

    //Tier 2 Abilities
    public static final RegistryObject<Upgrade> UPGRADE_SMELTER = UPGRADES.register("upgrade_smelter", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_SMOKER = UPGRADES.register("upgrade_smoker", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_HAMMER = UPGRADES.register("upgrade_hammer", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_CAUTERIZEWOUNDS = UPGRADES.register("upgrade_cauterizewounds", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_SWIMSPEED = UPGRADES.register("upgrade_swimspeed", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_GROUNDSTOMP = UPGRADES.register("upgrade_groundstomp", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_EXTINGUISH = UPGRADES.register("upgrade_extinguish", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_STUPEFY = UPGRADES.register("upgrade_stupefy", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_SPLASH = UPGRADES.register("upgrade_splash", Upgrade::new);

    //Tier 3 Abilities
    public static final RegistryObject<Upgrade> UPGRADE_ELYTRA = UPGRADES.register("upgrade_elytra", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_DROPTELEPORT = UPGRADES.register("upgrade_dropteleport", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_NEGATEFALLDAMAGE = UPGRADES.register("upgrade_negatefalldamage", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_NIGHTVISION = UPGRADES.register("upgrade_nightvision", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_DECOY = UPGRADES.register("upgrade_decoy", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_LINGERING = UPGRADES.register("upgrade_lingering", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_HOMING = UPGRADES.register("upgrade_homing", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_WATERBREATHING = UPGRADES.register("upgrade_waterbreathing", Upgrade::new);

    //Tier 4 Abilities
    public static final RegistryObject<Upgrade> UPGRADE_OREXRAY = UPGRADES.register("upgrade_orexray", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_GLOWING = UPGRADES.register("upgrade_glowing", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_INSTABREAK = UPGRADES.register("upgrade_instabreak", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_EARTHQUAKE = UPGRADES.register("upgrade_earthquake", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_NOAI = UPGRADES.register("upgrade_noai", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_FLIGHT = UPGRADES.register("upgrade_flight", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_LAVAIMMUNITY = UPGRADES.register("upgrade_lavaimmunity", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_PHASE = UPGRADES.register("upgrade_phase", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_DEATHPROTECTION = UPGRADES.register("upgrade_deathprotection", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_DEBUFFREMOVER = UPGRADES.register("upgrade_debuffremover", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_EPICARROW = UPGRADES.register("upgrade_epicarrow", Upgrade::new);
    public static final RegistryObject<Upgrade> UPGRADE_TIMEPROTECTION = UPGRADES.register("upgrade_time_protection", Upgrade::new);

    //Entities
    public static final RegistryObject<EntityType<CreatureCatcherEntity>> CreatureCatcherEntity = ENTITY_TYPES.register("creature_catcher",
            () -> EntityType.Builder.<CreatureCatcherEntity>of(CreatureCatcherEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("creature_catcher"));
    public static final RegistryObject<EntityType<JustDireArrow>> JustDireArrow = ENTITY_TYPES.register("justdirearrow",
            () -> EntityType.Builder.<JustDireArrow>of(JustDireArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    //     .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("justdirearrow"));

    public static final RegistryObject<EntityType<PortalProjectile>> PortalProjectile = ENTITY_TYPES.register("portal_projectile",
            () -> EntityType.Builder.<PortalProjectile>of(PortalProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("portal_projectile"));

    public static final RegistryObject<EntityType<PortalEntity>> PortalEntity = ENTITY_TYPES.register("portal_entity",
            () -> EntityType.Builder.<PortalEntity>of(PortalEntity::new, MobCategory.MISC)
                    .sized(1F, 2F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("portal_entity"));
    public static final RegistryObject<EntityType<DecoyEntity>> DecoyEntity = ENTITY_TYPES.register("decoy_entity",
            () -> EntityType.Builder.<DecoyEntity>of(DecoyEntity::new, MobCategory.MISC)
                    .sized(1F, 2F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("decoy_entity"));

    public static final RegistryObject<EntityType<TimeWandEntity>> TimeWandEntity = ENTITY_TYPES.register("time_wand_entity",
            () -> EntityType.Builder.<TimeWandEntity>of(TimeWandEntity::new, MobCategory.MISC)
                    .sized(1F, 1F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("time_wand_entity"));
    public static final RegistryObject<EntityType<ParadoxEntity>> ParadoxEntity = ENTITY_TYPES.register("paradox_entity",
            () -> EntityType.Builder.<ParadoxEntity>of(ParadoxEntity::new, MobCategory.MISC)
                    .sized(1F, 1F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("paradox_entity"));

    //Attributes
    public static final RegistryObject<Attribute> PHASE = ATTRIBUTES.register("phase", () -> new RangedAttribute("justdirethings.phase", 0D, 0D, Double.MAX_VALUE).setSyncable(true));


    //Containers
    public static final RegistryObject<MenuType<FuelCanisterContainer>> FuelCanister_Container = CONTAINERS.register("fuelcanister",
            () -> IForgeMenuType.create((windowId, inv, data) -> new FuelCanisterContainer(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<PocketGeneratorContainer>> PocketGenerator_Container = CONTAINERS.register("pocketgenerator",
            () -> IForgeMenuType.create((windowId, inv, data) -> new PocketGeneratorContainer(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<ToolSettingContainer>> Tool_Settings_Container = CONTAINERS.register("tool_settings",
            () -> IForgeMenuType.create((windowId, inv, data) -> new ToolSettingContainer(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<ItemCollectorContainer>> Item_Collector_Container = CONTAINERS.register("item_collector_container",
            () -> IForgeMenuType.create(ItemCollectorContainer::new));
    public static final RegistryObject<MenuType<BlockBreakerT1Container>> BlockBreakerT1_Container = CONTAINERS.register("blockbreakert1_container",
            () -> IForgeMenuType.create(BlockBreakerT1Container::new));
    public static final RegistryObject<MenuType<BlockBreakerT2Container>> BlockBreakerT2_Container = CONTAINERS.register("blockbreakert2_container",
            () -> IForgeMenuType.create(BlockBreakerT2Container::new));
    public static final RegistryObject<MenuType<BlockPlacerT1Container>> BlockPlacerT1_Container = CONTAINERS.register("blockplacert1_container",
            () -> IForgeMenuType.create(BlockPlacerT1Container::new));
    public static final RegistryObject<MenuType<BlockPlacerT2Container>> BlockPlacerT2_Container = CONTAINERS.register("blockplacert2_container",
            () -> IForgeMenuType.create(BlockPlacerT2Container::new));
    public static final RegistryObject<MenuType<ClickerT1Container>> ClickerT1_Container = CONTAINERS.register("clickert1_container",
            () -> IForgeMenuType.create(ClickerT1Container::new));
    public static final RegistryObject<MenuType<ClickerT2Container>> ClickerT2_Container = CONTAINERS.register("clickert2_container",
            () -> IForgeMenuType.create(ClickerT2Container::new));
    public static final RegistryObject<MenuType<SensorT1Container>> SensorT1_Container = CONTAINERS.register("sensort1_container",
            () -> IForgeMenuType.create(SensorT1Container::new));
    public static final RegistryObject<MenuType<SensorT2Container>> SensorT2_Container = CONTAINERS.register("sensort2_container",
            () -> IForgeMenuType.create(SensorT2Container::new));
    public static final RegistryObject<MenuType<DropperT1Container>> DropperT1_Container = CONTAINERS.register("droppert1_container",
            () -> IForgeMenuType.create(DropperT1Container::new));
    public static final RegistryObject<MenuType<DropperT2Container>> DropperT2_Container = CONTAINERS.register("droppert2_container",
            () -> IForgeMenuType.create(DropperT2Container::new));
    public static final RegistryObject<MenuType<GeneratorFluidT1Container>> GeneratorFluidT1_Container = CONTAINERS.register("generatorfluidt1_container",
            () -> IForgeMenuType.create(GeneratorFluidT1Container::new));
    public static final RegistryObject<MenuType<GeneratorT1Container>> GeneratorT1_Container = CONTAINERS.register("generatort1_container",
            () -> IForgeMenuType.create(GeneratorT1Container::new));
    public static final RegistryObject<MenuType<EnergyTransmitterContainer>> EnergyTransmitter_Container = CONTAINERS.register("energytransmitter_container",
            () -> IForgeMenuType.create(EnergyTransmitterContainer::new));
    public static final RegistryObject<MenuType<BlockSwapperT1Container>> BlockSwapperT1_Container = CONTAINERS.register("blockswappert1_container",
            () -> IForgeMenuType.create(BlockSwapperT1Container::new));
    public static final RegistryObject<MenuType<BlockSwapperT2Container>> BlockSwapperT2_Container = CONTAINERS.register("blockswappert2_container",
            () -> IForgeMenuType.create(BlockSwapperT2Container::new));
    public static final RegistryObject<MenuType<PlayerAccessorContainer>> PlayerAccessor_Container = CONTAINERS.register("playeraccessor_container",
            () -> IForgeMenuType.create(PlayerAccessorContainer::new));
    public static final RegistryObject<MenuType<FluidPlacerT1Container>> FluidPlacerT1_Container = CONTAINERS.register("fluidplacert1_container",
            () -> IForgeMenuType.create(FluidPlacerT1Container::new));
    public static final RegistryObject<MenuType<FluidPlacerT2Container>> FluidPlacerT2_Container = CONTAINERS.register("fluidplacert2_container",
            () -> IForgeMenuType.create(FluidPlacerT2Container::new));
    public static final RegistryObject<MenuType<FluidCollectorT1Container>> FluidCollectorT1_Container = CONTAINERS.register("fluidcollectort1_container",
            () -> IForgeMenuType.create(FluidCollectorT1Container::new));
    public static final RegistryObject<MenuType<FluidCollectorT2Container>> FluidCollectorT2_Container = CONTAINERS.register("fluidcollectort2_container",
            () -> IForgeMenuType.create(FluidCollectorT2Container::new));
    public static final RegistryObject<MenuType<PotionCanisterContainer>> PotionCanister_Container = CONTAINERS.register("potioncanister_container",
            () -> IForgeMenuType.create((windowId, inv, data) -> new PotionCanisterContainer(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<ParadoxMachineContainer>> ParadoxMachine_Container = CONTAINERS.register("paradoxmachine_container",
            () -> IForgeMenuType.create(ParadoxMachineContainer::new));
    public static final RegistryObject<MenuType<InventoryHolderContainer>> InventoryHolder_Container = CONTAINERS.register("inventoryholder_container",
            () -> IForgeMenuType.create(InventoryHolderContainer::new));
    public static final RegistryObject<MenuType<ExperienceHolderContainer>> Experience_Holder_Container = CONTAINERS.register("experienceholder_container",
            () -> IForgeMenuType.create(ExperienceHolderContainer::new));

    //Data Attachments
   /* public static final Supplier<AttachmentType<ItemStackHandler>> HANDLER = ATTACHMENT_TYPES.register(
            "handler", () -> AttachmentType.serializable(() -> new ItemStackHandler(1)).build());
    public static final Supplier<AttachmentType<GeneratorItemHandler>> GENERATOR_ITEM_HANDLER = ATTACHMENT_TYPES.register(
            "generator_item_handler", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof BaseMachineBE baseMachineBE)
                    return new GeneratorItemHandler(baseMachineBE.MACHINE_SLOTS);
                return new GeneratorItemHandler(1);
            }).build());*/
    /*public static final Supplier<AttachmentType<InventoryHolderItemHandler>> INVENTORY_HOLDER_ITEM_HANDLER = ATTACHMENT_TYPES.register(
            "inventory_holder_item_handler", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof InventoryHolderBE inventoryHolderBE)
                    return new InventoryHolderItemHandler(inventoryHolderBE.MACHINE_SLOTS, inventoryHolderBE);
                return new InventoryHolderItemHandler(1);
            }).build());*/
    /*public static final Supplier<AttachmentType<GeneratorFluidItemHandler>> GENERATOR_FLUID_ITEM_HANDLER = ATTACHMENT_TYPES.register(
            "generator_fluid_item_handler", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof BaseMachineBE baseMachineBE)
                    return new GeneratorFluidItemHandler(baseMachineBE.MACHINE_SLOTS);
                return new GeneratorFluidItemHandler(1);
            }).build());


    public static final Supplier<AttachmentType<MachineEnergyStorage>> ENERGYSTORAGE_MACHINES = ATTACHMENT_TYPES.register(
            "energystorage_machines", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof PoweredMachineBE feMachineBE) {
                    int capacity = feMachineBE.getMaxEnergy(); //Default
                    return new MachineEnergyStorage(capacity);
                } else {
                    throw new IllegalStateException("Cannot attach energy handler item to a non-PoweredMachine.");
                }
            }).build());*/
   /* public static final Supplier<AttachmentType<TransmitterEnergyStorage>> ENERGYSTORAGE_TRANSMITTERS = ATTACHMENT_TYPES.register(
            "energystorage_transmitters", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof EnergyTransmitterBE energyTransmitterBE) {
                    int capacity = energyTransmitterBE.getMaxEnergy(); //Default
                    return new TransmitterEnergyStorage(capacity, energyTransmitterBE);
                } else {
                    throw new IllegalStateException("Cannot attach energy handler item to a non-EnergyTransmitterBlock.");
                }
            }).build());*/
    /*public static final Supplier<AttachmentType<EnergyStorageNoReceive>> ENERGYSTORAGE_GENERATORS = ATTACHMENT_TYPES.register(
            "energystorage_generators", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof PoweredMachineBE feMachineBE) {
                    int capacity = feMachineBE.getMaxEnergy(); //Default
                    return new EnergyStorageNoReceive(capacity);
                } else {
                    throw new IllegalStateException("Cannot attach energy handler item to a non-PoweredMachine.");
                }
            }).build());*/
   /* public static final Supplier<AttachmentType<CompoundTag>> DEATH_DATA = ATTACHMENT_TYPES.register(
            "death_data",
            () -> AttachmentType.builder(CompoundTag::new).serialize(CompoundTag.CODEC).build()
    );*/

    //Fluids
    /*public static final Supplier<AttachmentType<JustDireFluidTank>> MACHINE_FLUID_HANDLER = ATTACHMENT_TYPES.register(
            "machine_fluid_handler", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof FluidMachineBE fluidMachineBE)
                    return new JustDireFluidTank(fluidMachineBE.getMaxMB());
                return new JustDireFluidTank(0);
            }).build());
    public static final Supplier<AttachmentType<JustDireFluidTank>> GENERATOR_FLUID_HANDLER = ATTACHMENT_TYPES.register(
            "generator_fluid_handler", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof FluidMachineBE fluidMachineBE)
                    return new JustDireFluidTank(fluidMachineBE.getMaxMB(), fluidstack -> fluidstack.getFluid() instanceof RefinedFuel);
                return new JustDireFluidTank(0);
            }).build());*/
    /*public static final Supplier<AttachmentType<JustDireFluidTank>> PARADOX_FLUID_HANDLER = ATTACHMENT_TYPES.register(
            "paradox_fluid_handler", () -> AttachmentType.serializable(holder -> {
                if (holder instanceof FluidMachineBE fluidMachineBE)
                    return new JustDireFluidTank(fluidMachineBE.getMaxMB(), fluidstack -> fluidstack.getFluid() instanceof TimeFluid);
                return new JustDireFluidTank(0);
            }).build());*/
}
