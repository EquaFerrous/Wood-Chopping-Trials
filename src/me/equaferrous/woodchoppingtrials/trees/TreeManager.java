package me.equaferrous.woodchoppingtrials.trees;

import me.equaferrous.woodchoppingtrials.utility.ConfigManager;
import me.equaferrous.woodchoppingtrials.Main;
import me.equaferrous.woodchoppingtrials.utility.MessageSystem;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreeManager {

    private static TreeManager instance;

    private final List<Tree> treeList = new ArrayList<>();
    private final HashMap<Block, Tree> treeLocations = new HashMap<>();

    // --------------------------------------

    private TreeManager() {
    }

    // ---------------------------------------

    public static TreeManager getInstance() {
        if (instance == null) {
            instance = new TreeManager();
        }
        return instance;
    }

    // -----------------------------------------

    public Tree createTree(Location location, TreeTier treeTier) {
        if (treeLocations.containsKey(location.getBlock())) {
            return null;
        }

        Tree newTree = new Tree(location, treeTier);
        treeList.add(newTree);
        treeLocations.put(location.getBlock(), newTree);
        return newTree;

    }

    public void deleteTree(Tree tree) {
        if (!treeList.contains(tree)) {
            return;
        }

        treeList.remove(tree);
        treeLocations.remove(tree.getLocation().getBlock());
        tree.delete();
    }

    public boolean deleteTree(Location location) {
        if (!treeLocations.containsKey(location.getBlock())) {
            return false;
        }

        Tree tree = treeLocations.get(location.getBlock());
        treeList.remove(tree);
        treeLocations.remove(location.getBlock());
        tree.delete();
        return true;
    }

    public Tree getTreeAtLocation(Location location) {
        return treeLocations.get(location.getBlock());
    }

    public List<Tree> getTreeList() {
        return treeList;
    }

    public void deleteAllTrees() {
        List<Tree> tempList = new ArrayList<>(treeList);
        for (Tree tree : tempList) {
            deleteTree(tree);
        }
    }

    // ---------------------------------------

    public void saveTreeData() {
        ConfigManager.resetConfig("treeData");
        FileConfiguration config = ConfigManager.getConfig("treeData");

        int treeNum = 1;
        for (Tree tree : treeList) {
            String path = "data."+ treeNum;

            config.set(path +".location", tree.getLocation());
            config.set(path +".tier", tree.getTier().toString());

            treeNum++;
        }
        ConfigManager.saveConfig(config, "treeData");
        MessageSystem.opBroadcast("Tree data saved.");
    }

    public void loadTreeData() {
        FileConfiguration config = ConfigManager.getConfig("treeData");
        if (!config.contains("data")) {
            return;
        }

        for (String key : config.getConfigurationSection("data").getKeys(false)) {
            Location location = config.getLocation("data."+ key +".location");
            TreeTier treeTier = TreeTier.valueOf(config.getString("data."+ key +".tier"));
            createTree(location, treeTier);
        }
        MessageSystem.opBroadcast("Tree data loaded.");
    }

    // ------------------------------

}
