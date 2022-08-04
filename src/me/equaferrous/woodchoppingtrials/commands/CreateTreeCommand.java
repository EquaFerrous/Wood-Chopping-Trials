package me.equaferrous.woodchoppingtrials.commands;

import me.equaferrous.woodchoppingtrials.trees.TreeManager;
import me.equaferrous.woodchoppingtrials.trees.TreeTier;
import me.equaferrous.woodchoppingtrials.utility.MessageSystem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateTreeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            MessageSystem.negativeMessage(commandSender, "Command only usable by players.");
            return true;
        }
        if (args.length != 1 && args.length != 4) {
            throwInvalidCommand(commandSender);
            return true;
        }

        Player player = (Player) commandSender;
        TreeTier tier;
        try {
            tier = TreeTier.valueOf(args[0].toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throwInvalidCommand(commandSender);
            return true;
        }

        Location location;
        if (args.length == 1) {
            location = player.getLocation();
        }
        else {
            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);
            }
            catch (NumberFormatException e) {
                throwInvalidCommand(commandSender);
                return true;
            }

            location = new Location(player.getWorld(), x, y, z);
        }

        executeCommand(location, tier, player);

        return true;
    }

    // ------------------------------------------

    private void executeCommand(Location location, TreeTier tier, CommandSender sender) {
        if (TreeManager.getInstance().createTree(location, tier) != null) {
            String blockPos = location.getBlockX() +" "+ location.getBlockY() +" "+ location.getBlockZ();
            MessageSystem.positiveMessage(sender, "Tier "+ tier +" tree created at "+ blockPos +".");
        }
        else {
            MessageSystem.negativeMessage(sender, "Tree already exists in this location.");
        }
    }

    private void throwInvalidCommand(CommandSender sender) {
        MessageSystem.negativeMessage(sender, "Correct usage: "+ ChatColor.LIGHT_PURPLE +"/createTree (one | two | ... | ten) [<x> <y> <z>]");
    }
}
