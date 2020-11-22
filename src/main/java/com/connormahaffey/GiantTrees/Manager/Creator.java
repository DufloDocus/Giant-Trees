package com.connormahaffey.GiantTrees.Manager;

import com.connormahaffey.GiantTrees.Generator.GeneratorTree;
import com.connormahaffey.GiantTrees.GiantTrees;
import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import com.connormahaffey.GiantTrees.Infos.TreeMaterialsInfos;
import com.connormahaffey.GiantTrees.Settings;
import com.connormahaffey.GiantTrees.Tools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Creator {

    private final BukkitScheduler bukkitScheduler;
    private long lastExecution;

    public Creator(final BukkitScheduler bukkitScheduler) {
        this.bukkitScheduler = bukkitScheduler;
    }

    public void create(final String[] command, final Player player) {
        final long timeSpent = (System.currentTimeMillis() - this.lastExecution) * 1000;
        if (timeSpent <= Settings.getInstance().getTreeWaitingDelayBetweenBuild()) {
            player.sendMessage(ChatColor.RED + "You should wait " + (Settings.getInstance().getTreeWaitingDelayBetweenBuild() - timeSpent) + " more seconds before create new tree");
            return;
        }
        final int height = Tools.stringToInt(command[0]);
        final int width = Tools.stringToInt(command[1]);
        final Material log = (command.length == 4 || command.length == 5) ? Material.getMaterial(command[2]) : null;
        final Material leaves = (command.length == 4 || command.length == 5) ? Material.getMaterial(command[3]) : null;
        final int density = (command.length == 5) ? Tools.stringToInt(command[4]) : -1;
        final boolean isValidSize = (height != -1 && width != -1);
        final boolean isValidMaterial = (log != null && leaves != null);
        final boolean isDensity = (density != -1);
        //creates tree object
        final TreeInfos tmpTreeInfos;
        switch (command.length) {
            case 2:
                tmpTreeInfos = (isValidSize) ? new TreeInfos(player, height, width) : null;
                break;
            case 4:
                tmpTreeInfos = (isValidSize && isValidMaterial) ? new TreeInfos(player, height, width, log, leaves) : null;
                break;
            case 5:
                tmpTreeInfos = (isValidSize && isValidMaterial && isDensity) ? new TreeInfos(player, height, width, log, leaves, density) : null;
                break;
            default:
                tmpTreeInfos = null;
        }
        if (tmpTreeInfos != null) {
            GiantTrees.logInfo("Asked for this tree => " + tmpTreeInfos.toString());
            //sees if any rules were broken
            if (!tmpTreeInfos.isValidHeight()) {
                player.sendMessage(ChatColor.RED + "Wrong min height [Min:" + TreeInfos.MIN_HEIGHT + "]");
            } else if (!tmpTreeInfos.isValidWidth()) {
                player.sendMessage(ChatColor.RED + "Wrong min width [Min:" + TreeInfos.MIN_WIDTH + "]");
            } else if (!GiantTrees.checkPermission(player, "nolimit") && tmpTreeInfos.getHeight() > Settings.getInstance().getTreeHeightMax()) {
                player.sendMessage(ChatColor.RED + "You don't have permission to make a tree that tall [Max:" + Settings.getInstance().getTreeHeightMax() + "]");
            } else if (!GiantTrees.checkPermission(player, "nolimit") && tmpTreeInfos.getWidth() > Settings.getInstance().getTreeWidthMax()) {
                player.sendMessage(ChatColor.RED + "You don't have permission to make a tree that wide [Max:" + Settings.getInstance().getTreeWidthMax() + "]");
            } else if (command.length == 5 && !tmpTreeInfos.isValidDensity()) {
                player.sendMessage(ChatColor.RED + "Wrong density [Min:" + TreeInfos.MIN_DENSITY + "|Max:" + TreeInfos.MAX_DENSITY + "]");
            } else if (tmpTreeInfos.isValid()) {
                if (!GiantTrees.checkPermission(player, "customdangerous") && tmpTreeInfos.getTreeMaterialsInfos().isDangerous()) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to use that block");
                } else if (!GiantTrees.checkPermission(player, "custom") && (!tmpTreeInfos.getTreeMaterialsInfos().getLogType().equals(TreeMaterialsInfos.DEFAULT_LOG) || !tmpTreeInfos.getTreeMaterialsInfos().getLeafType().equals(TreeMaterialsInfos.DEFAULT_LEAVES))) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to make custom trees");
                } else {
                    final GeneratorTree generatorTreeRunner = new GeneratorTree(bukkitScheduler, tmpTreeInfos, true);
                    this.bukkitScheduler.runTaskAsynchronously(GiantTrees.getPlugin(), generatorTreeRunner);
                    this.lastExecution = System.currentTimeMillis();
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid tree!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Invalid command format - did you specify height and width and/or log and/or leaf and/or dencity?");
        }
    }
}
