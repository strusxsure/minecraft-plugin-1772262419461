package com.stormai.plugin.listeners;

import com.stormai.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JoinListener implements Listener {
    private final Main plugin;

    public JoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            openWandSelectorGUI(player);
        }, 20L);
    }

    private void openWandSelectorGUI(Player player) {
        org.bukkit.inventory.Inventory gui = Bukkit.createInventory(player, 9, "Select Your Wand");

        ItemStack fireWand = createWand(Material.STICK, "Fire Wand", "Burns enemies on hit");
        ItemStack iceWand = createWand(Material.STICK, "Ice Wand", "Slows enemies on hit");
        ItemStack lightningWand = createWand(Material.STICK, "Lightning Wand", "Strikes lightning on hit");

        gui.setItem(3, fireWand);
        gui.setItem(4, iceWand);
        gui.setItem(5, lightningWand);

        player.openInventory(gui);
    }

    private ItemStack createWand(Material material, String displayName, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(java.util.Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}