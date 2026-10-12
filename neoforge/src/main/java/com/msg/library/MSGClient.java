package com.msg.library;

import com.msg.library.networking.MSGPackets;
import com.msg.library.networking.PacketHandler;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.configuration.SyncRegistries;
import net.neoforged.neoforge.network.registration.HandlerThread;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class MSGClient {

    public MSGClient(){}

    @SubscribeEvent
    public static void register(RegisterClientPayloadHandlersEvent event) {
        event.register(
            MSGPackets.BrewingPackets.ID,
            HandlerThread.NETWORK,
            PacketHandler::clientReceiveBrewingPacket
        );
    }
}
