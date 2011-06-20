package gianttrees;

import com.nijiko.permissions.PermissionHandler;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author Connor Mahaffey
 */
public class DoCommand {

    private CreateTree CT;
    private Settings S;
    private ArrayList<TreeFile> TF;
    private Server SE;

    public DoCommand(){
        S = new Settings();
    }

    public void doCommand(Player player, String[] args){
        if(args.length == 0){
            help(player);
        }
        else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")){
                help(player);
            }
            else if(args[0].equalsIgnoreCase("about")){
                player.sendMessage(ChatColor.GREEN + "Giant Trees!");
                player.sendMessage(ChatColor.GREEN + "Created By Connor Mahaffey");
            }
            else if(args[0].equalsIgnoreCase("reload")){
                if(S.checkPermission(player, "gianttrees.reload")){
                    S.loadSettings();
                    player.sendMessage(ChatColor.GREEN + "Config reloaded!");
                    createTreeFiles(player);
                }
                else{
                    player.sendMessage(ChatColor.RED + "You do not have permission to reload the settings!");
                }
            }
            else if(args[0].equalsIgnoreCase("undo")){
                String s;
                for(int i = 0; i < TF.size(); i++){
                    s = TF.get(i).getTreeWorldName();
                    if(s.equalsIgnoreCase(player.getWorld().getName())){
                        if(S.checkPermission(player, "gianttrees.undoall")){
                            TF.get(i).undo(player, player.getLocation(), true);
                        }
                        else{
                            TF.get(i).undo(player, player.getLocation(), false);
                        }

                    }
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Incorrect command parameter: " + args[0]);
                player.sendMessage(ChatColor.RED + "Please see /gt help for correct usage!");
            }
        }
        else if(args.length >= 2){
            MetaData M = getMetaData(args, player);
            try{
                int height = Integer.parseInt(args[0]);
                int width = Integer.parseInt(args[1]);
                int density = getDensity(args);
                if(height > 3 * width + 4 && player.getLocation().getY() + height < 128){
                    if(height > S.getMaxTreeHeight() && !S.checkPermission(player, "gianttrees.nolimit")){
                        player.sendMessage(ChatColor.RED + "You do not have permission to build a tree that tall!");
                    }
                    else if(width < 4){
                        player.sendMessage(ChatColor.RED + "You cannot build a tree that small!");
                    }
                    else if(width > S.getMaxTreeWidth() && !S.checkPermission(player, "gianttrees.nolimit")){
                        player.sendMessage(ChatColor.RED + "You do not have permission to build a tree that wide!");
                    }
                    else if(!M.isAllowed()){
                        player.sendMessage(ChatColor.RED + "You do not have permission to build custom block trees!");
                    }
                    else if(density == -1){
                        player.sendMessage(ChatColor.RED + "Invalid density!");
                    }
                    else{
                        height = height - width * 3 - 4;
                        if(!CT.isRunning()){
                            CT.createTree(player, player.getLocation(), height, width, density, M);
                        }
                        else{
                            player.sendMessage(ChatColor.GREEN + "Your tree has been added to the cache!");
                            CT.addToCache(new Tree(player, player.getLocation(), height, width, density, M));
                        }
                    }
                }
                else{
                    if(player.getLocation().getY() + height >= 128){
                        player.sendMessage(ChatColor.RED + "Not enough space to make that tree here!");
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "That height is too small for that width!");
                    }
                }
            }catch(Exception e){
                player.sendMessage(ChatColor.RED + "Invalid tree size!");
            }
        }
    }

    private MetaData getMetaData(String[] args, Player player){
        int[] meta = new int[4];
        boolean allowed = true;
        meta[0] = -1;
        meta[1] = -1;
        //success type: -1 = not successful, -2 = meta data, -3 = block type
        meta[2] = -1;
        meta[3] = -1;
        try{
            String treeType = args[2];
            String leafType = args[3];
            if(treeType.equalsIgnoreCase("oak") || treeType.equalsIgnoreCase("normal")){
                meta[0] = 0;
                meta[2] = -2;
            }
            else if(treeType.equalsIgnoreCase("spruce")){
                meta[0] = 1;
                meta[2] = -2;
            }
            else if(treeType.equalsIgnoreCase("birch")){
                meta[0] = 2;
                meta[2] = -2;
            }
            else{
                if(S.checkPermission(player, "gianttrees.custom")){
                    meta[0] = getNum(treeType);
                    if(meta[0] != -1){
                        meta[2] = -3;
                    }
                }
                else{
                    meta[0] = -1;
                    meta[1] = -1;
                    meta[2] = -1;
                    meta[3] = -1;
                    allowed = false;
                } 
            }

            if(leafType.equalsIgnoreCase("oak") || leafType.equalsIgnoreCase("normal")){
                meta[1] = 0;
                meta[3] = -2;
            }
            else if(leafType.equalsIgnoreCase("spruce")){
                meta[1] = 1;
                meta[3] = -2;
            }
            else if(leafType.equalsIgnoreCase("birch")){
                meta[1] = 2;
                meta[3] = -2;
            }
            else{
                if(S.checkPermission(player, "gianttrees.custom")){
                    meta[1] = getNum(leafType);
                    if(meta[1] != -1){
                        meta[3] = -3;
                    }
                }
                else{
                    meta[0] = -1;
                    meta[1] = -1;
                    meta[2] = -1;
                    meta[3] = -1;
                    allowed = false;
                }
            }
        }catch(Exception e){
        }

        return new MetaData(meta, allowed);
    }

    private int getNum(String num){
        try{
            int x = Integer.parseInt(num);
            if(x >= 0 && x <= 96){
                return x;
            }
            else{
                return -1;
            }
        }catch(Exception e){
            return -1;
        }
    }

    private int getDensity(String[] args){
        int density = 20;
        if(args.length == 5){
            try{
                density = Integer.parseInt(args[4]);
            }catch(Exception e){
                density = -1;
            }
        }

        if(density > 30 || density < 0){
            density = -1;
        }

        return density;
    }

    private void help(Player player){
        player.sendMessage(ChatColor.GREEN + "Command: /gt, /gtree, or /gianttree");
        player.sendMessage(ChatColor.GREEN + "Undo a Tree: /gt undo  - while standing near the tree");
        player.sendMessage(ChatColor.GREEN + "Reload Settings: /gt reload");
        player.sendMessage(ChatColor.GREEN + "Make a Tree: /gt <height> <width> <log> <leaf> <density>");
        player.sendMessage(ChatColor.GREEN + "----------------------------------------------------");
        player.sendMessage(ChatColor.GREEN + "<width> -  must be greater than 3; odd-sized widths ");
        player.sendMessage(ChatColor.GREEN + "will be moved up by 1 size (ex. 5 -> 6).");
        player.sendMessage(ChatColor.GREEN + "<height> -  must be at least 4 larger than 3 times the width,");
        player.sendMessage(ChatColor.GREEN + "and may not exceed the height of the map. The height includes");
        player.sendMessage(ChatColor.GREEN + "the trunk and treetop.");
        player.sendMessage(ChatColor.GREEN + "<log> and <leaf> can be block id's or a log/leaf type");
        player.sendMessage(ChatColor.GREEN + "Ex. birch, spruce, or oak");
        player.sendMessage(ChatColor.GREEN + "<density> - How dense leaf coverage is (0-30) - 20 is normal");

    }

    public void setServer(Server ser){
        SE = ser;
    }

    public void createTreeFiles(Player p){
        if(CT == null){
            List<World> w = SE.getWorlds();
            TF = new ArrayList<TreeFile>();
            for(int i = 0; i < w.size(); i++){
                TF.add(new TreeFile(w.get(i)));
            }
            CT = new CreateTree(TF);
        }
        else{
            if(CT.isRunning()){
                p.sendMessage(ChatColor.RED + "Cannot reload Undo files when a tree is being built!");
            }
            else{
                List<World> w = SE.getWorlds();
                TF = new ArrayList<TreeFile>();
                for(int i = 0; i < w.size(); i++){
                    TF.add(new TreeFile(w.get(i)));
                }
                CT = new CreateTree(TF);
                p.sendMessage(ChatColor.GREEN + "Undo files reloaded!");
            }
        }
        
    }

    public void setPermissions(boolean pi, PermissionHandler ph){
        S.setPermissions(pi, ph);
    }
}
