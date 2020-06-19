package me.kixstar.eco.listener;

import com.destroystokyo.paper.entity.Pathfinder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ChestListener implements Listener {

    @EventHandler
    public void onChestInteract(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        if (!inventory.contains(Material.STRUCTURE_BLOCK, 20)) {
            return;
        }

        inventory.all(Material.STRUCTURE_BLOCK).forEach((i, bag) -> {
            inventory.getLocation().getWorld().dropItemNaturally(inventory.getLocation(), bag);
            inventory.remove(bag);
        });

        Collection<Player> nearbyPlayers = inventory.getLocation().getNearbyPlayers(7);
        nearbyPlayers.forEach(player -> {
            Villager minikix = (Villager) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            minikix.setBaby();
            minikix.setAI(false);
            minikix.setInvulnerable(true);
            minikix.setCustomName("Mini Kix");
            minikix.setCustomNameVisible(true);
            Pathfinder pathfinder = minikix.getPathfinder();

            List<Item> bags = minikix.getLocation()
                    .getNearbyEntities(30, 30, 30)
                    .stream()
                    .filter(entity -> entity.getType() == EntityType.DROPPED_ITEM)
                    .map(entity -> (Item) entity)
                    .filter(item -> item.getItemStack().getType() == Material.STRUCTURE_BLOCK)
                    .collect(Collectors.toList());

            for (Item bag : bags) {
                Pathfinder.PathResult result = pathfinder.findPath(bag.getLocation());
                pathfinder.moveTo(result);

                while (pathfinder.getCurrentPath() != null) {

                }
                bag.remove();
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 0.0f);
            }

        });

    }

}
