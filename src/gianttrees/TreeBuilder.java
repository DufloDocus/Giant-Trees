package gianttrees;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 *
 * @author nisovin and Connor Mahaffey
 */

/*
 * This class was originally written by nisoven with several modifications
 */

public class TreeBuilder implements Runnable {
    
    private boolean undo;

    public TreeBuilder(boolean u){
        undo = u;
    }

    @Override
    public void run() {
        if(undo){
            while (Main.locUndoQueue.size() > 0 && Main.matUndoQueue.size() > 0) {
                Location loc = Main.locUndoQueue.remove();
                Material mat = Main.matUndoQueue.remove();
                Block block = loc.getBlock();
                block.setType(mat);
            }
        }
        else{
            while (Main.locQueue.size() > 0 && Main.matQueue.size() > 0) {
                Location loc = Main.locQueue.remove();
                Material mat = Main.matQueue.remove();
                MetaData meta = Main.metaData;
                Block block = loc.getBlock();
                if(mat.getId() == 17){
                    mat = meta.getTreeMaterial();
                    block.setType(mat);
                    if(meta.isTreeMeta()){
                        block.setData((byte)meta.getTreeMeta());
                    }
                }
                else{
                    mat = meta.getLeafMaterial();
                    block.setType(mat);
                    if(meta.isLeafMeta()){
                        block.setData((byte)meta.getLeafMeta());
                    }
                }
            }
        }
    }
}
