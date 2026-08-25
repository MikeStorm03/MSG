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

import net.fabricmc.api.ModInitializer;

public class MSG implements ModInitializer{

    @Override
    public void onInitialize() {
        ModRecipeType.init();
        ModRecipeSerializers.init();
    }
}
