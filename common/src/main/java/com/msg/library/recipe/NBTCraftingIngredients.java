/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public interface NBTCraftingIngredients {

    default boolean matches(@Nullable ItemStackTemplate ingredient, ItemStack itemStack) {
        if (itemStack.is(ingredient.item()) &&
            itemStack.getComponentsPatch().equals(ingredient.components()) &&
            ingredient.count() <= itemStack.getCount())
                return true;
        return false;
    }

    default boolean matches(@Nullable List<ItemStackTemplate> ingredients, ItemStack itemStack) {
        for (ItemStackTemplate ingredient : ingredients) if (matches(ingredient, itemStack)) return true;
        return false;
    }
}
