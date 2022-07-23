package me.equaferrous.woodchoppingtrials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class WoodChoppingTrials extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + this.getName() + " Enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + this.getName() + " Disabled");
    }
}
