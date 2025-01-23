/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherDependencyAccessor.java
 * @author Alexandru Delegeanu
 * @version 0.4
 * @description Provide access to all dependencies based on their class
 */

package dev.defaultybuf.feather.toolkit.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import dev.defaultybuf.feather.toolkit.api.interfaces.IEnabledModulesProvider;
import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;
import dev.defaultybuf.feather.toolkit.api.interfaces.IGeneralDependencyAccessor;
import dev.defaultybuf.feather.toolkit.core.modules.language.interfaces.ILanguage;

public class FeatherDependencyAccessor implements IGeneralDependencyAccessor {
    private final Map<Class<?>, Object> dependencies;

    public static class DependencyMapBuilder {
        private final Map<Class<?>, Object> dependencies = new HashMap<>();

        public void addDependency(final Class<?> clazz, final Object dependency) {
            this.dependencies.put(clazz, dependency);
        }

        public void removeDependency(final Class<?> clazz) {
            this.dependencies.remove(clazz);
        }

        public Map<Class<?>, Object> getMap() {
            return this.dependencies;
        }
    }

    public FeatherDependencyAccessor(final Map<Class<?>, Object> dependencies) {
        this.dependencies = dependencies;
    }

    public <T> T getInterface(final Class<T> clazz) {
        return clazz.cast(dependencies.get(clazz));
    }

    @Override
    public JavaPlugin getPlugin() throws IllegalStateException {
        assert getInterface(
                JavaPlugin.class) != null : "[dev.defaultybuf.feather.toolkit.api.core]@DependencyAccessor.getPlugin(): JavaPlugin instance not found in dependencies.";

        return getInterface(JavaPlugin.class);
    }

    @Override
    public IFeatherLogger getLogger() throws IllegalStateException {
        assert getInterface(
                IFeatherLogger.class) != null : "[dev.defaultybuf.feather.toolkit.api.core]@DependencyAccessor.getLogger(): IFeatherLogger instance not found in dependencies.";

        return getInterface(IFeatherLogger.class);
    }

    @Override
    public List<FeatherModule> getEnabledModules() throws IllegalStateException {
        assert getInterface(
                IEnabledModulesProvider.class) != null : "[dev.defaultybuf.feather.toolkit.api.core]@DependencyAccessor.getEnabledModules(): IEnabledModulesProvider instance not found in dependencies.";

        return getInterface(IEnabledModulesProvider.class).getEnabledModules();
    }

    @Override
    public ILanguage getLanguage() throws IllegalStateException {
        assert getInterface(
                ILanguage.class) != null : "[dev.defaultybuf.feather.toolkit.api.core]@DependencyAccessor.getLanguage(): ILanguage instance not found in dependencies.";

        return getInterface(ILanguage.class);
    }
}
