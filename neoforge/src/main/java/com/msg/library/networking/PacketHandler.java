package com.msg.library.networking;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketHandler {

    public static void clientReceiveBrewingPacket(final MSGPackets.BrewingPackets packets, final IPayloadContext context) {
        ConnectionHandling.allBrewIngredients = packets.brewingIngredients();
        ConnectionHandling.allBrewInputs = packets.brewingInputs();
    }}
