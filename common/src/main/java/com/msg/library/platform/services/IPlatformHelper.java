package com.msg.library.platform.services;

import java.util.List;

import com.msg.library.recipe.brewing.BrewingRecipe;

import net.minecraft.world.item.crafting.RecipeHolder;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    List<RecipeHolder<BrewingRecipe>> getAllBrewRecipe();

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }
}