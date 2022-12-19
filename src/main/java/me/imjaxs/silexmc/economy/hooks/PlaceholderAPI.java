package me.imjaxs.silexmc.economy.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.imjaxs.silexmc.economy.api.EconomyAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPI extends PlaceholderExpansion {
    private final EconomyAPI economyAPI;

    public PlaceholderAPI() {
        this.economyAPI = EconomyAPI.getAPI();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "economy";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ImJaxs";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.2";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return super.onPlaceholderRequest(player, params);
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("balance"))
            return String.valueOf(economyAPI.getBalance(player));
        return super.onRequest(player, params);
    }
}
