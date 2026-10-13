/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe.brewing;

import java.util.ArrayList;
import java.util.List;

import com.msg.library.recipe.ModRecipeSerializers;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.NBTCraftingIngredients;

// import com.msg.library.Constants;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BrewingRecipe implements NBTCraftingIngredients, Recipe<BrewingRecipeInput> {
    protected final List<ItemStack> reagents;
    protected final ItemStack input;
    protected final ItemStack output;
    public static final RecipeManager.CachedCheck<BrewingRecipeInput, ? extends BrewingRecipe> recipeCheck = RecipeManager.createCheck(ModRecipeType.BREWING);

    public BrewingRecipe(List<ItemStack> reagents, ItemStack input, ItemStack output) {
        this.reagents = reagents;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(BrewingRecipeInput input, Level level) {
        // Constants.LOG.info("Comparing Brewing Recipe:\n\tReagent in recipe is {};\tReagent input is {}\n\tInput in recipe is {};\tInput input is {}",
        //                     reagents.toString(),
        //                     input.reagent().getHoverName().getString(),
        //                     this.input.getHoverName().getString(),
        //                     input.input().getHoverName().getString()
        //                 );
        return matches(this.reagents, input.reagent()) && matches(this.input, input.input());
    }

    public ItemStack assemble(BrewingRecipeInput input, HolderLookup.Provider registries) {
        return this.output.copy();
    }

    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public List<Item> getReagentItems() {
        List<Item> itemList = new ArrayList<>();
        for (ItemStack reagent : this.reagents) itemList.add(reagent.getItem());
        return itemList;
    }

    public List<ItemStack> getReagentItemStacks() {
        return this.reagents;
    }

    public Item getInputItems() {
        return this.input.getItem();
    }

    public ItemStack getInputStacks() {
        return this.input;
    }

    public ItemStack getOutputItem() {
        return this.output;
    }

    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.output;
    }

    public RecipeType<?> getType() {
        return ModRecipeType.BREWING;
    }

    public interface Factory<T extends BrewingRecipe> {
        T create(List<ItemStack> reagents, ItemStack input, ItemStack output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BREWING_RECIPE;
    }
}
