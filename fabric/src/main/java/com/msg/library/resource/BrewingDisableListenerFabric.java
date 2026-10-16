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