package me.equaferrous.woodchoppingtrials.commands;

import me.equaferrous.woodchoppingtrials.trees.TreeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveTreeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED +"[x] "+ ChatColor.GRAY +"Command only usable by players.");
            return true;
        }

        Player player = (Player) commandSender;
        if (TreeManager.getInstance().deleteTree(player.getLocation())) {
            commandSender.sendMessage(ChatColor.GREEN +"[✓] "+ ChatColor.GRAY +"Tree removed.");
        }
        else {
            commandSender.sendMessage(ChatColor.RED +"[x] "+ ChatColor.GRAY +"No tree found.");
        }

        return true;
    }
}
