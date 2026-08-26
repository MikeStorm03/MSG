package com.msg.library.platform;

import java.util.List;

import javax.annotation.Nullable;

import com.msg.library.platform.services.IPlatformHelper;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Nullable
    public static List<RecipeHolder<BrewingRecipe>> allBrewRecipes = null;

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public List<RecipeHolder<BrewingRecipe>> getAllBrewRecipe() {
        return allBrewRecipes;
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }
}