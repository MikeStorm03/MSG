/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.networking;

import java.util.HashSet;
import java.util.Set;
// import java.util.UUID;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

// import com.msg.library.Constants;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class ConnectionHandling {

    // public static Set<UUID> hasMod = new HashSet<>();

    @Nullable
    public static Set<ItemStack> allBrewReagent = new HashSet<>();
    @Nullable
    public static Set<ItemStack> allBrewInputs = new HashSet<>();

    public static final void loadBrewingInputs(MinecraftServer server, RecipeManager recipeManager){
        allBrewReagent.clear();
        allBrewInputs.clear();

        Stream<RecipeHolder<?>> recipeStream = recipeManager.getRecipes().stream().filter(holder -> holder.value().getType() == ModRecipeType.BREWING);
        for (RecipeHolder<?> recipeHolder : recipeStream.toList()) {
            if (recipeHolder.value() instanceof BrewingRecipe recipe) {
                allBrewReagent.addAll(recipe.getReagentItemStacks());
                allBrewInputs.add(recipe.getInputStacks());
            }
        }

    }
}