/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file DependencyAccessorTest.java
 * @author Alexandru Delegeanu
 * @version 0.5
 * @test_unit DependencyAccessor#0.2
 * @description Unit tests for DependencyAccessor
 */

package dev.defaultybuf.feather.toolkit.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.defaultybuf.feather.toolkit.api.interfaces.IEnabledModulesProvider;
import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;
import dev.defaultybuf.feather.toolkit.core.modules.language.interfaces.ILanguage;

@ExtendWith(MockitoExtension.class)
class DependencyAccessorTest {
    @Mock ILanguage mockLanguage;
    @Mock JavaPlugin mockPlugin;
    @Mock IFeatherLogger mockLogger;
    @Mock IEnabledModulesProvider mockModulesProvider;

    FeatherDependencyAccessor dependencyAccessor;
    Map<Class<?>, Object> dependencyMap;

    @BeforeEach
    void setUp() {
        dependencyMap = new HashMap<>();
        dependencyMap.put(JavaPlugin.class, mockPlugin);
        dependencyMap.put(IFeatherLogger.class, mockLogger);
        dependencyMap.put(IEnabledModulesProvider.class, mockModulesProvider);
        dependencyMap.put(ILanguage.class, mockLanguage);

        dependencyAccessor = new FeatherDependencyAccessor(dependencyMap);
    }

    @Test
    void getInterface_validDependency() {
        var plugin = dependencyAccessor.getInterface(JavaPlugin.class);
        assertNotNull(plugin);
        assertEquals(mockPlugin, plugin);
    }

    @Test
    void getInterface_missingDependency() {
        assertNull(dependencyAccessor.getInterface(String.class));
    }

    @Test
    void getPlugin_success() {
        var plugin = dependencyAccessor.getPlugin();
        assertNotNull(plugin);
        assertEquals(mockPlugin, plugin);
    }

    @Test
    void getPlugin_missingDependency() {
        dependencyMap.remove(JavaPlugin.class);
        assertThrows(AssertionError.class, () -> dependencyAccessor.getPlugin());
    }

    @Test
    void getLogger_success() {
        var logger = dependencyAccessor.getLogger();
        assertNotNull(logger);
        assertEquals(mockLogger, logger);
    }

    @Test
    void getLogger_missingDependency() {
        dependencyMap.remove(IFeatherLogger.class);
        assertThrows(AssertionError.class, () -> dependencyAccessor.getLogger());
    }

    @Test
    void getEnabledModules_success() {
        var mockModules = List.of(mock(FeatherModule.class));
        Mockito.when(mockModulesProvider.getEnabledModules()).thenReturn(mockModules);

        var modules = dependencyAccessor.getEnabledModules();
        assertNotNull(modules);
        assertEquals(mockModules, modules);
    }

    @Test
    void getEnabledModules_missingDependency() {
        dependencyMap.remove(IEnabledModulesProvider.class);
        assertThrows(AssertionError.class, () -> dependencyAccessor.getEnabledModules());
    }

    @Test
    void getLanguage_success() {
        var language = dependencyAccessor.getLanguage();
        assertNotNull(language);
        assertEquals(mockLanguage, language);
    }

    @Test
    void getLanguage_missingDependency() {
        dependencyMap.remove(ILanguage.class);
        assertThrows(AssertionError.class, () -> dependencyAccessor.getLanguage());
    }
}
