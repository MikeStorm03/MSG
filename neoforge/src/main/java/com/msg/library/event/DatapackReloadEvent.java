package com.msg.library.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@SuppressWarnings("hiding")
public class DatapackReloadEvent {

    public static final StartDatapackReloadEvent BEFORE = new StartDatapackReloadEvent();
    public static final EndDataPackReloadEvent AFTER = new EndDataPackReloadEvent();

    @FunctionalInterface
	public interface StartDataPackReload<MinecraftServer, ResourceManager> {
		void startDataPackReload(MinecraftServer server, ResourceManager resourceManager);
	}

    @FunctionalInterface
	public interface EndDataPackReload<MinecraftServer, ResourceManager, Boolean> {
		void endDataPackReload(MinecraftServer server, ResourceManager resourceManager, boolean success);
	}

}