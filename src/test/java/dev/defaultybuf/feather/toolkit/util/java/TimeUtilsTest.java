/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file TimeUtilsTest.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @test_unit StringUtils#0.3
 * @description Unit tests for StringUtils
 */

package dev.defaultybuf.feather.toolkit.util.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TimeUtilsTest {

    @Test
    void testFormatElapsed() {
        assertEquals("5s", TimeUtils.formatElapsed(0L, 5000L)); // 5 seconds
        assertEquals("1h, 30m, 45s", TimeUtils.formatElapsed(0L, 5445000L)); // 1 hour, 30 minutes,
                                                                             // 45 seconds
        assertEquals("2m, 30s", TimeUtils.formatElapsed(0L, 150000L)); // 2 minutes, 30 seconds
        assertEquals("500ms", TimeUtils.formatElapsed(0L, 500L)); // 500 milliseconds
        assertEquals("0s", TimeUtils.formatElapsed(0L, 0L)); // 0 milliseconds should show as "0s"
        assertEquals("1h, 23m, 45s, 567ms",
                TimeUtils.formatElapsed(0L, 5025567L));// 1 hour, 23 minutes, 45 seconds, and 567
                                                       // milliseconds

        final var c_delay = 534543L;
        assertEquals("5s", TimeUtils.formatElapsed(0L + c_delay, 5000L + c_delay)); // 5 seconds
        assertEquals("1h, 30m, 45s",
                TimeUtils.formatElapsed(0L + c_delay, 5445000L + c_delay)); // 1 hour, 30 minutes,
                                                                            // 45 seconds

        assertEquals("2m, 30s", TimeUtils.formatElapsed(0L + c_delay, 150000L + c_delay)); // 2
                                                                                           // minutes,
                                                                                           // 30
                                                                                           // seconds
        assertEquals("500ms", TimeUtils.formatElapsed(0L + c_delay, 500L + c_delay)); // 500
                                                                                      // milliseconds
        assertEquals("0s", TimeUtils.formatElapsed(0L + c_delay, 0L + c_delay)); // 0 milliseconds
                                                                                 // should show as
                                                                                 // "0s"
        assertEquals("1h, 23m, 45s, 567ms",
                TimeUtils.formatElapsed(0L + c_delay, 5025567L + c_delay));// 1 hour, 23 minutes, 45
                                                                           // seconds,
                                                                           // and 567 milliseconds
    }

    @Test
    void testFormatRemaining() {
        var startMillis = Clock.currentTimeMillis() - 5000L; // 5 seconds ago
        var duration = 10000L; // 10 seconds
        var result = TimeUtils.formatRemaining(startMillis, duration);
        assertEquals("5s", result); // Remaining time is 5 seconds
    }

    @Test
    void testParseMillisWithValidInput() {
        assertEquals(3600000L, TimeUtils.parseMillis("1h"));
        assertEquals(60000L, TimeUtils.parseMillis("1m"));
        assertEquals(1000L, TimeUtils.parseMillis("1s"));
        assertEquals(500L, TimeUtils.parseMillis("500ms"));
    }

    @Test
    void testParseMillisWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> TimeUtils.parseMillis("1d"));
        assertThrows(IllegalArgumentException.class, () -> TimeUtils.parseMillis("1hh"));
        assertThrows(IllegalArgumentException.class, () -> TimeUtils.parseMillis("abc"));
    }

    @Test
    void testParseSecondsWithValidInput() {
        assertEquals(3600L, TimeUtils.parseSeconds("1h"));
        assertEquals(60L, TimeUtils.parseSeconds("1m"));
        assertEquals(1L, TimeUtils.parseSeconds("1s"));
        assertEquals(0L, TimeUtils.parseSeconds("500ms"));
    }

    @Test
    void testParseSecondsWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> TimeUtils.parseSeconds("1d"));
        assertThrows(IllegalArgumentException.class, () -> TimeUtils.parseSeconds("1ss"));
        assertThrows(IllegalArgumentException.class, () -> TimeUtils.parseSeconds("abc"));
    }

    @Test
    void dummyConstructor() {
        @SuppressWarnings("unused")
        var TimeUtils = new TimeUtils(); // TimeUtils should contain only static methods
    }
}
