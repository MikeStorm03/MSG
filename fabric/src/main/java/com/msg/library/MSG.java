/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library;

import com.msg.library.networking.PacketHandler;
import com.msg.library.recipe.ModRecipeBookCategories;
import com.msg.library.recipe.ModRecipeSerializers;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.resource.BrewingDisableListenerFabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class MSG implements ModInitializer{

    @Override
    public void onInitialize() {
        ModRecipeType.init();
        ModRecipeSerializers.init();
        ModRecipeBookCategories.init();

        PacketHandler.payloadRegistering();
        PacketHandler.serverpacket();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new BrewingDisableListenerFabric());
    }
}
