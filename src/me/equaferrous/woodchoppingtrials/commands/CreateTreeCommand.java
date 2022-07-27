package me.equaferrous.woodchoppingtrials.commands;

import me.equaferrous.woodchoppingtrials.trees.TreeManager;
import me.equaferrous.woodchoppingtrials.trees.TreeTier;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateTreeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED +"[x] "+ ChatColor.GRAY +"Command only usable by players.");
            return true;
        }
        if (args.length != 1) {
            commandSender.sendMessage(ChatColor.RED +"[x] "+ ChatColor.GRAY +"Correct usage: /createTree (one | two | ... | ten)");
            return true;
        }

        Player player = (Player) commandSender;
        TreeTier tier;
        try {
            tier = TreeTier.valueOf(args[0].toUpperCase());
        }
        catch (IllegalArgumentException e) {
            commandSender.sendMessage(ChatColor.RED +"[x] "+ ChatColor.GRAY +"Correct usage: /createTree (one | two | ... | ten)");
            return true;
        }

        if (TreeManager.getInstance().createTree(player.getLocation(), tier) != null) {
            commandSender.sendMessage(ChatColor.GREEN +"[✓] "+ ChatColor.GRAY +"Tree created.");
        }
        else {
            commandSender.sendMessage(ChatColor.RED +"[x] "+ ChatColor.GRAY +"Tree already exists.");
        }

        TreeManager.getInstance().createTree(player.getLocation(), tier);
        return true;
    }
}
