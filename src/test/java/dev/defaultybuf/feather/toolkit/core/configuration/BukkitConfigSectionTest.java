/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file BukkitConfigSectionTest.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @test_unit BukkitConfigSection#0.3
 * @description Unit tests for BukkitConfigSection
 */

package dev.defaultybuf.feather.toolkit.core.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.defaultybuf.feather.toolkit.core.configuration.bukkit.BukkitConfigSection;
import dev.defaultybuf.feather.toolkit.util.java.TimeUtils;
import dev.defaultybuf.feather.toolkit.util.parsing.InventoryConfig;

@ExtendWith(MockitoExtension.class)
class BukkitConfigSectionTest {
    @Mock ConfigurationSection mockConfigSection;

    BukkitConfigSection bukkitConfigSection;

    @BeforeEach
    void setUp() {
        bukkitConfigSection = new BukkitConfigSection(mockConfigSection);
    }

    @Test
    void testGetItemStack() {
        var expectedItemStack = mock(ItemStack.class);
        when(mockConfigSection.getItemStack("path")).thenReturn(expectedItemStack);

        var result = bukkitConfigSection.getItemStack("path");

        assertEquals(expectedItemStack, result);
    }

    @Test
    void testGetString() {
        when(mockConfigSection.getString("path")).thenReturn("value");

        var result = bukkitConfigSection.getString("path");

        assertEquals("value", result);
    }

    @Test
    void testGetBoolean() {
        when(mockConfigSection.getBoolean("path")).thenReturn(true);

        var result = bukkitConfigSection.getBoolean("path");

        assertTrue(result);
    }

    @Test
    void testGetBooleanWithDefault() {
        when(mockConfigSection.getBoolean("path", true)).thenReturn(true);

        var result = bukkitConfigSection.getBoolean("path", true);

        assertTrue(result);
    }

    @Test
    void testSetInt() {
        bukkitConfigSection.setInt("path", 42);

        verify(mockConfigSection).set("path", 42);
    }

    @Test
    void testGetInt() {
        when(mockConfigSection.getInt("path")).thenReturn(42);

        var result = bukkitConfigSection.getInt("path");

        assertEquals(42, result);
    }

    @Test
    void testGetDouble() {
        when(mockConfigSection.getDouble("path")).thenReturn(42.42);

        var result = bukkitConfigSection.getDouble("path");

        assertEquals(42.42, result);
    }

    @Test
    void testGetLong() {
        when(mockConfigSection.getLong("path")).thenReturn(42L);

        var result = bukkitConfigSection.getLong("path");

        assertEquals(42L, result);
    }

    @Test
    void testGetLongWithDefault() {
        when(mockConfigSection.getLong("path", 42L)).thenReturn(42L);

        var result = bukkitConfigSection.getLong("path", 42L);

        assertEquals(42L, result);
    }

    @Test
    void testGetStringList() {
        var expectedList = List.of("value1", "value2");
        when(mockConfigSection.getStringList("path")).thenReturn(expectedList);

        var result = bukkitConfigSection.getStringList("path");

        assertEquals(expectedList, result);
    }

    @Test
    void testGetKeys() {
        var expectedKeys = Set.of("key1", "key2");
        when(mockConfigSection.getKeys(false)).thenReturn(expectedKeys);

        var result = bukkitConfigSection.getKeys(false);

        assertEquals(expectedKeys, result);
    }

    @Test
    void testSetItemStack() {
        var mockItemStack = mock(ItemStack.class);
        bukkitConfigSection.setItemStack("path", mockItemStack);

        verify(mockConfigSection).set("path", mockItemStack);
    }

    @Test
    void testSetString() {
        bukkitConfigSection.setString("path", "value");

        verify(mockConfigSection).set("path", "value");
    }

    @Test
    void testSetBoolean() {
        bukkitConfigSection.setBoolean("path", true);

        verify(mockConfigSection).set("path", true);
    }

    @Test
    void testSetDouble() {
        bukkitConfigSection.setDouble("path", 42.42);

        verify(mockConfigSection).set("path", 42.42);
    }

    @Test
    void testSetLong() {
        bukkitConfigSection.setLong("path", 42L);

        verify(mockConfigSection).set("path", 42L);
    }

    @Test
    void testRemove() {
        bukkitConfigSection.remove("path");

        verify(mockConfigSection).set("path", null);
    }

    @Test
    void testGetStringSet() {
        var expectedList = List.of("value1", "value2");
        when(mockConfigSection.getStringList("path")).thenReturn(expectedList);

        var result = bukkitConfigSection.getStringSet("path");

        assertEquals(new HashSet<>(expectedList), result);
    }

    @Test
    void testGetMillis() {
        when(mockConfigSection.getString("path")).thenReturn("42ms");

        var result = bukkitConfigSection.getMillis("path");

        assertEquals(TimeUtils.parseMillis("42ms"), result);
    }

    @Test
    void testGetMillisWithDefault() {
        when(mockConfigSection.getString("path", "42ms")).thenReturn("42ms");

        var result = bukkitConfigSection.getMillis("path", "42ms");

        assertEquals(TimeUtils.parseMillis("42ms"), result);
    }

    @Test
    void testGetSeconds() {
        when(mockConfigSection.getString("path")).thenReturn("42s");

        var result = bukkitConfigSection.getSeconds("path");

        assertEquals(TimeUtils.parseSeconds("42s"), result);
    }

    @Test
    void testGetSecondsWithDefault() {
        when(mockConfigSection.getString("path", "42s")).thenReturn("42s");

        var result = bukkitConfigSection.getSeconds("path", "42s");

        assertEquals(TimeUtils.parseSeconds("42s"), result);
    }

    @Test
    void testGetTicks() {
        when(mockConfigSection.getString("path")).thenReturn("42s");

        var result = bukkitConfigSection.getTicks("path");

        assertEquals(TimeUtils.parseSeconds("42s") * 20, result);
    }

    @Test
    void testGetTicksWithDefault() {
        when(mockConfigSection.getString("path", "42s")).thenReturn("42s");

        var result = bukkitConfigSection.getTicks("path", "42s");

        assertEquals(TimeUtils.parseSeconds("42s") * 20, result);
    }

    @Test
    void testSetSeconds() {
        bukkitConfigSection.setSeconds("path", 42L);

        verify(mockConfigSection).set("path", "42s");
    }

    @Test
    void testSetMillis() {
        bukkitConfigSection.setMillis("path", 42L);

        verify(mockConfigSection).set("path", "42ms");
    }

    @Test
    void testSetTicks() {
        bukkitConfigSection.setTicks("path", 42L);

        verify(mockConfigSection).set("path", "2ms");
    }

    @Test
    void testSetStringList() {
        var values = List.of("value1", "value2");
        bukkitConfigSection.setStringList("path", values);

        verify(mockConfigSection).set("path", values);
    }

    @Test
    void testGetConfigurationSection() {
        var mockConfigSection2 = mock(ConfigurationSection.class);

        when(mockConfigSection2.getString("test.path")).thenReturn("TestString");

        when(mockConfigSection.getConfigurationSection("path"))
                .thenReturn(mockConfigSection2);

        var resultConfig = bukkitConfigSection.getConfigurationSection("path");

        assertNotNull(resultConfig);

        var resultGet = resultConfig.getString("test.path");
        assertEquals("TestString", resultGet);
        verify(mockConfigSection2).getString("test.path");
    }

    @Test
    void testSetInventory() {
        var mockInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            bukkitConfigSection.setInventory("path", mockInventory);

            mockedInventoryConfig.verify(
                    () -> InventoryConfig.serialize(bukkitConfigSection, "path", mockInventory));
        }
    }

    @Test
    void testGetInventory() {
        var mockInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            when(mockConfigSection.getConfigurationSection("path")).thenReturn(mockConfigSection);
            mockedInventoryConfig.when(() -> InventoryConfig.deserialize(any()))
                    .thenReturn(mockInventory);

            var result = bukkitConfigSection.getInventory("path");

            assertEquals(mockInventory, result);
        }
    }

    @Test
    void testGetInventoryWithDefault() {
        var defaultInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            when(mockConfigSection.getConfigurationSection("path")).thenReturn(mockConfigSection);
            mockedInventoryConfig.when(() -> InventoryConfig.deserialize(any())).thenReturn(null);

            var result = bukkitConfigSection.getInventory("path", defaultInventory);

            assertEquals(defaultInventory, result);
        }
    }

    @Test
    void testGetInventoryWithDefault_NonNullInventory() {
        var mockInventory = mock(Inventory.class);
        var defaultInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            when(mockConfigSection.getConfigurationSection("path")).thenReturn(mockConfigSection);
            mockedInventoryConfig.when(() -> InventoryConfig.deserialize(any()))
                    .thenReturn(mockInventory);

            var result = bukkitConfigSection.getInventory("path", defaultInventory);

            assertEquals(mockInventory, result);
        }
    }

}
