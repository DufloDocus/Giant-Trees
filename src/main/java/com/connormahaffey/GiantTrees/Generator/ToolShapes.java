package com.connormahaffey.GiantTrees.Generator;

import org.bukkit.Location;
import org.bukkit.Material;

public class ToolShapes {

    public static void generateLeaves(final Location currentLocation, final MaterialsTracker materialsTracker, final Material logMaterial, final Material leavesMaterial, final boolean upperOnly) {

        materialsTracker.changeBlock(currentLocation, logMaterial, true);

        Location leavesLocation;
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, 2, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);

//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, 1, -1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, 1, -1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, 1, -1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, 1, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, 1, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, 1, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, 1, 1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, 1, 1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, 1, 1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);

        leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, 0, -1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, 0, -1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, 0, -1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, 0, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, 0, 0);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, 0, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, 0, 1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, 0, 1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, 0, 1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);

        if (upperOnly) {
            return;
        }

//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, -1, -1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, -1, -1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, -1, -1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, -1, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, -1, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, -1, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, -1, -1, 1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);
        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, -1, 1);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
//            leavesLocation = GeneratorTree.shiftLocation(currentLocation, 1, -1, 1);
//            materialsTracker.changeBlock(leavesLocation, leavesMaterial);

        leavesLocation = GeneratorTree.shiftLocation(currentLocation, 0, -2, 0);
        materialsTracker.changeBlock(leavesLocation, leavesMaterial);
    }

    public static void generateLeaves(final Location currentLocation, final MaterialsTracker materialsTracker, final Material logMaterial, final Material leavesMaterial) {
        generateLeaves(currentLocation, materialsTracker, logMaterial, leavesMaterial, false);
    }
}
