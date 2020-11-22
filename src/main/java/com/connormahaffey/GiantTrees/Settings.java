package com.connormahaffey.GiantTrees;

import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;

/**
 * Holds all the settings for the program
 */
public class Settings {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final FileManager FILE_MANAGER = new FileManager();
    private static final String VERSION_SETTINGS = GiantTrees.getVersion() + "_1";
    private static Settings instance = null;

    private String _confVersion;
    private String _confComment;
    private String _confCommentMinAndHeight;
    /**
     * ---Settings commands---
     */
    private int treeHeightMax;
    private int treeWidthMax;
    private int treeBuildDelay;
    private int treeWaitingDelayBetweenBuild;
    /**
     * ---Settings for planting trees---
     */
    private boolean growPlantingAllowed;
    private boolean growRandomChance;
    private int growRandomChancePercent;
    private boolean growNearBlock;
    private String growNearBlockMaterial;
    private int growHeightMin;
    private int growWidthMin;
    private int growHeightMax;
    private int growWidthMax;
    private int growWaitingDelayBetweenTree;
    private String growMessage;
    /**
     * ---Settings for naturally occurring GiantTrees---
     */
    private boolean occurNaturallyAllowed;
    private int occurChancePercent; //per chunck
    private int occurHeightMin;
    private int occurWidthMin;
    private int occurHeightMax;
    private int occurWidthMax;
    private int occurWaitingDelayBetweenTree;
    private String occurOnWorlds;
    private boolean occurShowLocationInConsole;

    private Settings() {
    }

    public static Settings getInstance() {
        if (instance == null) {
            reloadSettings();
        }
        return instance;
    }

    /**
     * Loads all the settings for the program.
     *
     * If the version information doesn't match up or the config is the wrong
     * lenght, the default values are writen.
     */
    public static void reloadSettings() {
        final Settings settings;
        if (!FILE_MANAGER.pathExists("config.json")) {
            GiantTrees.logWarning("Config file doesn't exist, writing default one");
            settings = getDefaultSettings();
        } else {
            final String conf = FILE_MANAGER.read("config.json");
            final Settings userSettings = GSON.fromJson(conf, Settings.class);
            if (userSettings == null) {
                GiantTrees.logError("Config file writen incorrectly! Writing default values!");
                settings = getDefaultSettings();
            } else {
                if (!userSettings._confVersion.equals(VERSION_SETTINGS)) {
                    GiantTrees.logWarning("Old config file! Try to retrieve values!");
                }
                settings = mergeSettings(userSettings);
            }
        }
        writeSettings(settings);
        instance = settings;
    }

    /**
     * Get defaults settings
     */
    private static Settings getDefaultSettings() {
        final Settings settings = new Settings();
        settings._confVersion = VERSION_SETTINGS;
        settings._confComment = "For more information see: https://github.com/CMahaff/Giant-Trees/wiki/Settings";
        settings._confCommentMinAndHeight = "Min Height should not be lower than " + TreeInfos.MIN_HEIGHT + " and min width not lower than " + TreeInfos.MIN_WIDTH;
        settings.treeHeightMax = 50;
        settings.treeWidthMax = 15;
        settings.treeBuildDelay = 0;
        settings.treeWaitingDelayBetweenBuild = 0;
        settings.growPlantingAllowed = false;
        settings.growRandomChance = false;
        settings.growRandomChancePercent = 15;
        settings.growNearBlock = false;
        settings.growNearBlockMaterial = Material.GOLD_BLOCK.name();
        settings.growHeightMin = 10;
        settings.growWidthMin = 4;
        settings.growHeightMax = 34;
        settings.growWidthMax = 6;
        settings.growWaitingDelayBetweenTree = 10;
        settings.growMessage = "Power radiates from the sapling...";
        settings.occurNaturallyAllowed = false;
        settings.occurChancePercent = 5;
        settings.occurHeightMin = 24;
        settings.occurWidthMin = 4;
        settings.occurHeightMax = 34;
        settings.occurWidthMax = 6;
        settings.occurWaitingDelayBetweenTree = 20;
        settings.occurOnWorlds = "wolrd";
        settings.occurShowLocationInConsole = false;
        return settings;
    }

    /**
     * Merge with defaults settings
     */
    private static Settings mergeSettings(final Settings userSetting) {
        final Settings settingsBase = getDefaultSettings();
        settingsBase.treeHeightMax = userSetting.treeHeightMax;
        settingsBase.treeWidthMax = userSetting.treeWidthMax;
        settingsBase.treeBuildDelay = userSetting.treeBuildDelay;
        settingsBase.treeWaitingDelayBetweenBuild = userSetting.treeWaitingDelayBetweenBuild;
        settingsBase.growPlantingAllowed = userSetting.growPlantingAllowed;
        settingsBase.growRandomChance = userSetting.growRandomChance;
        settingsBase.growRandomChancePercent = userSetting.growRandomChancePercent;
        settingsBase.growNearBlock = userSetting.growNearBlock;
        settingsBase.growNearBlockMaterial = userSetting.growNearBlockMaterial;
        settingsBase.growHeightMin = userSetting.growHeightMin;
        settingsBase.growWidthMin = userSetting.growWidthMin;
        settingsBase.growHeightMax = userSetting.growHeightMax;
        settingsBase.growWidthMax = userSetting.growWidthMax;
        settingsBase.growWaitingDelayBetweenTree = userSetting.growWaitingDelayBetweenTree;
        settingsBase.growMessage = userSetting.growMessage;
        settingsBase.occurNaturallyAllowed = userSetting.occurNaturallyAllowed;
        settingsBase.occurChancePercent = userSetting.occurChancePercent;
        settingsBase.occurHeightMin = userSetting.occurHeightMin;
        settingsBase.occurWidthMin = userSetting.occurWidthMin;
        settingsBase.occurHeightMax = userSetting.occurHeightMax;
        settingsBase.occurWidthMax = userSetting.occurWidthMax;
        settingsBase.occurWaitingDelayBetweenTree = userSetting.occurWaitingDelayBetweenTree;
        settingsBase.occurOnWorlds = userSetting.occurOnWorlds;
        settingsBase.occurShowLocationInConsole = userSetting.occurShowLocationInConsole;
        return settingsBase;
    }

    /**
     * Writes settings to file
     */
    private static void writeSettings(final Settings settings) {
        FILE_MANAGER.write(GSON.toJson(settings), "config.json");
    }

    /**
     * Maximum Tree Height Setting
     *
     * @return Maximum Tree Height
     */
    public int getTreeHeightMax() {
        return this.treeHeightMax;
    }

    /**
     * Maximum Tree Width Setting
     *
     * @return Maximum Tree Width
     */
    public int getTreeWidthMax() {
        return this.treeWidthMax;
    }

    /**
     * Tree build delay
     *
     * @return delay
     */
    public int getTreeBuildDelay() {
        return this.treeBuildDelay;
    }

    /**
     * Wait after creation of a tree
     *
     * @return time
     */
    public int getTreeWaitingDelayBetweenBuild() {
        return this.treeWaitingDelayBetweenBuild;
    }

    /**
     * GiantTrees can be planted
     *
     * @return true or false
     */
    public boolean growPlantingAllowed() {
        return this.growPlantingAllowed;
    }

    /**
     * GiantTrees planted can randomly grow
     *
     * @return true or false
     */
    public boolean growRandomChance() {
        return this.growRandomChance;
    }

    /**
     * The percent chance a giant tree will randomly grow
     *
     * @return
     */
    public int getGrowRandomChancePercent() {
        return this.growRandomChancePercent;
    }

    /**
     * GiantTrees will grow if a specified block is near them
     *
     * @return true or false
     */
    public boolean growNearBlock() {
        return this.growNearBlock;
    }

    /**
     * The material that a giant tree must grow near
     *
     * @return Material
     */
    public Material getGrowNearBlockMaterial() {
        return Material.getMaterial(this.growNearBlockMaterial);
    }

    /**
     * The minimum height a tree will grow
     *
     * @return height
     */
    public int getGrowHeightMin() {
        return this.growHeightMin;
    }

    /**
     * The minimum width a tree will grow
     *
     * @return width
     */
    public int getGrowWidthMin() {
        return this.growWidthMin;
    }

    /**
     * The maximum height a tree will grow
     *
     * @return height
     */
    public int getGrowHeightMax() {
        return this.growHeightMax;
    }

    /**
     * The maximum width a tree will grow
     *
     * @return width
     */
    public int getGrowWidthMax() {
        return this.growWidthMax;
    }

    /**
     * Wait time after a tree grows
     *
     * @return time
     */
    public int getGrowWaitingDelayBetweenTree() {
        return this.growWaitingDelayBetweenTree;
    }

    /**
     * Message displayed when a tree is about to grow
     *
     * @return message
     */
    public String getGrowMessage() {
        return this.growMessage;
    }

    /**
     * Trees can naturally spawn in the world
     *
     * @return
     */
    public boolean occurNaturallyAllowed() {
        return this.occurNaturallyAllowed;
    }

    /**
     * The percent chance a tree will spawn in the world
     *
     * @return
     */
    public int getOccurChancePercent() {
        return this.occurChancePercent;
    }

    /**
     * The minimum occur height
     *
     * @return height
     */
    public int getOccurHeightMin() {
        return this.occurHeightMin;
    }

    /**
     * The minimum occur width
     *
     * @return
     */
    public int getOccurWidthMin() {
        return this.occurWidthMin;
    }

    /**
     * The maximum occur height
     *
     * @return height
     */
    public int getOccurHeightMax() {
        return this.occurHeightMax;
    }

    /**
     * The maximum occur width
     *
     * @return width
     */
    public int getOccurWidthMax() {
        return this.occurWidthMax;
    }

    /**
     * Wait time after occuring
     *
     * @return time
     */
    public int getOccurWaitingDelayBetweenTree() {
        return occurWaitingDelayBetweenTree;
    }

    /**
     * List of worlds trees will naturally occur on
     *
     * @return String ArrayList of world names
     */
    public List<String> getOccurOnWorldsList() {
        return Arrays.asList(this.occurOnWorlds.split(","));
    }

    /**
     * Get if the locations of trees being populated should be shown in console
     *
     * @return show or do not show
     */
    public boolean occurShowLocationInConsole() {
        return this.occurShowLocationInConsole;
    }

    /**
     * Bug fixes change the version number, but not the settings.
     *
     * A new version would over-write old settings, this fixes that.
     *
     * @return settings version
     */
    public static String getSettingsVersion() {
        return VERSION_SETTINGS;
    }
}
