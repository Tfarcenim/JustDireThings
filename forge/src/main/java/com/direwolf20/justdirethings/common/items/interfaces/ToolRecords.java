package com.direwolf20.justdirethings.common.items.interfaces;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class ToolRecords {
    public record AbilityCooldown(String abilityName, int cooldownTicks, boolean isactive) {
        public static final Codec<AbilityCooldown> CODEC = RecordCodecBuilder.create(
                cooldownInstance -> cooldownInstance.group(
                                Codec.STRING.fieldOf("ability_name").forGetter(AbilityCooldown::abilityName),
                                Codec.INT.fieldOf("cooldown_ticks").forGetter(AbilityCooldown::cooldownTicks),
                                Codec.BOOL.fieldOf("is_active").forGetter(AbilityCooldown::isactive)
                        )
                        .apply(cooldownInstance, AbilityCooldown::new)
        );
        public static final Codec<List<AbilityCooldown>> LIST_CODEC = CODEC.listOf();
        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("ability_name",abilityName);
            tag.putInt("cooldown_ticks",cooldownTicks);
            tag.putBoolean("is_active",isactive);
            return tag;
        }

        public static AbilityCooldown fromTag(CompoundTag tag) {
            return new AbilityCooldown(tag.getString("ability_name"),tag.getInt("cooldown_ticks"),tag.getBoolean("is_active"));
        }
    }

    public record AbilityBinding(String abilityName, int key, boolean isMouse, boolean requireEquipped) {
        public static final Codec<AbilityBinding> CODEC = RecordCodecBuilder.create(
                cooldownInstance -> cooldownInstance.group(
                                Codec.STRING.fieldOf("ability_name").forGetter(AbilityBinding::abilityName),
                                Codec.INT.fieldOf("key").forGetter(AbilityBinding::key),
                                Codec.BOOL.fieldOf("is_mouse").forGetter(AbilityBinding::isMouse),
                                Codec.BOOL.fieldOf("require_equipped").forGetter(AbilityBinding::requireEquipped)
                        )
                        .apply(cooldownInstance, AbilityBinding::new)
        );
        public static final Codec<List<AbilityBinding>> LIST_CODEC = CODEC.listOf();


        public CompoundTag toTag() {
            CompoundTag tag = new CompoundTag();
            tag.putString("ability_name",abilityName);
            tag.putInt("key",key);
            tag.putBoolean("is_mouse",isMouse);
            tag.putBoolean("require_equipped",requireEquipped);
            return tag;
        }

        public static AbilityBinding fromTag(CompoundTag tag) {
            return new AbilityBinding(tag.getString("ability_name"),tag.getInt("key"),tag.getBoolean("is_mouse"),tag.getBoolean("require_equipped"));
        }
    }
}
