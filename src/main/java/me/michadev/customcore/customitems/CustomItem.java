package me.michadev.customcore.customitems;

import me.michadev.customcore.CustomCore;
import me.michadev.customcore.utils.Common;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomItem {

    public abstract String getName();

    public abstract Material getMaterial();

    public abstract List<String> getLore();

    public abstract void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event);


    public String getId() {
        return getClass().getSimpleName().toLowerCase();
    }

    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(getMaterial(), 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        itemMeta.setDisplayName(Common.colorize(getName()));
        List<String> lore = new ArrayList<>();
        getLore().forEach(l-> lore.add(Common.colorize(l)));
        itemMeta.setLore(lore);

        container.set(CustomCore.rpgItemKey, PersistentDataType.STRING, getId());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
