package com.direwolf20.justdirethings.datagen;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.JustDireTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class JustDireBlockTags extends BlockTagsProvider {

    public JustDireBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, JustDireThings.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(Registration.GooBlock_Tier1.get())
                .add(Registration.GooBlock_Tier2.get())
                .add(Registration.GooBlock_Tier3.get())
                .add(Registration.GooBlock_Tier4.get())
                .add(Registration.GooSoil_Tier1.get())
                .add(Registration.GooSoil_Tier2.get())
                .add(Registration.GooSoil_Tier3.get())
                .add(Registration.GooSoil_Tier4.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Registration.FerricoreBlock.get())
                .add(Registration.RawFerricoreOre.get())
                .add(Registration.BlazeGoldBlock.get())
                .add(Registration.RawBlazegoldOre.get())
                .add(Registration.CelestigemBlock.get())
                .add(Registration.RawCelestigemOre.get())
                .add(Registration.EclipseAlloyBlock.get())
                .add(Registration.RawEclipseAlloyOre.get())
                .add(Registration.ItemCollector.get())
                .add(Registration.BlockBreakerT1.get())
                .add(Registration.BlockBreakerT2.get())
                .add(Registration.BlockPlacerT1.get())
                .add(Registration.BlockPlacerT2.get())
                .add(Registration.ClickerT1.get())
                .add(Registration.ClickerT2.get())
                .add(Registration.SensorT1.get())
                .add(Registration.SensorT2.get())
                .add(Registration.DropperT1.get())
                .add(Registration.DropperT2.get())
                .add(Registration.GeneratorT1.get())
                .add(Registration.GeneratorFluidT1.get())
                .add(Registration.EnergyTransmitter.get())
                .add(Registration.RawCoal_T1.get())
                .add(Registration.RawCoal_T2.get())
                .add(Registration.RawCoal_T3.get())
                .add(Registration.RawCoal_T4.get())
                .add(Registration.CoalBlock_T1.get())
                .add(Registration.CoalBlock_T2.get())
                .add(Registration.CoalBlock_T3.get())
                .add(Registration.CoalBlock_T4.get())
                .add(Registration.BlockSwapperT1.get())
                .add(Registration.BlockSwapperT2.get())
                .add(Registration.PlayerAccessor.get())
                .add(Registration.FluidPlacerT1.get())
                .add(Registration.FluidPlacerT2.get())
                .add(Registration.FluidCollectorT1.get())
                .add(Registration.FluidCollectorT2.get())
                .add(Registration.TimeCrystalCluster.get())
                .add(Registration.TimeCrystalBlock.get())
                .add(Registration.ParadoxMachine.get())
                .add(Registration.InventoryHolder.get())
                .add(Registration.ExperienceHolder.get())
                .add(Registration.CharcoalBlock.get());
        tag(JustDireTags.Blocks.LAWNMOWERABLE)
                .addTag(BlockTags.FLOWERS)
                .add(Blocks.TALL_GRASS)
                .add(Blocks.DEAD_BUSH)
                .add(Blocks.SWEET_BERRY_BUSH)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN);
        tag(Tags.Blocks.ORES)
                .add(Registration.RawFerricoreOre.get())
                .add(Registration.RawBlazegoldOre.get())
                .add(Registration.RawCelestigemOre.get())
                .add(Registration.RawEclipseAlloyOre.get())
                .add(Registration.RawCoal_T1.get())
                .add(Registration.RawCoal_T2.get())
                .add(Registration.RawCoal_T3.get())
                .add(Registration.RawCoal_T4.get());
        tag(Tags.Blocks.STORAGE_BLOCKS)
                .add(Registration.FerricoreBlock.get())
                .add(Registration.BlazeGoldBlock.get())
                .add(Registration.CelestigemBlock.get())
                .add(Registration.EclipseAlloyBlock.get())
                .add(Registration.CoalBlock_T1.get())
                .add(Registration.CoalBlock_T2.get())
                .add(Registration.CoalBlock_T3.get())
                .add(Registration.CoalBlock_T4.get());
        tag(BlockTags.BAMBOO_PLANTABLE_ON)
                .add(Registration.GooSoil_Tier1.get())
                .add(Registration.GooSoil_Tier2.get())
                .add(Registration.GooSoil_Tier3.get())
                .add(Registration.GooSoil_Tier4.get());
        tag(JustDireTags.Blocks.NO_AUTO_CLICK);
        tag(JustDireTags.Blocks.SWAPPERDENY)
                .add(Blocks.PISTON_HEAD)
                .add(Blocks.MOVING_PISTON)
                .add(Blocks.BEDROCK)
                .add(Blocks.END_PORTAL_FRAME)
                .add(Blocks.CANDLE_CAKE)
                .addTag(BlockTags.BEDS)
                .addTag(BlockTags.PORTALS)
                .addTag(BlockTags.DOORS);
        tag(JustDireTags.Blocks.ECLISEGATEDENY)
                .addTag(BlockTags.PORTALS)
                .addTag(BlockTags.DOORS)
                .addOptionalTag(new ResourceLocation("powah", "player_transmitters"));
        tag(JustDireTags.Blocks.PHASEDENY)
                .addTags(BlockTags.PORTALS)
                .add(Blocks.BARRIER,
                        Blocks.BEDROCK,
                        Blocks.END_PORTAL,
                        Blocks.END_PORTAL_FRAME,
                        Blocks.END_GATEWAY,
                        Blocks.STRUCTURE_BLOCK,
                        Blocks.JIGSAW);
   //     tag(Tags.Blocks.BUDDING_BLOCKS)
   //             .add(Registration.TimeCrystalBuddingBlock.get());
   //     tag(Tags.Blocks.BUDS)
   //             .add(Registration.TimeCrystalCluster_Small.get())
    //            .add(Registration.TimeCrystalCluster_Medium.get())
    //            .add(Registration.TimeCrystalCluster_Large.get());
  //      tag(Tags.Blocks.CLUSTERS)
   //             .add(Registration.TimeCrystalClusterBlock.get());
        tag(JustDireTags.Blocks.TICK_SPEED_DENY);
        tag(JustDireTags.Blocks.PARADOX_ALLOW)
                .addTag(Tags.Blocks.ORES);
        tag(JustDireTags.Blocks.PARADOX_ABSORB_DENY)
                .add(Blocks.BEDROCK);
        tag(JustDireTags.Blocks.CHARCOAL)
                .add(Registration.CharcoalBlock.get());
    }

    @Override
    public String getName() {
        return "JustDireThingsForge Tags";
    }
}
