/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.recipe.brewing;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;

public interface BrewingDisable {

    public static Set<PotionMixDisable> allDisablePotion = new HashSet<>();
    public static Set<ItemConvertDisable> allDisableItemConvert = new HashSet<>();

    Codec<BrewingDisable> CODEC = Type.CODEC.dispatch(BrewingDisable::getType, type -> switch (type) {
        case POTION_MIX -> PotionMixDisable.CODEC;
        case ITEM_CONVERT -> ItemConvertDisable.CODEC;
    });

    @NotNull Type getType();

    public enum Type implements StringRepresentable {
        POTION_MIX("potion_mix"), ITEM_CONVERT("item_convert");

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        private final String type;

        Type(String type) {
            this.type = type;
        }

        @Override
        public String getSerializedName() {
            return this.type;
        }
        
    }

    public record PotionMixDisable(Holder<Item> reagent, Holder<Potion> input) implements BrewingDisable {
        public static final MapCodec<PotionMixDisable> CODEC = RecordCodecBuilder.mapCodec(instance -> 
            instance.group(
                            ItemStack.ITEM_NON_AIR_CODEC.fieldOf("reagent").forGetter(PotionMixDisable::reagent),
                            Potion.CODEC.fieldOf("input").forGetter(PotionMixDisable::input)
            ).apply(instance, PotionMixDisable::new)
        );

        @Override
        public @NotNull Type getType() {
            return Type.POTION_MIX;
        }
        
    }

    public record ItemConvertDisable(Holder<Item> reagent, Holder<Item> input) implements BrewingDisable {
        public static final MapCodec<ItemConvertDisable> CODEC = RecordCodecBuilder.mapCodec(instance -> 
            instance.group(
                            ItemStack.ITEM_NON_AIR_CODEC.fieldOf("reagent").forGetter(ItemConvertDisable::reagent),
                            ItemStack.ITEM_NON_AIR_CODEC.fieldOf("input").forGetter(ItemConvertDisable::input)
            ).apply(instance, ItemConvertDisable::new)
        );

        @Override
        public @NotNull Type getType() {
            return Type.ITEM_CONVERT;
        }
    }
}