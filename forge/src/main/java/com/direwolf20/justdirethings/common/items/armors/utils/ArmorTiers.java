package com.direwolf20.justdirethings.common.items.armors.utils;

import com.direwolf20.justdirethings.setup.Registration;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;

public class ArmorTiers {
    public static final ArmorMaterial FERRICORE = new ArmorTier(
            "ferricore",
            15,
            Util.make(new EnumMap<>(ArmorItem.Type.class), p_323384_ -> {
                p_323384_.put(ArmorItem.Type.BOOTS, 2);
                p_323384_.put(ArmorItem.Type.LEGGINGS, 5);
                p_323384_.put(ArmorItem.Type.CHESTPLATE, 6);
                p_323384_.put(ArmorItem.Type.HELMET, 2);
            }),
            9,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            () -> Ingredient.of(Registration.FerricoreIngot.get())//,
          //  List.of(new ArmorMaterial.Layer(JustDireThings.id("ferricore"), "", true))
    );
    public static final ArmorMaterial BLAZEGOLD = new ArmorTier(
            "blazegold",
            25,
            Util.make(new EnumMap<>(ArmorItem.Type.class), p_323384_ -> {
                p_323384_.put(ArmorItem.Type.BOOTS, 2);
                p_323384_.put(ArmorItem.Type.LEGGINGS, 5);
                p_323384_.put(ArmorItem.Type.CHESTPLATE, 6);
                p_323384_.put(ArmorItem.Type.HELMET, 2);
            }),
            25,
            SoundEvents.ARMOR_EQUIP_GOLD,
            0.0F,
            0.0F,
            () -> Ingredient.of(Registration.BlazegoldIngot.get())//,
         //   List.of(new ArmorMaterial.Layer(JustDireThings.id("blazegold"), "", true))
    );
    public static final ArmorMaterial CELESTIGEM = new ArmorTier(
            "celestigem",
            25,
            Util.make(new EnumMap<>(ArmorItem.Type.class), p_323384_ -> {
                p_323384_.put(ArmorItem.Type.BOOTS, 3);
                p_323384_.put(ArmorItem.Type.LEGGINGS, 6);
                p_323384_.put(ArmorItem.Type.CHESTPLATE, 8);
                p_323384_.put(ArmorItem.Type.HELMET, 3);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            2.0F,
            0.0F,
            () -> Ingredient.of(Registration.Celestigem.get())//,
           // List.of(new ArmorMaterial.Layer(JustDireThings.id("celestigem"), "", true))
    );
    public static final ArmorMaterial ECLIPSEALLOY = new ArmorTier(
            "eclipsealloy",
            25,
            Util.make(new EnumMap<>(ArmorItem.Type.class), p_323384_ -> {
                p_323384_.put(ArmorItem.Type.BOOTS, 3);
                p_323384_.put(ArmorItem.Type.LEGGINGS, 6);
                p_323384_.put(ArmorItem.Type.CHESTPLATE, 8);
                p_323384_.put(ArmorItem.Type.HELMET, 3);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            3.0F,
            0.1F,
            () -> Ingredient.of(Registration.EclipseAlloyIngot.get())//,
            //List.of(new ArmorMaterial.Layer(JustDireThings.id("eclipsealloy"), "", true))
    );

}

