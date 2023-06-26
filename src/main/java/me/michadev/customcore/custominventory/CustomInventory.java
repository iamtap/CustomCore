package me.michadev.customcore.custominventory;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import static me.michadev.customcore.utils.Common.colorize;

public abstract class CustomInventory implements InventoryHolder {

    protected Inventory inventory;

    public abstract String getName();

    public abstract Integer getSlots();

    public abstract void onInventoryClicked(Player player, InventoryClickEvent event);

    public abstract void onInventoryOpened(Player player, InventoryOpenEvent event);

    public abstract void onInventoryClosed(Player player, InventoryCloseEvent event);

    @Override
    public Inventory getInventory() {
        if (inventory == null) inventory = Bukkit.createInventory(this, getSlots(), colorize(getName()));
        return inventory;
    }

    public void update() {
        inventory.getViewers().forEach(viewer-> {
            viewer.closeInventory();
            viewer.openInventory(getInventory());
        });
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

    public void notify(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player, sound, volume, pitch);
    }
}
