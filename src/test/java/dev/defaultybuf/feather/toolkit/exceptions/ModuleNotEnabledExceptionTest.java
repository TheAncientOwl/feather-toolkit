/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file ModuleNotEnabledExceptionTest.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @test_unit ModuleNotEnabledException#0.1
 * @description Unit tests for ModuleNotEnabledException
 */

package dev.defaultybuf.feather.toolkit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ModuleNotEnabledExceptionTest {

    @Test
    void testNoArgConstructor() {
        var exception = new ModuleNotEnabledException();
        assertNull(exception.getMessage(), "Message should be null for no-arg constructor");
    }

    @Test
    void testConstructorWithModuleName() {
        var moduleName = "TestModule";
        var exception = new ModuleNotEnabledException(moduleName);

        assertEquals("Module '" + moduleName + "' is not enabled", exception.getMessage(),
                "Message should match the expected format");
    }

    @Test
    void testConstructorWithEmptyModuleName() {
        var moduleName = "";
        var exception = new ModuleNotEnabledException(moduleName);

        assertEquals("Module '' is not enabled", exception.getMessage(),
                "Message should handle an empty module name correctly");
    }

    @Test
    void testConstructorWithNullModuleName() {
        var exception = new ModuleNotEnabledException(null);

        assertEquals("Module 'null' is not enabled", exception.getMessage(),
                "Message should handle a null module name correctly");
    }
}
