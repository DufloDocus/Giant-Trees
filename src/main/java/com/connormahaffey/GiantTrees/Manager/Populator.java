package com.connormahaffey.GiantTrees.Manager;

import com.connormahaffey.GiantTrees.Generator.GeneratorTree;
import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import com.connormahaffey.GiantTrees.Infos.TreeMaterialsInfos;
import com.connormahaffey.GiantTrees.Settings;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Populates the world with trees
 */
public class Populator {

    private final BukkitScheduler bukkitScheduler;

    public Populator(final BukkitScheduler bukkitScheduler) {
        this.bukkitScheduler = bukkitScheduler;
    }

    /**
     * Sees if chunk will have a tree, and if so adds it to the cache
     *
     * @param chunk chunk in question
     */
    public void parseChunk(final Chunk chunk) {
        if (getRandomChance(Settings.getInstance().getOccurChancePercent())) {
            final Location loc = getSurface(chunk.getWorld(), chunk.getX() * 16, chunk.getZ() * 16);
            if (loc != null) {
                if (Settings.getInstance().occurShowLocationInConsole()) {
                    GiantTrees.logInfo(loc.getWorld().getName() + " populated at X=" + loc.getX() + "   Z=" + loc.getZ());
                }
                final int height = getRandom(Settings.getInstance().getOccurHeightMax(), Settings.getInstance().getOccurHeightMin());
                final int width = getRandom(Settings.getInstance().getOccurWidthMax(), Settings.getInstance().getOccurWidthMin());
                final TreeInfos treeInfos = new TreeInfos(loc, height, width, TreeMaterialsInfos.DEFAULT_LOG, TreeMaterialsInfos.DEFAULT_LEAVES, 20);
                treeInfos.setResidingChunk(chunk);
                final GeneratorTree generatorTreeRunner = new GeneratorTree(this.bukkitScheduler, treeInfos, false);
                this.bukkitScheduler.runTaskAsynchronously(GiantTrees.getPlugin(), generatorTreeRunner);
            }
        }
    }

    /**
     * Finds the surface of the world, returns null if it can't be built on or
     * gets to the bottom
     *
     * @param world world
     * @param x x location of chunk
     * @param z z location of chunk
     * @return location of surface
     */
    private Location getSurface(final World world, int x, int z) {
        x = (int) (Math.random() * 16 + x);
        z = (int) (Math.random() * 16 + z);
        for (int y = world.getMaxHeight() - 1; y > 0; y--) {
            final Location currentLocation = new Location(world, x, y, z);
            final Material material = currentLocation.getBlock().getType();
            if (y == 1) {
                break;
            } else if (material == Material.AIR) {
                continue;
            } else if (!Tag.LOGS.getValues().contains(material) && !Tag.LEAVES.getValues().contains(material) && material != Material.GRASS && material != Material.GRASS_BLOCK && material != Material.DIRT) {
                break;
            } else if (material == Material.GRASS || material == Material.GRASS_BLOCK || material == Material.DIRT) {
                return currentLocation;
            }
        }
        return null;
    }

    /**
     * Gets a random number between two ints
     *
     * @param max max amount
     * @param min min amount
     * @return string of random int
     */
    private int getRandom(final int max, final int min) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Gets the random chance something will occur
     *
     * @param percentChance chance it will occur
     * @return if it occured or not
     */
    private boolean getRandomChance(final int percentChance) {
        final double percent = (double) (percentChance) / 100.0;
        return Math.random() + 0.01 <= percent;
    }
}
