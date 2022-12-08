package me.imjaxs.silexmc.economy.commands.admin.subs;

import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.api.EconomyAPI;
import me.imjaxs.silexmc.economy.commands.SubCommand;
import me.imjaxs.silexmc.economy.tools.Utils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SetCommand implements SubCommand {
    private final ConfigurationSection messages;
    private final EconomyAPI economyAPI;

    public SetCommand(Economy plugin) {
        this.messages = plugin.getConfig().getConfigurationSection("messages");
        this.economyAPI = EconomyAPI.getAPI();
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getUsage() {
        return "/economy set <player> <value>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 2)
            return false;

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            String message = messages.getString("generals.no-player");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(message));
            return true;
        }

        if (!NumberUtils.isNumber(args[1]))
            return false;
        double value = Double.parseDouble(args[1]);

        economyAPI.setBalance(target, value);
        String message;

        message = messages.getString("economy.set-balance");
        if (message != null && !message.isEmpty())
            sender.sendMessage(Utils.colorize(
                    message.replace("%player_name%", target.getName()).replace("%money%", economyAPI.formatValue(value))
            ));

        message = messages.getString("economy.has-set-balance");
        if (message != null && !message.isEmpty())
            sender.sendMessage(Utils.colorize(
                    message.replace("%money%", economyAPI.formatValue(value))
            ));
        return true;
    }
}
