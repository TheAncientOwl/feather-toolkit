/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file InventoryConfig.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Utility class for serializing/deserializing bukking Inventory in yaml format
 */

package dev.defaultybuf.feather.toolkit.util.parsing;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.defaultybuf.feather.toolkit.api.configuration.IPropertyAccessor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class InventoryConfig {
    private static final int MAX_INVENTORY_SIZE = 54;

    public static void serialize(final IPropertyAccessor config, final String path,
            final Inventory inventory) {
        final var inventorySize = inventory.getSize();
        config.setInt(path + ".size", Math.min(inventorySize, MAX_INVENTORY_SIZE));

        for (int index = 0; index < inventorySize; index++) {
            final ItemStack itemStack = inventory.getItem(index);
            if (itemStack != null) {
                config.setItemStack(path + ".content." + index, itemStack);
            }
        }
    }

    public static Inventory deserialize(final IPropertyAccessor inventoryConfig) {
        if (inventoryConfig == null) {
            return null;
        }

        try {
            final var inventorySize = Math.min(inventoryConfig.getInt("size"), MAX_INVENTORY_SIZE);
            Inventory inventory = Bukkit.createInventory(null, inventorySize,
                    LegacyComponentSerializer.legacyAmpersand().deserialize(
                            inventoryConfig.getString("display-name")));

            final IPropertyAccessor content = inventoryConfig.getConfigurationSection("content");
            for (final var slot : content.getKeys(false)) {
                final var itemStack = content.getItemStack(slot);
                inventory.setItem(Integer.parseInt(slot), itemStack);
            }

            return inventory;
        } catch (final Exception e) {
            return null;
        }
    }
}
