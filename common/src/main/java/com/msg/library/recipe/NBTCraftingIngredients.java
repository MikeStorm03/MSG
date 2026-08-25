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

public interface NBTCraftingIngredients {

    default boolean matches(@Nullable ItemStack ingredient, ItemStack itemStack) {
        if (ItemStack.isSameItem(ingredient, itemStack) &&
            ItemStack.isSameItemSameComponents(ingredient, itemStack) &&
            ingredient.getCount() <= itemStack.getCount())
                return true;
        return false;
    }

    default boolean matches(@Nullable List<ItemStack> ingredients, ItemStack itemStack) {
        for (ItemStack ingredient : ingredients) if (matches(ingredient, itemStack)) return true;
        return false;
    }
}
