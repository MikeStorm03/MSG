/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe.brewing;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.msg.library.recipe.ModRecipeBookCategories;
import com.msg.library.recipe.ModRecipeSerializers;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.NBTCraftingIngredients;

// import com.msg.library.Constants;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BrewingRecipe implements NBTCraftingIngredients, Recipe<BrewingRecipeInput> {
    protected final List<ItemStack> ingredients;
    protected final ItemStack input;
    protected final ItemStack result;
    public static final RecipeManager.CachedCheck<BrewingRecipeInput, ? extends BrewingRecipe> recipeCheck = RecipeManager.createCheck(ModRecipeType.BREWING);
    @Nullable
    private PlacementInfo placementInfo;

    public BrewingRecipe(List<ItemStack> ingredients, ItemStack input, ItemStack output) {
        this.ingredients = ingredients;
        this.input = input;
        this.result = output;
    }

    @Override
    public boolean matches(BrewingRecipeInput input, Level level) {
        // Constants.LOG.info("Comparing Brewing Recipe:\n\tIngredient in recipe is {};\tIngredient input is {}\n\tInput in recipe is {};\tInput input is {}",
        //                     ingredients.toString(),
        //                     input.ingredient().getHoverName().getString(),
        //                     this.input.getHoverName().getString(),
        //                     input.input().getHoverName().getString()
        //                 );
        return matches(this.ingredients, input.ingredient()) && matches(this.input, input.input());
    }

    @Override
    public ItemStack assemble(BrewingRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public List<Item> getIngredientItems() {
        List<Item> itemList = new ArrayList<>();
        for (ItemStack ingredient : this.ingredients) itemList.add(ingredient.getItem());
        return itemList;
    }

    public List<ItemStack> getIngredientItemStacks() {
        return this.ingredients;
    }

    public Item getInputItems() {
        return this.input.getItem();
    }

    public ItemStack getInputStacks() {
        return this.input;
    }

    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public RecipeType<BrewingRecipe> getType() {
        return ModRecipeType.BREWING;
    }

    public interface Factory<T extends BrewingRecipe> {
        T create(List<ItemStack> ingredients, ItemStack input, ItemStack result);
    }

    @Override
    public RecipeSerializer<BrewingRecipe> getSerializer() {
        return ModRecipeSerializers.BREWING_RECIPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            List<Ingredient> ingredientList = new ArrayList<>();
            for (ItemStack itemStack : this.ingredients) {
                ingredientList.add(Ingredient.of(itemStack.getItem()));
            }
            this.placementInfo = PlacementInfo.create(ingredientList);
        }
        return this.placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.BREWING;
    }
}
