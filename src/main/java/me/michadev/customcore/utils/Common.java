package me.michadev.customcore.utils;

import me.michadev.customcore.CustomCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.io.File;

public final class Common {

    private Common() {}

    public static Plugin getPlugin() {
        return CustomCore.getPlugin(CustomCore.class);
    }

    public static File getPluginFolder() {
        return getPlugin().getDataFolder();
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String  stringifyLocation(Location location) {
        return location.getWorld().getName() + "+" + location.getBlockX() + "+" + location.getBlockY() + "+" + location.getBlockZ();
    }

    public static Location locationifyString(String string) {
        String[] split = string.split("\\+");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }
}
