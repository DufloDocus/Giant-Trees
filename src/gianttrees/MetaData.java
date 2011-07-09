package gianttrees;

import org.bukkit.Material;

/**
 *
 * @author Connor Mahaffey
 */
public class MetaData {
    
    private int[] metaData;
    private boolean allowed, banned;

    public MetaData(int[] md, boolean a, boolean b){
        metaData = md;
        allowed = a;
        banned = b;
        if(metaData[2] == -1){
            metaData[2] = -3;
            metaData[0] = 17;
        }
        if(metaData[3] == -1){
            metaData[3] = -3;
            metaData[1] = 18;
        }
    }

    public boolean isAllowed(){
        return allowed;
    }

    public boolean isBanned(){
        return banned;
    }

    public int[] getMetaData(){
        return metaData;
    }

    public Material getTreeMaterial(){
        if(metaData[2] == -3){
            return Material.getMaterial(metaData[0]);
        }
        else{
            return Material.LOG;
        }
    }

    public Material getLeafMaterial(){
        if(metaData[3] == -3){
            return Material.getMaterial(metaData[1]);
        }
        else{
            return Material.LEAVES;
        }
    }

    public boolean isTreeMeta(){
        if(metaData[2] == -2){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isLeafMeta(){
        if(metaData[3] == -2){
            return true;
        }
        else{
            return false;
        }
    }

    public int getTreeMeta(){
        if(metaData[2] == -2){
            return metaData[0];
        }
        else{
            return -1;
        }
    }

    public int getLeafMeta(){
        if(metaData[3] == -2){
            return metaData[1];
        }
        else{
            return -1;
        }
    }

}
