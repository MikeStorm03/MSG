package com.msg.library.networking;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

// import com.msg.library.Constants;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.brewing.BrewingRecipe;

import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class ConnectionHandling {

    public static Set<UUID> hasMod = new HashSet<>();

    @Nullable
    public static Set<ItemStackTemplate> allBrewIngredients = new HashSet<>();
    @Nullable
    public static Set<ItemStackTemplate> allBrewInputs = new HashSet<>();

    public static final void loadBrewingInputs(RecipeManager recipeManager){

        allBrewIngredients.clear();
        allBrewInputs.clear();

        Stream<RecipeHolder<?>> recipeStream = recipeManager.getRecipes().stream().filter(holder -> holder.value().getType() == ModRecipeType.BREWING);
        for (RecipeHolder<?> recipeHolder : recipeStream.toList()) {
            if (recipeHolder.value() instanceof BrewingRecipe recipe) {
                allBrewIngredients.addAll(recipe.ingredients());
                allBrewInputs.add(recipe.input());
            }
        }
    }
}
