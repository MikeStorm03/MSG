/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library;

import com.msg.library.event.DatapackReloadEvent;
import com.msg.library.event.DatapackReloadListener;
import com.msg.library.networking.ConnectionHandling;
import com.msg.library.networking.MSGPackets;
import com.msg.library.recipe.ModRecipeBookCategories;
import com.msg.library.recipe.ModRecipeSerializers;
import com.msg.library.recipe.ModRecipeType;
import com.msg.library.recipe.brewing.PotionBrewingMixinAccess;
import com.msg.library.resource.BrewingDisableListener;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class MSG {

    public MSG() {
        CommonClass.init();
        customEventsRegistering();
    }

    public static void customEventsRegistering(){
        DatapackReloadEvent.AFTER.register((server, reloadableServerResources, success) -> {
            ConnectionHandling.loadBrewingInputs(server, reloadableServerResources.getRecipeManager());
            ((PotionBrewingMixinAccess) server.potionBrewing()).onDatapackReload();
        });
    }

    @SubscribeEvent
    public static void registerSetup(RegisterEvent event) {
        Registry<?> registry = event.getRegistry();
        if (registry.equals(BuiltInRegistries.RECIPE_TYPE)) ModRecipeType.init();
        else if (registry.equals(BuiltInRegistries.RECIPE_SERIALIZER)) ModRecipeSerializers.init();
        else if (registry.equals(BuiltInRegistries.RECIPE_BOOK_CATEGORY)) ModRecipeBookCategories.init();
    }

    @SubscribeEvent
    public static void addServerReloadListenersEvent(AddServerReloadListenersEvent event) {
        event.addListener(Constants.resourceLocation("brewing_disable"), new BrewingDisableListener());
        event.addListener(Constants.resourceLocation("datapack_reload_listener"), new DatapackReloadListener(event.getServerResources()));
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
            .executesOn(HandlerThread.NETWORK); 
        registrar.playToClient(
            MSGPackets.BrewingPackets.ID,
            MSGPackets.BrewingPackets.CODEC
        );
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        ConnectionHandling.loadBrewingInputs(server, server.getRecipeManager());
        ((PotionBrewingMixinAccess) server.potionBrewing()).onDatapackReload();
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        ServerPlayer playerJoining = event.getPlayer();
        if (playerJoining == null)
            event.getRelevantPlayers().forEach(playerOnline -> {
                if (!playerOnline.level().getServer().isSingleplayerOwner(playerOnline.nameAndId()))
                    PacketDistributor.sendToPlayer(playerOnline, new MSGPackets.BrewingPackets(ConnectionHandling.allBrewReagent, ConnectionHandling.allBrewInputs));
                });
        else
            if (!playerJoining.level().getServer().isSingleplayerOwner(playerJoining.nameAndId()))
                PacketDistributor.sendToPlayer(playerJoining, new MSGPackets.BrewingPackets(ConnectionHandling.allBrewReagent, ConnectionHandling.allBrewInputs));
    }
}