package com.connormahaffey.GiantTrees.Handler;

import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Manager.Populator;
import com.connormahaffey.GiantTrees.Settings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Used for natural occur trees
 */
public class HandlerChunk implements Listener {

    private final Map<String, Populator> manager;

    public HandlerChunk(final BukkitScheduler bukkitScheduler, final List<World> worlds) {
        this.manager = new HashMap<>();
        do {
            final String world = worlds.remove(0).getName();
            if (Settings.getInstance().getOccurOnWorldsList().contains(world)) {
                GiantTrees.logInfo("Adding " + world + " for auto populate");
                this.manager.put(world, new Populator(bukkitScheduler));
            }
        } while (!worlds.isEmpty());
    }

    /**
     * Watches for chunk population and if on a world set to spawn trees in the
     * wild, sends the chunk to be parsed
     *
     * @param chunkPopulateEvent Chunk Event
     */
    @EventHandler
    public void onChunkPopulate(final ChunkPopulateEvent chunkPopulateEvent) {
        final Chunk chunk = chunkPopulateEvent.getChunk();
        if (this.manager.containsKey(chunk.getWorld().getName())) {
            this.manager.get(chunk.getWorld().getName()).parseChunk(chunk);
        }
    }
}
