/*
MSG
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
    protected final List<ItemStack> regent;
    protected final ItemStack input;
    protected final ItemStack output;
    public static final RecipeManager.CachedCheck<BrewingRecipeInput, ? extends BrewingRecipe> recipeCheck = RecipeManager.createCheck(ModRecipeType.BREWING);
    @Nullable
    private PlacementInfo placementInfo;

    public BrewingRecipe(List<ItemStack> regent, ItemStack input, ItemStack output) {
        this.regent = regent;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(BrewingRecipeInput input, Level level) {
        // Constants.LOG.info("Comparing Brewing Recipe:\n\tIngredient in recipe is {};\tIngredient input is {}\n\tInput in recipe is {};\tInput input is {}",
        //                     regent.toString(),
        //                     input.regent().getHoverName().getString(),
        //                     this.input.getHoverName().getString(),
        //                     input.input().getHoverName().getString()
        //                 );
        return matches(this.regent, input.regent()) && matches(this.input, input.input());
    }

    @Override
    public ItemStack assemble(BrewingRecipeInput input, HolderLookup.Provider registries) {
        return this.output.copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public List<Item> regent() {
        List<Item> itemList = new ArrayList<>();
        for (ItemStack regent : this.regent) itemList.add(regent.getItem());
        return itemList;
    }

    public List<ItemStack> getRegentStacks() {
        return this.regent;
    }

    public Item input() {
        return this.input.getItem();
    }

    public ItemStack getInputStacks() {
        return this.input;
    }

    public ItemStack getOutputItem() {
        return this.output;
    }

    @Override
    public RecipeType<BrewingRecipe> getType() {
        return ModRecipeType.BREWING;
    }

    public interface Factory<T extends BrewingRecipe> {
        T create(List<ItemStack> regent, ItemStack input, ItemStack output);
    }

    @Override
    public RecipeSerializer<BrewingRecipe> getSerializer() {
        return ModRecipeSerializers.BREWING_RECIPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            List<Ingredient> ingredientList = new ArrayList<>();
            for (ItemStack itemStack : this.regent) {
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
