package me.equaferrous.woodchoppingtrials.trees;

import me.equaferrous.woodchoppingtrials.utility.ConfigManager;
import me.equaferrous.woodchoppingtrials.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreeManager {

    private static TreeManager instance;

    private final List<Tree> treeList = new ArrayList<>();
    private final HashMap<Location, Tree> treeLocations = new HashMap<>();

    // --------------------------------------

    public TreeManager() {
        if (instance != null) {
            return;
        }

        instance = this;
    }

    // ---------------------------------------

    public static TreeManager getInstance() {
        if (instance == null) {
            new TreeManager();
        }
        return instance;
    }

    // -----------------------------------------

    public Tree createTree(Location location, TreeTier treeTier) {
        if (!treeLocations.containsKey(location)) {
            Tree newTree = new Tree(location, treeTier);
            treeList.add(newTree);
            treeLocations.put(location, newTree);
            return newTree;
        }
        else {
            return null;
        }
    }

    public void deleteTree(Tree tree) {
        if (treeLocations.containsValue(tree)) {
            treeList.remove(tree);
            treeLocations.remove(tree.getLocation());
            tree.delete();
        }
    }

    public void deleteTree(Location treeLocation) {
        if (treeLocations.containsKey(treeLocation)) {
            Tree tree = treeLocations.get(treeLocation);
            treeList.remove(tree);
            treeLocations.remove(treeLocation);
            tree.delete();
        }
    }

    public Tree getTreeAtLocation(Location location) {
        return treeLocations.get(location);
    }

    public List<Tree> getTreeList() {
        return treeList;
    }

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
        Main.postServerMessage("TreeData saved.");
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
        Main.postServerMessage("TreeData loaded.");
    }
}
