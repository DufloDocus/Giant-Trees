package com.connormahaffey.GiantTrees.Generator;

import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

/**
 * Adds leaves to the trees
 */
public class GeneratorTreeLeaves {

    private final MaterialsTracker materialsTracker;
    private final TreeInfos treeInfos;

    public GeneratorTreeLeaves(final MaterialsTracker materialsTracker, final TreeInfos treeInfos) {
        this.materialsTracker = materialsTracker;
        this.treeInfos = treeInfos;
    }

    public void createTreeLeaves() {
        final Location currentLocation = GeneratorTree.shiftLocation(this.treeInfos.getLocation(), 0, this.treeInfos.getHeight(), 0);
        createTreeLeaves(currentLocation, Math.max(this.treeInfos.getWidth(), 5) * 1.2, this.treeInfos.getDensity());
    }

    private void createTreeLeaves(Location location, final double radius, final int density) {
        final double volume = (4 / 3) * Math.PI * radius * radius * radius;

        final List<Pos> addedPos = new ArrayList<>();

        for (int i = 0; i < ((volume * density) / 100); i++) {
            final Pos pos = getRandomPos(radius);
            if (addedPos.contains(pos)) {
                continue;
            }
            addedPos.add(pos);
            if (pos.Y < (radius * 10) / 100) {
                continue;
            }
            final Location currentLocation = new Location(location.getWorld(), location.getX() + pos.X, (location.getY() + pos.Y) - (radius * 10) / 100, location.getZ() + pos.Z);
            ToolShapes.generateLeaves(currentLocation, this.materialsTracker, this.treeInfos.getTreeMaterialsInfos().getLogType(), this.treeInfos.getTreeMaterialsInfos().getLeafType());
        }
    }

    private Pos getRandomPos(final double radius) {
        final double posMove = radius * 2;
        final double u = Math.random();
        final double v = Math.random();
        final double theta = u * 2.0 * Math.PI;
        final double phi = Math.acos(2.0 * v - 1.0);
        final double r = Math.cbrt(Math.random());
        final double sinTheta = Math.sin(theta);
        final double cosTheta = Math.cos(theta);
        final double sinPhi = Math.sin(phi);
        final double cosPhi = Math.cos(phi);
        final double x = r * sinPhi * cosTheta;
        final double y = r * sinPhi * sinTheta;
        final double z = r * cosPhi;
        return new Pos((int) Math.round((x * posMove)), (int) Math.round((y * posMove) + (posMove * 0.7)), (int) Math.round((z * posMove)));
    }

    private class Pos {

        int X;
        int Y;
        int Z;

        public Pos(int X, int Y, int Z) {
            this.X = X;
            this.Y = Y;
            this.Z = Z;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + this.X;
            hash = 79 * hash + this.Y;
            hash = 79 * hash + this.Z;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pos other = (Pos) obj;
            if (this.X != other.X) {
                return false;
            }
            if (this.Y != other.Y) {
                return false;
            }
            if (this.Z != other.Z) {
                return false;
            }
            return true;
        }

    }
}
