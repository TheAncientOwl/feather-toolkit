/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file ArgsTest.java
 * @author Alexandru Delegeanu
 * @version 0.5
 * @test_unit Args#0.7
 * @description Unit tests for Args
 */

package dev.defaultybuf.feather.toolkit.util.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class ArgsTest {
    MockedStatic<Bukkit> mockedBukkit = null;

    @BeforeEach
    void setUpMocks() {
        mockedBukkit = mockStatic(Bukkit.class);
    }

    @AfterEach
    void tearDownMocks() {
        mockedBukkit.close();
    }

    @Test
    void testParseResultSuccess() {
        var parsedArgs = new Object[] {"test", 42, 3.14};
        var result = new Args.ParseResult(parsedArgs, Args.ParseResult.PARSE_SUCCESS_INDEX);

        assertTrue(result.success(), "ParseResult should indicate success");
        assertEquals("test", result.getString(0), "First argument should be 'test'");
        assertEquals(42, result.getInt(1), "Second argument should be 42");
        assertEquals(3.14, result.getDouble(2), "Third argument should be 3.14");
    }

    @Test
    void testParseResultFailure() {
        var result = new Args.ParseResult(null, 1);

        assertFalse(result.success(), "ParseResult should indicate failure");
        assertEquals(1, result.failIndex(), "Failure index should be 1");
    }

    @Test
    void testParse() {
        var mockPlayer = mock(Player.class);
        when(Bukkit.getPlayerExact("PlayerName")).thenReturn(mockPlayer);

        var mockOfflinePlayer = mock(OfflinePlayer.class);
        when(Bukkit.getOfflinePlayer("OfflinePlayerName")).thenReturn(mockOfflinePlayer);
        when(mockOfflinePlayer.hasPlayedBefore()).thenReturn(true);

        var mockWorld = mock(World.class);
        when(Bukkit.getWorld("WorldName")).thenReturn(mockWorld);

        String[] inputArgs =
                {"123", "4.56", "PlayerName", "OfflinePlayerName", "WorldName", "somestr"};

        var result =
                Args.parse(inputArgs, Args::getInt, Args::getDouble, Args::getOnlinePlayer,
                        Args::getOfflinePlayer, Args::getWorld, Args::getString);

        assertTrue(result.success(), "Parse should succeed for valid input");
        assertEquals(123, result.getInt(0), "1st parsed argument should be 123");
        assertEquals(4.56, result.getDouble(1), "2nd parsed argument should be 4.56");
        assertNotNull(result.getPlayer(2), "3rd parsed argument ~ Online player should be found");
        assertNotNull(result.getOfflinePlayer(3),
                "4th parsed argument ~ Offline player should be found");
        assertNotNull(result.getWorld(4), "5th parsed argument ~ World should be found");
        assertEquals("somestr", result.getString(5), "6th parsed argument should be somestr");
    }

    @Test
    void testParseFailure() {
        String[] inputArgs = {"123", "invalid"};
        var result = Args.parse(inputArgs, Args::getInt, Args::getDouble);

        assertFalse(result.success(), "Parse should fail for invalid input");
        assertEquals(1, result.failIndex(), "Failure should occur at index 1");
    }

    @Test
    void testGetOnlinePlayer() {
        var mockPlayer = mock(Player.class);
        when(Bukkit.getPlayerExact("PlayerName")).thenReturn(mockPlayer);

        var result = Args.getOnlinePlayer("PlayerName");

        assertNotNull(result, "Online player should be found");
        assertEquals(mockPlayer, result, "Returned player should match mock");
    }

    @Test
    void testGetOfflinePlayer() {
        var mockOfflinePlayer = mock(OfflinePlayer.class);
        when(Bukkit.getOfflinePlayer("PlayerName")).thenReturn(mockOfflinePlayer);
        when(mockOfflinePlayer.hasPlayedBefore()).thenReturn(true);

        var result = Args.getOfflinePlayer("PlayerName");

        assertNotNull(result, "Offline player should be found");
        assertEquals(mockOfflinePlayer, result, "Returned offline player should match mock");
    }

    @Test
    void testGetWorld() {
        var mockWorld = mock(World.class);
        when(Bukkit.getWorld("WorldName")).thenReturn(mockWorld);

        var result = Args.getWorld("WorldName");

        assertNotNull(result, "World should be found");
        assertEquals(mockWorld, result, "Returned world should match mock");
    }

    @Test
    void testGetInt() {
        assertEquals(123, Args.getInt("123"), "Valid integer string should parse correctly");
        assertNull(Args.getInt("invalid"), "Invalid integer string should return null");
    }

    @Test
    void testGetDouble() {
        assertEquals(3.14, Args.getDouble("3.14"), "Valid double string should parse correctly");
        assertNull(Args.getDouble("invalid"), "Invalid double string should return null");
    }

    @Test
    void testGetString() {
        assertEquals("test", Args.getString("test"), "String should be returned as is");
    }

    @Test
    void dummyConstructor() {
        @SuppressWarnings("unused")
        var Args = new Args(); // Args should contain only static methods
    }
}
