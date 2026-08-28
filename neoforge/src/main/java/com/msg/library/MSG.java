/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.msg.library.platform.NeoForgePlatformHelper;
import com.msg.library.recipe.ModRecipeBookCategories;
import com.msg.library.recipe.ModRecipeSerializers;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class MSG {

    public MSG() {
        CommonClass.init();
    }

    @SubscribeEvent
    public static void registerSetup(RegisterEvent event) {
        Registry<?> registry = event.getRegistry();
        if (registry.equals(BuiltInRegistries.RECIPE_TYPE)) ModRecipeType.init();
        else if (registry.equals(BuiltInRegistries.RECIPE_SERIALIZER)) ModRecipeSerializers.init();
        else if (registry.equals(BuiltInRegistries.RECIPE_BOOK_CATEGORY)) ModRecipeBookCategories.init();
    }

    @SubscribeEvent
    public static void resourceLoadedEvent(OnDatapackSyncEvent event) {
        MinecraftServer server = event.getPlayer().level().getServer();
        Stream<RecipeHolder<?>> recipeStream = server.getRecipeManager().getRecipes().stream().filter(holder -> holder.value().getType() == ModRecipeType.BREWING);
        NeoForgePlatformHelper.allBrewRecipes = new ArrayList<>();
        for (RecipeHolder<?> recipeHolder : recipeStream.toList()) {
            if (recipeHolder.value() instanceof BrewingRecipe recipe) {
                NeoForgePlatformHelper.allBrewRecipes.add(new RecipeHolder<>(recipeHolder.id(), recipe));
            }
        }
    }
}