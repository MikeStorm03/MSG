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
import com.msg.library.resource.BrewingDisableListener;

import net.fabricmc.api.ModInitializer;
// import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
// import net.fabricmc.fabric.api.event.registry.RegistryAttributeHolder;
// import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;
// import net.minecraft.core.registries.Registries;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.server.packs.PackType;

public class MSG implements ModInitializer{

    @Override
    public void onInitialize() {
        ModRecipeType.init();
        ModRecipeSerializers.init();
        ModRecipeBookCategories.init();

        PacketHandler.payloadRegistering();
        PacketHandler.serverpacket();

        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(Constants.resourceLocation("brewing_disable"), new BrewingDisableListener());

    }
}
