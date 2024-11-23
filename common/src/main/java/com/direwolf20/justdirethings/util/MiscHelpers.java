package com.direwolf20.justdirethings.util;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class MiscHelpers {
    public enum RedstoneMode {
        IGNORED,
        LOW,
        HIGH,
        PULSE;

        public RedstoneMode next() {
            RedstoneMode[] values = values();
            int nextOrdinal = (this.ordinal() + 1) % values.length;
            return values[nextOrdinal];
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

}
