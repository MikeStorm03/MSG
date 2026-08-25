/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe.brewing;

import com.msg.library.Constants;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface ModRecipeType {

    RecipeType<BrewingRecipe> BREWING = register("brewing");

    static <T extends Recipe<?>> RecipeType<T> register(String string) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, Constants.resourceLocation(string), new RecipeType<T>(){});
    }

    static void init(){}
}