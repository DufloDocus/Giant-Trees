package com.connormahaffey.GiantTrees.Generator;

import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Add the base to the trees
 */
public class GeneratorTreeBase {

    private final MaterialsTracker materialsTracker;
    private final TreeInfos treeInfos;

    public GeneratorTreeBase(final MaterialsTracker materialsTracker, final TreeInfos treeInfos) {
        this.materialsTracker = materialsTracker;
        this.treeInfos = treeInfos;
    }

    public void createTreeBase() {
        final Location treeLocation = this.treeInfos.getLocation();
        Location currentLocation;
        currentLocation = GeneratorTree.shiftLocation(treeLocation, 0, 0, 0);
        createSlice(currentLocation, 1, (this.treeInfos.getWidth() / 2) + 3);
        currentLocation = GeneratorTree.shiftLocation(treeLocation, 0, 1, 0);
        createSlice(currentLocation, 1, (this.treeInfos.getWidth() / 2) + 2);
        currentLocation = GeneratorTree.shiftLocation(treeLocation, 0, 2, 0);
        createSlice(currentLocation, 1, (this.treeInfos.getWidth() / 2) + 1);
        currentLocation = GeneratorTree.shiftLocation(treeLocation, 0, 3, 0);
        createSlice(currentLocation, this.treeInfos.getHeight() - 4, (this.treeInfos.getWidth() / 2));
    }

    private void createSlice(final Location location, final int height, final int width) {
        for (int h = 0; h < height; h++) {
            for (int r = (width); r > 0; r--) {
                makeCircule(location, r);
            }
            location.setY(location.getY() + 1);
        }
    }

    private void makeCircule(final Location location, final int r) {
        for (double i = 0.0; i < 360.0; i += 0.1) {
            final double angle = i * Math.PI / 180;
            final int x = (int) Math.round(location.getX() + r * Math.cos(angle));
            final int z = (int) Math.round(location.getZ() + r * Math.sin(angle));
            final Location locationToReplace = new Location(location.getWorld(), x, location.getY(), z);
            final Material material = this.treeInfos.getTreeMaterialsInfos().getLogType();
            this.materialsTracker.changeBlock(locationToReplace, material);
        }
    }
}
