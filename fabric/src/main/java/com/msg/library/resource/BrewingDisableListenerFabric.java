/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.resource;

import com.msg.library.Constants;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class BrewingDisableListenerFabric extends BrewingDisableListener implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return Constants.resourceLocation("brewing_disable");
    }

    
}