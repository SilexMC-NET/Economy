package me.imjaxs.silexmc.economy.api;

import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.managers.PlayerManager;
import me.imjaxs.silexmc.economy.objects.EPlayer;
import me.imjaxs.silexmc.economy.tools.Utils;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class EconomyAPI {
    private static EconomyAPI instance;
    private final PlayerManager playerManager;

    public EconomyAPI(Economy plugin) {
        instance = this;
        this.playerManager = plugin.getPlayerManager();
    }

    public BigDecimal getBalance(OfflinePlayer player) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer == null) {
            return new BigDecimal("0.00");
        }
        return economyPlayer.getBalance();
    }

    public boolean hasBalance(OfflinePlayer player, BigDecimal value) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer == null)
            return false;

        int var = economyPlayer.getBalance().compareTo(value);
        return var >= 0;
    }

    public void setBalance(OfflinePlayer player, BigDecimal value) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer != null)
            economyPlayer.setBalance(value);
    }

    public void withdrawBalance(OfflinePlayer player, BigDecimal value) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer != null)
            economyPlayer.setBalance(hasBalance(player, value) ? economyPlayer.getBalance().subtract(value) : new BigDecimal("0.00"));
    }

    public void depositBalance(OfflinePlayer player, BigDecimal value) {
        EPlayer economyPlayer = playerManager.getPlayer(player);
        if (economyPlayer != null)
            economyPlayer.setBalance(economyPlayer.getBalance().add(value));
    }

    public String formatValue(BigDecimal value) {
        return Utils.format(value.doubleValue());
    }

    public static EconomyAPI getAPI() {
        return instance;
    }
}
