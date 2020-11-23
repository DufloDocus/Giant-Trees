package com.connormahaffey.GiantTrees.Generator;

import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import org.bukkit.Location;

/**
 * Adds branches to the trees
 */
public class GeneratorTreeBranch {

    private final MaterialsTracker materialsTracker;
    private final TreeInfos treeInfos;

    public GeneratorTreeBranch(final MaterialsTracker materialsTracker, final TreeInfos treeInfos) {
        this.materialsTracker = materialsTracker;
        this.treeInfos = treeInfos;
    }

    public void createTreeBranch() {
        final int moveFromCenter = (int) Math.round((this.treeInfos.getWidth() / 2) * 0.5);
        final int moveFromTop = this.treeInfos.getWidth() - 2;
        Location currentLocation;
        currentLocation = GeneratorTree.shiftLocation(this.treeInfos.getLocation(), moveFromCenter, this.treeInfos.getHeight() - moveFromTop, moveFromCenter);
        createSlice(currentLocation, (int) (this.treeInfos.getWidth() * 2), 1, 1, 1);
        currentLocation = GeneratorTree.shiftLocation(this.treeInfos.getLocation(), -moveFromCenter, this.treeInfos.getHeight() - moveFromTop, moveFromCenter);
        createSlice(currentLocation, (int) (this.treeInfos.getWidth() * 2), -1, 1, 1);
        currentLocation = GeneratorTree.shiftLocation(this.treeInfos.getLocation(), -moveFromCenter, this.treeInfos.getHeight() - moveFromTop, -moveFromCenter);
        createSlice(currentLocation, (int) (this.treeInfos.getWidth() * 2), -1, 1, -1);
        currentLocation = GeneratorTree.shiftLocation(this.treeInfos.getLocation(), moveFromCenter, this.treeInfos.getHeight() - moveFromTop, -moveFromCenter);
        createSlice(currentLocation, (int) (this.treeInfos.getWidth() * 2), 1, 1, -1);
    }

    private void createSlice(Location location, final int loopCount, final double xVar, final double yVar, final double zVar) {
        for (int i = 0; i < loopCount; i++) {
            ToolShapes.generateLeaves(location, this.materialsTracker, this.treeInfos.getTreeMaterialsInfos().getLogType(), this.treeInfos.getTreeMaterialsInfos().getLeafType(), true);
            location = GeneratorTree.shiftLocation(location, Math.random() * xVar, Math.random() * yVar, Math.random() * zVar);
        }
    }

}
