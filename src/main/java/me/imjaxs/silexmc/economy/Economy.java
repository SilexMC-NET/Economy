package me.imjaxs.silexmc.economy;

import me.imjaxs.silexmc.economy.api.EconomyAPI;
import me.imjaxs.silexmc.economy.commands.admin.AdminCommandManager;
import me.imjaxs.silexmc.economy.commands.user.BalanceCommand;
import me.imjaxs.silexmc.economy.commands.user.BalanceTopCommand;
import me.imjaxs.silexmc.economy.commands.user.PaymentCommand;
import me.imjaxs.silexmc.economy.hooks.PlaceholderAPI;
import me.imjaxs.silexmc.economy.listeners.PlayerListener;
import me.imjaxs.silexmc.economy.managers.PlayerManager;
import me.imjaxs.silexmc.economy.managers.database.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Economy extends JavaPlugin {
    private PlayerManager playerManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        /*
            MANAGERS
         */
        this.databaseManager = new DatabaseManager(this);
        databaseManager.createTable();

        this.playerManager = new PlayerManager(this);


        /*
            API
         */
        new EconomyAPI(this);


        /*
            CONFIG
         */
        this.saveDefaultConfig();

        /*
            COMMANDS
         */
        this.getCommand("economy").setExecutor(new AdminCommandManager(this));
        this.getCommand("balance").setExecutor(new BalanceCommand(this));
        this.getCommand("balancetop").setExecutor(new BalanceTopCommand(this));
        this.getCommand("payment").setExecutor(new PaymentCommand(this));


        /*
            LISTENERS
         */
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        /*
            HOOKS
         */
        if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
            new PlaceholderAPI().register();
    }

    @Override
    public void onDisable() {
        databaseManager.saveSyncPlayers();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
