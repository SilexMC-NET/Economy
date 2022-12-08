package me.imjaxs.silexmc.economy.commands.admin;

import com.google.common.collect.Lists;
import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.commands.SubCommand;
import me.imjaxs.silexmc.economy.commands.admin.subs.GiveCommand;
import me.imjaxs.silexmc.economy.commands.admin.subs.SetCommand;
import me.imjaxs.silexmc.economy.commands.admin.subs.TakeCommand;
import me.imjaxs.silexmc.economy.tools.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCommandManager implements CommandExecutor, TabExecutor {
    private final String PERMISSION = "economy.admin";

    private final ConfigurationSection messages;
    private final List<SubCommand> subCommands;

    public AdminCommandManager(Economy plugin) {
        this.messages = plugin.getConfig().getConfigurationSection("messages");
        this.subCommands = Lists.newArrayList();

        subCommands.add(new SetCommand(plugin));
        subCommands.add(new GiveCommand(plugin));
        subCommands.add(new TakeCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            String message = messages.getString("generals.no-permission");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(message));
            return true;
        }

        if (args.length == 0) {
            //
            return true;
        }
        String name = args[0];

        SubCommand subCommand = getSubCommand(name);
        if (subCommand == null) {
            String message = messages.getString("generals.no-subcommand");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(message));
            return true;
        }

        if (!subCommand.execute(sender, getSubCommandArgs(args))) {
            String message = messages.getString("generals.no-correct-usage");
            if (message != null && !message.isEmpty())
                sender.sendMessage(Utils.colorize(
                        message.replace("%command%", subCommand.getUsage())
                ));
            return true;
        }
        return true;
    }

    private SubCommand getSubCommand(String name) {
        for (SubCommand subCommand : subCommands)
            if (subCommand.getName().equalsIgnoreCase(name))
                return subCommand;
        return null;
    }

    private String[] getSubCommandArgs(String[] args) {
        String[] arguments = new String[args.length - 1];
        System.arraycopy(args,1, arguments, 0, arguments.length);
        return arguments;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(PERMISSION))
            return new ArrayList<>();

        if (args.length == 1)
            return Arrays.asList("set", "give", "take");

        if (args.length == 2)
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());

        return new ArrayList<>();
    }
}
