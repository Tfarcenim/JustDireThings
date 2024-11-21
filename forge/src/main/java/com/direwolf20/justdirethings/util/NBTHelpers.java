package com.direwolf20.justdirethings.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NBTHelpers {


    public record GlobalVec3(ResourceKey<Level> dimension, Vec3 position) {
        public String toVec3ShortString() {
            return String.format("%.2f, %.2f, %.2f", position.x(), position.y(), position.z());
        }

        public static final Codec<GlobalVec3> CODEC = RecordCodecBuilder.create(
                cooldownInstance -> cooldownInstance.group(
                                Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(GlobalVec3::dimension),
                                Vec3.CODEC.fieldOf("direction").forGetter(GlobalVec3::position)
                        )
                        .apply(cooldownInstance, GlobalVec3::new)
        );

        public static GlobalVec3 fromTag(CompoundTag tag) {
            if (!tag.contains("dimension")) return null;
            ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("dimension")));
            double x = tag.getDouble("vec3x");
            double y = tag.getDouble("vec3y");
            double z = tag.getDouble("vec3z");
            return new GlobalVec3(levelKey, new Vec3(x, y, z));
        }

        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("dimension", dimension.location().toString());
            tag.putDouble("vec3x", position.x);
            tag.putDouble("vec3y", position.y);
            tag.putDouble("vec3z", position.z);
            return tag;
        }

    }

    public record BoundInventory(GlobalPos globalPos, Direction direction) {
        public static final Codec<BoundInventory> CODEC = RecordCodecBuilder.create(
                cooldownInstance -> cooldownInstance.group(
                                GlobalPos.CODEC.fieldOf("globalpos").forGetter(BoundInventory::globalPos),
                                Direction.CODEC.fieldOf("direction").forGetter(BoundInventory::direction)
                        )
                        .apply(cooldownInstance, BoundInventory::new)
        );

        public static BoundInventory fromNBT(CompoundTag tag) {
            GlobalPos globalPos = NBTHelpers.nbtToGlobalPos(tag.getCompound("global_pos"));
            if (globalPos == null) return null;
            return new BoundInventory(globalPos, Direction.values()[tag.getInt("direction")]);
        }

        public CompoundTag toNBT() {
            CompoundTag tag = new CompoundTag();
            tag.put("global_pos", NBTHelpers.globalPosToNBT(globalPos));
            tag.putInt("direction", direction.ordinal());
            return tag;
        }
    }

    public record PortalDestination(GlobalVec3 globalVec3, Direction direction, String name) {
        public static final PortalDestination EMPTY = new PortalDestination(new GlobalVec3(Level.OVERWORLD, Vec3.ZERO), Direction.DOWN, "EMPTY");
        public static final Codec<PortalDestination> CODEC = RecordCodecBuilder.create(
                cooldownInstance -> cooldownInstance.group(
                                GlobalVec3.CODEC.fieldOf("globalVec3").forGetter(PortalDestination::globalVec3),
                                Direction.CODEC.fieldOf("direction").forGetter(PortalDestination::direction),
                                Codec.STRING.fieldOf("name").forGetter(PortalDestination::name)
                        )
                        .apply(cooldownInstance, PortalDestination::new)
        );


        public static PortalDestination fromNBT(CompoundTag tag) {
            PortalDestination portalDestination = null;
            if (tag.contains("globalVec3") && tag.contains("direction") && tag.contains("name")) {
                GlobalVec3 globalVec = NBTHelpers.nbtToGlobalVec3(tag.getCompound("globalVec3"));
                if (globalVec == null) return null;
                portalDestination = new PortalDestination(globalVec, Direction.values()[tag.getInt("direction")], tag.getString("name"));
            }
            return portalDestination;
        }

        public static CompoundTag toNBT(PortalDestination portalDestination) {
            CompoundTag tag = new CompoundTag();
            tag.put("globalVec3", portalDestination.globalVec3.toTag());
            tag.putInt("direction", portalDestination.direction.ordinal());
            tag.putString("name", portalDestination.name);
            return tag;
        }
    }

    public static CompoundTag globalPosToNBT(GlobalPos globalPos) {
        CompoundTag tag = new CompoundTag();
        tag.putString("dimension", globalPos.dimension().location().toString());
        tag.put("blockpos", NbtUtils.writeBlockPos(globalPos.pos()));
        return tag;
    }

    public static GlobalPos nbtToGlobalPos(CompoundTag tag) {
        ResourceKey<Level> levelKey;
        if (tag.contains("dimension"))
            levelKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("dimension")));
        else
            return null;
        BlockPos blockPos = NbtUtils.readBlockPos(tag.getCompound("blockpos"));
        return blockPos.equals(BlockPos.ZERO) ? null : GlobalPos.of(levelKey, blockPos);
    }

    public static CompoundTag vec3ToNBT(Vec3 vec3) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("vec3x", vec3.x);
        tag.putDouble("vec3y", vec3.y);
        tag.putDouble("vec3z", vec3.z);
        return tag;
    }

    public static Vec3 nbtToVec3(CompoundTag tag) {
        double x = tag.getDouble("vec3x");
        double y = tag.getDouble("vec3y");
        double z = tag.getDouble("vec3z");
        return new Vec3(x, y, z);
    }
}
