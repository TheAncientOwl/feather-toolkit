/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file DependencyMapBuilderTest.java
 * @author Alexandru Delegeanu
 * @version 0.4
 * @test_unit DependencyMapBuilder#0.2
 * @description Unit tests for DependencyMapBuilder
 */

package dev.defaultybuf.feather.toolkit.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.defaultybuf.feather.toolkit.api.dummies.Dummy1;
import dev.defaultybuf.feather.toolkit.api.dummies.Dummy2;

class DependencyMapBuilderTest {
    FeatherDependencyAccessor.DependencyMapBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new FeatherDependencyAccessor.DependencyMapBuilder();
    }

    @Test
    void addDependency_success() {
        // Arrange
        var clazz = Dummy1.class;
        var dependency = mock(Dummy1.class);

        // Act
        builder.addDependency(clazz, dependency);
        var map = builder.getMap();

        // Assert
        assertTrue(map.containsKey(clazz));
        assertEquals(dependency, map.get(clazz));
    }

    @Test
    void addDependency_overwriteExisting() {
        // Arrange
        var clazz = Dummy1.class;
        var dependency1 = new Dummy1("Dependency1");
        var dependency2 = new Dummy1("Dependency2");

        // Act
        builder.addDependency(clazz, dependency1);
        builder.addDependency(clazz, dependency2); // Overwrite
        var map = builder.getMap();

        // Assert
        assertTrue(map.containsKey(clazz));
        assertEquals(dependency2, map.get(clazz));
    }

    @Test
    void removeDependency_success() {
        // Arrange
        var clazz = Dummy2.class;
        var dependency = mock(Dummy2.class);
        builder.addDependency(clazz, dependency);

        // Act
        builder.removeDependency(clazz);
        var map = builder.getMap();

        // Assert
        assertFalse(map.containsKey(clazz));
    }

    @Test
    void removeDependency_nonExisting() {
        // Act
        builder.removeDependency(Dummy1.class); // Attempt to remove non-existing key
        var map = builder.getMap();

        // Assert
        assertFalse(map.containsKey(Dummy1.class)); // Ensure no exception is thrown
    }

    @Test
    void getMap_initiallyEmpty() {
        // Act
        var map = builder.getMap();

        // Assert
        assertNotNull(map);
        assertTrue(map.isEmpty());
    }

    @Test
    void getMap_withAddedDependencies() {
        // Arrange
        builder.addDependency(String.class, "TestString");
        builder.addDependency(Integer.class, 42);

        // Act
        var map = builder.getMap();

        // Assert
        assertEquals(2, map.size());
        assertTrue(map.containsKey(String.class));
        assertTrue(map.containsKey(Integer.class));
        assertEquals("TestString", map.get(String.class));
        assertEquals(42, map.get(Integer.class));
    }

}
