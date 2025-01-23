/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherSetupExceptionTest.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @test_unit FeatherSetupException#0.1
 * @description Unit tests for FeatherSetupException
 */

package dev.defaultybuf.feather.toolkit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class FeatherSetupExceptionTest {
    @Test
    void testNoArgConstructor() {
        var exception = new FeatherSetupException();
        assertNull(exception.getMessage(), "Message should be null for no-arg constructor");
        assertNull(exception.getCause(), "Cause should be null for no-arg constructor");
    }

    @Test
    void testConstructorWithMessage() {
        var message = "Custom error message";
        var exception = new FeatherSetupException(message);

        assertEquals(message, exception.getMessage(), "Message should match the input message");
        assertNull(exception.getCause(), "Cause should be null when not provided");
    }

    @Test
    void testConstructorWithMessageAndCause() {
        var message = "Custom error with cause";
        var cause = new RuntimeException("Root cause");
        var exception = new FeatherSetupException(message, cause);

        assertEquals(message, exception.getMessage(), "Message should match the input message");
        assertEquals(cause, exception.getCause(), "Cause should match the input cause");
    }

    @Test
    void testConstructorWithCause() {
        var cause = new RuntimeException("Root cause");
        var exception = new FeatherSetupException(cause);

        assertEquals(cause, exception.getCause(), "Cause should match the input cause");
        assertEquals(cause.toString(), exception.getMessage(),
                "Message should match the cause's toString()");
    }
}
