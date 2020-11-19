package com.connormahaffey.GiantTrees;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitScheduler;

/**
 *
 * @author Connor Mahaffey
 */
public class BlockPlaceHandler implements Listener {

    private TreeGrow TG;

    /**
     * Class declaration
     *
     * @param b instance of the bukkit scheduler
     */
    public BlockPlaceHandler(BukkitScheduler b) {
        TG = new TreeGrow(b);
    }

    /**
     * Gets a block place event for growing trees (random and near grow)
     *
     * @param e the event
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        boolean grow = false;

        if (GiantTrees.getSettings().allowBlockNearGrow() && GiantTrees.checkPermission(player, "grow")) {
            if (block.getTypeId() == 6) {
                grow = TG.nearGrow(block, true, e.getPlayer());
            }
            if (block.getTypeId() == GiantTrees.getSettings().getBlockNearGrowId()) {
                grow = TG.nearGrow(block, false, e.getPlayer());
            }
        }
        if (GiantTrees.getSettings().allowRandomChanceGrow() && GiantTrees.checkPermission(player, "grow")) {
            if (block.getTypeId() == 6 && !grow) {
                TG.randomGrow(block, GiantTrees.getSettings().getPercentRandomChanceGrow(), e.getPlayer());
            }
        }
    }

}
