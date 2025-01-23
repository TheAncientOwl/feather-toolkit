/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file StringUtilsTest.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @test_unit StringUtils#0.3
 * @description Unit tests for StringUtils
 */

package dev.defaultybuf.feather.toolkit.util.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.md_5.bungee.api.ChatColor;

class StringUtilsTest {

    @BeforeEach
    void setUp() {
        // Set up necessary conditions for each test if needed
    }

    @Test
    void testExceptionToStr() {
        var exception = new Exception("Test exception");
        var result = StringUtils.exceptionToStr(exception);
        assertNotNull(result);
        assertTrue(result.contains("Test exception"));
    }

    @Test
    void testReplacePlaceholdersSingleReplacement() {
        var message = "Hello, {name}!";
        var replacement = Pair.of("{name}", (Object) "John");
        var result = StringUtils.replacePlaceholders(message, replacement);
        assertEquals("Hello, John!", result);
    }

    @Test
    void testReplacePlaceholdersMultipleReplacements() {
        var message = "Hello, {name}! Your balance is {balance}.";
        var replacements = new ArrayList<Pair<String, Object>>();
        replacements.add(Pair.of("{name}", "John"));
        replacements.add(Pair.of("{balance}", 100));

        var result = StringUtils.replacePlaceholders(message, replacements);
        assertEquals("Hello, John! Your balance is 100.", result);
    }

    @Test
    void testGetOnlinePlayers() {
        // Mock the Bukkit API
        var mockPlayer1 = mock(Player.class);
        var mockPlayer2 = mock(Player.class);
        when(mockPlayer1.getName()).thenReturn("Player1");
        when(mockPlayer2.getName()).thenReturn("Player2");

        try (var mockedBukkit = mockStatic(Bukkit.class)) {
            // Return the mock players when Bukkit.getOnlinePlayers() is called
            mockedBukkit.when(Bukkit::getOnlinePlayers)
                    .thenReturn(List.of(mockPlayer1, mockPlayer2));

            // Test the method
            var players = StringUtils.getOnlinePlayers();
            assertNotNull(players);
            assertEquals(2, players.size());
            assertTrue(players.contains("Player1"));
            assertTrue(players.contains("Player2"));
        }
    }

    @Test
    void testGetWorlds() {
        // Mock the Bukkit API
        var mockWorld1 = mock(World.class);
        var mockWorld2 = mock(World.class);
        when(mockWorld1.getName()).thenReturn("World1");
        when(mockWorld2.getName()).thenReturn("World2");

        try (var mockedBukkit = mockStatic(Bukkit.class)) {
            // Return the mock worlds when Bukkit.getWorlds() is called
            mockedBukkit.when(Bukkit::getWorlds).thenReturn(List.of(mockWorld1, mockWorld2));

            var worlds = StringUtils.getWorlds();
            assertNotNull(worlds);
            assertEquals(2, worlds.size());
            assertTrue(worlds.contains("World1"));
            assertTrue(worlds.contains("World2"));
        }
    }

    @Test
    void testFilterStartingWith() {
        var list = List.of("Apple", "Banana", "Cherry", "Avocado");
        var result = StringUtils.filterStartingWith(list, "A");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("Apple"));
        assertTrue(result.contains("Avocado"));
    }

    @Test
    void testTranslateColors() {
        var message = "&aGreen &cRed";
        var result = StringUtils.translateColors(message);
        assertEquals(ChatColor.GREEN + "Green " + ChatColor.RED + "Red", result);
    }

    @Test
    void dummyConstructor() {
        @SuppressWarnings("unused")
        var StringUtils = new StringUtils(); // StringUtils should contain only static methods
    }
}
