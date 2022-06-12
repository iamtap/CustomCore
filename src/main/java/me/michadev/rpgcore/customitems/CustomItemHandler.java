package me.michadev.rpgcore.customitems;

import me.michadev.rpgcore.RPGCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CustomItemHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(RPGCore.customItemMap.get("FlightStick").getItem());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR) && isCustomItem(heldItem)) {
            CustomItem customItem = RPGCore.customItemMap.get(getItemId(heldItem));
            customItem.handleRightClick(player, heldItem, event);
        }

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR) && isCustomItem(heldItem)) {
            CustomItem customItem = RPGCore.customItemMap.get(getItemId(heldItem));
            customItem.handleLeftClick(player, heldItem, event);
        }
    }

    private boolean isCustomItem(ItemStack itemStack) {
        return (itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(RPGCore.rpgItemKey, PersistentDataType.STRING));
    }

    private String getItemId(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(RPGCore.rpgItemKey, PersistentDataType.STRING);
    }
}
