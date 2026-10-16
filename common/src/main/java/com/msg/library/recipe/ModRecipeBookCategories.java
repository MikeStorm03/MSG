/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe;

import com.msg.library.Constants;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public interface ModRecipeBookCategories {
    RecipeBookCategory BREWING = register("brewing");

    private static RecipeBookCategory register(String string) {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Constants.resourceLocation(string), new RecipeBookCategory());
    }

    static void init() {}
}
