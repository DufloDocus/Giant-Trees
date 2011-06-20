package gianttrees;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Connor Mahaffey
 */
public class Tree {
    
    private Player player;
    private Location location;
    private int height, width, density;
    private MetaData M;

    public Tree(){

    }

    public Tree(Player user, Location loc, int h, int w, int d, MetaData md){
        player = user;
        location = loc;
        height = h;
        width = w;
        density = d;
        M = md;
    }

    public Player getPlayer(){
        return player;
    }

    public Location getLocation(){
        return location;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getDensity(){
        return density;
    }

    public MetaData getMetaData(){
        return M;
    }

}
