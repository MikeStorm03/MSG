package com.msg.library;

import com.msg.library.networking.PacketHandler;

import net.fabricmc.api.ClientModInitializer;

public class MSGClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PacketHandler.clientPacket();
    }

}