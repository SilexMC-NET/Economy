package me.imjaxs.silexmc.economy.api;

import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.managers.PlayerManager;
import me.imjaxs.silexmc.economy.objects.EPlayer;
import me.imjaxs.silexmc.economy.tools.Utils;
import org.bukkit.OfflinePlayer;

public class EconomyAPI {
    private static EconomyAPI instance;
    private final PlayerManager playerManager;

    public EconomyAPI(Economy plugin) {
        instance = this;
        this.playerManager = plugin.getPlayerManager();
    }

    public double getBalance(OfflinePlayer player) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer == null) {
            System.out.println("null");
            return 0.0;
        }
        return economyPlayer.getBalance();
    }

    public void setBalance(OfflinePlayer player, double value) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer != null)
            economyPlayer.setBalance(value);
    }

    public void withdrawBalance(OfflinePlayer player, double value) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer != null)
            economyPlayer.setBalance(economyPlayer.getBalance() <= value ? 0 : economyPlayer.getBalance() - value);
    }

    public void depositBalance(OfflinePlayer player, double value) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer != null)
            economyPlayer.setBalance(economyPlayer.getBalance() + value);
    }

    public String formatValue(double value) {
        return Utils.format(value);
    }

    public static EconomyAPI getAPI() {
        return instance;
    }
}
