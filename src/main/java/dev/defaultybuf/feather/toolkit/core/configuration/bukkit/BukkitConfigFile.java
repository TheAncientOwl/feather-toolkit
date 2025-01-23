/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file BukkitConfigFile.java
 * @author Alexandru Delegeanu
 * @version 0.6
 * @description Bukkit implementation of @see IConfigFile
 */

package dev.defaultybuf.feather.toolkit.core.configuration.bukkit;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;
import dev.defaultybuf.feather.toolkit.api.configuration.IConfigSection;
import dev.defaultybuf.feather.toolkit.util.java.TimeUtils;
import dev.defaultybuf.feather.toolkit.util.parsing.InventoryConfig;

public class BukkitConfigFile implements IConfigFile {
    private final JavaPlugin plugin;
    private final String fileName;
    private File configFile;
    private FileConfiguration fileConfiguration;

    public BukkitConfigFile(final JavaPlugin plugin, final String fileName) throws IOException {
        this.plugin = plugin;
        this.fileName = fileName;

        this.configFile = new File(plugin.getDataFolder(), fileName);

        saveDefaultConfig();

        if (!this.configFile.exists()) {
            throw new IOException("File '" + fileName + "' does not exist");
        }

        loadConfig();
    }

    @Override
    public void saveDefaultConfig() {
        if (!this.configFile.exists()) {
            this.plugin.saveResource(fileName, false);
        }
    }

    @Override
    public void saveConfig() throws IOException {
        this.fileConfiguration.save(this.configFile);
    }

    @Override
    public void loadConfig() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    @Override
    public void reloadConfig() {
        loadConfig();
    }

    @Override
    public String getString(final String path) {
        return this.fileConfiguration.getString(path);
    }

    @Override
    public IConfigSection getConfigurationSection(final String path) {
        return new BukkitConfigSection(this.fileConfiguration.getConfigurationSection(path));
    }

    @Override
    public boolean getBoolean(final String path) {
        return this.fileConfiguration.getBoolean(path);
    }

    public void setInt(final String path, final int value) {
        this.fileConfiguration.set(path, value);
    }

    @Override
    public int getInt(final String path) {
        return this.fileConfiguration.getInt(path);
    }

    @Override
    public double getDouble(final String path) {
        return this.fileConfiguration.getDouble(path);
    }

    @Override
    public List<String> getStringList(final String path) {
        return this.fileConfiguration.getStringList(path);
    }

    @Override
    public boolean getBoolean(final String path, final boolean defaultValue) {
        return this.fileConfiguration.getBoolean(path, defaultValue);
    }

    @Override
    public Set<String> getKeys(final boolean recurse) {
        return this.fileConfiguration.getKeys(recurse);
    }

    @Override
    public String getString(final String path, final String defaultValue) {
        return this.fileConfiguration.getString(path, defaultValue);
    }

    @Override
    public void setInventory(final String path, final Inventory inventory) {
        InventoryConfig.serialize(this, path, inventory);
    }

    @Override
    public Inventory getInventory(final String path) {
        return InventoryConfig.deserialize(this.getConfigurationSection(path));
    }

    @Override
    public Inventory getInventory(final String path, final Inventory defaultInventory) {
        final var inventory = getInventory(path);
        return inventory == null ? defaultInventory : inventory;
    }

    @Override
    public void setItemStack(final String path, final ItemStack itemStack) {
        this.fileConfiguration.set(path, itemStack);
    }

    @Override
    public ItemStack getItemStack(final String path) {
        return this.fileConfiguration.getItemStack(path);
    }

    @Override
    public void setString(final String path, final String value) {
        this.fileConfiguration.set(path, value);
    }

    @Override
    public void setBoolean(final String path, final boolean value) {
        this.fileConfiguration.set(path, value);
    }

    @Override
    public void setDouble(final String path, final double value) {
        this.fileConfiguration.set(path, value);
    }

    @Override
    public void setLong(final String path, final long value) {
        this.fileConfiguration.set(path, value);
    }

    @Override
    public long getLong(final String path) {
        return this.fileConfiguration.getLong(path);
    }

    @Override
    public long getLong(final String path, final long defaultValue) {
        return this.fileConfiguration.getLong(path, defaultValue);
    }

    @Override
    public void remove(final String path) {
        this.fileConfiguration.set(path, null);
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public Set<String> getStringSet(final String path) {
        return new HashSet<String>(getStringList(path));
    }

    @Override
    public long getMillis(final String path) {
        return TimeUtils.parseMillis(this.fileConfiguration.getString(path));
    }

    @Override
    public long getMillis(final String path, final String defaultValue) {
        return TimeUtils.parseMillis(this.fileConfiguration.getString(path, defaultValue));
    }

    @Override
    public long getSeconds(final String path) {
        return TimeUtils.parseSeconds(this.fileConfiguration.getString(path));
    }

    @Override
    public long getSeconds(final String path, final String defaultValue) {
        return TimeUtils.parseSeconds(this.fileConfiguration.getString(path, defaultValue));
    }

    @Override
    public long getTicks(final String path) {
        return getSeconds(path) * 20;
    }

    @Override
    public long getTicks(final String path, final String defaultValue) {
        return getSeconds(path, defaultValue) * 20;
    }

    @Override
    public void setSeconds(final String path, final long seconds) {
        setString(path, String.valueOf(seconds) + "s");
    }

    @Override
    public void setMillis(final String path, final long millis) {
        setString(path, String.valueOf(millis) + "ms");
    }

    @Override
    public void setTicks(final String path, final long ticks) {
        setString(path, String.valueOf(ticks / 20) + "ms");
    }

    @Override
    public void setStringList(final String path, final List<String> values) {
        this.fileConfiguration.set(path, values);
    }

}
