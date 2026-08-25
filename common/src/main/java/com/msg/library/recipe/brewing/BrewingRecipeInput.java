/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record BrewingRecipeInput(ItemStack ingredient, ItemStack input) implements RecipeInput {
    public ItemStack getItem(int i) {
        ItemStack stack;
        switch (i) {
            case 0 -> stack = this.ingredient;
            case 1 -> stack = this.input;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + i);
        }

        return stack;
    }

    public int size() {
        return 2;
    }

    public boolean isEmpty() {
        return this.ingredient.isEmpty() && this.input.isEmpty();
    }
}