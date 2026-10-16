package com.msg.library.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerResources;

public class StartDatapackReloadEvent extends DatapackReloadEvent {
    
    private static final List<StartDataPackReload<MinecraftServer, ReloadableServerResources>> hooks = new ArrayList<>();

    public void register(StartDataPackReload<MinecraftServer, ReloadableServerResources> hook) {
        hooks.add(hook);
    }

    public static void execute(MinecraftServer server, ReloadableServerResources reloadableServerResources) {
        for (StartDataPackReload<MinecraftServer, ReloadableServerResources> hook : hooks) {
            hook.startDataPackReload(server, reloadableServerResources);
        }
    }
}