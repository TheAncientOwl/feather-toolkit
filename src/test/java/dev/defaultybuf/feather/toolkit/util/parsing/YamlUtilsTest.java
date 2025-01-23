/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file YamlUtils.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @test_unit YamlUtils#0.1
 * @description Unit tests for YamlUtils
 */

package dev.defaultybuf.feather.toolkit.util.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.defaultybuf.feather.toolkit.exceptions.FeatherSetupException;

@ExtendWith(MockitoExtension.class)
class YamlUtilsTest {

    @Test
    void testLoadYaml() throws Exception {
        // Mock the plugin and set up resource handling
        var mockPlugin = mock(JavaPlugin.class);
        var fileName = "valid.yml";

        var yamlContent = "value: 25\nother-value: 30";
        var mockInputStream = new ByteArrayInputStream(yamlContent.getBytes());

        // Mock the behavior of getResource to return your InputStream
        when(mockPlugin.getResource(fileName)).thenReturn(mockInputStream);

        // Call the method under test
        var fileConfiguration = YamlUtils.loadYaml(mockPlugin, fileName);

        // Assertions to check if the configuration is correctly loaded
        assertNotNull(fileConfiguration);
        assertEquals(25, fileConfiguration.getInt("value"));
        assertEquals(30, fileConfiguration.getInt("other-value"));
    }

    @Test
    void testLoadYaml_FileNotFound() {
        // Mock JavaPlugin
        var mockPlugin = mock(JavaPlugin.class);

        when(mockPlugin.getResource("missing.yml")).thenReturn(null);

        // Call method under test and assert exception
        var exception = assertThrows(FeatherSetupException.class, () -> {
            YamlUtils.loadYaml(mockPlugin, "missing.yml");
        });

        assertTrue(exception.getMessage().contains("missing.yml not found in plugin resources"),
                "Exception message should indicate missing file");
    }

    @Test
    void testLoadYaml_IOException() {
        // Mock JavaPlugin and input stream that throws IOException
        var mockPlugin = mock(JavaPlugin.class);
        var mockInputStream = mock(InputStream.class);

        // Mock the resource retrieval
        when(mockPlugin.getResource("invalid.yml")).thenReturn(mockInputStream);
        try {
            doThrow(new IOException("Stream read error")).when(mockInputStream).close();
        } catch (IOException e) {
            fail("Mock setup failed");
        }

        // Mock static Bukkit.getServer() and its logger
        try (var mockedBukkit = mockStatic(Bukkit.class)) {
            var mockServer = mock(Server.class);
            var mockLogger = mock(Logger.class);

            // Mock Bukkit.getServer() and Bukkit.getLogger()
            mockedBukkit.when(Bukkit::getServer).thenReturn(mockServer);
            mockedBukkit.when(Bukkit::getLogger).thenReturn(mockLogger);

            // Call method under test and assert exception
            var exception = assertThrows(FeatherSetupException.class, () -> {
                YamlUtils.loadYaml(mockPlugin, "invalid.yml");
            });

            assertTrue(exception.getMessage().contains("Error on parsing resource"),
                    "Exception message should indicate parsing error");
        }
    }

    @Test
    void testLoadYaml_NullFileConfiguration() {
        // Mock JavaPlugin and return valid input stream, but simulate null
        // FileConfiguration
        var mockPlugin = mock(JavaPlugin.class);
        var mockInputStream = new ByteArrayInputStream("".getBytes());

        when(mockPlugin.getResource("empty.yml")).thenReturn(mockInputStream);

        // Spy on YamlConfiguration to simulate a null return
        try (var mockedYamlConfiguration = mockStatic(YamlConfiguration.class)) {
            mockedYamlConfiguration
                    .when(() -> YamlConfiguration.loadConfiguration(any(InputStreamReader.class)))
                    .thenReturn(null);

            // Call method under test and assert exception
            var exception = assertThrows(FeatherSetupException.class, () -> {
                YamlUtils.loadYaml(mockPlugin, "empty.yml");
            });

            assertTrue(exception.getMessage().contains("Failed to read yaml resource"),
                    "Exception message should indicate null FileConfiguration");
        }
    }

    @Test
    void dummyConstructor() {
        @SuppressWarnings("unused")
        var YamlUtils = new YamlUtils(); // YamlUtils should contain only static methods
    }
}
