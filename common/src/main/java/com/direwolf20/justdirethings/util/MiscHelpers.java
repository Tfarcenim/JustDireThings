package com.direwolf20.justdirethings.util;

import com.direwolf20.justdirethings.Constants;
import com.direwolf20.justdirethings.JustDireThings;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

import static com.mojang.text2speech.Narrator.LOGGER;

public class MiscHelpers {
    public enum RedstoneMode {
        IGNORED,
        LOW,
        HIGH,
        PULSE;

        public RedstoneMode next() {
            return cycle(this);
        }
    }
    private static final Random rand = new Random();

    public static double nextDouble(double min, double max) {
        return min + (max - min) * rand.nextDouble();
    }

    public static Direction getPrimaryDirection(Vec3 vec) {
        double absX = Math.abs(vec.x);
        double absY = Math.abs(vec.y);
        double absZ = Math.abs(vec.z);

        // Determine the largest magnitude component
        if (absX > absY && absX > absZ) {
            return vec.x > 0 ? Direction.EAST : Direction.WEST;
        } else if (absY > absX && absY > absZ) {
            return vec.y > 0 ? Direction.UP : Direction.DOWN;
        } else {
            return vec.z > 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    public static Direction getFacingDirection(Player player) {
        float yaw = player.getYRot();
        float pitch = player.getXRot();

        // Convert yaw to horizontal direction
        Direction horizontalDirection = Direction.fromYRot(yaw);

        // Adjust for vertical direction if necessary (e.g., UP or DOWN)
        if (pitch < -45) {
            return Direction.UP;
        } else if (pitch > 45) {
            return Direction.DOWN;
        } else {
            return horizontalDirection;
        }
    }

    public static <E extends Enum<E>> E cycle(E e) {
        E[] values = (E[]) e.getClass().getEnumConstants();
        return values[(e.ordinal() + 1) % values.length];
    }

    public static <T extends Comparable<T>> JsonObject serializeBlockState(BlockState state) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());

        Collection<Property<?>> properties = state.getProperties();
        JsonObject propertiesJson = new JsonObject();


        if (!properties.isEmpty()) {
            for (Property<?> property : properties) {
                Property<T> tProperty = (Property<T>) property;
                T value = state.getValue(tProperty);
                tProperty.codec().encodeStart(JsonOps.INSTANCE,value).resultOrPartial(Constants.LOG::error)
                        .ifPresent(jsonElement -> propertiesJson.add(property.getName(),jsonElement));
            }
        }

        jsonObject.add("properties", propertiesJson);
        return jsonObject;
    }

    public static BlockState loadBlockState(JsonObject jsonObject) {
        Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(GsonHelper.getAsString(jsonObject,"block")));
        BlockState state = block.defaultBlockState();

        return state;
    }

}
