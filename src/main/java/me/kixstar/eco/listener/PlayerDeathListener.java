package me.kixstar.eco.listener;

import me.kixstar.eco.KixEco;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Economy econ = KixEco.getEconProvider();
        Player player = event.getEntity();
        double playerBal = econ.getBalance(player);
        //implement a config API that provides default values etc??
        double percentageOnDeath = KixEco.getInstance()
                .getConfig().getDouble("percentage-lost-on-death");
        double lostMoney = playerBal*percentageOnDeath/100;
        econ.withdrawPlayer(player, lostMoney);
    }
}
