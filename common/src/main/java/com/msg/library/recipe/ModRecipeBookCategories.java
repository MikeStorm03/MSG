package com.msg.library.recipe;

import com.msg.library.Constants;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public interface ModRecipeBookCategories {
    RecipeBookCategory BREWING = register("brewing");

    private static RecipeBookCategory register(String string) {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Constants.resourceLocation(string), new RecipeBookCategory());
    }

    static void init() {}
}
