/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file LanguageChangeEventTest.java
 * @author Alexandru Delegeanu
 * @version 0.5
 * @test_unit LanguageChangeEvent#0.3
 * @description Unit tests for LanguageChangeEvent
 */

package dev.defaultybuf.feather.toolkit.core.modules.language.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;

@ExtendWith(MockitoExtension.class)
class LanguageChangeEventTest {
    @Mock Player mockPlayer = null;
    @Mock IConfigFile mockTranslation = null;

    LanguageChangeEvent event = null;

    @BeforeEach
    void setUp() {
        event = new LanguageChangeEvent(mockPlayer, "en", mockTranslation);
    }

    @Test
    void testConstructor_ShouldInitializeFieldsCorrectly() {
        assertEquals(mockPlayer, event.getPlayer());
        assertEquals("en", event.getLanguage());
        assertEquals(mockTranslation, event.getTranslation());
        assertFalse(event.isCancelled(), "Event should not be cancelled by default");
    }

    @Test
    void testSetCancelled_ShouldChangeCancelledState() {
        assertFalse(event.isCancelled(), "Event should not be cancelled by default");

        event.setCancelled(true);
        assertTrue(event.isCancelled(), "Event should be marked as cancelled");

        event.setCancelled(false);
        assertFalse(event.isCancelled(), "Event should be marked as not cancelled");
    }

    @Test
    void testGetHandlers_ShouldReturnHandlerList() {
        var handlers = event.getHandlers();
        assertNotNull(handlers, "HandlerList should not be null");
        assertSame(handlers, LanguageChangeEvent.getHandlerList(),
                "getHandlers and getHandlerList should return the same instance");
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    void testEquals() {
        // Mock the Player object
        var mockPlayer1 = mock(Player.class);
        var mockPlayer2 = mock(Player.class);

        var mockConfigEn = mock(IConfigFile.class);
        var mockConfigDe = mock(IConfigFile.class);

        when(mockPlayer1.getName()).thenReturn("Player1");
        when(mockPlayer2.getName()).thenReturn("Player2");

        var event1 = new LanguageChangeEvent(mockPlayer1, "en", mockConfigEn);
        var event2 = new LanguageChangeEvent(mockPlayer1, "en", mockConfigEn);
        var event3 = new LanguageChangeEvent(mockPlayer2, "de", mockConfigDe);
        var event4 = new LanguageChangeEvent(mockPlayer1, "de", mockConfigDe);
        var event5 = new LanguageChangeEvent(mockPlayer1, "en", mockConfigDe);

        assertFalse(event1.equals("SomeString"), "Other types should not be equal to event");
        assertFalse(event1.equals(null), "Event should be different than null");
        assertTrue(event1.equals(event1), "An event should be equal to itself");
        assertTrue(event1.equals(event2),
                "Events with the same player and language should be equal");
        assertFalse(event1.equals(event3),
                "Events with different players or languages should not be equal");

        lenient().when(mockPlayer1.getName()).thenReturn("Player");
        lenient().when(mockPlayer2.getName()).thenReturn("Player");

        assertFalse(event1.equals(event4),
                "Events with same player, different language should not be equal");

        assertFalse(event1.equals(event5),
                "Events with same player, same language, different translation should not be equal");
    }
}
