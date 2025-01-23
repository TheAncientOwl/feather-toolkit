/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file CacheTest.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @test_unit Cache#0.2
 * @description Unit tests for Cache
 */

package dev.defaultybuf.feather.toolkit.util.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

class CacheTest {

    @Test
    void testCacheReturnsSameValue() {
        // Supplier returns a constant value
        Supplier<String> supplier = () -> "test";
        var cache = Cache.of(supplier);

        var firstCall = cache.get();
        var secondCall = cache.get();

        // Assert both calls return the same object
        assertEquals("test", firstCall);
        assertSame(firstCall, secondCall);
    }

    @Test
    void testCacheInitializesLazily() {
        // Supplier increments an AtomicInteger to track calls
        var counter = new AtomicInteger(0);
        Supplier<Integer> supplier = counter::incrementAndGet;
        var cache = Cache.of(supplier);

        // Supplier should not be called until get() is invoked
        assertEquals(0, counter.get());

        // First get() should call the supplier
        var value = cache.get();
        assertEquals(1, counter.get());
        assertEquals(1, value);

        // Subsequent calls should not call the supplier
        value = cache.get();
        assertEquals(1, counter.get());
        assertEquals(1, value);
    }

    @Test
    void testCacheHandlesNullSupplierResult() {
        // Supplier returns null
        Supplier<Object> supplier = () -> null;
        var cache = Cache.of(supplier);

        var firstCall = cache.get();
        var secondCall = cache.get();

        // Assert both calls return null
        assertNull(firstCall);
        assertNull(secondCall);

        // Assert both calls return the same null reference
        assertSame(firstCall, secondCall);
    }

    @Test
    void testCacheWorksWithDifferentTypes() {
        // Test with an Integer supplier
        Supplier<Integer> intSupplier = () -> 42;
        var intCache = Cache.of(intSupplier);
        assertEquals(42, intCache.get());

        // Test with a String supplier
        Supplier<String> stringSupplier = () -> "Hello, Cache!";
        var stringCache = Cache.of(stringSupplier);
        assertEquals("Hello, Cache!", stringCache.get());
    }

    @Test
    void testCacheHandlesMultipleInstancesIndependently() {
        // Two separate caches with independent suppliers
        var counter1 = new AtomicInteger(0);
        Supplier<Integer> supplier1 = counter1::incrementAndGet;

        var counter2 = new AtomicInteger(0);
        Supplier<Integer> supplier2 = counter2::incrementAndGet;

        var cache1 = Cache.of(supplier1);
        var cache2 = Cache.of(supplier2);

        // Interact with the first cache
        assertEquals(1, cache1.get());
        assertEquals(1, counter1.get());
        assertEquals(0, counter2.get());

        // Interact with the second cache
        assertEquals(1, cache2.get());
        assertEquals(1, counter2.get());
    }
}
