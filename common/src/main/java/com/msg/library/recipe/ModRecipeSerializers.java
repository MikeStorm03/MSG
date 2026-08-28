/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe;

import com.msg.library.Constants;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface ModRecipeSerializers {

    static void registryRecipeSerializer(String name, RecipeSerializer<?> serializer){
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Constants.resourceLocation(name), serializer);
    }

    static void init(){
        registryRecipeSerializer("brewing", BrewingRecipe.SERIALIZER);
    }
}
