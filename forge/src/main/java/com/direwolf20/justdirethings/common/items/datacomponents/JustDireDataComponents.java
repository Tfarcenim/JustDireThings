package com.direwolf20.justdirethings.common.items.datacomponents;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.ToolRecords;
import com.direwolf20.justdirethings.util.FillMode;
import com.direwolf20.justdirethings.util.MiscHelpers;
import com.direwolf20.justdirethings.util.NBTHelpers;
import com.direwolf20.justdirethings.util.PotionContents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mojang.text2speech.Narrator.LOGGER;

public class JustDireDataComponents {

    public static final String ABILITY_COOLDOWNS = "ability_cooldowns";


    static Boolean getBoolean(ItemStack stack,String key) {//booleans are bytes internally
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_BYTE) ? stack.getTag().getBoolean(key) : null;
    }

    static void setBoolean(ItemStack stack,Boolean value,String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putBoolean(key,value);
        }
    }

    static Integer getInt(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_INT) ? stack.getTag().getInt(key) : null;
    }

    static void setInt(ItemStack stack,Integer value,String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putInt(key,value);
        }
    }


    static  <E extends Enum<E>> E getEnum(ItemStack stack,String key,Class<E> clazz) {
        E[] constants = clazz.getEnumConstants();
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_INT) ? constants[stack.getTag().getInt(key)] : null;
    }

    static <E extends Enum<E>> void setEnum(ItemStack stack,E value,String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putInt(key,value.ordinal());
        }
    }

    static <E extends Enum<E>> void cycleEnum(ItemStack stack,String key,Class<E> clazz) {
        E e;
        if (stack.hasTag() && stack.getTag().contains(key,Tag.TAG_INT)) {
            e = getEnum(stack, key, clazz);
        } else {
            e = clazz.getEnumConstants()[0];
        }
        setEnum(stack, MiscHelpers.cycle(e),key);
    }


    static Double getDouble(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key,Tag.TAG_DOUBLE) ? stack.getTag().getDouble(key) : null;
    }

    static void setDouble(ItemStack stack,Double value,String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putDouble(key,value);
        }
    }

    static String getString(ItemStack stack,String key) {
        return stack.hasTag() && stack.getTag().contains(key) ? stack.getTag().getString(key) : null;
    }

    static void setString(ItemStack stack,String value, String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putString(key,value);
        }
    }


    public static String getEntityType(ItemStack stack) {
        return getString(stack,"entitytype");
    }

    public static void setEntityType(ItemStack stack,String value) {
        setString(stack,value,"entitytype");
    }

    public static UUID getUUID(ItemStack stack,String key) {
        if (stack.hasTag() && stack.getTag().hasUUID(key)) {
            return stack.getTag().getUUID(key);
        }
        return null;
    }

    public static void setUUID(ItemStack stack,UUID uuid,String key) {
        if (uuid == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putUUID(key,uuid);
        }
    }

    public static float getFloatingTicks(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getFloat("floatingticks") : 0;
    }

    public static BlockPos getLavaPos(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("lavapos")) {
            int[] ints = stack.getTag().getIntArray("lavapos");
            return new BlockPos(ints[0],ints[1],ints[2]);
        }
        return null;
    }

    public static void setLavaPos(ItemStack stack,@Nullable BlockPos value) {
        if (value == null) {
            stack.removeTagKey("lavapos");
        } else {
            stack.getOrCreateTag().putIntArray("lavapos",new int[]{value.getX(),value.getY(),value.getZ()});
        }
    }


    public static List<ToolRecords.AbilityCooldown> getAbilityCooldowns(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(ABILITY_COOLDOWNS)) {
            return getList(stack,ToolRecords.AbilityCooldown.CODEC,ABILITY_COOLDOWNS);
        }
        return null;
    }

    public static void setAbilityCooldowns(ItemStack stack, @Nullable List<ToolRecords.AbilityCooldown> cooldowns) {
        setList(stack,cooldowns, ToolRecords.AbilityCooldown.CODEC,ABILITY_COOLDOWNS);
    }

    public static NBTHelpers.BoundInventory getBoundInventory(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("bound_inventory")) {
            return NBTHelpers.BoundInventory.fromNBT(stack.getTag().getCompound("bound_inventory"));
        }
        return null;
    }

    public static void setBoundInventory(ItemStack stack,@Nullable NBTHelpers.BoundInventory inventory) {
        if (inventory == null) {
            stack.removeTagKey("bound_inventory");
        } else {
            stack.getOrCreateTag().put("bound_inventory",inventory.toNBT());
        }
    }

    public static Boolean getToolEnabled(ItemStack stack) {
        return getBoolean(stack,"tool_enabled");
    }

    public static void setToolEnabled(ItemStack stack,Boolean value) {
        setBoolean(stack,value,"tool_enabled");
    }

    public static NBTHelpers.GlobalVec3 getBoundGlobalVec3(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("bound_global_vec3")) {
            return NBTHelpers.GlobalVec3.fromTag(stack.getTagElement("bound_global_vec3"));
        }
        return null;
    }

    public static void setBoundGlobalVec3(ItemStack stack,@Nullable NBTHelpers.GlobalVec3 vec3) {
        if (vec3 == null) {
            stack.removeTagKey("bound_global_vec3");
        } else {
            stack.getOrCreateTag().put("bound_global_vec3",vec3.toTag());
        }
    }

    public static GlobalPos getBoundGlobalPos(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("bound_global_pos")) {
            return NBTHelpers.nbtToGlobalPos(stack.getTagElement("bound_global_pos"));
        }
        return null;
    }

    public static void setBoundGlobalPos(ItemStack stack,@Nullable GlobalPos pos) {
        if (pos == null) {
            stack.removeTagKey("bound_global_pos");
        } else {
            stack.getOrCreateTag().put("bound_global_pos",NBTHelpers.globalPosToNBT(pos));
        }
    }

    public static List<String> getLeftClickAbilities(ItemStack stack) {
        return getList(stack,Codec.STRING,"left_click_abilities");
    }

    public static void setLeftClickAbilities(ItemStack stack,@Nullable List<String> list) {
        setList(stack,list,Codec.STRING,"left_click_abilities");
    }

    static <T> List<T> getList(ItemStack stack, Codec<T> codec, String key) {
        if (stack.hasTag() && stack.getTag().contains(key,Tag.TAG_LIST)) {
            ListTag listTag = (ListTag) stack.getTag().get(key);
            return codec.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, listTag)).resultOrPartial(LOGGER::error).orElseGet(ArrayList::new);
        }
        return null;
    }

    static <T> void setList(ItemStack stack,List<T> list, Codec<T> codec, String key) {
        if (list == null) {
            stack.removeTagKey(key);
        } else {
            ListTag listTag = new ListTag();
            codec.listOf().encodeStart(NbtOps.INSTANCE, list).resultOrPartial(LOGGER::error).ifPresent(listTag::add);
            stack.getOrCreateTag().put(key,listTag);
        }
    }

    public static ToolRecords.AbilityBinding getAbilityBindings(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("ability_bindings")) {
            return ToolRecords.AbilityBinding.fromTag(stack.getTagElement("ability_bindings"));
        }
        return null;
    }

    public static void setAbilityBindings(ItemStack stack, ToolRecords.AbilityBinding binding) {
        if (binding == null) {
            stack.removeTagKey("ability_bindings");
        } else {
            stack.getOrCreateTag().put("ability_bindings",binding.toTag());
        }
    }

    public static Integer getPocketgenCounter(ItemStack stack) {
        return getInt(stack,"pocketgen_counter");
    }

    public static void setPocketgenCounter(ItemStack stack,Integer value) {
        setInt(stack,value,"pocketgen_counter");
    }

    public static Integer getPocketgenFuelmult(ItemStack stack) {
        return getInt(stack,"pocketgen_fuelmult");
    }

    public static void setPocketgenFuelmult(ItemStack stack,Integer value) {
        setInt(stack,value,"pocketgen_fuelmult");
    }

    public static Integer getPocketgenMaxburn(ItemStack stack) {
        return getInt(stack,"pocketgen_maxburn");
    }

    public static void setPocketgenMaxburn(ItemStack stack,Integer value) {
        setInt(stack,value,"pocketgen_maxburn");
    }

    public static Integer getFuelCanisterFuelLevel(ItemStack stack) {
        return getInt(stack,"fuelcanister_fuellevel");
    }

    public static void setFuelcanisterFuellevel(ItemStack stack,Integer value) {
        setInt(stack,value,"fuelcanister_fuellevel");
    }

    public static Double getFuelCanisterBurnspeed(ItemStack stack) {
        return getDouble(stack,"fuelcanister_burnspeed");
    }

    public static void setFuelcanisterBurnspeed(ItemStack stack,Double value) {
        setDouble(stack,value,"fuelcanister_burnspeed");
    }

    public static Boolean getCopyAreaSettings(ItemStack stack) {
        return getBoolean(stack,"copy_area_settings");
    }

    public static void setCopyAreaSettings(ItemStack stack,Boolean value) {
        setBoolean(stack,value,"copy_area_settings");
    }

    public static Boolean getCopyOffsetSettings(ItemStack stack) {
        return getBoolean(stack,"copy_offset_settings");
    }

    public static void setCopyOffsetSettings(ItemStack stack,Boolean value) {
        setBoolean(stack,value,"copy_offset_settings");
    }

    public static Boolean getCopyFilterSettings(ItemStack stack) {
        return getBoolean(stack,"copy_filter_settings");
    }

    public static void setCopyFilterSettings(ItemStack stack,Boolean value) {
        setBoolean(stack,value,"copy_filter_settings");
    }

    public static Boolean getCopyRedstoneSettings(ItemStack stack) {
        return getBoolean(stack,"copy_redstone_settings");
    }

    public static void setCopyRedstoneSettings(ItemStack stack,Boolean value) {
        setBoolean(stack,value,"copy_redstone_settings");
    }

    public static UUID getPortalgunUUID(ItemStack stack) {
        return getUUID(stack,"portalgun_uuid");
    }

    public static void setPortalgunUUID(ItemStack stack,UUID value) {
        setUUID(stack,value,"portalgun_uuid");
    }

    public static Integer getPortalgunFavorite(ItemStack stack) {
        return getInt(stack,"portalgun_favorite");
    }

    public static void setPortalgunFavorite(ItemStack stack,Integer value) {
        setInt(stack,value,"portalgun_favorite");
    }

    static NBTHelpers.PortalDestination getPortalDestination(ItemStack stack,String key) {
        if (stack.hasTag() && stack.getTag().contains(key)) {
            return NBTHelpers.PortalDestination.fromNBT(stack.getTagElement(key));
        }
        return null;
    }
    static void setPortalDestination(ItemStack stack, @Nullable NBTHelpers.PortalDestination destination,String key) {
        if (destination == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().put(key,destination.toNBT());
        }
    }

    static List<NBTHelpers.PortalDestination> getPortalGunFavorites(ItemStack stack) {
        return getList(stack, NBTHelpers.PortalDestination.CODEC,"portal_gun_favorites");
    }

    static void setPortalGunFavorites(ItemStack stack, @Nullable List<NBTHelpers.PortalDestination> destination) {
        setList(stack,destination, NBTHelpers.PortalDestination.CODEC,"portal_gun_favorites");
    }

    public static NBTHelpers.PortalDestination getPortalGunPrevious(ItemStack stack) {
        return getPortalDestination(stack,"portal_gun_previous");
    }

    public static void setPortalGunPrevious(ItemStack stack, @Nullable NBTHelpers.PortalDestination destination) {
        setPortalDestination(stack,destination,"portal_gun_previous");
    }

    public static Boolean getPortalGunStayOpen(ItemStack stack) {
        return getBoolean(stack,"portal_gun_stay_open");
    }

    public static void setPortalGunStayOpen(ItemStack stack,Boolean value) {
        setBoolean(stack,value,"portal_gun_stay_open");
    }

    public static FluidStack getFluidContainer(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("fluid_container")) {
            return FluidStack.loadFluidStackFromNBT(stack.getTagElement("fluid_container"));
        }
        return FluidStack.EMPTY;
    }

    public static void setFluidContainer(ItemStack stack,FluidStack fluidStack) {
        if (fluidStack.isEmpty()) {
            stack.removeTagKey("fluid_container");
        } else {
            stack.getOrCreateTag().put("fluid_container",fluidStack.writeToNBT(new CompoundTag()));
        }
    }

    public static Integer getForgeEnergy(ItemStack stack) {
        return getInt(stack,"forge_energy");
    }

    public static void setForgeEnergy(ItemStack stack,Integer value) {
        setInt(stack,value,"forge_energy");
    }

    public static FillMode getFluidCanisterMode(ItemStack stack) {
        return getEnum(stack,"fluid_canister_mode", FillMode.class);
    }

    public static void setFluidCanisterMode(ItemStack stack,FillMode value) {
        setEnum(stack,value,"fluid_canister_mode");
    }

    public static void cycleFluidCanisterMode(ItemStack stack) {
        cycleEnum(stack,"fluid_canister_mode", FillMode.class);
    }

    public static List<String> getStupefyTargets(ItemStack stack) {
        return getList(stack,Codec.STRING,"stupefy_targets");
    }

    public static void setStupefyTargets(ItemStack stack,List<String> strings) {
        setList(stack,strings,Codec.STRING,"stupefy_targets");
    }

    public static List<ItemStack> getItems(ItemStack stack,String key) {
        return getList(stack,ItemStack.CODEC,key);
    }

    public static void setItems(ItemStack stack,List<ItemStack> items,String key) {
        setList(stack,items,ItemStack.CODEC,key);
    }

    public static final String ITEMSTACK_HANDLER = "itemstack_handler";

    public static List<ItemStack> getItemstackHandler(ItemStack stack) {
        return getItems(stack,ITEMSTACK_HANDLER);
    }

    public static void setItemstackHandler(ItemStack stack,List<ItemStack> items) {
        setItems(stack,items,ITEMSTACK_HANDLER);
    }

    public static List<ItemStack> getToolContents(ItemStack stack) {
        return getItems(stack,"tool_contents");
    }

    public static void setToolContents(ItemStack stack,List<ItemStack> items) {
        setItems(stack,items,"tool_contents");
    }

    public static PotionContents getPotionContents(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("potion_contents")) {
            return PotionContents.fromTag(stack.getTagElement("potion_contents"));
        }
        return PotionContents.EMPTY;
    }

    public static void setPotionContents(ItemStack stack, PotionContents contents) {
        if (contents == null) {
            stack.removeTagKey("potion_contents");
        } else {
            stack.getOrCreateTag().put("potion_contents",contents.toTag());
        }
    }

    public static boolean hasAbilityCooldowns(ItemStack stack) {
        return containsTagKey(stack,ABILITY_COOLDOWNS);
    }

    public static boolean containsTagKey(ItemStack stack,String key) {
        return stack.getTagElement(key) != null;
    }

    public static Integer getPotionAmount(ItemStack stack) {
        return getInt(stack,"potion_amount");
    }

    public static void setPotionAmount(ItemStack stack,Integer value) {
        setInt(stack,value,"potion_amount");
    }

    public static Boolean isEpicArrow(ItemStack stack){
        return getBoolean(stack,"epic_arrow");
    }

    public static void setEpicArrow(ItemStack stack,Boolean value) {
        setBoolean(stack,value,"epic_arrow");
    }

    //public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> COPIED_MACHINE_DATA = COMPONENTS.register("copied_machine_data", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());

 //   public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<NBTHelpers.PortalDestination>>> PORTAL_GUN_FAVORITES = COMPONENTS.register("portal_gun_favorites", () -> DataComponentType.<List<NBTHelpers.PortalDestination>>builder().persistent(NBTHelpers.PortalDestination.CODEC.listOf()).networkSynchronized(NBTHelpers.PortalDestination.STREAM_CODEC.apply(ByteBufCodecs.list())).build());
    //public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_CONTAINER = COMPONENTS.register("fluid_container", () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());

   // public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<String>>> STUPEFY_TARGETS = COMPONENTS.register("stupefy_targets", () -> DataComponentType.<List<String>>builder().persistent(Codec.STRING.listOf()).networkSynchronized(ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())).build());

  //  public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> ITEMSTACK_HANDLER = COMPONENTS.register("itemstack_handler", () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());
  //  public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> TOOL_CONTENTS = COMPONENTS.register("tool_contents", () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());
   // public static final DeferredHolder<DataComponentType<?>, DataComponentType<PotionContents>> POTION_CONTENTS = COMPONENTS.register("potion_contents", () -> DataComponentType.<PotionContents>builder().persistent(PotionContents.CODEC).networkSynchronized(PotionContents.STREAM_CODEC).cacheEncoding().build());

   // public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> CUSTOM_DATA_1 = COMPONENTS.register("custom_data_1", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());


    public static Boolean getAbilityToggle(ItemStack stack,Ability ability) {
        return getBoolean(stack,ability+"_toggle");
    }

    public static void setAbilityToggle(ItemStack stack,Boolean value,Ability ability) {
        setBoolean(stack,value,ability+"_toggle");
    }

    public static Integer getAbilityValue(ItemStack stack,Ability ability) {
        return getInt(stack,ability+"_value");
    }

    public static void setAbilityValue(ItemStack stack,Integer value,Ability ability) {
        setInt(stack,value,ability+"_value");
    }

    public static Integer getAbilityBindingmode(ItemStack stack,Ability ability) {
        return getInt(stack,ability+"_bindingmode");
    }

    public static void setAbilityBindingmode(ItemStack stack,Integer value,Ability ability) {
        setInt(stack,value,ability+"_bindingmode");
    }

    public static Integer getAbilityCustomSetting(ItemStack stack,Ability ability) {
        return getInt(stack,ability+"_custom_setting");
    }

    public static void setAbilityCustomSetting(ItemStack stack,Integer value,Ability ability) {
        setInt(stack,value,ability+"_custom_setting");
    }


    public static Boolean getAbilityUpgradeInstalled(ItemStack stack,Ability ability) {
        return getBoolean(stack,ability+"_upgrade_installed");
    }

    public static void setAbilityUpgradeInstalled(ItemStack stack,Boolean value,Ability ability) {
        setBoolean(stack,value,ability+"_upgrade_installed");
    }
}
