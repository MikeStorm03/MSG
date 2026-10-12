/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.resource;

import java.io.BufferedReader;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.msg.library.Constants;
import com.msg.library.recipe.brewing.BrewingDisable;
import com.msg.library.recipe.brewing.BrewingDisable.ItemConvertDisable;
import com.msg.library.recipe.brewing.BrewingDisable.PotionMixDisable;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class BrewingDisableListener implements ResourceManagerReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
    
        BrewingDisable.allDisablePotion.clear();
        BrewingDisable.allDisableItemConvert.clear();

        for (Identifier id : resourceManager.listResources("brewing_disable", id -> id.getPath().toString().endsWith(".json")).keySet()) {

            try(BufferedReader buffer = resourceManager.getResource(id).get().openAsReader()) {
                // BrewingDisable.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8))).result().orElseThrow();
                BrewingDisable data = BrewingDisable.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(buffer)).resultOrPartial(Constants.LOG::error).orElseThrow();
                switch (data.getType()) {
                    case BrewingDisable.Type.POTION_MIX -> BrewingDisable.allDisablePotion.add((PotionMixDisable)data);
                    case BrewingDisable.Type.ITEM_CONVERT -> BrewingDisable.allDisableItemConvert.add((ItemConvertDisable)data);
                }
            } catch(Exception e) {
                Constants.LOG.error("Error occurred while loading disable brewing recipe " + id.toString(), e);
            }
        }

    }
}