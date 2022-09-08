package me.michadev.rpgcore.utils;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YamlSettings {

    private File file;
    private YamlConfiguration configuration;

    public YamlSettings(String parent, String name) {
        name = name.endsWith(".yml") ? name : name + ".yml";
        this.file = new File(Common.getPluginFolder().getAbsolutePath() + File.separator + parent, name);
        verify();
        reload();
    }

    public YamlSettings(String name) {
        name = name.endsWith(".yml") ? name : name + ".yml";
        this.file = new File(Common.getPluginFolder().getAbsolutePath(), name);
        verify();
        reload();
    }

    @SneakyThrows
    public void verify() {
        if (!(file.exists())) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    @SneakyThrows
    public void save() {
        this.configuration.save(this.file);
    }

    public void set(String path, Object value) {
        this.configuration.set(path, value);
        save();
    }

    public void remove(String path) {
        set(path, null);
    }

    public YamlConfiguration yaml() {
        return this.configuration;
    }
}
