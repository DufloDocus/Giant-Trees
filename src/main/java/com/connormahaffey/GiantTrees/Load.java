package com.connormahaffey.GiantTrees;

import java.util.List;
import org.bukkit.World;

/**
 *
 * @author Connor Mahaffey
 */
public class Load {

    private FileHandler FH;

    /**
     * Creates folders for worlds if they don't exist. Will eventually convert
     * old files to the new format
     */
    public Load() {
        FH = new FileHandler();
    }

    /**
     * Creates world folders if they don't exist
     *
     * @param worlds world list
     */
    public void load(List<World> worlds) {
        String worldName, path;
        String[] treeFile = new String[1];
        do {
            worldName = worlds.remove(0).getName();
            path = "Saves" + FH.separator + worldName + FH.separator + "CurrentTree.dat";
            if (!FH.pathExists(path)) {
                GiantTrees.logInfo("Creating new world folder: " + worldName);
                treeFile[0] = "0";
                FH.write(treeFile, path);
            }
        } while (!worlds.isEmpty());
    }

    /**
     * Loads and starts convert for new file format
     *
     * @param worlds world list
     */
    public void convert() {
        if (FH.pathExists("Undo Saves") && !FH.pathExists("Saves")) {
            GiantTrees.logInfo("Converting old files to new format...");
            GiantTrees.logInfo("DO NOT shutdown the server during this process!");
            Convert C = new Convert();
            C.doConversion();
            GiantTrees.logInfo("Done! Thanks for your patience!");
        }
    }
}
