package io.github.stonley890.dreamvisitor.functions;

import io.github.stonley890.dreamvisitor.Dreamvisitor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Flight {
    public static double energyCapacity = Dreamvisitor.getPlugin().getConfig().getInt("flightEnergyCapacity");
    public static double reactivationPoint = Dreamvisitor.getPlugin().getConfig().getInt("flightRegenerationPoint");;
    public static final Map<Player, Double> energy = new HashMap<>();
    private static final Map<Player, Boolean> energyDepletion = new HashMap<>();
    private static final Map<Player, Boolean> flightRestricted = new HashMap<>();

    public static void init() {
        Bukkit.getScheduler().runTaskTimer(Dreamvisitor.getPlugin(), () -> {

            // if Player is flying, remove one energy
            for (Player player : Bukkit.getOnlinePlayers()) {

                // If player does not have the dreamvisitor.fly permission, disable flight if not in creative
                if ((!player.hasPermission("dreamvisitor.fly") || isFlightRestricted(player) && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR)) {
                    player.setAllowFlight(false);
                }

                energy.putIfAbsent(player, energyCapacity);

                // Get bossbar (even if it's null)
                NamespacedKey namespacedKey = NamespacedKey.fromString("dreamvisitor:" + player.getUniqueId().toString().toLowerCase() + "-energy", Dreamvisitor.getPlugin());
                assert namespacedKey != null;
                KeyedBossBar bossBar = Bukkit.getBossBar(namespacedKey);

                if (energy.get(player) < energyCapacity) {

                    if (!player.isFlying() || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                        // Regenerate energy if not flying or in creative mode
                        try {
                            energy.compute(player, (k, i) -> i + 1);
                        } catch (NullPointerException e) {
                            energy.put(player, energyCapacity);
                        }
                    }

                    if (energy.get(player) > energyCapacity) energy.put(player, energyCapacity);

                    if (bossBar == null) { // Create bossbar if it's null
                        bossBar = Bukkit.createBossBar(namespacedKey, "Energy", BarColor.GREEN, BarStyle.SEGMENTED_10);
                        bossBar.addPlayer(player);
                        bossBar.setVisible(true);
                    }
                    // Set progress
                    bossBar.setProgress(energy.get(player) / energyCapacity);

                    // Remove player from flight if energy runs out
                    if (energy.get(player) <= 0) {
                        // Set bossbar to red if it's depleted
                        bossBar.setColor(BarColor.RED);
                        setPlayerDepleted(player, true);
                        if (player.isFlying()) {
                            player.setFlying(false);
                            player.setGliding(true);
                            player.setAllowFlight(false);
                        }
                    }

                    // Set bossbar to green if it reaches reactivation point
                    if (isPlayerDepleted(player) && energy.get(player) >= reactivationPoint) {
                        bossBar.setColor(BarColor.GREEN);
                        setPlayerDepleted(player, false);
                        if (player.hasPermission("dreamvisitor.fly")) {
                            player.setAllowFlight(true);
                        }
                    }

                } else if (bossBar != null) {
                    // Remove bossbar if it's full
                    bossBar.removePlayer(player);
                    bossBar.setVisible(false);
                    Bukkit.removeBossBar(namespacedKey);
                }
            }
        }, 0, 0);
    }

    public static boolean isPlayerDepleted(Player player) {
        return energyDepletion.computeIfAbsent(player, k -> false);
    }

    public static void setPlayerDepleted(Player player, boolean depleted) {
        energyDepletion.put(player, depleted);
    }

    public static boolean isFlightRestricted(Player player) {
        return flightRestricted.computeIfAbsent(player, k -> false);
    }

    public static void setFlightRestricted(@NotNull Player player, boolean restricted) {
        flightRestricted.put(player, restricted);
        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            player.setAllowFlight(!restricted && !isPlayerDepleted(player));
            if (restricted && player.isFlying()) {
                player.setFlying(false);
                player.setGliding(true);
            }
        }

    }
}
