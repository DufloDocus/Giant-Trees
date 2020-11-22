package com.connormahaffey.GiantTrees;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Handles all file operations in the program
 */
public class FileManager {

    public final static String SEPARATOR = File.separator;
    public final static String FOLDER = "plugins" + SEPARATOR + "GiantTrees" + SEPARATOR;

    public FileManager() {
    }

    /**
     * Creates world folders if they don't exist
     *
     * @param worldsName worlds name list
     */
    public void onLoad(final List<String> worldsName) {
        do {
            final String worldName = worldsName.remove(0);
            final String path = getCurrentTreeIdFilePath(worldName);
            if (!pathExists(path)) {
                GiantTrees.logInfo("Creating new world folder: " + worldName);
                final String[] treeFile = new String[1];
                treeFile[0] = "0";
                writeOld(treeFile, path);
            }
        } while (!worldsName.isEmpty());
    }

    public boolean pathExists(final String location) {
        final File file = new File(FOLDER + location);
        return file.exists();
    }

    /**
     * Gets the folders in a given directory
     *
     * @param folder path of the directory
     * @return String array of folders
     */
    public String[] getFolders(final String folder) {
        final String folderFullPath = FOLDER + folder;
        final File currentFolder = new File(folderFullPath);
        final String[] paths = currentFolder.list();
        final ArrayList<String> folders = new ArrayList<>();
        for (final String path : paths) {
            final File temp = new File(folderFullPath + SEPARATOR + path);
            if (temp.isDirectory()) {
                folders.add(path);
            }
        }
        return folders.toArray(new String[0]);
    }

    /**
     * Reads a plain text file
     *
     * @param path path
     * @return String Array of data
     */
    public String[] readOld(final String path) {
        final String fileFullPath = FOLDER + path;
        try {
            final File file = new File(fileFullPath);
            final BufferedReader in = new BufferedReader(new FileReader(file));
            String s = "", full = "";
            do {
                full += s + "\n";
                s = in.readLine();
            } while (s != null);
            in.close();
            return full.substring(1).split("\n");
        } catch (final Exception e) {
            GiantTrees.logError("Cannot read file: " + fileFullPath, e);
        }
        return null;
    }

    public String read(final String path) {
        final String fileFullPath = FOLDER + path;
        try {
            final File file = new File(fileFullPath);
            final BufferedReader in = new BufferedReader(new FileReader(file));
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (final Exception e) {
            GiantTrees.logError("Cannot read file: " + fileFullPath, e);
        }
        return null;
    }

    /**
     * Writes a plain text file
     *
     * @param data String Array of what to write
     * @param path path
     */
    public void writeOld(final String[] data, final String path) {
        final String fileFullPath = FOLDER + path;
        try {
            final File file = new File(fileFullPath);
            final File folderPath = new File(file.getPath().substring(0, file.getPath().lastIndexOf(SEPARATOR)));
            folderPath.mkdirs();
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final String data1 : data) {
                out.write(data1);
                out.newLine();
            }
            out.close();
        } catch (final Exception e) {
            GiantTrees.logError("Cannot write to file: " + fileFullPath, e);
        }
    }

    public void write(final String data, final String path) {
        final String fileFullPath = FOLDER + path;
        try {
            final File file = new File(fileFullPath);
            final File folderPath = new File(file.getPath().substring(0, file.getPath().lastIndexOf(SEPARATOR)));
            folderPath.mkdirs();
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(data);
            out.close();
        } catch (final Exception e) {
            GiantTrees.logError("Cannot write to file: " + fileFullPath, e);
        }
    }

    public void delete(final String path) {
        final String fillFullPath = FOLDER + path;
        final File file = new File(fillFullPath);
        if (!file.delete()) {
            GiantTrees.logWarning("Unable to delete: " + fillFullPath);
        }
    }

    /**
     * Writes a single file to an archive
     *
     * @param zipPath where the zip will be
     * @param filePath where the file is
     */
    public void writeToArchive(final String zipPath, final String filePath) {
        final String zipFullPath = FOLDER + zipPath;
        if (pathExists(zipFullPath)) {
            delete(zipFullPath);
        }
        createZipFile(zipPath, filePath);
    }

    /**
     * Extracts and reads one file from an archive
     *
     * @param path path
     * @return String Array of data
     */
    public String[] readFromArchive(final String path) {
        //Special Thanks: 
        //http://www.exampledepot.com/egs/java.util.zip/GetZip.html
        //http://java.sun.com/developer/technicalArticles/Programming/PerfTuning/
        final String fileFullPath = FOLDER + path;
        try {
            final String worldName = getWorldNameFromPath(path);
            final File zip = new File(fileFullPath);
            final ZipInputStream in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zip)));
            final ZipEntry zippedFile = in.getNextEntry();
            final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(FOLDER + "Saves" + SEPARATOR + worldName + SEPARATOR + zippedFile.getName().replace(".zip", ".dat")));
            final byte[] buffer = new byte[getZipSize(fileFullPath)];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            final String[] data = readOld("Saves" + SEPARATOR + worldName + SEPARATOR + zippedFile.getName().replace(".zip", ".dat"));
            delete("Saves" + SEPARATOR + worldName + SEPARATOR + zippedFile.getName().replace(".zip", ".dat"));
            return data;
        } catch (final Exception e) {
            GiantTrees.logError("Could not extract file from archive: " + fileFullPath, e);
        }
        return null;
    }

    /**
     * Creates a zip file
     *
     * @param zipPath where the zip will be
     * @param filePath where the file is
     */
    private void createZipFile(String zipPath, String filePath) {
        //Special Thanks:
        //http://www.exampledepot.com/egs/java.util.zip/CreateZip.html
        final String zipFullPath = FOLDER + zipPath;
        try {
            final File zip = new File(zipFullPath);
            final File file = new File(FOLDER + filePath);
            final BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zip)));
            out.putNextEntry(new ZipEntry(file.getName()));
            int len;
            byte[] buffer = new byte[(int) file.length()];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
            delete(filePath);
        } catch (final Exception e) {
            GiantTrees.logError("Could not create archive: " + zipFullPath, e);
        }
    }

    /**
     * Gets the world name from a path
     *
     * @param path
     * @return world name
     */
    private String getWorldNameFromPath(final String path) {
        return path.substring(path.lastIndexOf("Saves" + SEPARATOR) + 6, path.lastIndexOf(SEPARATOR));
    }

    /**
     * Gets the size of a zip entry
     *
     * @param pat
     * @return size
     */
    private int getZipSize(final String path) {
        //Special thanks:
        //http://vimalathithen.blogspot.com/2006/06/using-zipentrygetsize.html
        int size = -1;
        try {
            final ZipFile zipfile = new ZipFile(path);
            final Enumeration zipEnum = zipfile.entries();
            while (zipEnum.hasMoreElements()) {
                final ZipEntry zipEntry = (ZipEntry) zipEnum.nextElement();
                size = (int) zipEntry.getSize();
            }
            return size;
        } catch (final Exception e) {
            GiantTrees.logError("Problem getting zip size!", e);
        }
        return size;
    }

    public String getCurrentTreeIdFilePath(final String worldName) {
        return "Saves" + FileManager.SEPARATOR + worldName + FileManager.SEPARATOR + "CurrentTree.dat";
    }

    public String getTreesFilePath(final String worldName) {
        return "Saves" + FileManager.SEPARATOR + worldName + FileManager.SEPARATOR + "Trees.dat";
    }

    public String getTreeZipFilePath(final String worldName, final int id) {
        return "Saves" + FileManager.SEPARATOR + worldName + FileManager.SEPARATOR + "Tree" + id + ".zip";
    }

    public String getTreeFilePath(final String worldName, final int id) {
        return "Saves" + FileManager.SEPARATOR + worldName + FileManager.SEPARATOR + "Tree" + id + ".dat";
    }
}
