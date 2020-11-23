package com.connormahaffey.GiantTrees;

import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlocksChanger extends Runner {

    private final Map<Block, Material> blocksToChange;

    public BlocksChanger(final Map<Block, Material> blocksToChange) {
        this.blocksToChange = blocksToChange;
    }

    @Override
    public void doJob() {
        System.out.println("BlockChanger doJob started");
        this.blocksToChange.forEach((block, material) -> {
            block.setType(material);
        });
        System.out.println("BlockChanger  doJob ended");
    }

}
