package me.michadev.rpgcore.utils;

import me.michadev.rpgcore.RPGCore;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.File;

public final class Common {

    private Common() {}

    public static Plugin getPlugin() {
        return RPGCore.getPlugin(RPGCore.class);
    }

    public static File getPluginFolder() {
        return getPlugin().getDataFolder();
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
