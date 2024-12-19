package com.direwolf20.justdirethings.common.items.interfaces;

import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface LeftClickableTool {
    static void setBindingMode(ItemStack stack, Ability ability, int mode) {
        JustDireDataComponents.setAbilityBindingmode(stack, mode, ability);
    }

    static int getBindingMode(ItemStack stack, Ability ability) {
        Integer i = JustDireDataComponents.getAbilityBindingmode(stack,ability);
        return i != null ? i : 0;
    }

    static void removeFromLeftClickList(ItemStack stack, Ability ability) {
        Set<Ability> abilityList = getLeftClickList(stack);
        abilityList.remove(ability);
        setLeftClickList(stack, abilityList);
    }

    static void addToLeftClickList(ItemStack stack, Ability ability) {
        Set<Ability> abilityList = getLeftClickList(stack);
        abilityList.add(ability);
        setLeftClickList(stack, abilityList);
    }

    static void setLeftClickList(ItemStack stack, Set<Ability> abilityList) {
        List<String> abilitiesNamesList = new ArrayList<>();
        for (Ability ability : abilityList) {
            abilitiesNamesList.add(ability.getName());
        }
        JustDireDataComponents.setLeftClickAbilities(stack, abilitiesNamesList);
    }

    static Set<Ability> getLeftClickList(ItemStack stack) {
        Set<Ability> abilities = new HashSet<>();
        List<String> abilitiesList = JustDireDataComponents.getLeftClickAbilities(stack);
        if (abilitiesList == null) abilitiesList = new ArrayList<>();
        for (String abilityName : abilitiesList) {
            if (getBindingMode(stack, Ability.byName(abilityName)) == 1)
                abilities.add(Ability.byName(abilityName));
        }
        return abilities;
    }

    static ToolRecords.AbilityBinding getAbilityBinding(ItemStack stack, Ability ability) {
        List<ToolRecords.AbilityBinding> abilityBindings = getCustomBindingList(stack);
        return abilityBindings.stream().filter(k -> k.abilityName().equals(ability.getName()))
                .findFirst()
                .orElse(null);
    }

    static void removeFromCustomBindingList(ItemStack stack, Ability ability) {
        List<ToolRecords.AbilityBinding> abilityBindings = new ArrayList<>(getCustomBindingList(stack));
        abilityBindings.removeIf(k -> k.abilityName().equals(ability.getName()));
        setCustomBindingList(stack, abilityBindings);
    }

    static void addToCustomBindingList(ItemStack stack, ToolRecords.AbilityBinding binding) {
        Ability ability = Ability.byName(binding.abilityName());
        removeFromCustomBindingList(stack, ability);
        List<ToolRecords.AbilityBinding> abilityList = getCustomBindingList(stack);
        abilityList.add(binding);
        setCustomBindingList(stack, abilityList);
    }

    static void setCustomBindingList(ItemStack stack, List<ToolRecords.AbilityBinding> abilityList) {
        JustDireDataComponents.setAbilityBindings(stack,abilityList);
    }

    static List<ToolRecords.AbilityBinding> getCustomBindingList(ItemStack stack) {
        List<ToolRecords.AbilityBinding> bindings = JustDireDataComponents.getAbilityBindings(stack);
        return bindings != null ? bindings : new ArrayList<>();
    }

    static List<Ability> getCustomBindingListFor(ItemStack stack, int key, boolean isMouse, Player player) {
        List<ToolRecords.AbilityBinding> abilityBindings = getCustomBindingList(stack);
        boolean isEquipped = ToggleableTool.isItemEquipped(stack, player);
        List<Ability> returnSet = new ArrayList<>(abilityBindings.stream().filter(k -> k.isMouse() == isMouse &&
                        k.key() == key &&
                        JustDireDataComponents.getAbilityBindingmode(stack,Ability.byName(k.abilityName())) == 2 &&
                        (!k.requireEquipped() || isEquipped))
                .map(ToolRecords.AbilityBinding::abilityName)
                .map(Ability::byName)
                .toList());
        return returnSet;
    }

    static ItemStack getLeftClickableItem(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() instanceof LeftClickableTool)
            return mainHand;
        ItemStack offHand = player.getOffhandItem();
        if (offHand.getItem() instanceof LeftClickableTool)
            return offHand;
        return ItemStack.EMPTY;
    }
}
