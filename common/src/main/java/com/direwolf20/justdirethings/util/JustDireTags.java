package com.direwolf20.justdirethings.util;

import com.direwolf20.justdirethings.JustDireThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class JustDireTags {

    public static class Blocks {

        public static final TagKey<Block> LAWNMOWERABLE = modTag("lawnmowerable");
        public static final TagKey<Block> NO_AUTO_CLICK = modTag("noautoclick");
        public static final TagKey<Block> SWAPPERDENY = modTag("swapper_deny");
        public static final TagKey<Block> ECLISEGATEDENY = modTag("eclipsegate_deny");
        public static final TagKey<Block> PHASEDENY = modTag("phase_deny");
        public static final TagKey<Block> TICK_SPEED_DENY = modTag("tick_speed_deny");
        public static final TagKey<Block> PARADOX_ALLOW = modTag("paradox_allow");
        public static final TagKey<Block> PARADOX_ABSORB_DENY = modTag("paradox_absorb_deny");
        public static final TagKey<Block> CHARCOAL = forgeTag("storage_blocks/charcoal");

        private static TagKey<Block> forgeTag(String name) {
            return new TagKey<>(Registries.BLOCK,new ResourceLocation("c", name));
        }

        private static TagKey<Block> modTag(String name) {
            return new TagKey<>(Registries.BLOCK,new ResourceLocation(JustDireThings.MODID, name));
        }
    }

}
