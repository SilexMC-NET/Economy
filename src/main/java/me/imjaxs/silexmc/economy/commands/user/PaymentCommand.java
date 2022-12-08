package me.imjaxs.silexmc.economy.commands.user;

import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.api.EconomyAPI;
import me.imjaxs.silexmc.economy.tools.Utils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PaymentCommand implements CommandExecutor {
    private final ConfigurationSection messages;
    private final EconomyAPI economyAPI;

    public PaymentCommand(Economy plugin) {
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

        if (args.length != 2) {
            String message = messages.getString("generals.no-correct-usage");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(
                        message.replace("%command%", "/pay <player> <value>")
                ));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            String message = messages.getString("generals.no-player");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(message));
            return true;
        }

        if (!NumberUtils.isNumber(args[1]) || player.getUniqueId().equals(target.getUniqueId())) {
            String message = messages.getString("generals.no-correct-usage");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(
                        message.replace("%command%", "/pay <player> <value>")
                ));
            return true;
        }
        double value = Double.parseDouble(args[1]);

        if (economyAPI.getBalance(player) < value) {
            String message = messages.getString("economy.not-enough-balance");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(message));
            return true;
        }
        String message;

        economyAPI.withdrawBalance(player, value);
        message = messages.getString("economy.sent-payment");
        if (message != null && !message.isEmpty())
            sender.sendMessage(Utils.colorize(
                    message.replace("%money%", economyAPI.formatValue(value)).replace("%player_name%", target.getName())
            ));

        economyAPI.depositBalance(player, value);
        message = messages.getString("economy.receive-payment");
        if (message != null && !message.isEmpty())
            sender.sendMessage(Utils.colorize(
                    message.replace("%money%", economyAPI.formatValue(value)).replace("%player_name%", player.getName())
            ));
        return true;
    }
}
