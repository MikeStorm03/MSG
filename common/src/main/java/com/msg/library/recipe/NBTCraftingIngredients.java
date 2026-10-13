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

    default boolean matches(@Nullable ItemStack reagent, ItemStack itemStack) {
        if (ItemStack.isSameItem(reagent, itemStack) &&
            ItemStack.isSameItemSameComponents(reagent, itemStack) &&
            reagent.getCount() <= itemStack.getCount())
                return true;
        return false;
    }

    default boolean matches(@Nullable List<ItemStack> reagents, ItemStack itemStack) {
        for (ItemStack reagent : reagents) if (matches(reagent, itemStack)) return true;
        return false;
    }
}
