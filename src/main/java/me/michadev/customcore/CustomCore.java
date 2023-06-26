package me.michadev.customcore;

import me.michadev.customcore.customblocks.CustomBlock;
import me.michadev.customcore.customblocks.CustomBlockHandler;
import me.michadev.customcore.custominventory.CustomInventoryHandler;
import me.michadev.customcore.customitems.CustomItem;
import me.michadev.customcore.customitems.CustomItemHandler;
import me.michadev.customcore.customitems.impl.FlightStick;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class CustomCore extends JavaPlugin {

    public static NamespacedKey rpgItemKey;
    public static NamespacedKey rpgBlockKey;

    public static Map<String, CustomItem> customItemMap;
    public static CustomBlockHandler blockHandler;

    @Override
    public void onEnable() {
        rpgItemKey  = new NamespacedKey(this, "rpg_item_key");
        rpgBlockKey = new NamespacedKey(this, "rpg_block_key");
        customItemMap = new HashMap<>();

        registerItems(new FlightStick());
        registerListeners(new CustomItemHandler(), new CustomInventoryHandler(), (blockHandler = new CustomBlockHandler()));

        blockHandler.loadPlacedBlocks();
    }

    private void registerItems(CustomItem... customItems) {
        Arrays.asList(customItems).forEach(ci-> customItemMap.put(ci.getId(), ci));
    }

    private void registerBlocks(CustomBlock... customBlocks) {
        Arrays.asList(customBlocks).forEach(cb-> blockHandler.blockRegistry.put(cb.getIdentifier(), cb));
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(l-> Bukkit.getPluginManager().registerEvents(l, this));
    }
}
