/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file BukkitConfigFileTest.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @test_unit BukkitConfigFile#0.3
 * @description Unit tests for BukkitConfigFile
 */

package dev.defaultybuf.feather.toolkit.core.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.defaultybuf.feather.toolkit.core.configuration.bukkit.BukkitConfigFile;
import dev.defaultybuf.feather.toolkit.testing.utils.TempFile;
import dev.defaultybuf.feather.toolkit.testing.utils.TestUtils;
import dev.defaultybuf.feather.toolkit.util.java.TimeUtils;
import dev.defaultybuf.feather.toolkit.util.parsing.InventoryConfig;

@ExtendWith(MockitoExtension.class)
class BukkitConfigFileTest {
    @Mock JavaPlugin mockPlugin;
    @Mock YamlConfiguration mockFileConfiguration;

    BukkitConfigFile bukkitConfigFile;
    TempFile testYml;

    @BeforeEach
    void setUp() throws IOException {
        testYml = TestUtils.makeTempFile(TestUtils.getTestDataFolderPath().resolve("test.yml"));

        lenient().when(mockPlugin.getDataFolder()).thenReturn(TestUtils.getTestDataFolder());

        try (var mockedYaml = mockStatic(YamlConfiguration.class)) {
            mockedYaml.when(() -> YamlConfiguration.loadConfiguration(any(File.class)))
                    .thenReturn(mockFileConfiguration);
            bukkitConfigFile = new BukkitConfigFile(mockPlugin, "test.yml");
            assertEquals("test.yml", bukkitConfigFile.getFileName());
        }
    }

    @AfterEach
    void tearDown() {
        testYml.close();
    }

    @Test
    void testConstructor_FileDoesNotExist() {
        testYml.close();
        assertThrows(IOException.class, () -> new BukkitConfigFile(mockPlugin, "test.yml"));
    }

    @Test
    void testSaveConfig() throws IOException {
        bukkitConfigFile.saveConfig();

        verify(mockFileConfiguration).save(any(File.class));
    }

    @Test
    void testLoadConfig() {
        try (var mockedYaml = mockStatic(YamlConfiguration.class)) {
            mockedYaml.when(() -> YamlConfiguration.loadConfiguration(any(File.class)))
                    .thenReturn(mockFileConfiguration);

            bukkitConfigFile.loadConfig();

            assertNotNull(bukkitConfigFile.getConfigurationSection("path"));
        }
    }

    @Test
    void testReloadConfig() {
        assertDoesNotThrow(() -> {
            bukkitConfigFile.reloadConfig();
        });
    }

    @Test
    void testGetString() {
        when(mockFileConfiguration.getString("path")).thenReturn("value");

        var result = bukkitConfigFile.getString("path");

        assertEquals("value", result);
    }

    @Test
    void testGetStringWithDefault() {
        when(mockFileConfiguration.getString("path", "default")).thenReturn("value");

        var result = bukkitConfigFile.getString("path", "default");

        assertEquals("value", result);
    }

    @Test
    void testGetBoolean() {
        when(mockFileConfiguration.getBoolean("path")).thenReturn(true);

        var result = bukkitConfigFile.getBoolean("path");

        assertTrue(result);
    }

    @Test
    void testGetBooleanWithDefault() {
        when(mockFileConfiguration.getBoolean("path", true)).thenReturn(true);

        var result = bukkitConfigFile.getBoolean("path", true);

        assertTrue(result);
    }

    @Test
    void testSetInt() {
        bukkitConfigFile.setInt("path", 42);

        verify(mockFileConfiguration).set("path", 42);
    }

    @Test
    void testGetInt() {
        when(mockFileConfiguration.getInt("path")).thenReturn(42);

        var result = bukkitConfigFile.getInt("path");

        assertEquals(42, result);
    }

    @Test
    void testGetDouble() {
        when(mockFileConfiguration.getDouble("path")).thenReturn(42.42);

        var result = bukkitConfigFile.getDouble("path");

        assertEquals(42.42, result);
    }

    @Test
    void testGetLong() {
        when(mockFileConfiguration.getLong("path")).thenReturn(42L);

        var result = bukkitConfigFile.getLong("path");

        assertEquals(42L, result);
    }

    @Test
    void testGetLongWithDefault() {
        when(mockFileConfiguration.getLong("path", 42L)).thenReturn(42L);

        var result = bukkitConfigFile.getLong("path", 42L);

        assertEquals(42L, result);
    }

    @Test
    void testGetStringList() {
        var expectedList = List.of("value1", "value2");
        when(mockFileConfiguration.getStringList("path")).thenReturn(expectedList);

        var result = bukkitConfigFile.getStringList("path");

        assertEquals(expectedList, result);
    }

    @Test
    void testGetKeys() {
        var expectedKeys = Set.of("key1", "key2");
        when(mockFileConfiguration.getKeys(false)).thenReturn(expectedKeys);

        var result = bukkitConfigFile.getKeys(false);

        assertEquals(expectedKeys, result);
    }

    @Test
    void testSetItemStack() {
        var mockItemStack = mock(ItemStack.class);
        bukkitConfigFile.setItemStack("path", mockItemStack);

        verify(mockFileConfiguration).set("path", mockItemStack);
    }

    @Test
    void testGetItemStack() {
        var expectedItemStack = mock(ItemStack.class);
        when(mockFileConfiguration.getItemStack("path")).thenReturn(expectedItemStack);

        var result = bukkitConfigFile.getItemStack("path");

        assertEquals(expectedItemStack, result);
    }

    @Test
    void testSetString() {
        bukkitConfigFile.setString("path", "value");

        verify(mockFileConfiguration).set("path", "value");
    }

    @Test
    void testSetBoolean() {
        bukkitConfigFile.setBoolean("path", true);

        verify(mockFileConfiguration).set("path", true);
    }

    @Test
    void testSetDouble() {
        bukkitConfigFile.setDouble("path", 42.42);

        verify(mockFileConfiguration).set("path", 42.42);
    }

    @Test
    void testSetLong() {
        bukkitConfigFile.setLong("path", 42L);

        verify(mockFileConfiguration).set("path", 42L);
    }

    @Test
    void testRemove() {
        bukkitConfigFile.remove("path");

        verify(mockFileConfiguration).set("path", null);
    }

    @Test
    void testGetStringSet() {
        var expectedList = List.of("value1", "value2");
        when(mockFileConfiguration.getStringList("path")).thenReturn(expectedList);

        var result = bukkitConfigFile.getStringSet("path");

        assertEquals(new HashSet<>(expectedList), result);
    }

    @Test
    void testGetMillis() {
        when(mockFileConfiguration.getString("path")).thenReturn("42ms");

        var result = bukkitConfigFile.getMillis("path");

        assertEquals(TimeUtils.parseMillis("42ms"), result);
    }

    @Test
    void testGetMillisWithDefault() {
        when(mockFileConfiguration.getString("path", "42ms")).thenReturn("42ms");

        var result = bukkitConfigFile.getMillis("path", "42ms");

        assertEquals(TimeUtils.parseMillis("42ms"), result);
    }

    @Test
    void testGetSeconds() {
        when(mockFileConfiguration.getString("path")).thenReturn("42s");

        var result = bukkitConfigFile.getSeconds("path");

        assertEquals(TimeUtils.parseSeconds("42s"), result);
    }

    @Test
    void testGetSecondsWithDefault() {
        when(mockFileConfiguration.getString("path", "42s")).thenReturn("42s");

        var result = bukkitConfigFile.getSeconds("path", "42s");

        assertEquals(TimeUtils.parseSeconds("42s"), result);
    }

    @Test
    void testGetTicks() {
        when(mockFileConfiguration.getString("path")).thenReturn("42s");

        var result = bukkitConfigFile.getTicks("path");

        assertEquals(TimeUtils.parseSeconds("42s") * 20, result);
    }

    @Test
    void testGetTicksWithDefault() {
        when(mockFileConfiguration.getString("path", "42s")).thenReturn("42s");

        var result = bukkitConfigFile.getTicks("path", "42s");

        assertEquals(TimeUtils.parseSeconds("42s") * 20, result);
    }

    @Test
    void testSetSeconds() {
        bukkitConfigFile.setSeconds("path", 42L);

        verify(mockFileConfiguration).set("path", "42s");
    }

    @Test
    void testSetMillis() {
        bukkitConfigFile.setMillis("path", 42L);

        verify(mockFileConfiguration).set("path", "42ms");
    }

    @Test
    void testSetTicks() {
        bukkitConfigFile.setTicks("path", 42L);

        verify(mockFileConfiguration).set("path", "2ms");
    }

    @Test
    void testSetStringList() {
        var values = List.of("value1", "value2");
        bukkitConfigFile.setStringList("path", values);

        verify(mockFileConfiguration).set("path", values);
    }

    @Test
    void testSetInventory() {
        var mockInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            bukkitConfigFile.setInventory("path", mockInventory);

            mockedInventoryConfig.verify(
                    () -> InventoryConfig.serialize(bukkitConfigFile, "path", mockInventory));
        }
    }

    @Test
    void testGetInventory() {
        var mockInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            when(mockFileConfiguration.getConfigurationSection("path"))
                    .thenReturn(mockFileConfiguration);
            mockedInventoryConfig.when(() -> InventoryConfig.deserialize(any()))
                    .thenReturn(mockInventory);

            var result = bukkitConfigFile.getInventory("path");

            assertEquals(mockInventory, result);
        }
    }

    @Test
    void testGetInventoryWithDefault() {
        var defaultInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            when(mockFileConfiguration.getConfigurationSection("path"))
                    .thenReturn(mockFileConfiguration);
            mockedInventoryConfig.when(() -> InventoryConfig.deserialize(any())).thenReturn(null);

            var result = bukkitConfigFile.getInventory("path", defaultInventory);

            assertEquals(defaultInventory, result);
        }
    }

    @Test
    void testGetInventoryWithDefault_NonNullInventory() {
        var mockInventory = mock(Inventory.class);
        var defaultInventory = mock(Inventory.class);
        try (var mockedInventoryConfig = mockStatic(InventoryConfig.class)) {
            when(mockFileConfiguration.getConfigurationSection("path"))
                    .thenReturn(mockFileConfiguration);
            mockedInventoryConfig.when(() -> InventoryConfig.deserialize(any()))
                    .thenReturn(mockInventory);

            var result = bukkitConfigFile.getInventory("path", defaultInventory);

            assertEquals(mockInventory, result);
        }
    }
}
