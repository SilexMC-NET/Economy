package me.imjaxs.silexmc.economy.managers;

import com.google.common.collect.Lists;
import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.api.EconomyAPI;
import me.imjaxs.silexmc.economy.managers.database.DatabaseManager;
import me.imjaxs.silexmc.economy.objects.EPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class PlayerManager {
    private final DatabaseManager databaseManager;
    private final List<EPlayer> playerCache, tempPlayerCache;

    public PlayerManager(Economy plugin) {
        this.databaseManager = plugin.getDatabaseManager();

        this.playerCache = Lists.newArrayList();
        this.tempPlayerCache = Lists.newArrayList();

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (playerCache.isEmpty())
                return;

            databaseManager.saveAsyncPlayers().whenComplete((unused, throwable) -> {
                if (throwable != null) {
                    throwable.printStackTrace();
                } else {
                    databaseManager.loadOrderPlayers().whenComplete((tempPlayers, throwableLoad) -> {
                        if (throwableLoad != null) {
                            throwableLoad.printStackTrace();
                        } else {
                            if (tempPlayers != null && !tempPlayers.isEmpty()) {
                                tempPlayerCache.clear();
                                tempPlayerCache.addAll(tempPlayers);
                            }
                        }
                    });
                }
            });
        }, 20L, 20L * 60 * 15);
    }

    public EPlayer getPlayer(OfflinePlayer player) {
        for (EPlayer var : this.playerCache)
            if (var.getUniqueID().equals(player.getUniqueId()))
                return var;
        return null;
    }

    public EPlayer getPlayerPosition(int value) {
        int position = value - 1;

        if (this.tempPlayerCache.size() < position)
            return null;
        return this.tempPlayerCache.get(position);
    }

    public List<EPlayer> getPlayerCache() {
        return playerCache;
    }

    public List<EPlayer> getTempPlayerCache() {
        return tempPlayerCache;
    }
}
