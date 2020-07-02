package me.kixstar.eco;

import me.kixstar.eco.listener.CoinpouchListener;
import me.kixstar.eco.listener.PlayerDeathListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class KixEco extends JavaPlugin {


    private static RegisteredServiceProvider<Economy> economyProvider;

    private static final Plugin instance = Bukkit.getServer().getPluginManager().getPlugin("KixEco");

    public void onEnable() {
        KixEco.economyProvider = getServer().getServicesManager().getRegistration(Economy.class);

        this.getServer().getPluginManager().registerEvents(new CoinpouchListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

    }

    public void onDisable() {

    }

    public static KixEco getInstance() {
        return null == instance ? null : (KixEco) instance;
    }

    public static Economy getEconProvider() {return KixEco.economyProvider.getProvider();}

}
