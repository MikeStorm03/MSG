/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// import com.msg.library.Constants;
import com.msg.library.recipe.brewing.BrewingRecipe;
import com.msg.library.recipe.brewing.ModRecipeType;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.BrewingStandMenu.IngredientsSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

@Mixin(IngredientsSlot.class)
public class IngredientsSlotMixin {
    @Inject(method = "Lnet/minecraft/world/inventory/BrewingStandMenu$IngredientsSlot;mayPlace(Lnet/minecraft/world/item/ItemStack;)Z",
            at = @At("RETURN"),
            cancellable = true)
    private void injected(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        List<RecipeHolder<BrewingRecipe>> allBrewRecipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipeType.BREWING);
        // Constants.LOG.info("All brewing recipe are:\n\r\r{}", allBrewRecipes);
        for (RecipeHolder<BrewingRecipe> recipeHolder : allBrewRecipes) {
            for (Item ingredient : recipeHolder.value().getIngredientItems()) {
                if (itemStack.is(ingredient)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
