package com.connormahaffey.GiantTrees.Infos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;

/**
 * Holds the material
 */
public class TreeMaterialsInfos {

    private static final List<Material> ACCEPTED_LIST;
    private static final List<Material> DANGEROUS_LIST;
    public static final List<Material> SAPLING;
    public static final Map<Material, Material> SAPLING_LOG;
    public static final Map<Material, Material> SAPLING_LEAVES;
    public static final Material DEFAULT_SAPLING = Material.OAK_SAPLING;
    public static final Material DEFAULT_LOG = Material.OAK_LOG;
    public static final Material DEFAULT_LEAVES = Material.OAK_LEAVES;
    private final Material logType;
    private final Material leafType;

    static {
        DANGEROUS_LIST = new ArrayList();
        DANGEROUS_LIST.add(Material.BEDROCK);
        DANGEROUS_LIST.add(Material.WATER);
        DANGEROUS_LIST.add(Material.LAVA);
        DANGEROUS_LIST.add(Material.SAND);
        DANGEROUS_LIST.add(Material.GRAVEL);
        DANGEROUS_LIST.add(Material.TNT);
        DANGEROUS_LIST.add(Material.SPAWNER);
        DANGEROUS_LIST.add(Material.CHEST);
        ACCEPTED_LIST = new ArrayList();
        ACCEPTED_LIST.addAll(DANGEROUS_LIST);
        ACCEPTED_LIST.add(Material.COBBLESTONE);
        ACCEPTED_LIST.add(Material.MOSSY_COBBLESTONE);
        ACCEPTED_LIST.add(Material.STONE);
        ACCEPTED_LIST.add(Material.SMOOTH_STONE);
        ACCEPTED_LIST.add(Material.STONE_BRICKS);
        ACCEPTED_LIST.add(Material.MOSSY_STONE_BRICKS);
        ACCEPTED_LIST.add(Material.CRACKED_STONE_BRICKS);
        ACCEPTED_LIST.add(Material.CHISELED_STONE_BRICKS);
        ACCEPTED_LIST.add(Material.GRANITE);
        ACCEPTED_LIST.add(Material.POLISHED_GRANITE);
        ACCEPTED_LIST.add(Material.DIORITE);
        ACCEPTED_LIST.add(Material.POLISHED_DIORITE);
        ACCEPTED_LIST.add(Material.ANDESITE);
        ACCEPTED_LIST.add(Material.POLISHED_ANDESITE);
        ACCEPTED_LIST.add(Material.GRASS_BLOCK);
        ACCEPTED_LIST.add(Material.GRASS_PATH);
        ACCEPTED_LIST.add(Material.DIRT);
        ACCEPTED_LIST.add(Material.COARSE_DIRT);
        ACCEPTED_LIST.add(Material.PODZOL);
        ACCEPTED_LIST.add(Material.OAK_LEAVES);
        ACCEPTED_LIST.add(Material.OAK_LOG);
        ACCEPTED_LIST.add(Material.OAK_PLANKS);
        ACCEPTED_LIST.add(Material.OAK_WOOD);
        ACCEPTED_LIST.add(Material.SPRUCE_LEAVES);
        ACCEPTED_LIST.add(Material.SPRUCE_LOG);
        ACCEPTED_LIST.add(Material.SPRUCE_PLANKS);
        ACCEPTED_LIST.add(Material.SPRUCE_WOOD);
        ACCEPTED_LIST.add(Material.BIRCH_LEAVES);
        ACCEPTED_LIST.add(Material.BIRCH_LOG);
        ACCEPTED_LIST.add(Material.BIRCH_PLANKS);
        ACCEPTED_LIST.add(Material.BIRCH_WOOD);
        ACCEPTED_LIST.add(Material.JUNGLE_LEAVES);
        ACCEPTED_LIST.add(Material.JUNGLE_LOG);
        ACCEPTED_LIST.add(Material.JUNGLE_PLANKS);
        ACCEPTED_LIST.add(Material.JUNGLE_WOOD);
        ACCEPTED_LIST.add(Material.ACACIA_LEAVES);
        ACCEPTED_LIST.add(Material.ACACIA_LOG);
        ACCEPTED_LIST.add(Material.ACACIA_PLANKS);
        ACCEPTED_LIST.add(Material.ACACIA_WOOD);
        ACCEPTED_LIST.add(Material.DARK_OAK_LEAVES);
        ACCEPTED_LIST.add(Material.DARK_OAK_LOG);
        ACCEPTED_LIST.add(Material.DARK_OAK_PLANKS);
        ACCEPTED_LIST.add(Material.DARK_OAK_WOOD);
        ACCEPTED_LIST.add(Material.SANDSTONE);
        ACCEPTED_LIST.add(Material.CHISELED_SANDSTONE);
        ACCEPTED_LIST.add(Material.SMOOTH_SANDSTONE);
        ACCEPTED_LIST.add(Material.RED_SAND);
        ACCEPTED_LIST.add(Material.RED_SANDSTONE);
        ACCEPTED_LIST.add(Material.CHISELED_RED_SANDSTONE);
        ACCEPTED_LIST.add(Material.SMOOTH_RED_SANDSTONE);
        ACCEPTED_LIST.add(Material.GOLD_ORE);
        ACCEPTED_LIST.add(Material.GOLD_BLOCK);
        ACCEPTED_LIST.add(Material.IRON_ORE);
        ACCEPTED_LIST.add(Material.IRON_BLOCK);
        ACCEPTED_LIST.add(Material.COAL_ORE);
        ACCEPTED_LIST.add(Material.COAL_BLOCK);
        ACCEPTED_LIST.add(Material.SPONGE);
        ACCEPTED_LIST.add(Material.GLASS);
        ACCEPTED_LIST.add(Material.LAPIS_ORE);
        ACCEPTED_LIST.add(Material.LAPIS_BLOCK);
        ACCEPTED_LIST.add(Material.COBWEB);
        ACCEPTED_LIST.add(Material.WHITE_WOOL);
        ACCEPTED_LIST.add(Material.ORANGE_WOOL);
        ACCEPTED_LIST.add(Material.MAGENTA_WOOL);
        ACCEPTED_LIST.add(Material.LIGHT_BLUE_WOOL);
        ACCEPTED_LIST.add(Material.YELLOW_WOOL);
        ACCEPTED_LIST.add(Material.LIME_WOOL);
        ACCEPTED_LIST.add(Material.PINK_WOOL);
        ACCEPTED_LIST.add(Material.GRAY_WOOL);
        ACCEPTED_LIST.add(Material.LIGHT_GRAY_WOOL);
        ACCEPTED_LIST.add(Material.CYAN_WOOL);
        ACCEPTED_LIST.add(Material.PURPLE_WOOL);
        ACCEPTED_LIST.add(Material.BLUE_WOOL);
        ACCEPTED_LIST.add(Material.BROWN_WOOL);
        ACCEPTED_LIST.add(Material.GREEN_WOOL);
        ACCEPTED_LIST.add(Material.RED_WOOL);
        ACCEPTED_LIST.add(Material.BLACK_WOOL);
        ACCEPTED_LIST.add(Material.BRICKS);
        ACCEPTED_LIST.add(Material.OBSIDIAN);
        ACCEPTED_LIST.add(Material.DIAMOND_ORE);
        ACCEPTED_LIST.add(Material.DIAMOND_BLOCK);
        ACCEPTED_LIST.add(Material.REDSTONE_ORE);
        ACCEPTED_LIST.add(Material.REDSTONE_BLOCK);
        ACCEPTED_LIST.add(Material.SNOW_BLOCK);
        ACCEPTED_LIST.add(Material.ICE);
        ACCEPTED_LIST.add(Material.CLAY);
        ACCEPTED_LIST.add(Material.NETHERRACK);
        ACCEPTED_LIST.add(Material.NETHERITE_BLOCK);
        ACCEPTED_LIST.add(Material.NETHER_BRICKS);
        ACCEPTED_LIST.add(Material.CRACKED_NETHER_BRICKS);
        ACCEPTED_LIST.add(Material.CRACKED_STONE_BRICKS);
        ACCEPTED_LIST.add(Material.BLACKSTONE);
        ACCEPTED_LIST.add(Material.POLISHED_BLACKSTONE);
        ACCEPTED_LIST.add(Material.POLISHED_BLACKSTONE_BRICKS);
        ACCEPTED_LIST.add(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        ACCEPTED_LIST.add(Material.SOUL_SAND);
        ACCEPTED_LIST.add(Material.GLOWSTONE);
        ACCEPTED_LIST.add(Material.WHITE_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.ORANGE_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.MAGENTA_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.LIGHT_BLUE_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.YELLOW_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.LIME_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.PINK_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.GRAY_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.LIGHT_GRAY_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.CYAN_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.PURPLE_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.BLUE_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.BROWN_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.GREEN_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.RED_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.BLACK_STAINED_GLASS);
        ACCEPTED_LIST.add(Material.RED_MUSHROOM_BLOCK);
        ACCEPTED_LIST.add(Material.BROWN_MUSHROOM_BLOCK);
        ACCEPTED_LIST.add(Material.MELON);
        ACCEPTED_LIST.add(Material.PUMPKIN);
        ACCEPTED_LIST.add(Material.MYCELIUM);
        ACCEPTED_LIST.add(Material.EMERALD_ORE);
        ACCEPTED_LIST.add(Material.EMERALD_BLOCK);
        ACCEPTED_LIST.add(Material.QUARTZ_BLOCK);
        ACCEPTED_LIST.add(Material.QUARTZ_BRICKS);
        ACCEPTED_LIST.add(Material.QUARTZ_PILLAR);
        ACCEPTED_LIST.add(Material.CHISELED_QUARTZ_BLOCK);
        ACCEPTED_LIST.add(Material.SLIME_BLOCK);
        ACCEPTED_LIST.add(Material.PRISMARINE);
        ACCEPTED_LIST.add(Material.PRISMARINE_BRICKS);
        ACCEPTED_LIST.add(Material.DARK_PRISMARINE);
        ACCEPTED_LIST.add(Material.SEA_LANTERN);
        ACCEPTED_LIST.add(Material.HAY_BLOCK);
        ACCEPTED_LIST.add(Material.PACKED_ICE);
        ACCEPTED_LIST.add(Material.CHORUS_PLANT);
        ACCEPTED_LIST.add(Material.CHORUS_FLOWER);
        ACCEPTED_LIST.add(Material.PURPUR_BLOCK);
        ACCEPTED_LIST.add(Material.PURPUR_PILLAR);
        ACCEPTED_LIST.add(Material.END_STONE);
        ACCEPTED_LIST.add(Material.END_STONE_BRICKS);
        ACCEPTED_LIST.add(Material.FROSTED_ICE);
        ACCEPTED_LIST.add(Material.WHITE_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.WHITE_TERRACOTTA);
        ACCEPTED_LIST.add(Material.ORANGE_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.ORANGE_TERRACOTTA);
        ACCEPTED_LIST.add(Material.MAGENTA_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.MAGENTA_TERRACOTTA);
        ACCEPTED_LIST.add(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.LIGHT_BLUE_TERRACOTTA);
        ACCEPTED_LIST.add(Material.YELLOW_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.YELLOW_TERRACOTTA);
        ACCEPTED_LIST.add(Material.LIME_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.LIME_TERRACOTTA);
        ACCEPTED_LIST.add(Material.PINK_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.PINK_TERRACOTTA);
        ACCEPTED_LIST.add(Material.GRAY_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.GRAY_TERRACOTTA);
        ACCEPTED_LIST.add(Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.LIGHT_GRAY_TERRACOTTA);
        ACCEPTED_LIST.add(Material.CYAN_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.CYAN_TERRACOTTA);
        ACCEPTED_LIST.add(Material.PURPLE_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.PURPLE_TERRACOTTA);
        ACCEPTED_LIST.add(Material.BLUE_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.BLUE_TERRACOTTA);
        ACCEPTED_LIST.add(Material.BROWN_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.BROWN_TERRACOTTA);
        ACCEPTED_LIST.add(Material.GREEN_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.GREEN_TERRACOTTA);
        ACCEPTED_LIST.add(Material.RED_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.RED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.BLACK_GLAZED_TERRACOTTA);
        ACCEPTED_LIST.add(Material.BLACK_TERRACOTTA);
        ACCEPTED_LIST.add(Material.WHITE_CONCRETE);
        ACCEPTED_LIST.add(Material.ORANGE_CONCRETE);
        ACCEPTED_LIST.add(Material.MAGENTA_CONCRETE);
        ACCEPTED_LIST.add(Material.LIGHT_BLUE_CONCRETE);
        ACCEPTED_LIST.add(Material.YELLOW_CONCRETE);
        ACCEPTED_LIST.add(Material.LIME_CONCRETE);
        ACCEPTED_LIST.add(Material.PINK_CONCRETE);
        ACCEPTED_LIST.add(Material.GRAY_CONCRETE);
        ACCEPTED_LIST.add(Material.LIGHT_GRAY_CONCRETE);
        ACCEPTED_LIST.add(Material.CYAN_CONCRETE);
        ACCEPTED_LIST.add(Material.PURPLE_CONCRETE);
        ACCEPTED_LIST.add(Material.BLUE_CONCRETE);
        ACCEPTED_LIST.add(Material.BROWN_CONCRETE);
        ACCEPTED_LIST.add(Material.GREEN_CONCRETE);
        ACCEPTED_LIST.add(Material.RED_CONCRETE);
        ACCEPTED_LIST.add(Material.BLACK_CONCRETE);
        ACCEPTED_LIST.add(Material.WHITE_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.ORANGE_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.MAGENTA_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.LIGHT_BLUE_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.YELLOW_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.LIME_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.PINK_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.GRAY_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.LIGHT_GRAY_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.CYAN_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.PURPLE_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.BLUE_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.BROWN_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.GREEN_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.RED_CONCRETE_POWDER);
        ACCEPTED_LIST.add(Material.BLACK_CONCRETE_POWDER);

        SAPLING = new ArrayList<>();
        SAPLING.add(Material.OAK_SAPLING);
        SAPLING.add(Material.SPRUCE_SAPLING);
        SAPLING.add(Material.BIRCH_SAPLING);
        SAPLING.add(Material.JUNGLE_SAPLING);
        SAPLING.add(Material.ACACIA_SAPLING);
        SAPLING.add(Material.DARK_OAK_SAPLING);
        SAPLING_LOG = new HashMap<>();
        SAPLING_LOG.put(Material.OAK_SAPLING, Material.OAK_LOG);
        SAPLING_LOG.put(Material.SPRUCE_SAPLING, Material.SPRUCE_LOG);
        SAPLING_LOG.put(Material.BIRCH_SAPLING, Material.BIRCH_LOG);
        SAPLING_LOG.put(Material.JUNGLE_SAPLING, Material.JUNGLE_LOG);
        SAPLING_LOG.put(Material.ACACIA_SAPLING, Material.ACACIA_LOG);
        SAPLING_LOG.put(Material.DARK_OAK_SAPLING, Material.DARK_OAK_LOG);
        SAPLING_LEAVES = new HashMap<>();
        SAPLING_LOG.put(Material.OAK_SAPLING, Material.OAK_LEAVES);
        SAPLING_LOG.put(Material.SPRUCE_SAPLING, Material.SPRUCE_LEAVES);
        SAPLING_LOG.put(Material.BIRCH_SAPLING, Material.BIRCH_LEAVES);
        SAPLING_LOG.put(Material.JUNGLE_SAPLING, Material.JUNGLE_LEAVES);
        SAPLING_LOG.put(Material.ACACIA_SAPLING, Material.ACACIA_LEAVES);
        SAPLING_LOG.put(Material.DARK_OAK_SAPLING, Material.DARK_OAK_LEAVES);

    }

    public TreeMaterialsInfos(final Material logType, final Material leafType) {
        this.logType = logType;
        this.leafType = leafType;
    }

    public Material getLogType() {
        return logType;
    }

    public Material getLeafType() {
        return leafType;
    }

    public boolean isValid() {
        return !(this.logType == null || this.leafType == null);
    }

    /**
     * Checks if the log or leaf type is on the dangerous list
     *
     * @return true or false
     */
    public boolean isDangerous() {
        return DANGEROUS_LIST.contains(this.logType) || DANGEROUS_LIST.contains(this.leafType);
    }

    @Override
    public String toString() {
        return "TreeMaterialsInfos{" + "logType=" + this.logType.name() + ", this.leafType=" + leafType.name() + '}';
    }
}
