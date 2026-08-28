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

import com.msg.library.platform.FabricPlatformHelper;
import com.msg.library.recipe.ModRecipeBookCategories;
import com.msg.library.recipe.ModRecipeSerializers;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;

public class MSG implements ModInitializer{

    @Override
    public void onInitialize() {
        ModRecipeType.init();
        ModRecipeSerializers.init();
        ModRecipeBookCategories.init();

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            MinecraftServer server = player.level().getServer();
            Stream<RecipeHolder<?>> recipeStream = server.getRecipeManager().getRecipes().stream().filter(holder -> holder.value().getType() == ModRecipeType.BREWING);
            FabricPlatformHelper.allBrewRecipes = new ArrayList<>();
            for (RecipeHolder<?> recipeHolder : recipeStream.toList()) {
                if (recipeHolder.value() instanceof BrewingRecipe recipe) {
                    FabricPlatformHelper.allBrewRecipes.add(new RecipeHolder<>(recipeHolder.id(), recipe));
                }
            }
        });
    }
}
