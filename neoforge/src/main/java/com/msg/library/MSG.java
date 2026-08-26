/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.msg.library.platform.NeoForgePlatformHelper;
import com.msg.library.recipe.ModRecipeBookCategories;
import com.msg.library.recipe.ModRecipeSerializers;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerLifecycleEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class MSG {

    public MSG() {}

    @SubscribeEvent
    public static void registerSetup(RegisterEvent event) {
        Registry<?> registry = event.getRegistry();
        if (registry.equals(BuiltInRegistries.RECIPE_TYPE)) ModRecipeType.init();
        else if (registry.equals(BuiltInRegistries.RECIPE_SERIALIZER)) ModRecipeSerializers.init();
        else if (registry.equals(BuiltInRegistries.RECIPE_BOOK_CATEGORY)) ModRecipeBookCategories.init();
    }

    // @SubscribeEvent
    // @SuppressWarnings("unchecked")
    // public static void resourceLoadedEvent(ServerLifecycleEvent event) {
    //     NeoForgePlatformHelper.allBrewRecipes = ((List<RecipeHolder<BrewingRecipe>>) event.getServer().getRecipeManager().getRecipes().stream().filter(holder -> holder.value().getType() == ModRecipeType.BREWING));
    // }

    @SubscribeEvent
    public static void serverStartedEvent(ServerStartedEvent event) {
        Stream<RecipeHolder<?>> recipeStream = event.getServer().getRecipeManager().getRecipes().stream().filter(holder -> holder.value().getType() == ModRecipeType.BREWING);
        NeoForgePlatformHelper.allBrewRecipes = new ArrayList<>();
        for (RecipeHolder<?> recipeHolder : recipeStream.toList()) {
            if (recipeHolder.value() instanceof BrewingRecipe recipe) {
                    NeoForgePlatformHelper.allBrewRecipes.add(new RecipeHolder<>(recipeHolder.id(), recipe));
            }
        }
    }
}