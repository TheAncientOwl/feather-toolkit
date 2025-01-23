/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file BukkitConfigSection.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @description Bukkit implementation of @see IConfigSection
 */

package dev.defaultybuf.feather.toolkit.core.configuration.bukkit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.defaultybuf.feather.toolkit.api.configuration.IConfigSection;
import dev.defaultybuf.feather.toolkit.util.java.TimeUtils;
import dev.defaultybuf.feather.toolkit.util.parsing.InventoryConfig;

public class BukkitConfigSection implements IConfigSection {
    private final ConfigurationSection configSection;

    public BukkitConfigSection(final ConfigurationSection configSection) {
        this.configSection = configSection;
    }

    @Override
    public String getString(final String path) {
        return this.configSection.getString(path);
    }

    @Override
    public boolean getBoolean(final String path) {
        return this.configSection.getBoolean(path);
    }

    public void setInt(final String path, final int value) {
        this.configSection.set(path, value);
    }

    @Override
    public int getInt(final String path) {
        return this.configSection.getInt(path);
    }

    @Override
    public double getDouble(final String path) {
        return this.configSection.getDouble(path);
    }

    @Override
    public IConfigSection getConfigurationSection(final String path) {
        return new BukkitConfigSection(this.configSection.getConfigurationSection(path));
    }

    @Override
    public List<String> getStringList(final String path) {
        return this.configSection.getStringList(path);
    }

    @Override
    public boolean getBoolean(final String path, final boolean defaultValue) {
        return this.configSection.getBoolean(path, defaultValue);
    }

    @Override
    public Set<String> getKeys(final boolean recurse) {
        return this.configSection.getKeys(recurse);
    }

    @Override
    public String getString(final String path, final String defaultValue) {
        return this.configSection.getString(path, defaultValue);
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
        this.configSection.set(path, itemStack);
    }

    @Override
    public ItemStack getItemStack(final String path) {
        return this.configSection.getItemStack(path);
    }

    @Override
    public void setString(final String path, final String value) {
        this.configSection.set(path, value);
    }

    @Override
    public void setBoolean(final String path, final boolean value) {
        this.configSection.set(path, value);
    }

    @Override
    public void setDouble(final String path, final double value) {
        this.configSection.set(path, value);
    }

    @Override
    public void setLong(final String path, final long value) {
        this.configSection.set(path, value);
    }

    @Override
    public long getLong(final String path) {
        return this.configSection.getLong(path);
    }

    @Override
    public long getLong(final String path, final long defaultValue) {
        return this.configSection.getLong(path, defaultValue);
    }

    @Override
    public void remove(final String path) {
        this.configSection.set(path, null);
    }

    @Override
    public Set<String> getStringSet(final String path) {
        return new HashSet<String>(getStringList(path));
    }

    @Override
    public long getMillis(final String path) {
        return TimeUtils.parseMillis(this.configSection.getString(path));
    }

    @Override
    public long getMillis(final String path, final String defaultValue) {
        return TimeUtils.parseMillis(this.configSection.getString(path, defaultValue));
    }

    @Override
    public long getSeconds(final String path) {
        return TimeUtils.parseSeconds(this.configSection.getString(path));
    }

    @Override
    public long getSeconds(final String path, final String defaultValue) {
        return TimeUtils.parseSeconds(this.configSection.getString(path, defaultValue));
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
        this.configSection.set(path, values);
    }

}
