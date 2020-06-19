package me.kixstar.eco;

import me.kixstar.eco.listener.CoinpouchListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class KixEco extends JavaPlugin {

    private static final Plugin instance = Bukkit.getServer().getPluginManager().getPlugin("KixEco");

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new CoinpouchListener(), this);
    }

    public void onDisable() {

    }

    public static KixEco getInstance() {
        return null == instance ? null : (KixEco) instance;
    }

}
