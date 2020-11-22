package com.connormahaffey.GiantTrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Gets rid of an un-wanted tree from file and the world.
 *
 * FIXME1 make an undo compare to last id of a specific player
 */
public class Undo extends Runner implements FileManagement {

    private final ArrayList<Location> treeLocations = new ArrayList<>();
    private final BukkitScheduler bukkitScheduler;
    private final Player player;
    private final Location location;

    public Undo(final BukkitScheduler bukkitScheduler, final Player player, final Location location) {
        this.bukkitScheduler = bukkitScheduler;
        this.player = player;
        this.location = location;
    }

    /**
     * Finds the closest tree, removes it, then deletes the file
     */
    @Override
    public void doJob() {
        final String woldName = this.player.getWorld().getName();
        final String treesDataFile = FILE_MANAGER.getTreesFilePath(woldName);
        if (FILE_MANAGER.pathExists(treesDataFile)) {
            final String fileContent = FILE_MANAGER.read(treesDataFile);
            final ArrayList<Integer> treesId = new ArrayList<>();
            final ArrayList<String> playersName = new ArrayList<>();
            final List<FileDbTreesWrapper> treesData = new ArrayList<>(Arrays.asList(GSON.fromJson(fileContent, FileDbTreesWrapper[].class)));
            treesData.forEach((tree) -> {
                this.treeLocations.add(new Location(this.player.getWorld(), tree.getX(), tree.getY(), tree.getZ()));
                treesId.add(tree.getId());
                playersName.add(tree.getOwner());
            });
            final int closest = getClosest(this.location);
            if (closest != -1) {
                final String currentTreeDataZip = FILE_MANAGER.getTreeZipFilePath(woldName, treesId.get(closest));
                final String owner = playersName.get(closest);
                if (owner.equalsIgnoreCase(this.player.getName()) || GiantTrees.checkPermission(this.player, "undoall")) {
                    this.player.sendMessage(ChatColor.GREEN + "Starting undo...");
                    final String[] data = FILE_MANAGER.readFromArchive(currentTreeDataZip);

                    final Map<Block, Material> blocksToChange = new HashMap<>();
                    for (int i = 0; i < data.length; i += 4) {
                        final Block block = new Location(this.player.getWorld(), Tools.getDouble(data[i]), Tools.getDouble(data[i + 1]), Tools.getDouble(data[i + 2])).getBlock();
                        final Material material = Material.getMaterial(data[i + 3]);
                        blocksToChange.put(block, material);
                    }
                    final BlocksChanger blocksChanger = new BlocksChanger(blocksToChange);
                    final int id = this.bukkitScheduler.scheduleSyncDelayedTask(GiantTrees.getPlugin(), blocksChanger);
                    do {
                        pause();
                    } while (!blocksChanger.isDone());
                    FILE_MANAGER.delete(currentTreeDataZip);
                    treesData.remove(closest);
                    if (treesData.size() > 0) {
                        final String newTreesData = GSON.toJson(treesData.toArray());
                        FILE_MANAGER.write(newTreesData, treesDataFile);
                    } else {
                        FILE_MANAGER.delete(treesDataFile);
                    }
                    this.player.sendMessage(ChatColor.GREEN + "Tree has been undone!");
                } else {
                    this.player.sendMessage(ChatColor.RED + "This tree is owned by " + owner);
                }
            } else {
                this.player.sendMessage(ChatColor.RED + "No tree to undo within 25 blocks!");
            }
        } else {
            this.player.sendMessage(ChatColor.RED + "You haven't created any trees on this world yet!");
        }
    }

    /**
     * Finds the closest tree to a given location
     *
     * @param loc location of the person
     * @return the number of the tree it is
     */
    private int getClosest(final Location loc) {
        final double[] close = {100, 100, 100};
        double closest = 100;
        int treeMatch = 0;
        for (int i = 0; i < this.treeLocations.size(); i++) {
            final double x = Math.abs(this.treeLocations.get(i).getX() - loc.getX());
            final double y = Math.abs(this.treeLocations.get(i).getY() - loc.getY());
            final double z = Math.abs(this.treeLocations.get(i).getZ() - loc.getZ());
            final double average = (x + y + z) / 3;
            if (average < closest) {
                closest = average;
                close[0] = x;
                close[1] = y;
                close[2] = z;
                treeMatch = i;
            }
        }
        if (close[0] > 25 || close[1] > 25 || close[2] > 25) {
            return -1;
        }
        return treeMatch;
    }
}
