package me.equaferrous.woodchoppingtrials;

import me.equaferrous.woodchoppingtrials.commands.CreateTreeCommand;
import me.equaferrous.woodchoppingtrials.commands.RemoveTreeCommand;
import me.equaferrous.woodchoppingtrials.events.PreventLeafDropsEvent;
import me.equaferrous.woodchoppingtrials.trees.TreeManager;
import me.equaferrous.woodchoppingtrials.trees.TreeTier;
import me.equaferrous.woodchoppingtrials.utility.MessageSystem;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {

        plugin = this;
        this.getCommand("createTree").setExecutor(new CreateTreeCommand());
        this.getCommand("removeTree").setExecutor(new RemoveTreeCommand());
        getServer().getPluginManager().registerEvents(new PreventLeafDropsEvent(), this);


        setupDefaultConfig();
        TreeTier.setupValues(this.getConfig());

        TreeManager treeManager = TreeManager.getInstance();
        treeManager.loadTreeData();

        MessageSystem.opBroadcast("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        TreeManager.getInstance().saveTreeData();
        TreeManager.getInstance().deleteAllTrees();

        World overworld = Bukkit.getWorlds().get(0);
        List<Entity> entityList = overworld.getEntities();
        for (Entity entity : entityList) {
            if (entity.getScoreboardTags().contains(getPlugin().getName())) {
                entity.remove();
            }
        }

        MessageSystem.opBroadcast("Plugin disabled.");
    }

    // -----------------------------------------------------------

    public static Plugin getPlugin() {
        return plugin;
    }

    // ----------------------------------------------------------

    private void setupDefaultConfig() {
        FileConfiguration config = this.getConfig();

        TreeTier.setupConfig(config);

        config.options().copyDefaults(true);
        this.saveDefaultConfig();
    }


}
