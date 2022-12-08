package me.imjaxs.silexmc.economy.commands.user;

import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.api.EconomyAPI;
import me.imjaxs.silexmc.economy.tools.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private final ConfigurationSection messages;
    private final EconomyAPI economyAPI;

    public BalanceCommand(Economy plugin) {
        this.messages = plugin.getConfig().getConfigurationSection("messages");
        this.economyAPI = EconomyAPI.getAPI();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            String message = messages.getString("generals.no-console");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(message));
            return true;
        }

        Player player = (Player) sender;
        double balance = economyAPI.getBalance(player);

        String message = messages.getString("economy.balance");
        if (message != null && !message.isEmpty())
            sender.sendMessage(Utils.colorize(
                    message.replace("%balance%", economyAPI.formatValue(balance))
            ));
        return true;
    }
}
