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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.msg.library.recipe.ModRecipeBookCategories;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.NBTCraftingIngredients;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

// import com.msg.library.Constants;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BrewingRecipe implements NBTCraftingIngredients, Recipe<BrewingRecipeInput> {
    protected final List<ItemStackTemplate> ingredients;
    protected final ItemStackTemplate input;
    protected final ItemStackTemplate result;
    public static final RecipeManager.CachedCheck<BrewingRecipeInput, ? extends BrewingRecipe> recipeCheck = RecipeManager.createCheck(ModRecipeType.BREWING);
    @Nullable
    private PlacementInfo placementInfo;
    public static final MapCodec<BrewingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
                                                instance -> instance.group(
                                                    Codec.list(ItemStackTemplate.CODEC).fieldOf("ingredient").forGetter(r -> r.ingredients),
                                                    ItemStackTemplate.CODEC.fieldOf("input").forGetter(r -> r.input),
                                                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result)
                                                ).apply(instance, BrewingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BrewingRecipe> STREAM_CODEC = StreamCodec.composite(
                                                ByteBufCodecs.collection(ArrayList::new, ItemStackTemplate.STREAM_CODEC),BrewingRecipe::ingredients,
                                                ItemStackTemplate.STREAM_CODEC, BrewingRecipe::input,
                                                ItemStackTemplate.STREAM_CODEC, BrewingRecipe::result,
                                                BrewingRecipe::new);
    public static final RecipeSerializer<BrewingRecipe> SERIALIZER = new RecipeSerializer<BrewingRecipe>(MAP_CODEC, STREAM_CODEC);;

    public BrewingRecipe(List<ItemStackTemplate> ingredients, ItemStackTemplate input, ItemStackTemplate output) {
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
    public ItemStack assemble(BrewingRecipeInput input) {
        return this.result.create();
    }

    public ItemStackTemplate result(){
        return this.result;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    public List<ItemStackTemplate> ingredients(){
        return this.ingredients;
    }

    public List<Item> getIngredientItems() {
        List<Item> itemList = new ArrayList<>();
        for (ItemStackTemplate template : this.ingredients) itemList.add(template.item().value());
        return itemList;
    }

    public List<ItemStack> getIngredientItemStacks() {
        List<ItemStack> ingredienItemStacks = new ArrayList<>();
        for (ItemStackTemplate template : this.ingredients) ingredienItemStacks.add(template.create());
        return ingredienItemStacks;
    }

    public ItemStackTemplate input(){
        return this.input;
    }

    public Item getInputItems() {
        return this.input.item().value();
    }

    public ItemStack getInputStacks() {
        return this.input.create();
    }

    @Override
    public RecipeType<BrewingRecipe> getType() {
        return ModRecipeType.BREWING;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.BREWING;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            List<Ingredient> ingredientList = new ArrayList<>();
            for (ItemStackTemplate itemStackTemplate : this.ingredients) ingredientList.add(Ingredient.of(itemStackTemplate.item().value()));
            this.placementInfo = PlacementInfo.create(ingredientList);
        }
        return this.placementInfo;
    }

    @Override
    public RecipeSerializer<? extends Recipe<BrewingRecipeInput>> getSerializer() {
        return SERIALIZER;
    }
}
