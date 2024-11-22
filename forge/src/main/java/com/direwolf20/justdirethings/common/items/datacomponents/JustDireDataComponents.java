package com.direwolf20.justdirethings.common.items.datacomponents;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.ToolRecords;
import com.direwolf20.justdirethings.util.NBTHelpers;
import com.direwolf20.justdirethings.util.PotionContents;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class JustDireDataComponents {

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

    public static ToolRecords.AbilityCooldown getAbilityCooldowns(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("ability_cooldowns")) {
            return ToolRecords.AbilityCooldown.fromTag(stack.getTag().getCompound("ability_cooldowns"));
        }
        return null;
    }

    public static void setAbilityCooldowns(ItemStack stack, @Nullable ToolRecords.AbilityCooldown cooldowns) {
        if (cooldowns == null) {
            stack.removeTagKey("ability_cooldowns");
        } else {
            stack.getOrCreateTag().put("ability_cooldowns",cooldowns.toTag());
        }
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
        return getList(stack,STRING,"left_click_abilities");
    }

    public static void setLeftClickAbilities(ItemStack stack,@Nullable List<String> list) {
        setList(stack,list,STRING,"left_click_abilities");
    }

    static class Bicoder<T>{
        private final Function<T, Tag> encoder;
        private final Function<Tag, T> decoder;
        private final byte b;

        Bicoder(Function<T,Tag> encoder,Function<Tag,T> decoder, byte b) {
            this.encoder = encoder;
            this.decoder = decoder;
            this.b = b;
        }
    }

    static final Bicoder<String> STRING = new Bicoder<>(StringTag::valueOf,Tag::getAsString,Tag.TAG_STRING);
    static final Bicoder<NBTHelpers.PortalDestination> PORTAL_DESTINATION = new Bicoder<>(NBTHelpers.PortalDestination::toNBT,tag -> NBTHelpers.PortalDestination.fromNBT((CompoundTag) tag),Tag.TAG_COMPOUND);
    static final Bicoder<ItemStack> ITEM_STACK = new Bicoder<>(IForgeItemStack::serializeNBT, tag -> ItemStack.of((CompoundTag) tag),Tag.TAG_COMPOUND);


    static <T> List<T> getList(ItemStack stack, Bicoder<T> bicoder, String key) {
        if (stack.hasTag() && stack.getTag().contains(key,Tag.TAG_LIST)) {
            ListTag listTag = stack.getTag().getList(key,bicoder.b);
            List<T> list = new ArrayList<>();
            for (Tag tag : listTag) {
                list.add(bicoder.decoder.apply(tag));
            }
            return list;
        }
        return null;
    }

    static <T> void setList(ItemStack stack,List<T> list, Bicoder<T> bicoder, String key) {
        if (list == null) {
            stack.removeTagKey(key);
        } else {
            ListTag listTag = new ListTag();
            list.forEach(s -> listTag.add(bicoder.encoder.apply(s)));
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
        return getList(stack,PORTAL_DESTINATION,"portal_gun_favorites");
    }

    static void setPortalGunFavorites(ItemStack stack, @Nullable List<NBTHelpers.PortalDestination> destination) {
        setList(stack,destination,PORTAL_DESTINATION,"portal_gun_favorites");
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

    public static Integer getFluidCanisterMode(ItemStack stack) {
        return getInt(stack,"fluid_canister_mode");
    }

    public static void setFluidCanisterMode(ItemStack stack,Integer value) {
        setInt(stack,value,"fluid_canister_mode");
    }

    public static List<String> getStupefyTargets(ItemStack stack) {
        return getList(stack,STRING,"stupefy_targets");
    }

    public static void setStupefyTargets(ItemStack stack,List<String> strings) {
        setList(stack,strings,STRING,"stupefy_targets");
    }

    static List<ItemStack> getItems(ItemStack stack,String key) {
        return getList(stack,ITEM_STACK,key);
    }

    static void setItems(ItemStack stack,List<ItemStack> items,String key) {
        setList(stack,items,ITEM_STACK,key);
    }

    public static List<ItemStack> getItemstackHandler(ItemStack stack) {
        return getItems(stack,"itemstack_handler");
    }

    public static void setItemstackHandler(ItemStack stack,List<ItemStack> items) {
        setItems(stack,items,"itemstack_handler");
    }

    public static List<ItemStack> getToolContents(ItemStack stack) {
        return getItems(stack,"tool_contents");
    }

    public static void setToolContents(ItemStack stack,List<ItemStack> items) {
        setItems(stack,items,"tool_contents");
    }

    public static PotionContents getPotionContents(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("potion_contents")) {

        }
        return PotionContents.EMPTY;
    }

    //public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> COPIED_MACHINE_DATA = COMPONENTS.register("copied_machine_data", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());

 //   public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<NBTHelpers.PortalDestination>>> PORTAL_GUN_FAVORITES = COMPONENTS.register("portal_gun_favorites", () -> DataComponentType.<List<NBTHelpers.PortalDestination>>builder().persistent(NBTHelpers.PortalDestination.CODEC.listOf()).networkSynchronized(NBTHelpers.PortalDestination.STREAM_CODEC.apply(ByteBufCodecs.list())).build());
    //public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_CONTAINER = COMPONENTS.register("fluid_container", () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());

   // public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<String>>> STUPEFY_TARGETS = COMPONENTS.register("stupefy_targets", () -> DataComponentType.<List<String>>builder().persistent(Codec.STRING.listOf()).networkSynchronized(ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())).build());

  //  public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> ITEMSTACK_HANDLER = COMPONENTS.register("itemstack_handler", () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());
  //  public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> TOOL_CONTENTS = COMPONENTS.register("tool_contents", () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<PotionContents>> POTION_CONTENTS = COMPONENTS.register("potion_contents", () -> DataComponentType.<PotionContents>builder().persistent(PotionContents.CODEC).networkSynchronized(PotionContents.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> POTION_AMOUNT = COMPONENTS.register("potion_amount", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> EPIC_ARROW = COMPONENTS.register("epic_arrow", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> CUSTOM_DATA_1 = COMPONENTS.register("custom_data_1", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());

    public static final Map<Ability, DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>>> ABILITY_TOGGLES = new HashMap<>();
    public static final Map<Ability, DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>> ABILITY_CUSTOM_SETTINGS = new HashMap<>();
    public static final Map<Ability, DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>>> ABILITY_UPGRADE_INSTALLS = new HashMap<>();
    public static final Map<Ability, DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>> ABILITY_VALUES = new HashMap<>();
    public static final Map<Ability, DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>> ABILITY_BINDING_MODES = new HashMap<>();

    public static void genAbilityData() {
        for (Ability ability : Ability.values()) {
            DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> ABILITY_TOGGLE = COMPONENTS.register(ability.getName() + "_toggle", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL.orElse(true)).networkSynchronized(ByteBufCodecs.BOOL).build());
            DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ABILITY_VALUE = COMPONENTS.register(ability.getName() + "_value", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
            DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ABILITY_BINDING_MODE = COMPONENTS.register(ability.getName() + "_bindingmode", () -> DataComponentType.<Integer>builder().persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
            ABILITY_TOGGLES.put(ability, ABILITY_TOGGLE);
            ABILITY_VALUES.put(ability, ABILITY_VALUE);
            ABILITY_BINDING_MODES.put(ability, ABILITY_BINDING_MODE);
            if (ability.hasCustomSetting()) {
                DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ABILITY_CUSTOM_SETTING = COMPONENTS.register(ability.getName() + "_custom_setting", () -> DataComponentType.<Integer>builder().persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
                ABILITY_CUSTOM_SETTINGS.put(ability, ABILITY_CUSTOM_SETTING);
            }
            if (ability.requiresUpgrade()) {
                DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> ABILITY_UPGRADE_INSTALLED = COMPONENTS.register(ability.getName() + "_upgrade_installed", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL.orElse(true)).networkSynchronized(ByteBufCodecs.BOOL).build());
                ABILITY_UPGRADE_INSTALLS.put(ability, ABILITY_UPGRADE_INSTALLED);
            }
        }
    }

    private static @NotNull <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, final Codec<T> codec) {
        return register(name, codec, null);
    }

    private static @NotNull <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, final Codec<T> codec, @Nullable final StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        if (streamCodec == null) {
            return COMPONENTS.register(name, () -> DataComponentType.<T>builder().persistent(codec).build());
        } else {
            return COMPONENTS.register(name, () -> DataComponentType.<T>builder().persistent(codec).networkSynchronized(streamCodec).build());
        }
    }
}
