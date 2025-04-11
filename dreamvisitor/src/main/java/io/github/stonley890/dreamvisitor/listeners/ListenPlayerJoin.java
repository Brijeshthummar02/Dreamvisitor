package io.github.stonley890.dreamvisitor.listeners;

import io.github.stonley890.dreamvisitor.Bot;
import io.github.stonley890.dreamvisitor.Dreamvisitor;
import io.github.stonley890.dreamvisitor.data.PlayerMemory;
import io.github.stonley890.dreamvisitor.data.PlayerUtility;
import io.github.stonley890.dreamvisitor.functions.Sandbox;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ListenPlayerJoin implements Listener {
    
    @EventHandler
    public void onPlayerJoinEvent(@NotNull PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        final User lpUser = Dreamvisitor.getLuckPerms().getUserManager().getUser(player.getUniqueId());
        if (lpUser != null) {
            String prefix = lpUser.getCachedData().getMetaData().getPrefix();
            if (prefix != null) player.setPlayerListName(prefix.replace('&', '§') + player.getName());
        }

        // Enable flight
        if (player.hasPermission("dreamvisitor.fly")) {
            player.setAllowFlight(true);
        }

        // Send join messages
        String chatMessage = "**" + Bot.escapeMarkdownFormatting(ChatColor.stripColor(player.getName())) + " joined the game**";
        try {
            Bot.getGameChatChannel().sendMessage(chatMessage).queue();
        } catch (InsufficientPermissionException e) {
            Bukkit.getLogger().warning("Dreamvisitor does not have sufficient permissions to send messages in game chat channel: " + e.getMessage());
        }
        Bot.sendLog(chatMessage);

        PlayerMemory memory = PlayerUtility.getPlayerMemory(player.getUniqueId());

        if (memory.sandbox) {
            boolean sandboxerOnline = false;
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("dreamvisitor.sandbox")) {
                    sandboxerOnline = true;
                    onlinePlayer.sendMessage(Dreamvisitor.PREFIX + player.getName() + " is currently in sandbox mode.");
                }
            }
            if (!sandboxerOnline) Sandbox.disableSandbox(player);
        }

    }
    
}
