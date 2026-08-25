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
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface ModRecipeSerializers {

    RecipeSerializer<BrewingRecipe> BREWING_RECIPE = register("brewing", new BrewingRecipeSerializer<>(BrewingRecipe::new));

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String string, S recipeSerializer) {
        return (S)(Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Constants.resourceLocation(string), recipeSerializer));
    }

    static void init(){}
}
