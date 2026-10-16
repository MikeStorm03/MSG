/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.msg.library.recipe.brewing.BrewingRecipe;
import com.msg.library.recipe.brewing.BrewingRecipeInput;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {

    @ModifyVariable(
        method = "Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;serverTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;)V",
        at = @At("STORE"),
        ordinal = 0
    )
    private static boolean modifyTestingForBrewingVariable(boolean bool, Level level, BlockPos blockPos, BlockState blockState, BrewingStandBlockEntity brewingStandBlockEntity){
        ItemStack regent = brewingStandBlockEntity.getItem(3);
        @Nullable RecipeHolder<? extends BrewingRecipe> recipeHolder = null;
        for (int i = 0; i < 3; i++) {
            recipeHolder = BrewingRecipe.recipeCheck.getRecipeFor(new BrewingRecipeInput(regent, brewingStandBlockEntity.getItem(i)), (ServerLevel) level).orElse(null);
            if (recipeHolder != null) break; 
        }
        return bool || recipeHolder != null;
    }

    @Redirect(
        method = "Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;serverTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;)V",
        at = @At(value = "INVOKE",
                target = "Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;doBrew(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/NonNullList;)V")
    )
    private static void redirectDoBrew(Level level, BlockPos blockPos, NonNullList<ItemStack> nonNullList){
        BlockEntity brewingStandBlockEntity = level.getBlockEntity(blockPos);
        if (brewingStandBlockEntity instanceof BrewingStandBlockEntity) {

            ItemStack regent = (ItemStack)nonNullList.get(3);
            @Nullable RecipeHolder<? extends BrewingRecipe> recipe;
            PotionBrewing potionBrewing = level.potionBrewing();
            int regentCost = 1;

            for (int i = 0; i < 3; i++) {

                recipe = BrewingRecipe.recipeCheck.getRecipeFor(new BrewingRecipeInput(regent, nonNullList.get(i)), (ServerLevel) level).orElse(null);

                if (recipe == null) nonNullList.set(i, potionBrewing.mix(regent, nonNullList.get(i)));
                else {
                    nonNullList.set(i, recipe.value().getOutputItem().copy());
                    for (ItemStack recipeRegent : recipe.value().getRegentStacks()) 
                        if (ItemStack.isSameItem(recipeRegent, regent)) {
                            if (recipeRegent.getCount() > regentCost)
                                regentCost = recipeRegent.getCount();
                            break;
                        }
                }
            }

            regent.shrink(regentCost);
            ItemStack remainingItem = regent.getItem().getCraftingRemainder();
            if (!remainingItem.isEmpty())
                if (regent.isEmpty()) {
                    regent = remainingItem;
                } else {
                    Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), remainingItem);
                }

            nonNullList.set(3, regent);
            level.levelEvent(1035, blockPos, 0);
        }
    }

}
