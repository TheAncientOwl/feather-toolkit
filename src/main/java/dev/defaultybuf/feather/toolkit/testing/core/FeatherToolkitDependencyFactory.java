/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherToolkitDependencyFactory.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Create mocks / actual instances of all toolkit modules 
 *              and inject them into test dependencies maps
 */

package dev.defaultybuf.feather.toolkit.testing.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import org.bukkit.plugin.java.JavaPlugin;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;
import dev.defaultybuf.feather.toolkit.core.configuration.bukkit.BukkitConfigFile;
import dev.defaultybuf.feather.toolkit.core.modules.language.components.LanguageManager;
import dev.defaultybuf.feather.toolkit.core.modules.language.interfaces.ILanguage;
import dev.defaultybuf.feather.toolkit.core.modules.reload.components.ReloadModule;
import dev.defaultybuf.feather.toolkit.core.modules.reload.interfaces.IReloadModule;
import dev.defaultybuf.feather.toolkit.testing.core.annotations.DependencyFactory;
import dev.defaultybuf.feather.toolkit.testing.core.annotations.Resource;
import dev.defaultybuf.feather.toolkit.testing.utils.TempFile;
import dev.defaultybuf.feather.toolkit.testing.utils.TempModule;
import dev.defaultybuf.feather.toolkit.testing.utils.TestUtils;

public class FeatherToolkitDependencyFactory {

    @DependencyFactory(of = ILanguage.class)
    public static final DependencyHelper<LanguageManager> getLanguageFactory() {
        return new DependencyHelper<LanguageManager>(
                LanguageManager.class,
                ILanguage.class,
                "LanguageManager",
                "language");
    }

    @DependencyFactory(of = IReloadModule.class)
    public static final DependencyHelper<ReloadModule> getReloadFactory() {
        return new DependencyHelper<ReloadModule>(
                ReloadModule.class,
                IReloadModule.class,
                "ReloadModule",
                "reload");
    }

    public static final record DependencyHelper<T extends FeatherModule>(Class<T> moduleClass,
            Class<?> interfaceClass,
            String name,
            String relativeFolder) {
        public Path relativeConfig() {
            return relativeResource("config.yml");
        }

        public Path relativeResource(Object resource) {
            return Paths.get(relativeFolder, resource.toString());
        }

        public Path absoluteResource(Object resource) {
            return TestUtils.getTestDataFolderPath()
                    .resolve(Paths.get(relativeFolder, resource.toString()));
        }

        public TempFile makeTempResource(Object relativePath, String content) {
            return TestUtils.makeTempFile(absoluteResource(relativePath), content);
        }

        public T MockModule(final Map<Class<?>, Object> dependencies) {
            var mockModule = mock(moduleClass);
            var mockConfig = mock(IConfigFile.class);

            lenient().when(mockModule.getModuleName()).thenReturn(name);
            lenient().when(mockModule.getConfig()).thenReturn(mockConfig);

            dependencies.put(interfaceClass, mockModule);

            return mockModule;
        }

        public TempModule<T> ActualModule(final Resource[] resources,
                final JavaPlugin plugin, final Map<Class<?>, Object> dependencies) {
            T moduleOut = null;

            lenient().when(plugin.getDataFolder())
                    .thenReturn(TestUtils.getTestDataFolder());

            final var resourcesTempFiles = new ArrayList<TempFile>();

            for (final var resource : resources) {
                resourcesTempFiles.add(makeTempResource(resource.path(),
                        resource.content()));
            }

            try {
                moduleOut = (T) moduleClass
                        .getConstructor(FeatherModule.InitData.class)
                        .newInstance(new FeatherModule.InitData(
                                name,
                                (Supplier<IConfigFile>) () -> {
                                    try {
                                        return relativeConfig() == null
                                                ? null
                                                : new BukkitConfigFile(
                                                        plugin,
                                                        relativeConfig().toString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                }, dependencies));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            TempModule<T> tempModule = new TempModule<T>(moduleOut, resourcesTempFiles);

            assertNotNull(tempModule.module().getConfig(),
                    "Failed to load config file for " + name + " module, '"
                            + relativeConfig()
                            + "'");

            dependencies.put(interfaceClass, tempModule.module());

            return tempModule;
        }
    }
}
