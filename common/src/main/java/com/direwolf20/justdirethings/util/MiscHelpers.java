package com.direwolf20.justdirethings.util;

import com.direwolf20.justdirethings.Constants;
import com.direwolf20.justdirethings.JustDireThings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;

import static com.mojang.text2speech.Narrator.LOGGER;

public class MiscHelpers {
    public enum RedstoneMode {
        IGNORED,
        LOW,
        HIGH,
        PULSE;

        public static final String KEY = "redstoneMode";

        public RedstoneMode next() {
            return cycle(this);
        }

        public static RedstoneMode getMode(CompoundTag tag) {
            return getEnum(tag,KEY,RedstoneMode.class);
        }

        public void setMode(CompoundTag tag) {
            setEnum(tag,KEY,this);
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

    public static <E extends Enum<E>> E getEnum(CompoundTag tag,String key,Class<E> clazz) {
        E[] values = clazz.getEnumConstants();
        if (tag.contains(key, Tag.TAG_INT)) {
            return values[tag.getInt(key)];
        }
        return null;
    }

    public static  <E extends Enum<E>> void setEnum(CompoundTag tag,String key,E value) {
        if (value == null) {
            tag.remove(key);
        } else {
            tag.putInt(key,value.ordinal());
        }
    }

    public static JsonElement serializeBlockState(BlockState state) {
        return BlockState.CODEC.encodeStart(JsonOps.INSTANCE,state).resultOrPartial(Constants.LOG::error).orElseThrow();
    }

    public static BlockState loadBlockState(JsonElement jsonElement) {
        return BlockState.CODEC.parse(JsonOps.INSTANCE,jsonElement).resultOrPartial(Constants.LOG::error).orElseThrow();
    }

    public static AABB encapsulatingFullBlocks(BlockPos pStartPos, BlockPos pEndPos) {
        return new AABB(
                Math.min(pStartPos.getX(), pEndPos.getX()),
                Math.min(pStartPos.getY(), pEndPos.getY()),
                Math.min(pStartPos.getZ(), pEndPos.getZ()),
                Math.max(pStartPos.getX(), pEndPos.getX()) + 1,
                Math.max(pStartPos.getY(), pEndPos.getY()) + 1,
                Math.max(pStartPos.getZ(), pEndPos.getZ()) + 1
        );
    }

}
