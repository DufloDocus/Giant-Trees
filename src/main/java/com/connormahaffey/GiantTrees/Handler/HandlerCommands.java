package com.connormahaffey.GiantTrees.Handler;

import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import com.connormahaffey.GiantTrees.Manager.Canceller;
import com.connormahaffey.GiantTrees.Manager.Creator;
import com.connormahaffey.GiantTrees.Settings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Used when user run commands
 */
public class HandlerCommands {

    private final Creator managerCreator;
    private final Canceller managerCanceller;

    public HandlerCommands(final BukkitScheduler bukkitScheduler) {
        this.managerCreator = new Creator(bukkitScheduler);
        this.managerCanceller = new Canceller(bukkitScheduler);
    }

    /**
     * Parses a command a player sends
     *
     * @param player player issuing command
     * @param command command in question
     */
    public void command(final Player player, final String[] command) {
        if (!GiantTrees.checkPermission(player, "build")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to make trees!");
            return;
        }
        switch (command.length) {
            case 0:
                help(player);
                break;
            case 1:
                final String currentCommand = command[0].toLowerCase();
                switch (currentCommand) {
                    case "help":
                        help(player);
                        break;
                    case "about":
                        about(player);
                        break;
                    case "reload":
                        reload(player);
                        break;
                    case "undo":
                        this.managerCanceller.undo(player);
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "Incorrect command parameter: " + command[0]);
                        player.sendMessage(ChatColor.RED + "Please see /gt help for correct usage!");
                        break;
                }
                break;
            default:
                this.managerCreator.create(command, player);
                break;

        }
    }

    /**
     * Help section of the program
     *
     * @param player player to help
     */
    private void help(final Player player) {
        final Settings settings = Settings.getInstance();
        player.sendMessage(ChatColor.GREEN + "Command: /gt, /gtree, or /gianttree");
        player.sendMessage(ChatColor.GREEN + "Undo a Tree: /gt undo  - while standing near the tree");
        player.sendMessage(ChatColor.GREEN + "Reload Settings: /gt reload");
        player.sendMessage(ChatColor.GREEN + "Make a Tree: /gt <height> <width> <log> <leaf> <density>");
        player.sendMessage(ChatColor.GREEN + "----------------------------------------------------");
        player.sendMessage(ChatColor.GREEN + "<height> -  must be between " + TreeInfos.MIN_HEIGHT + " and " + settings.getTreeHeightMax());
        player.sendMessage(ChatColor.GREEN + "<width> -  must be between " + TreeInfos.MIN_WIDTH + " and " + settings.getTreeWidthMax());
        player.sendMessage(ChatColor.GREEN + "<log> and <leaf> come from the material enum");
        player.sendMessage(ChatColor.GREEN + "Ex. AOK_LOG AOK_LEAVES");
        player.sendMessage(ChatColor.GREEN + "<density> - How dense leaf coverage is between " + TreeInfos.MIN_DENSITY + " and " + TreeInfos.MAX_DENSITY + " - " + TreeInfos.DEFAULT_DENSITY + " is default");

    }

    /**
     * About section of the program
     *
     * @param player player to send to
     */
    private void about(final Player player) {
        player.sendMessage(ChatColor.GREEN + "GiantTrees! Version " + GiantTrees.getVersion());
        player.sendMessage(ChatColor.GREEN + "Created By Connor Mahaffey and updated by DufloDocus");
    }

    /**
     * Reloads all settings for the program. For most settings, the server will
     * need to be restarted
     *
     * @param player player issuing command
     */
    private void reload(final Player player) {
        if (GiantTrees.checkPermission(player, "reload")) {
            Settings.reloadSettings();
            player.sendMessage(ChatColor.GREEN + "Config reloaded!");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to do that!");
        }
    }

}
