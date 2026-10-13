package com.msg.library.networking;

import com.msg.library.Constants;
import com.msg.library.recipe.brewing.PotionBrewingMixinAccess;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketHandler {

    public static void serverpacket() {

        // Getting Brewing ingredients when server started
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            ConnectionHandling.loadBrewingInputs(server, server.getRecipeManager());
            ((PotionBrewingMixinAccess) server.potionBrewing()).onDatapackReload();
        });

        // Getting Brewing ingredients when reload datapack.
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (success) {
                ConnectionHandling.loadBrewingInputs(server, server.getRecipeManager());
                ((PotionBrewingMixinAccess) server.potionBrewing()).onDatapackReload();
            }
        });

        // // Server register that client has mod
        // ServerPlayNetworking.registerGlobalReceiver(MSGPackets.HasMod.ID, (payload, context) -> {
        //     ConnectionHandling.hasMod.add(context.player().getUUID());
        // });

        // Send brewing ingredients and input to players when they join or when server reload datapack
        // This doesn't send packet to singleplayer owner however
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            if (!player.level().getServer().isSingleplayerOwner(player.getGameProfile()))
                ServerPlayNetworking.send(player, new MSGPackets.BrewingPackets(ConnectionHandling.allBrewReagent, ConnectionHandling.allBrewInputs));
        });

        // // Remove player that disconnected from list that contain players has mod installed
        // ServerLoginConnectionEvents.DISCONNECT.register((listener, server) -> {
        //     ConnectionHandling.hasMod.removeIf(uuid -> server.getPlayerList().getPlayer(uuid) == null);
        //     Constants.LOG.info("Current players that has mod installed is: {}", ConnectionHandling.hasMod);
        // });
    }

    public static void clientPacket(){

        // Client inform server that it has mod
        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            // sender.sendPacket(new MSGPackets.HasMod());
        });

        // Client take the brewing ingredients sent by server
        ClientPlayNetworking.registerGlobalReceiver(MSGPackets.BrewingPackets.ID, (payload, context) -> {
            ConnectionHandling.allBrewReagent = payload.brewingReagent();
            ConnectionHandling.allBrewInputs = payload.brewingInputs();
        });

        // Reset brewing ingredients to the client
        ClientPlayConnectionEvents.DISCONNECT.register((listener, client) -> {
            ConnectionHandling.allBrewReagent = null;
            ConnectionHandling.allBrewInputs = null;
        });
    }

    public static void payloadRegistering(){
        // PayloadTypeRegistry.playC2S().register(MSGPackets.HasMod.ID, MSGPackets.HasMod.CODEC);
        PayloadTypeRegistry.playS2C().register(MSGPackets.BrewingPackets.ID, MSGPackets.BrewingPackets.CODEC);
    }
}