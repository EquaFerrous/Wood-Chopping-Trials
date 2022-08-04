package me.equaferrous.woodchoppingtrials.commands;

import me.equaferrous.woodchoppingtrials.trees.TreeManager;
import me.equaferrous.woodchoppingtrials.utility.MessageSystem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveTreeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            MessageSystem.negativeMessage(commandSender, "Command only usable by players.");
            return true;
        }
        if (args.length != 0 && args.length != 3) {
            throwInvalidCommand(commandSender);
            return true;
        }

        Player player = (Player) commandSender;

        Location location;
        if (args.length == 0) {
            location = player.getLocation();
        }
        else {
            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(args[0]);
                y = Double.parseDouble(args[1]);
                z = Double.parseDouble(args[2]);
            }
            catch (NumberFormatException e) {
                throwInvalidCommand(commandSender);
                return true;
            }

            location = new Location(player.getWorld(), x, y, z);
        }

        executeCommand(location, commandSender);

        return true;
    }

    // ----------------------------------------

    private void throwInvalidCommand(CommandSender sender) {
        MessageSystem.negativeMessage(sender, "Correct usage: "+ ChatColor.LIGHT_PURPLE +"/removeTree [<x> <y> <z>]");
    }

    private void executeCommand(Location location, CommandSender sender) {
        if (TreeManager.getInstance().deleteTree(location)) {
            String locationText = location.getBlockX() +" "+ location.getBlockY() +" "+ location.getBlockZ();
            MessageSystem.positiveMessage(sender, "Tree removed at "+ locationText +".");
        }
        else {
            MessageSystem.negativeMessage(sender, "No tree found at this location.");
        }
    }
}
