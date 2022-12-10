package me.imjaxs.silexmc.economy.objects.builder.inventory;

import me.imjaxs.silexmc.economy.tools.Utils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SimpleInventory implements InventoryHolder {
    private final Inventory inventory;

    public SimpleInventory(int slot, String title) {
        this.inventory = Bukkit.createInventory(this, slot, Utils.colorize(title));
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
