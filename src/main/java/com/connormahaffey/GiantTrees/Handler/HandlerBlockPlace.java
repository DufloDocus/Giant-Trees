package com.connormahaffey.GiantTrees.Handler;

import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Infos.TreeMaterialsInfos;
import com.connormahaffey.GiantTrees.Manager.Grower;
import com.connormahaffey.GiantTrees.Settings;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Used while block is placed
 */
public class HandlerBlockPlace implements Listener {

    private final Grower manager;

    public HandlerBlockPlace(final BukkitScheduler bukkitScheduler) {
        this.manager = new Grower(bukkitScheduler);
    }

    /**
     * Gets a block place event for growing trees (random and near grow)
     *
     * @param blockPlaceEvent the event
     */
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent blockPlaceEvent) {
        final Block block = blockPlaceEvent.getBlock();
        final Player player = blockPlaceEvent.getPlayer();
        boolean grow = false;
        if (Settings.getInstance().growNearBlock() && GiantTrees.checkPermission(player, "grow")) {
            grow = this.manager.nearGrow(block, blockPlaceEvent.getPlayer());
        }
        if (Settings.getInstance().growRandomChance() && GiantTrees.checkPermission(player, "grow") && (block.getType().equals(TreeMaterialsInfos.DEFAULT_SAPLING) && !grow)) {
            this.manager.randomGrow(block, Settings.getInstance().getGrowRandomChancePercent(), blockPlaceEvent.getPlayer());
        }
    }

}
