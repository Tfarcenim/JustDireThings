package com.direwolf20.justdirethings.datagen;

import com.direwolf20.justdirethings.JustDireThings;
import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class JustDireItemTags extends ItemTagsProvider {
    public JustDireItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, blockTags.contentsGetter(), JustDireThings.MODID, helper);
    }

    public static final TagKey<Item> WRENCHES = forgeTag("wrenches");
    public static final TagKey<Item> TOOLS_WRENCH = forgeTag("tools/wrench");
    public static final TagKey<Item> RAW_FERRICORE = forgeTag("raw_materials/ferricore");
    public static final TagKey<Item> RAW_BLAZEGOLD = forgeTag("raw_materials/blazegold");
    public static final TagKey<Item> RAW_ECLIPSEALLOY = forgeTag("raw_materials/eclipsealloy");
    public static final TagKey<Item> INGOT_FERRICORE = forgeTag("ingots/ferricore");
    public static final TagKey<Item> INGOT_BLAZEGOLD = forgeTag("ingots/blazegold");
    public static final TagKey<Item> INGOT_ECLIPSEALLOY = forgeTag("ingots/eclipsealloy");

    public static final TagKey<Item> BOWS = forgeTag("tools/bow");
    public static final TagKey<Item> RANGED_WEAPON = forgeTag("tools/ranged_weapon");
    public static final TagKey<Item> MELEE_WEAPON = forgeTag("tools/melee_weapon");
    public static final TagKey<Item> MINING_TOOL = forgeTag("tools/mining_tool");
    public static final TagKey<Item> PAXEL = forgeTag("tools/paxel");


    private static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(new ResourceLocation("c", name));
    }

    public static final TagKey<Item> FUEL_CANISTER_DENY = ItemTags.create(JustDireThings.id("deny_fuel_canister"));
    public static final TagKey<Item> AUTO_SMELT_DENY = ItemTags.create(JustDireThings.id("auto_smelt_deny"));
    public static final TagKey<Item> AUTO_SMOKE_DENY = ItemTags.create(JustDireThings.id("auto_smoke_deny"));
    public static final TagKey<Item> GOO_REVIVE_TIER_1 = ItemTags.create(JustDireThings.id("goo_revive_tier_1"));
    public static final TagKey<Item> GOO_REVIVE_TIER_2 = ItemTags.create(JustDireThings.id("goo_revive_tier_2"));
    public static final TagKey<Item> GOO_REVIVE_TIER_3 = ItemTags.create(JustDireThings.id("goo_revive_tier_3"));
    public static final TagKey<Item> GOO_REVIVE_TIER_4 = ItemTags.create(JustDireThings.id("goo_revive_tier_4"));
    public static final TagKey<Item> PARADOX_DENY = ItemTags.create(JustDireThings.id("paradox_deny"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AUTO_SMELT_DENY);
        tag(AUTO_SMOKE_DENY);
        tag(FUEL_CANISTER_DENY)
                .add(Items.LAVA_BUCKET);
        tag(ItemTags.SWORDS)
                .add(Registration.FerricoreSword.get())
                .add(Registration.BlazegoldSword.get())
                .add(Registration.CelestigemSword.get())
                .add(Registration.EclipseAlloySword.get());
        tag(ItemTags.PICKAXES)
                .add(Registration.FerricorePickaxe.get())
                .add(Registration.BlazegoldPickaxe.get())
                .add(Registration.CelestigemPickaxe.get())
                .add(Registration.EclipseAlloyPickaxe.get())
                .add(Registration.CelestigemPaxel.get())
                .add(Registration.EclipseAlloyPaxel.get());
        tag(ItemTags.SHOVELS)
                .add(Registration.FerricoreShovel.get())
                .add(Registration.BlazegoldShovel.get())
                .add(Registration.CelestigemShovel.get())
                .add(Registration.EclipseAlloyShovel.get())
                .add(Registration.CelestigemPaxel.get())
                .add(Registration.EclipseAlloyPaxel.get());
        tag(ItemTags.AXES)
                .add(Registration.FerricoreAxe.get())
                .add(Registration.BlazegoldAxe.get())
                .add(Registration.CelestigemAxe.get())
                .add(Registration.EclipseAlloyAxe.get())
                .add(Registration.CelestigemPaxel.get())
                .add(Registration.EclipseAlloyPaxel.get());
        tag(ItemTags.HOES)
                .add(Registration.FerricoreHoe.get())
                .add(Registration.BlazegoldHoe.get())
                .add(Registration.CelestigemHoe.get())
                .add(Registration.EclipseAlloyHoe.get());
        tag(Tags.Items.INGOTS)
                .add(Registration.FerricoreIngot.get())
                .add(Registration.BlazegoldIngot.get())
                .add(Registration.EclipseAlloyIngot.get());
        tag(Tags.Items.RAW_MATERIALS)
                .add(Registration.RawFerricore.get())
                .add(Registration.RawBlazegold.get())
                .add(Registration.RawEclipseAlloy.get());
        tag(Tags.Items.GEMS)
                .add(Registration.Celestigem.get());
        tag(WRENCHES)
                .add(Registration.FerricoreWrench.get());
        tag(TOOLS_WRENCH)
                .add(Registration.FerricoreWrench.get());
        tag(Tags.Items.STORAGE_BLOCKS)
                .add(Registration.FerricoreBlock_ITEM.get())
                .add(Registration.BlazeGoldBlock_ITEM.get())
                .add(Registration.CelestigemBlock_ITEM.get())
                .add(Registration.EclipseAlloyBlock_ITEM.get());
        tag(Tags.Items.ARMORS_BOOTS)
                .add(Registration.FerricoreBoots.get())
                .add(Registration.BlazegoldBoots.get())
                .add(Registration.CelestigemBoots.get())
                .add(Registration.EclipseAlloyBoots.get());
        tag(Tags.Items.ARMORS_LEGGINGS)
                .add(Registration.FerricoreLeggings.get())
                .add(Registration.BlazegoldLeggings.get())
                .add(Registration.CelestigemLeggings.get())
                .add(Registration.EclipseAlloyLeggings.get());
        tag(Tags.Items.ARMORS_CHESTPLATES)
                .add(Registration.FerricoreChestplate.get())
                .add(Registration.BlazegoldChestplate.get())
                .add(Registration.CelestigemChestplate.get())
                .add(Registration.EclipseAlloyChestplate.get());
        tag(Tags.Items.ARMORS_HELMETS)
                .add(Registration.FerricoreHelmet.get())
                .add(Registration.BlazegoldHelmet.get())
                .add(Registration.CelestigemHelmet.get())
                .add(Registration.EclipseAlloyHelmet.get());
        tag(Tags.Items.TOOLS_BOWS)
                .add(Registration.FerricoreBow.get())
                .add(Registration.BlazegoldBow.get())
                .add(Registration.CelestigemBow.get())
                .add(Registration.EclipseAlloyBow.get());
        tag(RAW_FERRICORE)
                .add(Registration.RawFerricore.get());
        tag(RAW_BLAZEGOLD)
                .add(Registration.RawBlazegold.get());
        tag(RAW_ECLIPSEALLOY)
                .add(Registration.RawEclipseAlloy.get());
        tag(INGOT_FERRICORE)
                .add(Registration.FerricoreIngot.get());
        tag(INGOT_BLAZEGOLD)
                .add(Registration.BlazegoldIngot.get());
        tag(INGOT_ECLIPSEALLOY)
                .add(Registration.EclipseAlloyIngot.get());
        tag(GOO_REVIVE_TIER_1)
                .add(Items.SUGAR)
                .add(Items.ROTTEN_FLESH);
        tag(GOO_REVIVE_TIER_2)
                .add(Items.NETHER_WART)
                .add(Items.BLAZE_POWDER);
        tag(GOO_REVIVE_TIER_3)
                .add(Items.CHORUS_FRUIT)
                .add(Items.ENDER_PEARL);
        tag(GOO_REVIVE_TIER_4)
                .add(Items.SCULK)
                .add(Items.SCULK_CATALYST);
        tag(BOWS)
                .add(Registration.FerricoreBow.get())
                .add(Registration.BlazegoldBow.get())
                .add(Registration.CelestigemPickaxe.get())
                .add(Registration.EclipseAlloyBow.get());
        tag(RANGED_WEAPON)
                .add(Registration.FerricoreBow.get())
                .add(Registration.BlazegoldBow.get())
                .add(Registration.CelestigemPickaxe.get())
                .add(Registration.EclipseAlloyBow.get());
        tag(MELEE_WEAPON)
                .add(Registration.FerricoreSword.get())
                .add(Registration.FerricoreAxe.get())
                .add(Registration.BlazegoldSword.get())
                .add(Registration.BlazegoldAxe.get())
                .add(Registration.CelestigemSword.get())
                .add(Registration.CelestigemAxe.get())
                .add(Registration.EclipseAlloySword.get())
                .add(Registration.EclipseAlloyAxe.get())
                .add(Registration.CelestigemPaxel.get())
                .add(Registration.EclipseAlloyPaxel.get());
        tag(MINING_TOOL)
                .add(Registration.CelestigemPaxel.get())
                .add(Registration.EclipseAlloyPaxel.get());
        tag(PAXEL)
                .add(Registration.CelestigemPaxel.get())
                .add(Registration.EclipseAlloyPaxel.get());
        tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
                .add(Registration.FerricorePickaxe.get())
                .add(Registration.BlazegoldPickaxe.get())
                .add(Registration.CelestigemPickaxe.get())
                .add(Registration.EclipseAlloyPickaxe.get())
                .add(Registration.CelestigemPaxel.get())
                .add(Registration.EclipseAlloyPaxel.get());
        tag(PARADOX_DENY)
                .add(Items.BEDROCK);
    }

    @Override
    public String getName() {
        return "JustDireThingsForge Item Tags";
    }
}
