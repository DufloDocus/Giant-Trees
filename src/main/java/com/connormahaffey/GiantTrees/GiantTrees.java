package com.connormahaffey.GiantTrees;

import com.connormahaffey.GiantTrees.Handler.HandlerBlockPlace;
import com.connormahaffey.GiantTrees.Handler.HandlerChunk;
import com.connormahaffey.GiantTrees.Handler.HandlerCommands;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GiantTrees extends JavaPlugin {

    private static final String VERSION = "0.5.1"; //remember to change plugin.yml and pom.xml version too!
    private static final FileManager FILE_MANAGER = new FileManager();
    private static Plugin instance;
    private HandlerCommands handlerCommands;
    private HandlerChunk handlerChunk;
    private HandlerBlockPlace handlerBlockPlace;
    private PluginManager pluginManager;

    /**
     * Code to run when disabled
     */
    @Override
    public void onDisable() {
        getLogger().info("version " + VERSION + " is disabled");
    }

    /**
     * Code to run when enabled.
     *
     * Loads settings, worlds, turns on necessary events, and sets up
     * permissions
     */
    @Override
    public void onEnable() {
        instance = this;
        load(getServer().getWorlds());
        final Settings settings = Settings.getInstance();
        this.handlerCommands = new HandlerCommands(getServer().getScheduler());
        this.pluginManager = getServer().getPluginManager();
        if (settings.occurNaturallyAllowed()) {
            this.handlerChunk = new HandlerChunk(getServer().getScheduler(), getServer().getWorlds());
            this.pluginManager.registerEvents(this.handlerChunk, this);
        }
        if (settings.growPlantingAllowed()) {
            this.handlerBlockPlace = new HandlerBlockPlace(getServer().getScheduler());
            this.pluginManager.registerEvents(this.handlerBlockPlace, this);
        }
        getLogger().info("version " + VERSION + " is enabled");
    }

    /**
     * Code to run when a command is used
     *
     * @param commandSender sent the command
     * @param cmd command
     * @param commandLabel front of the command
     * @param args arguments of the command
     * @return command used properly or not
     */
    @Override
    public boolean onCommand(final CommandSender commandSender, final Command cmd, final String commandLabel, final String[] args) {
        if (commandLabel.equalsIgnoreCase("gianttree") || commandLabel.equalsIgnoreCase("gtree") || commandLabel.equalsIgnoreCase("gt")) {
            if (commandSender instanceof Player) {
                this.handlerCommands.command((Player) commandSender, args);
            } else {
                getLogger().warning("You cannot execute commands from console");
            }
            return true;
        }
        return false;
    }

    /**
     * Plugin object version of GiantTrees
     *
     * @return GiantTrees plugin object
     */
    public static Plugin getPlugin() {
        return instance;
    }

    /**
     * Add an info message to the log
     *
     * @param content what to be sent
     */
    public static void logInfo(final String content) {
        instance.getLogger().log(Level.INFO, content);
    }

    /**
     * Add a warning message to the log
     *
     * @param content what to be sent
     */
    public static void logWarning(String content) {
        instance.getLogger().log(Level.WARNING, content);
    }

    /**
     * Add a error message to the log
     *
     * @param content what to be sent
     */
    public static void logError(final String content) {
        instance.getSLF4JLogger().error(content);
    }

    /**
     * Add a error message to the log
     *
     * @param content what to be sent
     * @param e exception
     */
    public static void logError(final String content, final Exception e) {
        instance.getSLF4JLogger().error(content, e);
    }

    /**
     * Version of GiantTrees running
     *
     * @return GiantTrees version
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Checks if a user has permission to do something
     *
     * @param player player in question
     * @param perm permission in question
     * @return true or false
     */
    public static boolean checkPermission(final Player player, final String perm) {
        return player.hasPermission("gianttrees." + perm);
    }

    /**
     * Creates world folders if they don't exist
     *
     * @param worlds world list
     */
    public void load(final List<World> worlds) {
        final List<String> worldsName = new ArrayList<>();
        worlds.forEach((world) -> {
            worldsName.add(world.getName());
        });
        FILE_MANAGER.onLoad(worldsName);
    }
}
