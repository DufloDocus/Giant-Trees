package com.connormahaffey.GiantTrees.Generator;

import com.connormahaffey.GiantTrees.GiantTrees;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Keeps track of the blocks changed and what they are changed to.
 *
 * Does not actually change the blocks
 */
public class MaterialsTracker {

    private final ArrayList<Block> blockList;
    private final ArrayList<Material> oldTypeList;
    private final ArrayList<Material> newTypeList;

    public MaterialsTracker() {
        this.blockList = new ArrayList<>();
        this.oldTypeList = new ArrayList<>();
        this.newTypeList = new ArrayList<>();
    }

    /**
     * ArrayList of blocks changed
     *
     * @return blocks changed
     */
    public ArrayList<Block> getBlockList() {
        return this.blockList;
    }

    /**
     * ArrayList of what material blocks used to be
     *
     * @return blocks changed
     */
    public ArrayList<Material> getOldTypeList() {
        return this.oldTypeList;
    }

    /**
     * ArrayList of what blocks will be
     *
     * @return material list
     */
    public ArrayList<Material> getNewTypeList() {
        return this.newTypeList;
    }

    /**
     * Changes the block/adds the block to the blocklist and hashset Will not
     * add blocks already in the hashset
     *
     * @param blockLocation location of the block
     * @param material material to change the block to (adds to material list)
     * @param force force change block
     */
    public void changeBlock(final Location blockLocation, final Material material, final boolean force) {
        try {
            final Block block = blockLocation.getBlock();
            boolean shouldAdd = false;
            if (force) {
                if (this.blockList.contains(block)) {
                    final int i = this.blockList.indexOf(block);
                    this.newTypeList.set(i, material);
                } else {
                    shouldAdd = true;
                }
            } else if (!this.blockList.contains(block)) {
                shouldAdd = true;
            }
            if (shouldAdd) {
                this.blockList.add(block);
                this.oldTypeList.add(block.getType());
                this.newTypeList.add(material);
            }
        } catch (Exception e) {
            GiantTrees.logError("Problem while changing block!", e);
        }
    }

    /**
     * Changes the block/adds the block to the blocklist and hashset Will not
     * add blocks already in the hashset
     *
     * @param blockLocation location of the block
     * @param material material to change the block to (adds to material list)
     */
    public void changeBlock(final Location blockLocation, final Material material) {
        changeBlock(blockLocation, material, false);
    }
}
