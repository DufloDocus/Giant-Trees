package com.connormahaffey.GiantTrees;

import com.connormahaffey.GiantTrees.Generator.MaterialsTracker;
import com.connormahaffey.GiantTrees.Infos.TreeInfos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Saves a tree's undo data to file
 */
public class Save extends Runner implements FileManagement {

    private final MaterialsTracker materialsTracker;
    private final TreeInfos treeInfos;

    public Save(final MaterialsTracker materialsTracker, final TreeInfos treeInfos) {
        this.materialsTracker = materialsTracker;
        this.treeInfos = treeInfos;
    }

    @Override
    public void doJob() {
        final String woldName = this.treeInfos.getWorldName();
        final int id = getCurrentTreeFile(FILE_MANAGER.getCurrentTreeIdFilePath(woldName)) + 1;
        final String treesDataFile = FILE_MANAGER.getTreesFilePath(woldName);
        List<FileDbTreesWrapper> treesData = null;
        if (FILE_MANAGER.pathExists(treesDataFile)) {
            final String fileContent = FILE_MANAGER.read(treesDataFile);
            try {
                treesData = new ArrayList<>(Arrays.asList(GSON.fromJson(fileContent, FileDbTreesWrapper[].class)));
            } catch (final Exception e) {
                GiantTrees.logError("Probleme while parsing the file, will generate new file", e);
            }
        }
        if (treesData == null) {
            treesData = new ArrayList<>();
        }

        final Location treeLocation = treeInfos.getLocation();
        final FileDbTreesWrapper treeWrapper = new FileDbTreesWrapper(id, this.treeInfos.getPlayerName(), (int) treeLocation.getX(), (int) treeLocation.getY(), (int) treeLocation.getZ());
        treesData.add(treeWrapper);
        final String newTreesData = GSON.toJson(treesData.toArray());
        FILE_MANAGER.write(newTreesData, FILE_MANAGER.getTreesFilePath(woldName));

        final ArrayList<Material> materialList = materialsTracker.getOldTypeList();
        final ArrayList<Location> locationList = getLocations(materialsTracker.getBlockList());
        final String[] treeFile = new String[locationList.size() * 4];
        int materialSpot = 0;
        for (int i = 0; i < treeFile.length; i += 4) {
            final Location location = locationList.remove(0);
            treeFile[i] = String.valueOf(location.getX());
            treeFile[i + 1] = String.valueOf(location.getY());
            treeFile[i + 2] = String.valueOf(location.getZ());
            treeFile[i + 3] = materialList.get(materialSpot).name();
            materialSpot++;
        }
        FILE_MANAGER.writeOld(treeFile, FILE_MANAGER.getTreeFilePath(woldName, id));
        FILE_MANAGER.writeToArchive(FILE_MANAGER.getTreeZipFilePath(woldName, id), FILE_MANAGER.getTreeFilePath(woldName, id));

        final String[] currentTree = {String.valueOf(id)};
        FILE_MANAGER.writeOld(currentTree, FILE_MANAGER.getCurrentTreeIdFilePath(woldName));
    }

    /**
     * Changes a list of blocks to a list of locations
     *
     * @param blockList The list of blocks
     * @return The list of locations
     */
    private ArrayList<Location> getLocations(final ArrayList<Block> blockList) {
        final ArrayList<Location> locationList = new ArrayList<>();
        for (int i = 0; i < blockList.size(); i++) {
            final Location location = blockList.get(i).getLocation();
            locationList.add(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()));
        }
        return locationList;
    }

    /**
     * Gets the current tree file number
     *
     * @param path Path of the tree file; CurrentTree.dat
     * @return Tree file number
     */
    private int getCurrentTreeFile(final String path) {
        try {
            return Integer.parseInt(FILE_MANAGER.readOld(path)[0]);
        } catch (final Exception e) {
            GiantTrees.logError("Probleme while getting current tree file number", e);
        }
        return 1;
    }
}
