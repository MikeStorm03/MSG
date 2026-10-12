package com.msg.library.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

// import com.msg.library.Constants;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class DatapackReloadListener extends Event implements PreparableReloadListener {
    public static ReloadableServerResources serverResources;

    public DatapackReloadListener(ReloadableServerResources resources){
        serverResources = resources;
    }

    @Override
    public CompletableFuture<Void> reload(SharedState sharedState, Executor backgroundExecutor,
                                          PreparationBarrier barrier, Executor gameExecutor) {

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        boolean serverIsNotNull = server != null;

        return CompletableFuture.supplyAsync(() -> {
            // method before reload here
            // Constants.LOG.info("Loading before datapack load event.");
            if (serverIsNotNull) StartDatapackReloadEvent.execute(server, serverResources);
            return new DatapackReloadEvent();
        }, backgroundExecutor).thenCompose(barrier::wait).thenAcceptAsync((event) -> {
            // method reload successful here
            // Constants.LOG.info("Loading datapack load successful event.");
            if (serverIsNotNull) EndDataPackReloadEvent.execute(server, serverResources, true);
        }, gameExecutor).exceptionally((throwable) -> {
            // method reload fail here
            // Constants.LOG.info("Loading datapack load fail event.");
            if (serverIsNotNull) EndDataPackReloadEvent.execute(server, serverResources, false);
            return null; 
        });
    }
    
}
