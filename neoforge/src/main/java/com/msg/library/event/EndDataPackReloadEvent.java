package com.msg.library.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerResources;

public class EndDataPackReloadEvent extends DatapackReloadEvent {
    
    private static final List<EndDataPackReload<MinecraftServer, ReloadableServerResources, Boolean>> hooks = new ArrayList<>();

    public void register(EndDataPackReload<MinecraftServer, ReloadableServerResources, Boolean> hook) {
        hooks.add(hook);
    }

    public static void execute(MinecraftServer server, ReloadableServerResources reloadableServerResources, boolean success) {
        for (EndDataPackReload<MinecraftServer, ReloadableServerResources, Boolean> hook : hooks) {
            hook.endDataPackReload(server, reloadableServerResources, success);
        }
    }
}