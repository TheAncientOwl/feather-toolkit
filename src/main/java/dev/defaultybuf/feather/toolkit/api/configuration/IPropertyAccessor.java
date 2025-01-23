/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file IPropertyAccessor.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @description Config property accessor interface
 */

package dev.defaultybuf.feather.toolkit.api.configuration;

import java.util.List;
import java.util.Set;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IPropertyAccessor {
    public void remove(final String path);

    public void setString(final String path, final String value);

    public String getString(final String path);

    public String getString(final String path, final String defaultValue);

    public void setBoolean(final String path, final boolean value);

    public boolean getBoolean(final String path);

    public boolean getBoolean(final String path, final boolean defaultValue);

    public void setInt(final String path, final int value);

    public int getInt(final String path);

    public void setDouble(final String path, final double value);

    public double getDouble(final String path);

    public IConfigSection getConfigurationSection(final String path);

    public void setStringList(final String path, final List<String> values);

    public List<String> getStringList(final String path);

    public Set<String> getKeys(final boolean recurse);

    public Inventory getInventory(final String path);

    public Inventory getInventory(final String path, final Inventory defaultInventory);

    public void setInventory(final String path, final Inventory inventory);

    public void setItemStack(final String path, final ItemStack itemStack);

    public ItemStack getItemStack(final String path);

    public void setLong(final String path, final long value);

    public long getLong(final String path);

    public long getLong(final String path, final long defaultValue);

    public Set<String> getStringSet(final String path);

    public long getSeconds(final String path);

    public long getSeconds(final String path, final String defaultValue);

    public void setSeconds(final String path, final long seconds);

    public long getMillis(final String path);

    public long getMillis(final String path, final String defaultValue);

    public void setMillis(final String path, final long millis);

    public long getTicks(final String path);

    public long getTicks(final String path, final String defaultValue);

    public void setTicks(final String path, final long ticks);
}
