package me.michadev.rpgcore.utils;

import org.bukkit.ChatColor;

public final class Common {

    private Common() {
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
