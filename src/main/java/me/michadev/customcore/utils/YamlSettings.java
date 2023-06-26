package me.michadev.customcore.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YamlSettings {

    private static Map<String, YamlSettings> configurationMap;

    private String name;
    private File file;
    private YamlConfiguration configuration;

    public YamlSettings(String parent, String name) {
        this.name = name.endsWith(".yml") ? name : name + ".yml";
        this.file = new File(Common.getPluginFolder().getAbsolutePath() + File.separator + parent, name);
        verify();
        reload();

        if (configurationMap == null) {
            configurationMap = new HashMap<>();
            configurationMap.put(this.name, this);
        }
    }

    public YamlSettings(String name) {
        this.name = name.endsWith(".yml") ? name : name + ".yml";
        this.file = new File(Common.getPluginFolder().getAbsolutePath(), name);
        verify();
        reload();

        if (configurationMap == null) {
            configurationMap = new HashMap<>();
            configurationMap.put(this.name, this);
        }
    }

    public static YamlSettings getConfiguration(String parent, String name) {
        if (configurationMap.containsKey(name)) return configurationMap.get(name);
        if (parent == null) return new YamlSettings(name);
        return new YamlSettings(parent, name);
    }

    public void verify() {
        if (!(file.exists())) {
            file.getParentFile().mkdirs();

            try { file.createNewFile();}
            catch (IOException exception) { exception.printStackTrace(); }
        }
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try { this.configuration.save(this.file); }
        catch (IOException exception) { exception.printStackTrace(); }
    }

    public void set(String path, Object value) {
        this.configuration.set(path, value);
        save();
    }

    public void remove(String path) {
        set(path, null);
    }

    public YamlConfiguration getYaml() {
        return this.configuration;
    }
}
