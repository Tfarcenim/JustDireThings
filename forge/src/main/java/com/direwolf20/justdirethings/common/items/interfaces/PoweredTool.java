package com.direwolf20.justdirethings.common.items.interfaces;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public interface PoweredTool extends PoweredItem {
    default Multimap<Attribute, AttributeModifier> getPoweredAttributeModifiers(ItemStack stack, Multimap<Attribute, AttributeModifier> originalModifiers) {
    /*    ItemAttributeModifiers modifiers = ItemAttributeModifiers.builder().build();
        if (PoweredItem.getAvailableEnergy(stack) >= getBlockBreakFECost()) {
            return originalModifiers;
        } else {
            for (ItemAttributeModifiers.Entry entry : originalModifiers.modifiers()) {
                if (!entry.attribute().equals(Attributes.ATTACK_DAMAGE))
                    modifiers.withModifierAdded(entry.attribute(), entry.modifier(), entry.slot());
            }
        }
        return modifiers;*/
        return null;
    }

    default int getBlockBreakFECost() {
        return 50;
    }
}
