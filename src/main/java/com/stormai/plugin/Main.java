package com.stormai.plugin;

import com.stormai.plugin.listeners.JoinListener;
import com.stormai.plugin.listeners.WandListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("WandSelector plugin enabled!");
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new WandListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("WandSelector plugin disabled!");
    }
}