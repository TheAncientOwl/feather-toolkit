/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file JavaExtTest.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @test_unit JavaExt#0.1
 * @description Unit tests for JavaExt
 */

package dev.defaultybuf.feather.toolkit.util.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

class JavaExtTest {

    @Test
    void testFindIf() {
        // Test with a list and a matching predicate
        var list = List.of("apple", "banana", "cherry");
        Predicate<String> startsWithB = s -> s.startsWith("b");
        var result = JavaExt.findIf(list, startsWithB);
        assertTrue(result.isPresent());
        assertEquals("banana", result.get());

        // Test with no matching predicate
        Predicate<String> startsWithZ = s -> s.startsWith("z");
        result = JavaExt.findIf(list, startsWithZ);
        assertFalse(result.isPresent());

        // Test with an empty list
        result = JavaExt.findIf(List.of(), startsWithB);
        assertFalse(result.isPresent());

        // Test with null list
        result = JavaExt.findIf(null, startsWithB);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindIndex() {
        // Test with a list and a matching predicate
        var list = List.of("apple", "banana", "cherry");
        Predicate<String> startsWithB = s -> s.startsWith("b");
        var result = JavaExt.findIndex(list, startsWithB);
        assertTrue(result.isPresent());
        assertEquals(1, result.get());

        // Test with no matching predicate
        Predicate<String> startsWithZ = s -> s.startsWith("z");
        result = JavaExt.findIndex(list, startsWithZ);
        assertFalse(result.isPresent());

        // Test with an empty list
        result = JavaExt.findIndex(List.of(), startsWithB);
        assertFalse(result.isPresent());

        // Test with null list
        result = JavaExt.findIndex(null, startsWithB);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindLastIndex() {
        // Test with a list and a matching predicate
        var list = List.of("apple", "banana", "apple", "cherry");
        Predicate<String> isApple = "apple"::equals;
        var result = JavaExt.findLastIndex(list, isApple);
        assertTrue(result.isPresent());
        assertEquals(2, result.get());

        // Test with no matching predicate
        Predicate<String> startsWithZ = s -> s.startsWith("z");
        result = JavaExt.findLastIndex(list, startsWithZ);
        assertFalse(result.isPresent());

        // Test with an empty list
        result = JavaExt.findLastIndex(List.of(), isApple);
        assertFalse(result.isPresent());

        // Test with null list
        result = JavaExt.findLastIndex(null, isApple);
        assertFalse(result.isPresent());
    }

    @Test
    void testContains() {
        // Test with a list and a matching predicate
        var list = List.of("apple", "banana", "cherry");
        Predicate<String> startsWithB = s -> s.startsWith("b");
        var result = JavaExt.contains(list, startsWithB);
        assertTrue(result);

        // Test with no matching predicate
        Predicate<String> startsWithZ = s -> s.startsWith("z");
        result = JavaExt.contains(list, startsWithZ);
        assertFalse(result);

        // Test with an empty list
        result = JavaExt.contains(List.of(), startsWithB);
        assertFalse(result);

        // Test with null list
        result = JavaExt.contains(null, startsWithB);
        assertFalse(result);
    }

    @Test
    void dummyConstructor() {
        @SuppressWarnings("unused") // JavaExt should contain only static methods
        var dummy = new JavaExt();
    }
}
