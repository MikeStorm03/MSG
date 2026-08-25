/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library;

import com.msg.library.recipe.brewing.ModRecipeSerializers;
import com.msg.library.recipe.brewing.ModRecipeType;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class MSG {

    public MSG() {}

    @SubscribeEvent
    public static void registerSetup(RegisterEvent event) {
        Registry<?> registry = event.getRegistry();
        if (registry.equals(BuiltInRegistries.RECIPE_TYPE)) ModRecipeType.init();
        else if (registry.equals(BuiltInRegistries.RECIPE_SERIALIZER)) ModRecipeSerializers.init();
    }
}