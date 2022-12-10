package me.imjaxs.silexmc.economy.objects.builder;

import me.imjaxs.silexmc.economy.tools.Utils;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static ItemBuilder builder(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder builder(Material material, int data) {
        return new ItemBuilder(new ItemStack(material, 1, (short) data));
    }

    public static ItemBuilder builder(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    public ItemBuilder name(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.colorize(name));

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Utils.colorize(lore));

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder owner(String player) {
        if (itemStack.getType() != Material.SKULL_ITEM && itemStack.getDurability() != 3)
            return this;

        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        itemMeta.setOwner(player);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
