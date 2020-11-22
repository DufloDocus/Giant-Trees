package com.connormahaffey.GiantTrees.Manager;

import com.connormahaffey.GiantTrees.Generator.GeneratorTree;
import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Undo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Canceller {

    final BukkitScheduler bukkitScheduler;

    public Canceller(final BukkitScheduler bukkitScheduler) {
        this.bukkitScheduler = bukkitScheduler;
    }

    public void undo(final Player player) {
        final boolean somethingIsRunnig = GeneratorTree.USERS_TREES_BUILDING.containsKey(player.getUniqueId()) && !GeneratorTree.USERS_TREES_BUILDING.get(player.getUniqueId()).isEmpty();
        if (!somethingIsRunnig) {
            final Undo undoRunner = new Undo(this.bukkitScheduler, player, player.getLocation());
            this.bukkitScheduler.runTaskAsynchronously(GiantTrees.getPlugin(), undoRunner);
        } else {
            player.sendMessage(ChatColor.RED + "Cannot undo a tree while one is being built!");
        }
    }
}
