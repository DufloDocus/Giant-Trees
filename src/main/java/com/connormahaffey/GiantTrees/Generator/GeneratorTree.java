package com.connormahaffey.GiantTrees.Generator;

import com.connormahaffey.GiantTrees.BlocksChanger;
import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import com.connormahaffey.GiantTrees.Runner;
import com.connormahaffey.GiantTrees.Save;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Handles Creation of trees
 */
public class GeneratorTree extends Runner {

    public static final Map<UUID, List<TreeInfos>> USERS_TREES_BUILDING;
    private final BukkitScheduler bukkitScheduler;
    private final TreeInfos treeInfos;
    private boolean save = true;

    static {
        USERS_TREES_BUILDING = new HashMap<>();
    }

    /**
     * Optional parameters for creating trees, including the wait after creation
     * time and whether or not to save
     *
     * @param bukkitScheduler BukkitScheduler
     * @param treeInfos TreeInfos
     * @param save to save or not
     */
    public GeneratorTree(final BukkitScheduler bukkitScheduler, final TreeInfos treeInfos, final boolean save) {
        this.bukkitScheduler = bukkitScheduler;
        this.treeInfos = treeInfos;
        this.save = save;
    }

    /**
     * Creates a tree using other classes.
     *
     * As an independent thread it can wait while the blocks in question are put
     * into a list and then spawned in the world.
     */
    @Override
    public void doJob() {
        //set up variables
        final Player player = this.treeInfos.getPlayer();
        if (player != null) {
            if (!USERS_TREES_BUILDING.containsKey(player.getUniqueId())) {
                USERS_TREES_BUILDING.put(player.getUniqueId(), new ArrayList<>());
            }
            USERS_TREES_BUILDING.get(player.getUniqueId()).add(this.treeInfos);
            say(ChatColor.RED + "Stand Back!", player);
        }
        //load chunk for tree populate events, since no players may be nearby
        if (this.treeInfos.hasResidingChunk()) {
            this.treeInfos.getResisidingChunk().load();
        }
        if (this.treeInfos.getTreeMaterialsInfos().isValid()) {
            final MaterialsTracker materialsTracker = new MaterialsTracker();
            //figure out the base
            makeBase(materialsTracker, this.treeInfos);
            //figure out the branches
            makeBranches(materialsTracker, this.treeInfos);
            //figure out the leaves
            makeLeaves(materialsTracker, this.treeInfos);
            //give the user time to flee
            pause(5000);
            //set the blocks
            applyBlocks(materialsTracker);
            //unload and save the chunk if no one is around
            if (this.treeInfos.hasResidingChunk()) {
                this.treeInfos.getResisidingChunk().unload(true);
            }
            //write to file
            if (this.save) {
                final Save saveRunner = new Save(materialsTracker, this.treeInfos);
                this.bukkitScheduler.runTask(GiantTrees.getPlugin(), saveRunner);
                do {
                    pause();
                } while (!saveRunner.isDone());
            }
            say(ChatColor.GREEN + "Giant Tree Created!", player);
        }
        if (player != null) {
            USERS_TREES_BUILDING.get(player.getUniqueId()).remove(this.treeInfos);
        }
    }

    /**
     * Makes the base of the tree using other classes
     *
     * @param materialsTracker materialsTracker object
     * @param treeInfos tree object
     */
    private void makeBase(final MaterialsTracker materialsTracker, final TreeInfos treeInfos) {
        final GeneratorTreeBase treeBase = new GeneratorTreeBase(materialsTracker, treeInfos);
        treeBase.createTreeBase();
    }

    /**
     * Makes the branches of the tree using other classes
     *
     * @param materialsTracker materialsTracker object
     * @param treeInfos tree object
     */
    private void makeBranches(final MaterialsTracker materialsTracker, final TreeInfos treeInfos) {
        final GeneratorTreeBranch treeBranch = new GeneratorTreeBranch(materialsTracker, treeInfos);
        treeBranch.createTreeBranch();
    }

    /**
     * Makes the leaves of the tree using other classes
     *
     * @param materialsTracker materialsTracker object
     * @param treeInfos tree object
     */
    private void makeLeaves(final MaterialsTracker materialsTracker, final TreeInfos treeInfos) {
        final GeneratorTreeLeaves treeLeaves = new GeneratorTreeLeaves(materialsTracker, treeInfos);
        treeLeaves.createTreeLeaves();
    }

    private void applyBlocks(final MaterialsTracker materialsTracker) {
        final Map<Block, Material> blocksToChange = new HashMap<>();
        for (int spot = 0; spot < materialsTracker.getBlockList().size(); spot++) {
            final Block block = materialsTracker.getBlockList().get(spot);
            final Material material = materialsTracker.getNewTypeList().get(spot);
            blocksToChange.put(block, material);
        }
        final BlocksChanger blocksChanger = new BlocksChanger(blocksToChange);
        final int id = this.bukkitScheduler.scheduleSyncDelayedTask(GiantTrees.getPlugin(), blocksChanger);
        do {
            pause();
        } while (!blocksChanger.isDone());
    }

    /**
     * Sends a message to a player if there is a player.
     *
     * Avoids error if the tree being created doesn't have a specified owner
     *
     * @param message message to send
     * @param player player to send to
     */
    private void say(final String message, final Player player) {
        if (player != null) {
            player.sendMessage(message);
        }
    }

    public static Location copyLocation(final Location origineLocation) {
        return new Location(origineLocation.getWorld(), origineLocation.getX(), origineLocation.getY(), origineLocation.getZ());
    }

    public static Location shiftLocation(final Location origineLocation, final double shiftX, final double shiftY, final double shiftZ) {
        final Location location = copyLocation(origineLocation);
        location.setX(location.getX() + shiftX);
        location.setY(location.getY() + shiftY);
        location.setZ(location.getZ() + shiftZ);
        return location;
    }
}
