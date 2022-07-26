package me.equaferrous.woodchoppingtrials.trees;

import me.equaferrous.woodchoppingtrials.WoodChoppingTrials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.configuration.file.FileConfiguration;

public enum TreeTier {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN;

    // ---------------------------------------------------

    private TreeType treeType;
    private Material saplingType;
    private Material logType;
    private int growTime;

    // --------------------------------------------------

    TreeTier() {
    }

    // ---------------------------------------------------

    public TreeType getTreeType() {
        return treeType;
    }

    public Material getSaplingType() {
        return saplingType;
    }

    public Material getLogType() {
        return logType;
    }

    public int getGrowTime() {
        return growTime;
    }

    public static void setupConfig(FileConfiguration config) {
        for (TreeTier tier : TreeTier.values()) {
            String path = "treeTier."+ tier.name();
            config.addDefault(path +".treeType", "TREE");
            config.addDefault(path +".saplingType", "OAK_SAPLING");
            config.addDefault(path +".logType", "OAK_LOG");
            config.addDefault(path +".growTime", 100);
        }
    }

    public static void setupValues(FileConfiguration config) {
        for (TreeTier tier : TreeTier.values()) {
            String path = "treeTier."+ tier.name();
            tier.treeType = TreeType.valueOf(config.getString(path +".treeType").toUpperCase());
            tier.saplingType = Material.valueOf(config.getString(path +".saplingType").toUpperCase());
            tier.logType = Material.valueOf(config.getString(path +".logType").toUpperCase());
            tier.growTime = config.getInt(path +".growTime");
        }
    }
}
