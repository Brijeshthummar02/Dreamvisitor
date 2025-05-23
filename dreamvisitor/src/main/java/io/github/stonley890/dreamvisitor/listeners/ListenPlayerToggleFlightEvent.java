package io.github.stonley890.dreamvisitor.listeners;

import io.github.stonley890.dreamvisitor.Dreamvisitor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ListenPlayerToggleFlightEvent implements Listener {

    private static final HashMap<Player, BukkitTask> wingFlapSoundTask = new HashMap<>();

    @EventHandler
    public void onToggleFlight(@NotNull PlayerToggleFlightEvent event) {

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            if (event.isFlying()) {
                player.setGliding(false);
                player.setFlySpeed(0.05f);
                player.setFlying(true);

                wingFlapSoundTask.put(player, Bukkit.getScheduler().runTaskTimer(Dreamvisitor.getPlugin(), () -> {
                    if (!player.isOnline() || !player.isFlying()) {
                        // remove this task
                        cancelWingFlapSoundTask(player);

                    } else {
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 0.5f, 1.2f);
                    }
                }, 1, 15));


            } else {
                player.setFlying(false);
                player.setGliding(true);
            }
        } else {
            if (event.isFlying()) {
                player.setFlySpeed(0.1f);
                player.setFlying(true);
            } else {
                player.setFlying(false);
            }
        }


    }

    private static void cancelWingFlapSoundTask(Player player) {
        wingFlapSoundTask.get(player).cancel();
    }

}
