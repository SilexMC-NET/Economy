package me.imjaxs.silexmc.economy.commands.user;

import me.imjaxs.silexmc.economy.Economy;
import me.imjaxs.silexmc.economy.api.EconomyAPI;
import me.imjaxs.silexmc.economy.managers.PlayerManager;
import me.imjaxs.silexmc.economy.objects.EPlayer;
import me.imjaxs.silexmc.economy.objects.builder.ItemBuilder;
import me.imjaxs.silexmc.economy.objects.builder.inventory.SimpleInventory;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class BalanceTopCommand implements CommandExecutor {
    private final EconomyAPI economyAPI;
    private final PlayerManager playerManager;

    public BalanceTopCommand(Economy plugin) {
        this.economyAPI = EconomyAPI.getAPI();
        this.playerManager = plugin.getPlayerManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            //
            return true;
        }
        Player player = (Player) sender;

        player.openInventory(getInventory(player));
        return true;
    }

    private Map.Entry<EPlayer, Integer> getPlayer(Player player) {
        List<EPlayer> players = playerManager.getTempPlayerCache();

        for (int i = 0; i < players.size(); i++) {
            EPlayer var = players.get(i);
            if (var.getUniqueID().equals(player.getUniqueId()))
                return new AbstractMap.SimpleEntry<>(var, i + 1);
        }
        return null;
    }

    private Inventory getInventory(Player player) {
        SimpleInventory inventory = new SimpleInventory(36, "&8» &7Balances");
        List<EPlayer> players = playerManager.getTempPlayerCache();

        for (int i = 0; i < inventory.getInventory().getSize(); i++)
            inventory.getInventory().setItem(i, ItemBuilder.builder(Material.STAINED_GLASS_PANE, 15).build());

        int[] slots = new int[]{3, 4, 5, 10, 11, 12, 13, 15, 15, 16};
        for (int i = 0; i < 10; i++) {
            int value = i + 1;

            if (players.size() < i + 1)
                break;

            EPlayer var = players.get(i);
            inventory.getInventory().setItem(slots[i], ItemBuilder.builder(Material.SKULL_ITEM, 3)
                    .name(
                            "&f#%position%. &e%player_name%: &f%money% &8(&a%money_format%&8)"
                                    .replace("%position%", String.valueOf(value))
                                    .replace("%player_name%", var.getName())
                                    .replace("%money%", String.format("%,.2f", var.getBalance()))
                                    .replace("%money_format%", economyAPI.formatValue(var.getBalance()))
                    )
                    .owner(var.getName())
                    .build()
            );
        }

        Map.Entry<EPlayer, Integer> playerInfo = getPlayer(player);
        if (playerInfo != null) {
            EPlayer var = playerInfo.getKey(); int value = playerInfo.getValue();
            inventory.getInventory().setItem(31, ItemBuilder.builder(Material.SKULL_ITEM, 3)
                    .name(
                            "&f#%position%. &eYou"
                                    .replace("%position%", String.valueOf(value)) //.replace("%player_name%", var.getName()).replace("%money%", economyAPI.formatValue(var.getBalance()))
                    )
                    .owner(var.getName())
                    .build()
            );
        }

        return inventory.getInventory();
    }
}
