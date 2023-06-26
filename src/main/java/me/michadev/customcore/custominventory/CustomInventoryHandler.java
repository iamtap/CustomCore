package me.michadev.customcore.custominventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class CustomInventoryHandler implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            fromBukkitInventory(event.getInventory()).onInventoryClicked((Player) event.getWhoClicked(), event);
        }
        catch (RuntimeException ignored) {

        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        try {
            fromBukkitInventory(event.getInventory()).onInventoryOpened((Player) event.getPlayer(), event);
        }
        catch (RuntimeException ignored) {

        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        try {
            fromBukkitInventory(event.getInventory()).onInventoryClosed((Player) event.getPlayer(), event);
        }
        catch (RuntimeException ignored) {

        }
    }

    private CustomInventory fromBukkitInventory(Inventory inventory) {
        if (!(inventory.getHolder() instanceof CustomInventory))
            throw new RuntimeException();

        return (CustomInventory) inventory.getHolder();
    }
}
