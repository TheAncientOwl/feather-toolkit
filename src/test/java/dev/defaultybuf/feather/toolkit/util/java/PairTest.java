/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file PairTest.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @test_unit Pair#0.1
 * @description Unit tests for Pair
 */

package dev.defaultybuf.feather.toolkit.util.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class PairTest {

    @Test
    void testPairInitializationWithConstructor() {
        // Create a Pair using the constructor
        var pair = Pair.of(1, "one");

        // Assert both fields are initialized correctly
        assertEquals(1, pair.first);
        assertEquals("one", pair.second);
    }

    @Test
    void testPairInitializationWithFactoryMethod() {
        // Create a Pair using the static of() method
        var pair = Pair.of("pi", 3.14);

        // Assert both fields are initialized correctly
        assertEquals("pi", pair.first);
        assertEquals(3.14, pair.second);
    }

    @Test
    void testPairAllowsNullValues() {
        // Create a Pair with null values
        var pair = Pair.of(null, null);

        // Assert both fields are null
        assertNull(pair.first);
        assertNull(pair.second);
    }

    @Test
    void testPairWithDifferentTypes() {
        // Create a Pair with heterogeneous types
        var pair = Pair.of(42, "Answer");

        // Assert correct values and types
        assertEquals(42, pair.first);
        assertEquals("Answer", pair.second);
    }

    @Test
    void testPairFieldsAreMutable() {
        // Create a Pair and modify its fields
        var pair = Pair.of(1, "one");

        pair.first = 2;
        pair.second = "two";

        // Assert fields are updated
        assertEquals(2, pair.first);
        assertEquals("two", pair.second);
    }
}
