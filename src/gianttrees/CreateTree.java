package gianttrees;

import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author Connor Mahaffey
 */
public class CreateTree implements Runnable{

    private int trunkHeight;
    private int trunkWidth;
    private int treeDensity;
    private Player player;
    private Location location;
    private ArrayList<TreeFile> TF;
    private boolean isRunning;
    private ArrayList<Tree> T;
    private ArrayList<String> blockIds;
    private ArrayList<Location> blockLocations;
    private HashSet<Block> locations;
    private MetaData M;

    public CreateTree(ArrayList<TreeFile> t){
        isRunning = false;
        T = new ArrayList<Tree>();
        blockIds = new ArrayList<String>();
        blockLocations = new ArrayList<Location>();
        locations = new HashSet<Block>();
        TF = t;
    }
    
    public void createTree(Player p, Location l, int height, int width, int density, MetaData md){
        player = p;
        location = l;
        trunkHeight = height;
        trunkWidth = width;
        treeDensity = density;
        M = md;
        try{
            Thread t = new Thread(this);
            t.start();
        }catch(Exception e){
            player.sendMessage(ChatColor.RED + "Unable to create tree");
            e.printStackTrace();
        }
    }

    public void run(){
        int tempHeight, tempWidth;
        boolean undoFileExists = false;
        Tree tree;
        isRunning = true;
        String s;
        
        do{
            s = "";
            for(int i = 0; i < TF.size(); i++){
                s = TF.get(i).getTreeWorldName();
                if(s.equalsIgnoreCase(player.getWorld().getName())){
                    undoFileExists = true;
                }
            }
            if(!T.isEmpty()){
                tree = T.get(0);
                player = tree.getPlayer();
                trunkHeight = tree.getHeight();
                trunkWidth = tree.getWidth();
                treeDensity = tree.getDensity();
                location = tree.getLocation();
                M = tree.getMetaData();
                T.remove(0);
                sleep(1000);
            }
            if(undoFileExists){
                blockIds.clear();
                blockLocations.clear();
                locations.clear();
                Main.locQueue.clear();
                Main.matQueue.clear();
                Main.metaData = M;
                player.sendMessage(ChatColor.RED + "Stand Back!");
                sleep(5000);
                //create original trunk
                createTrunk();
                //store for later
                tempHeight = trunkHeight;
                tempWidth = trunkWidth;
                //creat tree base
                trunkHeight = 3;
                trunkWidth += 2;
                createTrunk();
                trunkHeight = 2;
                trunkWidth += 2;
                createTrunk();
                trunkHeight = 1;
                trunkWidth += 2;
                createTrunk();
                //return values to normal
                trunkHeight = tempHeight;
                trunkWidth = tempWidth;
                createBranches();
                createBranches();
                createTop(3, 4, 3);
                createTop(4, 4, 5);
                createTop(5, 4, 4);
                createTop(5, 4, 5);
                createTop(4, 5, 4);
                createTop(4, 3, 4);
                createTop(4, 4, 4);
                //createRandomLogs();
                //createLeaves();
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new TreeBuilder(false), 10);
                s = "";
                for(int i = 0; i < TF.size(); i++){
                    s = TF.get(i).getTreeWorldName();
                    if(s.equalsIgnoreCase(player.getWorld().getName())){
                        TF.get(i).writeTreeFile(player, location, blockIds, blockLocations);
                    }
                }
                player.sendMessage(ChatColor.GREEN + "Your tree is finished!");
            }
            else{
                player.sendMessage(ChatColor.RED + "Please use /gt reload before building a tree here!");
            }
        }while(!T.isEmpty());
        
        isRunning = false;
    }

    private void createTrunk(){
        double innerSpot = 1;
        int trunkWidthTemp = 0;
        for(int i = 0; i < trunkHeight; i++){//does height, Y ways
            trunkWidthTemp = trunkWidth;
            innerSpot = 1;
            do{//does width Z ways
                for(int k = -1 * (trunkWidthTemp / 2); k <= trunkWidthTemp / 2; k++){//does width X ways
                    //this is the part thats off by one
                    innerSpot = (-1 * innerSpot) + 1;
                    changeBlock(location, k, i, innerSpot, Material.LOG);
                    innerSpot = (-1 * innerSpot) + 1;
                    changeBlock(location, k, i, innerSpot, Material.LOG);
                }
                innerSpot++;
                trunkWidthTemp -= 2;
            }while(trunkWidthTemp >= 2);
        }
    }

    private void createBranches(){
        Location topLocation1 = new Location(location.getWorld(), location.getX(), location.getY() + trunkHeight - 2, location.getZ());
        Location topLocation2 = new Location(location.getWorld(), location.getX(), location.getY() + trunkHeight - 2, location.getZ());
        Location topLocation3 = new Location(location.getWorld(), location.getX(), location.getY() + trunkHeight - 2, location.getZ());
        Location topLocation4 = new Location(location.getWorld(), location.getX(), location.getY() + trunkHeight - 2, location.getZ());
        for(int i = 0; i < trunkWidth; i++){
            topLocation1 = changeBlock(topLocation1, getRandomXZ(), getRandomY(), getRandomXZ(), Material.LOG);
            topLocation2 = changeBlock(topLocation2, -1 * getRandomXZ(), getRandomY(), getRandomXZ(), Material.LOG);
            topLocation3 = changeBlock(topLocation3, -1 * getRandomXZ(), getRandomY(), -1 * getRandomXZ(), Material.LOG);
            topLocation4 = changeBlock(topLocation4, getRandomXZ(), getRandomY(), -1 * getRandomXZ(), Material.LOG);
        }
    }

    private double getRandomXZ(){
        return Math.random() * 2;
    }

    private double getRandomY(){
        return Math.random() * 1.5;
    }

    private void createTop(double xRand, double yRand, double zRand){
        Location top = new Location(location.getWorld(), location.getX(), location.getY() + trunkHeight, location.getZ());
        double x, y, z;
        double oX, oY, oZ;
        double upRCornerX, upRCornerZ, upLCornerX, upLCornerZ, botRCornerX, botRCornerZ,
               botLCornerX, botLCornerZ;
        oX = top.getX();
        oY = top.getY();
        oZ = top.getZ();

        upRCornerX = trunkWidth * 2 + oX - trunkWidth;
        upRCornerZ = trunkWidth * 2 + oZ - trunkWidth;
        upLCornerX = -1 * trunkWidth * 2 + oX + trunkWidth;
        upLCornerZ = trunkWidth * 2 + oZ - trunkWidth;
        botRCornerX = trunkWidth * 2 + oX - trunkWidth;
        botRCornerZ = -1 * trunkWidth * 2 + oZ + trunkWidth;
        botLCornerX = -1 * trunkWidth * 2 + oX + trunkWidth;
        botLCornerZ = -1 * trunkWidth * 2 + oZ + trunkWidth;

        for(int i = 0; i < trunkHeight * (((double)treeDensity / 100.0) * trunkHeight) * (trunkWidth * 2 - 4); i++){
            x = Math.random() * 8 - xRand;
            y = Math.random() * 8 - yRand;
            z = Math.random() * 8 - zRand;
            //stops leaves from going outside boundaries
            if(top.getX() + x > trunkWidth * 2 + oX|| top.getX() + x < -1 * trunkWidth * 2 + oX){
                x = -1 * x;
            }
            if(top.getY() + y > trunkWidth * 3 + oY || top.getY() + y < oY){
                y = -1 * y;
            }
            if(top.getZ() + z > trunkWidth * 2 + oZ || top.getZ() + z < -1 * trunkWidth * 2 + oZ){
                z = -1 * z;
            }
            //makes slightly rounded edges on trees
            if(top.getX() + x > upRCornerX && top.getZ() + z > upRCornerZ){
                z -= trunkWidth / 2;
            }
            else if(top.getX() + x < upLCornerX && top.getZ() + z > upLCornerZ){
                x += trunkWidth / 2;
            }
            else if(top.getX() + x > botRCornerX && top.getZ() + z < botRCornerZ){
                x -= trunkWidth / 2;
            }
            else if(top.getX() + x < botLCornerX && top.getZ() + z < botLCornerZ){
                z += trunkWidth / 2;
            }
            //makes tree smaller near the top
            if(top.getY() + y > trunkWidth * 2.3 + oY){
                if(top.getX() + x > trunkWidth * 1.5 + oX){
                    x -= trunkWidth / 2;
                }
                if(top.getX() + x < trunkWidth * 1.5 * -1 + oX){
                    x += trunkWidth / 2;
                }
                if(top.getZ() + z > trunkWidth * 1.5 + oZ){
                    z -= trunkWidth / 2;
                }
                if(top.getZ() + z < trunkWidth * 1.5 * -1 + oZ){
                    z += trunkWidth / 2;
                }
            }
            //do the leaves
            doLeaves(top, x + 1, y, z);
            doLeaves(top, x - 1, y, z);
            doLeaves(top, x, y, z + 1);
            doLeaves(top, x, y, z - 1);
            doLeaves(top, x, y + 1, z);
            doLeaves(top, x, y - 1, z);
            doLeaves(top, x + 1, y, z + 1);
            doLeaves(top, x - 1, y, z + 1);
            doLeaves(top, x + 1, y, z - 1);
            doLeaves(top, x - 1, y, z - 1);
            top = changeBlock(top, x, y, z, Material.LOG);
        }
       
    }

    private void doLeaves(Location loc, double x, double y, double z){
        try{
            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
            Block block = loc2.getBlock();
            if (!locations.contains(block)) {
            	changeBlock(loc2, 0, 0, 0, Material.LEAVES);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private Location changeBlock(Location loc, double shiftX, double shiftY, double shiftZ, Material material){
        Block block;
        int type;
        Location location2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        
        try{
            location2.setX(location2.getX() + shiftX);
            location2.setY(location2.getY() + shiftY);
            location2.setZ(location2.getZ() + shiftZ);
            block = location2.getBlock();
            type = block.getTypeId();
            if((type != M.getTreeMaterial().getId() && type != M.getLeafMaterial().getId()) || type == 0){
                if(!locations.contains(block)){
                    blockLocations.add(location2);
                    blockIds.add(String.valueOf(type));
                    locations.add(block);
                }
            }

            Main.locQueue.add(location2);
            Main.matQueue.add(material);

            return location2;
        }catch(Exception e){
            return location2;
        }
    }

    private void sleep(int time){
        try{
            Thread.sleep(time);
        }catch(Exception e){
            System.err.println("ERROR: Could not sleep!");
        }
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void addToCache(Tree tree){
        T.add(tree);
    }

}
