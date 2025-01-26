/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherUnitTest.java
 * @author Alexandru Delegeanu
 * @version 0.21
 * @description Utility class for developing unit tests that use modules
 */

package dev.defaultybuf.feather.toolkit.testing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mockStatic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.defaultybuf.feather.toolkit.api.interfaces.IEnabledModulesProvider;
import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;
import dev.defaultybuf.feather.toolkit.api.interfaces.IPlayerLanguageAccessor;
import dev.defaultybuf.feather.toolkit.core.modules.language.interfaces.ILanguage;
import dev.defaultybuf.feather.toolkit.testing.annotations.ActualModule;
import dev.defaultybuf.feather.toolkit.testing.annotations.DependencyFactory;
import dev.defaultybuf.feather.toolkit.testing.annotations.InjectDependencies;
import dev.defaultybuf.feather.toolkit.testing.annotations.MockedModule;
import dev.defaultybuf.feather.toolkit.testing.annotations.Resource;
import dev.defaultybuf.feather.toolkit.testing.annotations.StaticMock;
import dev.defaultybuf.feather.toolkit.testing.utils.TestUtils;
import dev.defaultybuf.feather.toolkit.util.java.Pair;

@ExtendWith(MockitoExtension.class)
public abstract class FeatherUnitTest {
    @Mock protected Server mockServer;
    @Mock protected JavaPlugin mockJavaPlugin;
    @Mock protected IFeatherLogger mockFeatherLogger;
    @Mock protected IEnabledModulesProvider mockEnabledModulesProvider;
    @Mock protected IPlayerLanguageAccessor mockPlayersLanguageAccessor;

    @MockedModule protected ILanguage mockLanguage;

    protected Map<Class<?>, Object> dependenciesMap;

    protected void setUp() {}

    protected void tearDown() {}

    @BeforeEach
    void setUpDependencyAccessorTest() {
        lenientCommons();
        initDependencies();
        setUpAnnotations();
        setUp();
    }

    @AfterEach
    void tearDownDependencyAccessorTest() {
        closeResources();
        tearDown();
    }

    void closeResources() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ActualModule.class)
                    || field.isAnnotationPresent(StaticMock.class)) {
                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(this);
                    if (fieldValue instanceof AutoCloseable) {
                        ((AutoCloseable) fieldValue).close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void lenientCommons() {
        lenient().when(mockJavaPlugin.getServer()).thenReturn(mockServer);
        lenient().when(mockJavaPlugin.getDataFolder()).thenReturn(TestUtils.getTestDataFolder());
    }

    void initDependencies() {
        dependenciesMap = new HashMap<>();

        dependenciesMap.put(JavaPlugin.class, mockJavaPlugin);
        dependenciesMap.put(IFeatherLogger.class, mockFeatherLogger);
        dependenciesMap.put(IEnabledModulesProvider.class, mockEnabledModulesProvider);
        dependenciesMap.put(IPlayerLanguageAccessor.class, mockPlayersLanguageAccessor);

        lenient().when(mockPlayersLanguageAccessor.getPlayerLanguageCode(any(OfflinePlayer.class)))
                .thenReturn("en");

        mockLanguage =
                FeatherToolkitDependencyFactory.getLanguageFactory().MockModule(dependenciesMap);
        dependenciesMap.put(ILanguage.class, mockLanguage);
    }

    // Map TestClass -> Factories
    static final Map<Class<?>, Map<Class<?>, Method>> TestFactoriesCache = new HashMap<>();

    void setUpAnnotations() {
        Map<Class<?>, Method> factories = TestFactoriesCache.get(this.getClass());
        if (factories == null) {
            factories = setupDependencyFactories();
            TestFactoriesCache.put(this.getClass(), factories);
        }

        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MockedModule.class)) {
                injectMockedModule(field, factories);
            } else if (field.isAnnotationPresent(ActualModule.class)) {
                injectActualModule(field, factories);
            } else if (field.isAnnotationPresent(StaticMock.class)) {
                createStaticMock(field);
            }
        }
    }

    void injectMockedModule(final Field field, final Map<Class<?>, Method> factories) {
        field.setAccessible(true);
        try {
            final var factory = factories.get(field.getType());
            assert factory != null : "Missing factory of " + field.getType().getName()
                    + " in test class " + this.getClass().getName();
            final var dependencyHelper = (DependencyHelper<?>) factory.invoke(null);

            field.set(this, dependencyHelper.MockModule(dependenciesMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void injectActualModule(final Field field, final Map<Class<?>, Method> factories) {
        field.setAccessible(true);
        try {
            ActualModule annotation = field.getAnnotation(ActualModule.class);
            Resource[] resources = annotation.resources();
            Class<?> interfaceClass = annotation.of();

            final var factory = factories.get(interfaceClass);
            assert factory != null : "Missing factory of " + interfaceClass.getName()
                    + " in test class " + this.getClass().getName();
            final var dependencyHelper = (DependencyHelper<?>) factory.invoke(null);

            field.set(this,
                    dependencyHelper.ActualModule(resources, mockJavaPlugin, dependenciesMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createStaticMock(final Field field) {
        field.setAccessible(true);
        try {
            field.set(this, mockStatic(field.getAnnotation(StaticMock.class).of()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    protected Pair<String, Object> anyPlaceholder() {
        return any(Pair.class);
    }

    Map<Class<?>, Method> setupDependencyFactories() {
        final var map = new HashMap<Class<?>, Method>();

        for (final var method : FeatherToolkitDependencyFactory.class.getMethods()) {
            if (method.isAnnotationPresent(DependencyFactory.class)) {
                map.put(method.getAnnotation(DependencyFactory.class).of(), method);
            }
        }

        if (this.getClass().isAnnotationPresent(InjectDependencies.class)) {
            for (final var factory : this.getClass().getAnnotation(InjectDependencies.class)
                    .factories()) {
                for (final var method : factory.getMethods()) {
                    if (method.isAnnotationPresent(DependencyFactory.class)) {
                        map.put(method.getAnnotation(DependencyFactory.class).of(), method);
                    }
                }
            }
        }

        return map;
    }
}
