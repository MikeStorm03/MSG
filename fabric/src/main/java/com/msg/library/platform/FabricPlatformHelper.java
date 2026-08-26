package com.msg.library.platform;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.msg.library.platform.services.IPlatformHelper;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.crafting.RecipeHolder;

public class FabricPlatformHelper implements IPlatformHelper {
    @Nullable
    public static List<RecipeHolder<BrewingRecipe>> allBrewRecipes = null;

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public List<RecipeHolder<BrewingRecipe>> getAllBrewRecipe() {
        return allBrewRecipes;
    } 

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
