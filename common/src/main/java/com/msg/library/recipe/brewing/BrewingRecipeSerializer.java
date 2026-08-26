/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe.brewing;

import java.util.List;
import java.util.Objects;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BrewingRecipeSerializer<T extends BrewingRecipe> implements RecipeSerializer<T> {
    private final BrewingRecipe.Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public BrewingRecipeSerializer(BrewingRecipe.Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec((instance) -> {
            P3<Mu<T>, List<ItemStack>, ItemStack, ItemStack> var10000 = instance.group(
                    Codec.list(ItemStack.STRICT_CODEC).fieldOf("ingredient").forGetter((brewingRecipe) -> brewingRecipe.ingredients),
                    ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("input").forGetter((brewingRecipe) -> brewingRecipe.input),
                    ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter((brewingRecipe) -> brewingRecipe.result)
            );
            Objects.requireNonNull(factory);
            return var10000.apply(instance, factory::create);   
        });
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public MapCodec<T> codec() {
        return this.codec;
    }

    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    private T fromNetwork(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        List<ItemStack> ingredients = ItemStack.OPTIONAL_LIST_STREAM_CODEC.decode(registryFriendlyByteBuf);
        ItemStack input = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf);
        ItemStack result = ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf);
        return (T)this.factory.create(ingredients, input, result);
    }

    private void toNetwork(RegistryFriendlyByteBuf registryFriendlyByteBuf, T brewingRecipe) {
        ItemStack.OPTIONAL_LIST_STREAM_CODEC.encode(registryFriendlyByteBuf, brewingRecipe.ingredients);
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, brewingRecipe.input);
        ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, brewingRecipe.result);
    }

    public BrewingRecipe create(List<ItemStack> ingredients, ItemStack input, ItemStack itemStack) {
        return this.factory.create(ingredients, input, itemStack);
    }
}