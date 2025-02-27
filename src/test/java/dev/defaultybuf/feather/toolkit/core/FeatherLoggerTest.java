/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherLoggerTest.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @test_unit FeatherLogger#0.4
 * @description Unit tests for FeatherLogger
 */

package dev.defaultybuf.feather.toolkit.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeatherLoggerTest {
    @Mock JavaPlugin mockPlugin;
    @Mock Logger mockLogger;

    FeatherLogger featherLogger;
    ArgumentCaptor<String> messageCaptor;

    @BeforeEach
    void setUp() {
        when(mockPlugin.getLogger()).thenReturn(mockLogger);
        featherLogger = new FeatherLogger(mockPlugin);
        messageCaptor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void testInfo() {
        var message = "Info message";
        featherLogger.info(message);

        verify(mockLogger).log(eq(Level.INFO), messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

    @Test
    void testWarn() {
        var message = "Warn message";
        featherLogger.warn("Warn message");

        verify(mockLogger).log(eq(Level.WARNING), messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

    @Test
    void testError() {
        var message = "Error message";
        featherLogger.error("Error message");

        verify(mockLogger).log(eq(Level.SEVERE), messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

    @Test
    void testDebug() {
        var message = "Debug message";
        featherLogger.debug("Debug message");

        verify(mockLogger).log(eq(Level.INFO), messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

}
