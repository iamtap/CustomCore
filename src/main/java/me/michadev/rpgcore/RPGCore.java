package me.michadev.rpgcore;

import me.michadev.rpgcore.customitems.CustomItem;
import me.michadev.rpgcore.customitems.CustomItemHandler;
import me.michadev.rpgcore.customitems.FlightStick;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class RPGCore extends JavaPlugin {

    public static NamespacedKey rpgItemKey;
    public static Map<String, CustomItem> customItemMap;

    @Override
    public void onEnable() {
        rpgItemKey = new NamespacedKey(this, "rpg-item-key");
        customItemMap = new HashMap<>();

        registerItems(new FlightStick());
        registerListeners(new CustomItemHandler());
    }

    private void registerItems(CustomItem... customItems) {
        Arrays.asList(customItems).forEach(ci-> customItemMap.put(ci.getId(), ci));
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(l-> Bukkit.getPluginManager().registerEvents(l, this));
    }
}
