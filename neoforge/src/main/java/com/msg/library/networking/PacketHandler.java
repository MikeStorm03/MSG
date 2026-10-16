/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.networking;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketHandler {

    public static void clientReceiveBrewingPacket(final MSGPackets.BrewingPackets packets, final IPayloadContext context) {
        ConnectionHandling.allBrewReagent = packets.brewingReagent();
        ConnectionHandling.allBrewInputs = packets.brewingInputs();
    }
}