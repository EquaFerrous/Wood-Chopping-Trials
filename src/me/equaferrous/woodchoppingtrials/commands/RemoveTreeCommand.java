package me.equaferrous.woodchoppingtrials.commands;

import me.equaferrous.woodchoppingtrials.trees.TreeManager;
import me.equaferrous.woodchoppingtrials.utility.MessageSystem;
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

        Player player = (Player) commandSender;
        if (TreeManager.getInstance().deleteTree(player.getLocation())) {
            MessageSystem.positiveMessage(player, "Tree removed.");
        }
        else {
            MessageSystem.negativeMessage(player, "No tree found.");
        }

        return true;
    }
}
