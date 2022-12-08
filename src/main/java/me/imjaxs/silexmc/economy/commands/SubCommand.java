package me.imjaxs.silexmc.economy.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    String getName();
    String getUsage();
    boolean execute(CommandSender sender, String[] args);
}
