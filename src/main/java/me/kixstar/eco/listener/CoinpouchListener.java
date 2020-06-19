package me.kixstar.eco.listener;

import me.kixstar.eco.ExperienceOpening;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.*;

public class CoinpouchListener implements Listener {

    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    Map<Player, ExperienceOpening> bars = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasItem()
            || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            || event.getItem().getType() != Material.STRUCTURE_BLOCK) {
        return;
        }

        if (!bars.containsKey(event.getPlayer()) || bars.get(event.getPlayer()).isStopped()) {
            ExperienceOpening opening = new ExperienceOpening(executor, event.getPlayer());
            opening.onComplete(player -> {
                player.sendMessage(new TextComponent("Opened bag."));
                ItemStack hand = player.getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                bars.remove(player);
            });
            bars.put(event.getPlayer(), opening);
        }
        bars.get(event.getPlayer()).increment();
    }

}
