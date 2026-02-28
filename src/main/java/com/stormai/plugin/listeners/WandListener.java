package com.stormai.plugin.listeners;

import com.stormai.plugin.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class WandListener implements Listener {
    private final Main plugin;

    public WandListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Select Your Wand")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == org.bukkit.Material.AIR) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
                String wandName = clickedItem.getItemMeta().getDisplayName();
                ItemStack wand = new ItemStack(org.bukkit.Material.STICK);
                ItemMeta meta = wand.getItemMeta();
                meta.setDisplayName(wandName);
                meta.getPersistentDataContainer().set(new org.bukkit.NamespacedKey(plugin, "selected_wand"), PersistentDataType.STRING, wandName);
                wand.setItemMeta(meta);

                player.getInventory().addItem(wand);
                player.closeInventory();
                player.sendMessage("§6You have selected the " + wandName + "!");
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack mainHand = player.getInventory().getItemInMainHand();

            if (mainHand.hasItemMeta() && mainHand.getItemMeta().getPersistentDataContainer().has(new org.bukkit.NamespacedKey(plugin, "selected_wand"), PersistentDataType.STRING)) {
                String wandType = mainHand.getItemMeta().getPersistentDataContainer().get(new org.bukkit.NamespacedKey(plugin, "selected_wand"), PersistentDataType.STRING);

                switch (wandType) {
                    case "Fire Wand":
                        event.getEntity().setFireTicks(100);
                        break;
                    case "Ice Wand":
                        if (event.getEntity() instanceof org.bukkit.entity.LivingEntity) {
                            ((org.bukkit.entity.LivingEntity) event.getEntity()).addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SLOW, 200, 1));
                        }
                        break;
                    case "Lightning Wand":
                        if (event.getEntity().getLocation().getWorld() != null) {
                            event.getEntity().getLocation().getWorld().strikeLightning(event.getEntity().getLocation());
                        }
                        break;
                }
            }
        } else if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Player player = (Player) projectile.getShooter();
                ItemStack mainHand = player.getInventory().getItemInMainHand();

                if (mainHand.hasItemMeta() && mainHand.getItemMeta().getPersistentDataContainer().has(new org.bukkit.NamespacedKey(plugin, "selected_wand"), PersistentDataType.STRING)) {
                    String wandType = mainHand.getItemMeta().getPersistentDataContainer().get(new org.bukkit.NamespacedKey(plugin, "selected_wand"), PersistentDataType.STRING);

                    switch (wandType) {
                        case "Fire Wand":
                            event.getEntity().setFireTicks(100);
                            break;
                        case "Ice Wand":
                            if (event.getEntity() instanceof org.bukkit.entity.LivingEntity) {
                                ((org.bukkit.entity.LivingEntity) event.getEntity()).addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SLOW, 200, 1));
                            }
                            break;
                        case "Lightning Wand":
                            if (event.getEntity().getLocation().getWorld() != null) {
                                event.getEntity().getLocation().getWorld().strikeLightning(event.getEntity().getLocation());
                            }
                            break;
                    }
                }
            }
        }
    }
}