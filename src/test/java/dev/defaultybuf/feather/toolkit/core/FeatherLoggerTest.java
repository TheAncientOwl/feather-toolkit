/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherLoggerTest.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @test_unit FeatherLogger#0.3
 * @description Unit tests for FeatherLogger
 */

package dev.defaultybuf.feather.toolkit.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.bukkit.command.ConsoleCommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeatherLoggerTest {
    @Mock ConsoleCommandSender mockConsole;

    FeatherLogger featherLogger;
    ArgumentCaptor<String> messageCaptor;

    @BeforeEach
    void setUp() {
        featherLogger = new FeatherLogger(mockConsole);
        messageCaptor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void testInfo() {
        var message = "Info message";
        featherLogger.info(message);

        verify(mockConsole).sendMessage(messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

    @Test
    void testWarn() {
        var message = "Warn message";
        featherLogger.warn("Warn message");

        verify(mockConsole).sendMessage(messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

    @Test
    void testError() {
        var message = "Error message";
        featherLogger.error("Error message");

        verify(mockConsole).sendMessage(messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

    @Test
    void testDebug() {
        var message = "Debug message";
        featherLogger.debug("Debug message");

        verify(mockConsole).sendMessage(messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains(message));
    }

}
