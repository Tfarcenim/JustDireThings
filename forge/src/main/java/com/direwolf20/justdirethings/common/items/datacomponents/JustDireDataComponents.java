package com.direwolf20.justdirethings.common.items.datacomponents;

import com.direwolf20.justdirethings.common.items.interfaces.Ability;
import com.direwolf20.justdirethings.common.items.interfaces.ToolRecords;
import com.direwolf20.justdirethings.util.NBTHelpers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class JustDireDataComponents {

    static Boolean getBoolean(ItemStack stack,String key) {
        return stack.hasTag() ? stack.getTag().getBoolean(key) : null;
    }

    static void setBoolean(ItemStack stack,Boolean value,String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putBoolean(key,value);
        }
    }

    static Integer getInt(ItemStack stack,String key) {
        return stack.hasTag() ? stack.getTag().getInt(key) : null;
    }

    static void setInt(ItemStack stack,Integer value,String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putInt(key,value);
        }
    }

    static Integer getInt(ItemStack stack,String key) {
        return stack.hasTag() ? stack.getTag().getInt(key) : null;
    }

    static void setInt(ItemStack stack,Integer value,String key) {
        if (value == null) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTag().putInt(key,value);
        }
    }

    public static String getEntityType(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getString("entitytype") : null;
    }

    public static void setEntityType(ItemStack stack,String value) {
        if (value == null) {
            stack.removeTagKey("entitytype");
        } else {
            stack.getOrCreateTag().putString("entitytype",value);
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
        if (stack.hasTag() && stack.getTag().contains("left_click_abilities")) {
            ListTag listTag = stack.getTag().getList("left_click_abilities", Tag.TAG_STRING);
            List<String> list = new ArrayList<>();
            for (Tag tag : listTag) {
                list.add(tag.getAsString());
            }
            return list;
        }
        return null;
    }

    public static void setLeftClickAbilities(ItemStack stack,@Nullable List<String> list) {
        if (list == null) {
            stack.removeTagKey("left_click_abilities");
        } else {
            ListTag listTag = new ListTag();
            list.forEach(s -> listTag.add(StringTag.valueOf(s)));
            stack.getOrCreateTag().put("left_click_abilities",listTag);
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


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> FUELCANISTER_FUELLEVEL = COMPONENTS.register("fuelcanister_fuellevel", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> FUELCANISTER_BURNSPEED = COMPONENTS.register("fuelcanister_burnspeed", () -> DataComponentType.<Double>builder().persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> COPY_AREA_SETTINGS = COMPONENTS.register("copy_area_settings", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> COPY_OFFSET_SETTINGS = COMPONENTS.register("copy_offset_settings", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> COPY_FILTER_SETTINGS = COMPONENTS.register("copy_filter_settings", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> COPY_REDSTONE_SETTINGS = COMPONENTS.register("copy_redstone_settings", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> COPIED_MACHINE_DATA = COMPONENTS.register("copied_machine_data", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> PORTALGUN_UUID = COMPONENTS.register("portalgun_uuid", () -> DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> PORTALGUN_FAVORITE = COMPONENTS.register("portalgun_favorite", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<NBTHelpers.PortalDestination>>> PORTAL_GUN_FAVORITES = COMPONENTS.register("portal_gun_favorites", () -> DataComponentType.<List<NBTHelpers.PortalDestination>>builder().persistent(NBTHelpers.PortalDestination.CODEC.listOf()).networkSynchronized(NBTHelpers.PortalDestination.STREAM_CODEC.apply(ByteBufCodecs.list())).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<NBTHelpers.PortalDestination>> PORTAL_GUN_PREVIOUS = COMPONENTS.register("portal_gun_previous", () -> DataComponentType.<NBTHelpers.PortalDestination>builder().persistent(NBTHelpers.PortalDestination.CODEC).networkSynchronized(NBTHelpers.PortalDestination.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> PORTAL_GUN_STAY_OPEN = COMPONENTS.register("portal_gun_stay_open", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_CONTAINER = COMPONENTS.register("fluid_container", () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> FORGE_ENERGY = COMPONENTS.register("forge_energy", () -> DataComponentType.<Integer>builder().persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> FLUID_CANISTER_MODE = COMPONENTS.register("fluid_canister_mode", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<String>>> STUPEFY_TARGETS = COMPONENTS.register("stupefy_targets", () -> DataComponentType.<List<String>>builder().persistent(Codec.STRING.listOf()).networkSynchronized(ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> ITEMSTACK_HANDLER = COMPONENTS.register("itemstack_handler", () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> TOOL_CONTENTS = COMPONENTS.register("tool_contents", () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());
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
