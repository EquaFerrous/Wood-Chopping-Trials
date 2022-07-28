package me.equaferrous.woodchoppingtrials.utility;

import me.equaferrous.woodchoppingtrials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageSystem {

    // ------------------------------------

    private MessageSystem() {

    }

    // ------------------------------------

    public static void message(CommandSender recipient, String message) {
        recipient.sendMessage(getPluginPrefix() + message);
    }

    public static void log(String message) {
        Bukkit.getLogger().info(getPluginPrefix() + message );
    }

    public static void positiveMessage(CommandSender recipient, String message) {
        recipient.sendMessage(ChatColor.GREEN +"[✓] "+ ChatColor.GRAY + message);
    }

    public static void negativeMessage(CommandSender recipient, String message) {
        recipient.sendMessage(ChatColor.RED +"[x] "+ ChatColor.GRAY + message);
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(getPluginPrefix() + message);
    }

    public static void opBroadcast(String message) {
        Bukkit.broadcast(getPluginPrefix() + message, "gameAdmin");
    }

    public static String getPluginPrefix() {
        return ChatColor.LIGHT_PURPLE +"["+ Main.getPlugin().getName() +"] "+ ChatColor.GRAY;
    }
}
