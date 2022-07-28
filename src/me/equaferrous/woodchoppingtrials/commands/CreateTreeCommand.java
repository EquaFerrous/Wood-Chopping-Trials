package me.equaferrous.woodchoppingtrials.commands;

import me.equaferrous.woodchoppingtrials.trees.TreeManager;
import me.equaferrous.woodchoppingtrials.trees.TreeTier;
import me.equaferrous.woodchoppingtrials.utility.MessageSystem;
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
        if (args.length != 1) {
            MessageSystem.negativeMessage(commandSender, "Correct usage: /createTree (one | two | ... | ten)");
            return true;
        }

        Player player = (Player) commandSender;
        TreeTier tier;
        try {
            tier = TreeTier.valueOf(args[0].toUpperCase());
        }
        catch (IllegalArgumentException e) {
            MessageSystem.negativeMessage(player, "Correct usage: /createTree (one | two | ... | ten)");
            return true;
        }

        if (TreeManager.getInstance().createTree(player.getLocation(), tier) != null) {
            MessageSystem.positiveMessage(player, "Tree created.");
        }
        else {
            MessageSystem.negativeMessage(player, "Tree already exists.");
        }

        TreeManager.getInstance().createTree(player.getLocation(), tier);
        return true;
    }
}
