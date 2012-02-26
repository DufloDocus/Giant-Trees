package com.connormahaffey.GiantTrees;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitScheduler;

/**
 *
 * @author Connor Mahaffey
 */
public class TreePopulator{

    private CreateTree CT;

    /**
     * Populates the world with trees
     * @param b BukkitScheduler
     */
    public TreePopulator(BukkitScheduler b){
        CT = new CreateTree(b, false, GiantTrees.getSettings().getWaitAfterOccur() * 1000);
        CT.setTreeBuildDelay(0);
    }
    /**
     * Sees if chunk will have a tree, and if so adds it to the cache
     * @param chunk chunk in question
     */
    public void parseChunk(Chunk chunk){
        if(getRandomChance(GiantTrees.getSettings().getPercentChanceOccur())){
            Location loc = getSurface(chunk.getWorld(), chunk.getX() * 16, chunk.getZ() * 16);
            if(loc != null){
                if(GiantTrees.getSettings().showShowXZLocationConsole()){
                    GiantTrees.logInfo(loc.getWorld().getName() + " populated at X=" + loc.getX() + "   Z=" + loc.getZ());
                }
                Tree tree = new Tree(loc,
                        getRandom(GiantTrees.getSettings().getMaximumOccurHeight(), GiantTrees.getSettings().getMinimumOccurHeight()),
                        getRandom(GiantTrees.getSettings().getMaximumOccurWidth(), GiantTrees.getSettings().getMinimumOccurWidth()),
                        "17", "18", "20");
                tree.setResidingChunk(chunk);
                CT.addTree(tree);
            }
        }
    }
    /**
     * Finds the surface of the world, returns null if it can't be built on or gets to the bottom
     * @param world world
     * @param x x location of chunk
     * @param z z location of chunk
     * @return location of surface
     */
    private Location getSurface(World world, int x, int z){
        x = (int)(Math.random() * 16 + x);
        z = (int)(Math.random() * 16 + z);
        Location loc = new Location(world, x, 0, z);
        int id = 0;
        for(int y = world.getMaxHeight() - 1; y > 0; y--){
              loc = new Location(world, x, y, z);
              id = loc.getBlock().getTypeId();
              if(id != 0){
                  if(id == 8 || id == 19 || id == 10 || id == 11 || id == 51 || id == 7){
                      loc = null;
                      break;
                  }
                  else if(id != 17 && id != 18 && id != 81 && id != 83 && id != 37 && id != 38 && id != 39 && id != 40 && id != 86 && id != 31 && id != 32){
                      break;
                  }
              }
              if(y == 1){
                  loc = null;
                  break;
              }
        }

        return loc;
    }
    /**
     * Gets a random number between two ints
     * @param max max amount
     * @param min min amount
     * @return string of random int
     */
    private String getRandom(int max, int min){
        int num = (int) (Math.random() * (max - min) + min);

        return String.valueOf(num);
    }
    /**
     * Gets the random chance something will occur
     * @param percentChance chance it will occur
     * @return if it occured or not
     */
    private boolean getRandomChance(int percentChance){
        double percent = (double)(percentChance) / 100.0;
        if(Math.random() + 0.01 <= percent){
            return true;
        }
        else{
            return false;
        }
    }
}
