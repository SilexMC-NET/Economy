package me.imjaxs.silexmc.economy.managers.database;

import com.glyart.mystral.database.AsyncDatabase;
import com.glyart.mystral.database.Credentials;
import com.glyart.mystral.database.Database;
import com.glyart.mystral.database.Mystral;
import com.glyart.mystral.sql.BatchSetter;
import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.managers.PlayerManager;
import me.imjaxs.silexmc.economy.objects.EPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class DatabaseManager {
    private final String TABLE = "economy_cache";
    private final Economy plugin;
    private final AsyncDatabase asyncDatabase;

    public DatabaseManager(Economy plugin) {
        this.plugin = plugin;

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("database");
        Credentials credentials = Credentials.builder()
                .host(section.getString("hostname"))
                .user(section.getString("username"))
                .password(section.getString("password"))
                .schema(section.getString("database"))
                .port(section.getInt("port"))
                .pool("economy-pool")
                .build();
        Executor executor = (command -> Bukkit.getScheduler().runTaskAsynchronously(plugin, command));

        this.asyncDatabase = Mystral.newAsyncDatabase(credentials, executor);
    }

    public void createTable() {
        asyncDatabase.update("CREATE TABLE IF NOT EXISTS " + TABLE + " (name TEXT, uniqueID TEXT, balance DOUBLE);", false);
    }

    public CompletableFuture<Integer> insertPlayer(EPlayer player) {
        return asyncDatabase.update("INSERT INTO " + TABLE + " VALUES(?, ?, ?);", new Object[]{
                player.getName(), player.getUniqueID().toString(), player.getBalance()
        }, false, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE);
    }

    public CompletableFuture<EPlayer> loadPlayer(UUID uniqueID) {
        return asyncDatabase.queryForObject("SELECT * FROM " + TABLE + " WHERE uniqueID = ?;", new Object[]{uniqueID.toString()}, (result, row) -> {
            return new EPlayer(result.getString(1), UUID.fromString(result.getString(2)), result.getDouble(3));
        }, Types.VARCHAR);
    }

    public CompletableFuture<Integer> savePlayer(EPlayer player) {
        return asyncDatabase.update("UPDATE " + TABLE + " SET balance = ? WHERE uniqueID = ?;", new Object[]{
                player.getBalance(), player.getUniqueID().toString()
        }, false, Types.DOUBLE, Types.VARCHAR);
    }

    public CompletableFuture<List<EPlayer>> loadPlayers() {
        return asyncDatabase.queryForList("SELECT * FROM " + TABLE + " ORDER BY balance DESC;", (result, row) -> {
            return new EPlayer(result.getString("name"), UUID.fromString(result.getString("uniqueID")), result.getDouble("balance"));
        });
    }

    public void saveSyncPlayers() {
        PlayerManager playerManager = plugin.getPlayerManager();
        List<EPlayer> playerCache = playerManager.getPlayerCache();

        if (!asyncDatabase.getDatabase().isPresent())
            return;

        Database database = asyncDatabase.getDatabase().get();
        database.batchUpdate("UPDATE " + TABLE + " SET balance = ? WHERE uniqueID = ?;", new BatchSetter() {
            @Override
            public void setValues(@NotNull PreparedStatement statement, int i) throws SQLException {
                EPlayer economyPlayer = playerCache.get(i);

                statement.setDouble(1, economyPlayer.getBalance());
                statement.setString(2, economyPlayer.getUniqueID().toString());
            }

            @Override
            public int getBatchSize() {
                return playerCache.size();
            }
        });
    }

    public CompletableFuture<Void> saveAsyncPlayers() {
        PlayerManager playerManager = plugin.getPlayerManager();
        List<EPlayer> playerCache = playerManager.getPlayerCache();

        return asyncDatabase.batchUpdate("UPDATE " + TABLE + " SET balance = ? WHERE uniqueID = ?;", new BatchSetter() {
            @Override
            public void setValues(@NotNull PreparedStatement statement, int i) throws SQLException {
                EPlayer economyPlayer = playerCache.get(i);

                statement.setDouble(1, economyPlayer.getBalance());
                statement.setString(2, economyPlayer.getUniqueID().toString());
            }

            @Override
            public int getBatchSize() {
                return playerCache.size();
            }
        });
    }
}
