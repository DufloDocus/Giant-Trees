package com.connormahaffey.GiantTrees.Infos;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Holds all the data for a tree - height, width, owner, etc.
 */
public class TreeInfos {

    public static final int MIN_HEIGHT = 10;
    public static final int MIN_WIDTH = 2;
    public static final int DEFAULT_DENSITY = 70;
    public static final int MIN_DENSITY = 40;
    public static final int MAX_DENSITY = 100;

    private final int height, width, density;
    private final Player player;
    private final TreeMaterialsInfos treeMaterialsInfos;
    private final Location location;

    private Chunk chunk;
    private boolean residingChunk = false;

    /**
     * Assumes defaults for all other parameters
     *
     * @param player owner
     * @param height height
     * @param width width
     */
    public TreeInfos(final Player player, final int height, final int width) {
        this.player = player;
        this.location = getLocationFromPlayer(player);
        this.height = height;
        this.width = width;
        this.density = DEFAULT_DENSITY;
        this.treeMaterialsInfos = new TreeMaterialsInfos(TreeMaterialsInfos.DEFAULT_LOG, TreeMaterialsInfos.DEFAULT_LEAVES);
    }

    /**
     * Assumes defaults for all other parameters
     *
     * @param player owner
     * @param height height
     * @param width width
     * @param logType log type
     * @param leafType leaf type
     */
    public TreeInfos(final Player player, final int height, final int width, final Material logType, final Material leafType) {
        this.player = player;
        this.location = getLocationFromPlayer(player);
        this.height = height;
        this.width = width;
        this.density = DEFAULT_DENSITY;
        this.treeMaterialsInfos = new TreeMaterialsInfos(logType, leafType);
    }

    /**
     * Full configuration with a player
     *
     * @param player owner
     * @param height height
     * @param width width
     * @param logType log type
     * @param leafType leaf type
     * @param density density
     */
    public TreeInfos(final Player player, final int height, int width, final Material logType, final Material leafType, final int density) {
        this.player = player;
        this.location = getLocationFromPlayer(player);
        this.height = height;
        this.width = width;
        this.density = density;
        this.treeMaterialsInfos = new TreeMaterialsInfos(logType, leafType);
    }

    /**
     * For no player instances, use a location instead This type of tree will
     * return an owner of null and owner name of ""
     *
     * @param location location
     * @param height height
     * @param width width
     * @param logType log type
     * @param leafType leaf type
     * @param density density
     */
    public TreeInfos(final Location location, final int height, final int width, final Material logType, final Material leafType, final int density) {
        this.player = null;
        this.location = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        this.height = height;
        this.width = width;
        this.density = density;
        this.treeMaterialsInfos = new TreeMaterialsInfos(logType, leafType);
    }
    
    private Location getLocationFromPlayer(final Player player) {
        return new Location(player.getWorld(), Math.round(player.getLocation().getX()), Math.round(player.getLocation().getY()), Math.round(player.getLocation().getZ()));
    }

    /**
     * The player who made the tree
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The name of the player who made the tree
     *
     * @return name
     */
    public String getPlayerName() {
        if (this.player == null) {
            return "";
        }
        return this.player.getName();
    }

    /**
     * The location the tree will be centered on
     *
     * @return location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * The name of the world the tree is on
     *
     * @return world name
     */
    public String getWorldName() {
        if (this.player == null) {
            return this.location.getWorld().getName();
        }
        return this.player.getWorld().getName();
    }

    /**
     * Height of the tree
     *
     * @return height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * The width of the tree
     *
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * The dennsity of the tree
     *
     * @return density
     */
    public int getDensity() {
        return this.density;
    }

    /**
     * The tree's TreeInfosMaterials
     *
     * @return TreeInfosMaterials object
     */
    public TreeMaterialsInfos getTreeMaterialsInfos() {
        return this.treeMaterialsInfos;
    }

    /**
     * Chunk this tree resides in.
     *
     * This fixes issues with TreePopulator spawning trees in non-active chunks.
     * This may eventually be needed for all events, but as it is right now,
     * Bukkit seems to know if you are changing blocks in a chunk and not to
     * close it, so its only needed when chunks have just been generated and
     * aren't necessarily open.
     *
     * @param c Chunk this tree resides in
     */
    public void setResidingChunk(Chunk c) {
        this.chunk = c;
        this.residingChunk = true;
    }

    /**
     * Does this tree have a record of which chunk it resides in
     *
     * @return has a chunk reference
     */
    public boolean hasResidingChunk() {
        return this.residingChunk;
    }

    /**
     * Get the chunk this tree resides in
     *
     * @return chunk this tree resides in
     */
    public Chunk getResisidingChunk() {
        return this.chunk;
    }

    /**
     * Is a valid size, density, and log+leaves.
     *
     * @return true or false
     */
    public boolean isValid() {
        return (this.density >= 4 && this.height >= 0 && this.width >= 0 && this.treeMaterialsInfos.isValid());
    }

    /**
     * Is a valid height
     *
     * @return true or false
     */
    public boolean isValidHeight() {
        return this.height >= MIN_HEIGHT;
    }

    /**
     * Is a valid width
     *
     * @return true or false
     */
    public boolean isValidWidth() {
        return this.width >= MIN_WIDTH;
    }

    /**
     * Is a valid width
     *
     * @return true or false
     */
    public boolean isValidDensity() {
        return this.density >= MIN_DENSITY && this.density <= MAX_DENSITY;
    }

    @Override
    public String toString() {
        return "TreeInfos{height=" + this.height + ", width=" + this.width + ", density=" + this.density + ", location=" + this.location.getX() + "/" + this.location.getY() + "/" + this.location.getZ() + ", player=" + this.getPlayerName() + ", treeMaterialsInfos=" + this.treeMaterialsInfos.toString() + "}";
    }

}
