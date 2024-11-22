package com.direwolf20.justdirethings.util;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


public record PotionContents(Optional<Potion> potion, Optional<Integer> customColor,
                             List<MobEffectInstance> customEffects) {

    public static final PotionContents EMPTY = new PotionContents(Optional.empty(), Optional.empty(), List.of());

    public PotionContents(Potion potion) {
        this(Optional.of(potion), Optional.empty(), List.of());
    }


    public Iterable<MobEffectInstance> getAllEffects() {
        return this.potion.map(potionHolder -> this.customEffects.isEmpty() ? potionHolder.getEffects() : Iterables.concat(potionHolder.getEffects(), this.customEffects)).orElse(this.customEffects);
    }

    public int getColor() {
        return this.customColor.orElseGet(() -> getColor(this.getAllEffects()));
    }

    public static int getColor(Holder<Potion> potion) {
        return getColor(potion.value().getEffects());
    }

    public static int getColor(Iterable<MobEffectInstance> effects) {
        return getColorOptional(effects).orElse(-13083194);
    }

    public static OptionalInt getColorOptional(Iterable<MobEffectInstance> effects) {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;

        for (MobEffectInstance mobEffectInstance : effects) {
            if (mobEffectInstance.isVisible()) {
                int m = mobEffectInstance.getEffect().getColor();
                int n = mobEffectInstance.getAmplifier() + 1;
                i += n * FastColor.ARGB32.red(m);
                j += n * FastColor.ARGB32.green(m);
                k += n * FastColor.ARGB32.blue(m);
                l += n;
            }
        }

        if (l == 0) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(FastColor.ARGB32.color(i / l, j / l, k / l, 1));
        }
    }

    public List<MobEffectInstance> customEffects() {
        return Lists.transform(this.customEffects, MobEffectInstance::new);
    }

    public Optional<Potion> potion() {
        return this.potion;
    }

    public Optional<Integer> customColor() {
        return this.customColor;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        potion.ifPresent(potion1 -> {
            if (potion1 != Potions.EMPTY) {
                tag.putString(PotionUtils.TAG_POTION, BuiltInRegistries.POTION.getKey(potion1).toString());
            }
        });
        customColor.ifPresent(integer -> tag.putInt(PotionUtils.TAG_CUSTOM_POTION_COLOR,integer)) ;
        ListTag listTag = new ListTag();
        customEffects.forEach(mobEffectInstance -> listTag.add(mobEffectInstance.save(new CompoundTag())));
        tag.put(PotionUtils.TAG_CUSTOM_POTION_EFFECTS,listTag);
        return tag;
    }

    public static PotionContents fromTag(CompoundTag tag) {
        Potion potion = PotionUtils.getPotion(tag);
        Optional<Integer> color = Optional.ofNullable(tag.contains(PotionUtils.TAG_CUSTOM_POTION_COLOR) ? tag.getInt(PotionUtils.TAG_CUSTOM_POTION_COLOR) : null);
        List<MobEffectInstance> effects = PotionUtils.getCustomEffects(tag);
        return new PotionContents(Optional.of(potion),color,effects);
    }

}

