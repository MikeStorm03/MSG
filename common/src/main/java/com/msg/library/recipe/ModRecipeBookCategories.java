package com.msg.library.recipe;

import com.msg.library.CommonClass;
import com.msg.library.Constants;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public interface ModRecipeBookCategories {
    RecipeBookCategory BREWING = register("brewing");

    private static RecipeBookCategory register(String name) {
        CommonClass.ignoreNameSpace.add(name);
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Constants.resourceLocation(name), new RecipeBookCategory());
    }

    static void init() {}
}
