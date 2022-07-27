package me.equaferrous.woodchoppingtrials.utility;

import me.equaferrous.woodchoppingtrials.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    // -------------------------------------

    private ConfigManager() {

    }

    // -------------------------------------

    public static FileConfiguration getConfig(String configName) {
        String fileName = configName +".yml";
        File file = new File(Main.getPlugin().getDataFolder(), fileName);
        if (!file.exists()) {
            Main.getPlugin().saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void saveConfig(FileConfiguration config, String configName) {
        String fileName = configName +".yml";
        File file = new File(Main.getPlugin().getDataFolder(), fileName);
        if (!file.exists()) {
            Main.getPlugin().saveResource(fileName, false);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetConfig(String configName) {
        String fileName = configName +".yml";
        Main.getPlugin().saveResource(fileName, true);
    }

}
