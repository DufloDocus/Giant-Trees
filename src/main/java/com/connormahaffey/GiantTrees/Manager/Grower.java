package com.connormahaffey.GiantTrees.Manager;

import com.connormahaffey.GiantTrees.Generator.GeneratorTree;
import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import com.connormahaffey.GiantTrees.Infos.TreeMaterialsInfos;
import com.connormahaffey.GiantTrees.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Grower {
    private final BukkitScheduler bukkitScheduler;

    /**
     * Handles random and near-block tree growth
     *
     * @param bukkitScheduler BukkitScheduler
     */
    public Grower(final BukkitScheduler bukkitScheduler) {
        this.bukkitScheduler = bukkitScheduler;
    }

    /**
     * Checks to see if a block or sappling placed is near another
     * block/sappling and should be a Giant Tree Known Bug: Tree grows at
     * location of last block placed, not the sappling
     *
     * @param block the block
     * @param player player who planted it
     * @return if a tree is grown or not
     */
    public boolean nearGrow(final Block block, final Player player) {
        if (block.getType() == TreeMaterialsInfos.DEFAULT_SAPLING && (isBlockNear(block, Settings.getInstance().getGrowNearBlockMaterial(), -1))) {
            createTree(block.getLocation(), player, block);
            return true;
        } else if (block.getType() == Settings.getInstance().getGrowNearBlockMaterial() && (isBlockNear(block, TreeMaterialsInfos.DEFAULT_SAPLING, 1))) {
            // FIXME known bug, will create tree where block is placed, not sappling
            createTree(block.getLocation(), player, block);
            return true;
        }
        return false;
    }

    /**
     * Handles random growth of trees
     *
     * @param block block placed
     * @param chance
     * @param player
     */
    public void randomGrow(final Block block, final int chance, final Player player) {
        final double dChance = (double) chance / 100.0;
        if (Math.random() + 0.01 <= dChance) {
            createTree(block.getLocation(), player, block);
        }
    }

    /**
     * Tells if a block is near another block
     *
     * @param block the block
     * @param id the id
     * @param change up or down depending on if it's the sappling or not
     * @return true or false
     */
    private boolean isBlockNear(final Block block, final Material materialBlock, final int change) {
        boolean isId = false;
        final Location loc = new Location(block.getLocation().getWorld(), block.getLocation().getX() - 1, block.getLocation().getY(), block.getLocation().getZ() - 1);
        Location loc1, loc2, loc3;
        loc1 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        for (int y = 0; y < 2; y++) {
            for (int z = 0; z < 3; z++) {
                loc1 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc.getZ() + z);
                loc2 = new Location(loc1.getWorld(), loc1.getX() + 1, loc1.getY(), loc.getZ() + z);
                loc3 = new Location(loc1.getWorld(), loc1.getX() + 2, loc1.getY(), loc.getZ() + z);
                if (loc1.getBlock().getType().equals(materialBlock) || loc2.getBlock().getType().equals(materialBlock) || loc3.getBlock().getType().equals(materialBlock)) {
                    isId = true;
                    break;
                }
            }
            loc1 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY() + change, loc1.getZ());
        }
        return isId;
    }

    /**
     * Get a random value between two numbers
     *
     * @param max max number
     * @param min min number
     * @return string value of the random number
     */
    private int getRandom(final int max, final int min) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Add a tree to the list of trees to be created. If the block in question
     * is the sappling, check its data to see what type it is, and make the tree
     * that type.
     *
     * @param location location of the tree
     * @param player player who planted it
     * @param block block in question
     */
    private void createTree(final Location location, final Player player, final Block block) {
        player.sendMessage(ChatColor.GREEN + Settings.getInstance().getGrowMessage());
        final int height = getRandom(Settings.getInstance().getGrowHeightMax(), Settings.getInstance().getGrowHeightMin());
        final int width = getRandom(Settings.getInstance().getGrowWidthMax(), Settings.getInstance().getGrowWidthMin());
        final Material log;
        final Material leaves;
        if (!TreeMaterialsInfos.SAPLING.contains(block.getType())) {
            log = TreeMaterialsInfos.DEFAULT_LOG;
            leaves = TreeMaterialsInfos.DEFAULT_LEAVES;
        } else {
            log = TreeMaterialsInfos.SAPLING_LOG.get(block.getType());
            leaves = TreeMaterialsInfos.SAPLING_LEAVES.get(block.getType());
        }
        final TreeInfos treeInfos = new TreeInfos(location, height, width, log, leaves, 20);
        final GeneratorTree generatorTreeRunner = new GeneratorTree(bukkitScheduler, treeInfos, false);
        this.bukkitScheduler.runTaskAsynchronously(GiantTrees.getPlugin(), generatorTreeRunner);
    }
}
