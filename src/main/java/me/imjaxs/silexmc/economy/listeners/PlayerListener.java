package me.imjaxs.silexmc.economy.listeners;

import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.managers.PlayerManager;
import me.imjaxs.silexmc.economy.managers.database.DatabaseManager;
import me.imjaxs.silexmc.economy.objects.EPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final DatabaseManager databaseManager;
    private final PlayerManager playerManager;

    public PlayerListener(Economy plugin) {
        this.databaseManager = plugin.getDatabaseManager();
        this.playerManager = plugin.getPlayerManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        EPlayer checkPlayer = playerManager.getPlayer(player);
        if (checkPlayer == null) {
            databaseManager.loadPlayer(player.getUniqueId()).whenComplete((var, throwable) -> {
                EPlayer economyPlayer = throwable != null && var == null ? new EPlayer(player.getName(), player.getUniqueId()) : var;

                if (throwable != null)
                    databaseManager.insertPlayer(economyPlayer).whenComplete((integer, throwableInsert) -> {
                        if (throwableInsert != null)
                            throwableInsert.printStackTrace();
                    });
                playerManager.getPlayerCache().add(economyPlayer);
            });
        }
    }

    @EventHandler
    public void unQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        EPlayer checkPlayer = playerManager.getPlayer(player);
        if (checkPlayer != null)
            databaseManager.savePlayer(checkPlayer).whenComplete((integer, throwable) -> {
                if (throwable != null)
                    throwable.printStackTrace();
            });
    }
}
