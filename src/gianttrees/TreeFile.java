package gianttrees;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author Connor Mahaffey
 */
public class TreeFile implements Runnable{

    private String path;
    private int currentTree = 0, treeToRead = 1;
    private ArrayList<String> ownerList;
    private ArrayList<Location> locationList;
    private World world;
    private boolean isRunning = false;
    private Player tempPlayer;

    public TreeFile(World w){
        world = w;
        path = "plugins" + File.separator + "Giant Trees" + File.separator + "Undo Saves" + File.separator + world.getName() + File.separator;
        ownerList = new ArrayList<String>();
        locationList = new ArrayList<Location>();
        getTreeFileInfo();
        createListOfTrees();
    }

    public void writeTreeFile(Player player, Location location, ArrayList<String> blockType, ArrayList<Location> locOfBlock){
        currentTree++;
        File file = new File(path + "Tree" + currentTree + ".dat");
        try{
            ownerList.add(player.getName());
            locationList.add(location);
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write("Owner:" + player.getName());
            out.newLine();
            out.write(String.valueOf(location.getX()));
            out.newLine();
            out.write(String.valueOf(location.getY()));
            out.newLine();
            out.write(String.valueOf(location.getZ()));
            out.newLine();
            do{
                out.write(blockType.get(0));
                out.newLine();
                out.write(String.valueOf(locOfBlock.get(0).getX()));
                out.newLine();
                out.write(String.valueOf(locOfBlock.get(0).getY()));
                out.newLine();
                out.write(String.valueOf(locOfBlock.get(0).getZ()));
                out.newLine();
                blockType.remove(0);
                locOfBlock.remove(0);
            }while(!blockType.isEmpty());
            out.close();
            setTreeFileInfo();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void undo(Player player, Location location, boolean superUser){
        int spot = getTreeMatch(location);
        if(spot != -1){
            String user = ownerList.get(spot);
            if(player.getName().equalsIgnoreCase(user) || superUser){
                if(!isRunning){
                    try{
                        tempPlayer = player;
                        treeToRead = spot + 1;
                        Thread t = new Thread(this);
                        t.start();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED + "Sorry, only one tree can be undone at once!");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "You don't have permission to undo this tree!");
            }
        }
        else{
            player.sendMessage(ChatColor.RED + "No tree to undo within 15 blocks of this location!");
        }
        
    }

    private void createListOfTrees(){
        double x, y, z;
        for(int i = 1; i <= currentTree; i++){
            File file = new File(path + "Tree" + i + ".dat");
            try{
                BufferedReader in = new BufferedReader(new FileReader(file));
                ownerList.add(in.readLine().replace("Owner:", ""));
                x = Double.parseDouble(in.readLine());
                y = Double.parseDouble(in.readLine());
                z = Double.parseDouble(in.readLine());
                locationList.add(new Location(world, x, y, z));
                in.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private int getTreeMatch(Location loc){
        double closest = 100;
        double x, y, z, average;
        double[] close = {100, 100, 100};
        int treeMatch = 0;
        for(int i = 0; i < currentTree; i++){
            //change to closest average
            x = Math.abs(locationList.get(i).getX() - loc.getX());
            y = Math.abs(locationList.get(i).getY() - loc.getY());
            z = Math.abs(locationList.get(i).getZ() - loc.getZ());
            average = (x + y + z) / 3;
            if(average < closest && !ownerList.get(i).equalsIgnoreCase("IS UNDONE")){
                closest = average;
                close[0] = x;
                close[1] = y;
                close[2] = z;
                treeMatch = i;
            }
        }

        if(close[0] > 25 || close[1] > 25 || close[2] > 25){
            return -1;
        }
        else{
            return treeMatch;
        }
    }

    private void getTreeFileInfo(){
        File file = new File(path + "Trees.dat");
        if(!file.exists()){
            setTreeFileInfo();
        }
        String s;
        try{
            BufferedReader in  = new BufferedReader(new FileReader(file));
            s = in.readLine();
            currentTree = Integer.parseInt(s);
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setTreeFileInfo(){
        File dir = new File(path);
        dir.mkdirs();
        File file = new File(path + "Trees.dat");
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(String.valueOf(currentTree));
            out.newLine();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        isRunning = true;
        tempPlayer.sendMessage(ChatColor.GREEN + "Undo started...");
        readTreeFile();
        setTreeFileGone();
        tempPlayer.sendMessage(ChatColor.GREEN + "Undo finished!");
        isRunning = false;
    }

    private void readTreeFile(){
        File file = new File(path + "Tree" + treeToRead + ".dat");
        String[] s = new String[4];
        int type;
        double x, y, z;
        Main.locUndoQueue.clear();
        Main.matUndoQueue.clear();
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            //first four are unimportant
            in.readLine();
            in.readLine();
            in.readLine();
            in.readLine();
            s[0] = in.readLine();
            do{
                s[1] = in.readLine();
                s[2] = in.readLine();
                s[3] = in.readLine();
                type = Integer.parseInt(s[0]);
                x = Double.parseDouble(s[1]);
                y = Double.parseDouble(s[2]);
                z = Double.parseDouble(s[3]);
                Main.locUndoQueue.add(new Location(world, x, y, z));
                Main.matUndoQueue.add(Material.getMaterial(type));
                s[0] = in.readLine();
            }while(s[0] != null);
            in.close();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new TreeBuilder(true), 10);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setTreeFileGone(){
        int spot = treeToRead - 1;
        File file = new File(path + "Tree" + treeToRead + ".dat");
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write("Owner:IS UNDONE");
            out.newLine();
            out.write(String.valueOf(locationList.get(spot).getX()));
            out.newLine();
            out.write(String.valueOf(locationList.get(spot).getY()));
            out.newLine();
            out.write(String.valueOf(locationList.get(spot).getZ()));
            out.newLine();
            out.close();
            ownerList.set(spot, "IS UNDONE");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getTreeWorldName(){
        return world.getName();
    }
}
