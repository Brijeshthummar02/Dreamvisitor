package io.github.stonley890.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CmdRadio implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Check for Staff tag
            if (player.getScoreboardTags().contains("Staff")) {
                if (args.length > 0) {
                    buildMessage(args, player.getName());
                    return true;
                } else {
                    return false;
                }

            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to run that command!");
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            
            if (args.length > 0) {
                buildMessage(args, "Console");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        
    }

    void buildMessage(String[] args, String name) {

        // Set color of name to red if from console
        ChatColor nameColor = ChatColor.YELLOW;
        if (name.equals("Console")) {
            nameColor = ChatColor.RED;
        }

        // Build message
        StringBuilder message = new StringBuilder().append(ChatColor.DARK_AQUA + "[Staff Radio] " + nameColor + "<" + name + "> " + ChatColor.WHITE);
        for (int i = 0; i != args.length; i++)
        {
            message.append(args[i] + " ");
        }

        String finalMessage = message.toString();

        // Send message
        Bukkit.getLogger().info(finalMessage);
        for (Player operator : Bukkit.getServer().getOnlinePlayers())
        {
            if (operator.isOp())
            {
                operator.sendMessage(finalMessage);
            }
        }
    }
    
}
