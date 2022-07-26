package me.equaferrous.woodchoppingtrials;

import me.equaferrous.woodchoppingtrials.trees.Tree;
import me.equaferrous.woodchoppingtrials.trees.TreeTier;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class WoodChoppingTrials extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {

        plugin = this;
        setupDefaultConfig();

        TreeTier.setupValues(this.getConfig());

        World overworld = Bukkit.getWorlds().get(0);
        new Tree(new Location(overworld, 18, -60, 8), TreeTier.ONE);

        Bukkit.getLogger().info(ChatColor.GREEN + this.getName() + " Enabled");
    }

    @Override
    public void onDisable() {
        World overworld = Bukkit.getWorlds().get(0);
        List<Entity> entityList = overworld.getEntities();
        for (Entity entity : entityList) {
            if (entity.getScoreboardTags().contains(getPlugin().getName())) {
                entity.remove();
            }
        }

        Bukkit.getLogger().info(ChatColor.RED + this.getName() + " Disabled");
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
