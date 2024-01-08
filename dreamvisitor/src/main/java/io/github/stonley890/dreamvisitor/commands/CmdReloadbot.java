package io.github.stonley890.dreamvisitor.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.stonley890.dreamvisitor.Bot;
import io.github.stonley890.dreamvisitor.Dreamvisitor;
import io.github.stonley890.dreamvisitor.discord.DiscCommandsManager;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

public class CmdReloadbot implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        
        sender.sendMessage(Dreamvisitor.PREFIX + ChatColor.WHITE + "Shutting down the bot instance...");

        if (!Dreamvisitor.botFailed) {
            Bot.getJda().shutdown();
        }

        sender.sendMessage(Dreamvisitor.PREFIX + ChatColor.WHITE + "Starting a new bot instance...");
        Dreamvisitor.botFailed = false;
        Bukkit.getScheduler().runTask(Dreamvisitor.plugin, Bot::startBot);

        if (Dreamvisitor.botFailed) {
            sender.sendMessage(Dreamvisitor.PREFIX + ChatColor.RED + "The bot was unable to start.");
        } else {
            DiscCommandsManager.initChannelsRoles(Dreamvisitor.getPlugin().getConfig());
            sender.sendMessage(Dreamvisitor.PREFIX + ChatColor.BLUE + "Dreamvisitor bot has been restarted.");
        }

        return true;
    }

}
