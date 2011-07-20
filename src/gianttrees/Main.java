package gianttrees;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Connor Mahaffey
 */
/*
 * This program was written by Connor Mahaffey
 * 
 * Special Thanks:
 * Nisovin - His awesome fix for block setting issues/speed
 */
public class Main extends JavaPlugin{

    private static final Logger log = Logger.getLogger("Minecraft");
    private final String version = "0.3.3";
    private boolean permissionsInstalled = true;
    private DoCommand DC;
    private PermissionHandler permissionHandler;
    private PluginManager PM;
    protected static Main plugin;
    protected static Queue<Location> locQueue = new LinkedBlockingQueue<Location>();
    protected static Queue<Material> matQueue = new LinkedBlockingQueue<Material>();
    protected static MetaData metaData;
    protected static Queue<Location> locUndoQueue = new LinkedBlockingQueue<Location>();
    protected static Queue<Material> matUndoQueue = new LinkedBlockingQueue<Material>();

    public void onDisable() {
        log.info("[Giant Trees] version " + version + " is disabled");
    }

    public void onEnable() {
        plugin = this;
        setupPermissions();
        DC = new DoCommand();
        DC.setPermissions(permissionsInstalled, permissionHandler);
        DC.setServer(getServer());
        DC.createTreeFiles(null);
        PM = getServer().getPluginManager();
        log.info("[Giant Trees] version " + version + " is enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        Player player = (Player)sender;
        if(commandLabel.equalsIgnoreCase("gianttree") || commandLabel.equalsIgnoreCase("gt") || commandLabel.equalsIgnoreCase("gtree")){
            if(checkPermission(player)){
                DC.doCommand(player, args);
                return true;
            }
            else{
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }
        }
        else{
            return false;
        }
                
    }

    private void setupPermissions(){
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null){
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
            }
            else{
                permissionsInstalled = false;
            }
        }
    }

    private boolean checkPermission(Player player){
        if(permissionsInstalled){
            return permissionHandler.has(player, "gianttrees.build");
        }
        else{
            return player.hasPermission("gianttrees.build");
        }
    }
}
