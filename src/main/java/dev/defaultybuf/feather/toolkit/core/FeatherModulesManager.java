/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherModulesManager.java
 * @author Alexandru Delegeanu
 * @version 0.7
 * @description Class responsible for modules lifecycle
 */

package dev.defaultybuf.feather.toolkit.core;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;

import org.bukkit.plugin.java.JavaPlugin;

import dev.defaultybuf.feather.toolkit.api.FeatherDependencyAccessor;
import dev.defaultybuf.feather.toolkit.api.FeatherCommand;
import dev.defaultybuf.feather.toolkit.api.FeatherListener;
import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;
import dev.defaultybuf.feather.toolkit.api.interfaces.IEnabledModulesProvider;
import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;
import dev.defaultybuf.feather.toolkit.core.configuration.bukkit.BukkitConfigFile;
import dev.defaultybuf.feather.toolkit.exceptions.FeatherSetupException;
import dev.defaultybuf.feather.toolkit.exceptions.ModuleNotEnabledException;
import dev.defaultybuf.feather.toolkit.util.java.Pair;
import dev.defaultybuf.feather.toolkit.util.java.StringUtils;
import dev.defaultybuf.feather.toolkit.util.parsing.YamlUtils;

public class FeatherModulesManager {

    private static final class ModuleConfig {
        public Set<String> dependencies = null;
        public FeatherModule instance = null;
        public boolean mandatory = false;
        public List<String> listeners = null;
        public List<Pair<String, String>> commands = null;
    }

    private static class InitializationData {
        public Map<String, ModuleConfig> moduleConfigs = new HashMap<>();
        public Set<String> enabledModules = new HashSet<>();
    }

    private InitializationData init = new InitializationData();

    private Map<String, FeatherModule> modules = new HashMap<>();
    private FeatherDependencyAccessor.DependencyMapBuilder dependenciesMapBuilder =
            new FeatherDependencyAccessor.DependencyMapBuilder();
    private List<String> enableOrder = new ArrayList<>();

    /**
     * @param name of the module
     * @return true if module is enabled, false otherwise
     */
    public boolean isModuleEnabled(final String name) {
        return this.init.enabledModules.contains(name);
    }

    public List<FeatherModule> getEnabledModules() {
        final var modules = new ArrayList<FeatherModule>();

        for (final var moduleName : this.enableOrder) {
            if (!moduleName.startsWith(FeatherModule.HIDE_LIFECYCLE_PREFIX)) {
                modules.add(this.modules.get(moduleName));
            }
        }

        return modules;
    }

    public void onEnable(final FeatherPlugin core)
            throws FeatherSetupException, ModuleNotEnabledException, IOException {
        this.init.moduleConfigs.clear();
        this.init.enabledModules.clear();
        this.modules.clear();
        this.enableOrder.clear();

        loadModules(core);
        computeEnableOrder();

        this.init.moduleConfigs.forEach((moduleName, moduleConfig) -> {
            this.modules.put(moduleName, moduleConfig.instance);
        });

        enableModules(core);
        doPostEnableCleanup();
    }

    public void onDisable(final IFeatherLogger logger) {
        disableModules(logger);
    }

    /**
     * Load modules configs from file
     * 
     * @param plugin
     * @throws FeatherSetupException
     */
    void loadModules(final FeatherPlugin plugin) throws FeatherSetupException {
        this.dependenciesMapBuilder.addDependency(JavaPlugin.class, plugin);
        this.dependenciesMapBuilder.addDependency(IFeatherLogger.class, plugin.getFeatherLogger());
        this.dependenciesMapBuilder.addDependency(IEnabledModulesProvider.class, plugin);

        final var config = YamlUtils.loadYaml(plugin, FeatherPlugin.FEATHER_TOOLKIT_CONFIG_YML)
                .getConfigurationSection("modules");

        for (final var moduleName : config.getKeys(false)) {
            final var moduleConfig = config.getConfigurationSection(moduleName);

            // 1. create module config
            final var module = new ModuleConfig();
            this.init.moduleConfigs.put(moduleName, module);

            // 2. create module instance
            final var moduleClass = moduleConfig.getString("class");
            final var configFilePath = moduleConfig.getString("config");
            try {
                module.instance = (FeatherModule) Class.forName(moduleClass)
                        .getConstructor(FeatherModule.InitData.class)
                        .newInstance(new FeatherModule.InitData(moduleName,
                                (Supplier<IConfigFile>) () -> {
                                    try {
                                        return configFilePath == null ? null
                                                : new BukkitConfigFile(plugin, configFilePath);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                }, dependenciesMapBuilder.getMap()));

                for (final var interfaceName : moduleConfig.getStringList("interfaces")) {
                    dependenciesMapBuilder.addDependency(Class.forName(interfaceName),
                            module.instance);
                }
            } catch (final Exception e) {
                throw new FeatherSetupException(
                        "Could not generate instance of class " + moduleClass + "\nReason: "
                                + StringUtils.exceptionToStr(e));
            }

            // 3. set mandatory
            module.mandatory = moduleConfig.getBoolean("mandatory");

            // 4. set listeners
            module.listeners = moduleConfig.getStringList("listeners");

            // 5. set commands
            module.commands = new ArrayList<>();
            final var commandsConfig = moduleConfig.getConfigurationSection("commands");
            if (commandsConfig != null) {
                commandsConfig.getKeys(false).forEach(commandName -> module.commands
                        .add(Pair.of(commandName, commandsConfig.getString(commandName))));
            }

            // 6. set dependencies
            module.dependencies = new HashSet<>(moduleConfig.getStringList("dependencies"));
        }

        // check if dependencies are actual modules
        for (final var moduleEntry : this.init.moduleConfigs.entrySet()) {
            final var moduleName = moduleEntry.getKey();
            final var moduleConfig = moduleEntry.getValue();

            for (final var dependency : moduleConfig.dependencies) {
                if (!this.init.moduleConfigs.containsKey(dependency)) {
                    throw new FeatherSetupException(
                            "Dependency '" + dependency + "' of module '" + moduleName
                                    + "' does not name any valid module");
                }
            }
        }
    }

    /**
     * Self explanatory
     * 
     * @throws FeatherSetupException
     */
    void computeEnableOrder() throws FeatherSetupException {
        // 1. build the graph and compute in-degrees
        final var graph = new HashMap<String, HashSet<String>>();
        final var inDegrees = new HashMap<String, Integer>();

        for (final var moduleName : this.init.moduleConfigs.keySet()) {
            inDegrees.put(moduleName, 0);
            graph.put(moduleName, new HashSet<>());
        }

        for (final var entry : this.init.moduleConfigs.entrySet()) {
            final var moduleName = entry.getKey();
            for (final var dependency : entry.getValue().dependencies) {
                graph.get(dependency).add(moduleName);
                inDegrees.put(moduleName, inDegrees.get(moduleName) + 1);
            }
        }

        // 2. perform topological sort using Kahn's algorithm
        final Queue<String> queue = new ArrayDeque<>();
        for (final var entry : inDegrees.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            final var moduleName = queue.poll();
            this.enableOrder.add(moduleName);

            for (final var neighhbor : graph.get(moduleName)) {
                inDegrees.put(neighhbor, inDegrees.get(neighhbor) - 1);
                if (inDegrees.get(neighhbor) == 0) {
                    queue.add(neighhbor);
                }
            }
        }

        // check for cycles
        if (this.enableOrder.size() != this.init.moduleConfigs.size()) {
            throw new FeatherSetupException(
                    "There is a cycle in the dependency graph of modules configuration. This is weird O.o, please contact the developer");
        }
    }

    /**
     * Enable modules taking into account if the module was or not enabled in config
     * 
     * @param core
     * @throws FeatherSetupException
     * @throws ModuleNotEnabledException
     * @throws IOException
     */
    private void enableModules(final JavaPlugin core)
            throws FeatherSetupException, ModuleNotEnabledException, IOException {
        final IConfigFile modulesEnabledConfig =
                new BukkitConfigFile(core, FeatherPlugin.ENABLED_MODULES_YML);

        final var plugin = core;
        final var pluginManager = plugin.getServer().getPluginManager();

        for (final var moduleName : enableOrder) {
            // try to enable the module
            final var module = this.init.moduleConfigs.get(moduleName);
            final var configEnabled = modulesEnabledConfig.getBoolean(moduleName, true);

            if (!configEnabled && !module.mandatory) {
                continue;
            }

            // check if all dependencies are enabled
            for (final var dependency : module.dependencies) {
                if (!this.init.enabledModules.contains(dependency)) {
                    throw new FeatherSetupException(
                            "Module " + moduleName + " failed to enable because dependency "
                                    + dependency + " is not enabled yet.");
                }
            }

            module.instance.onEnable();

            this.init.enabledModules.add(moduleName);

            // register commands
            for (final var command : module.commands) {
                final var commandName = command.first;
                final var commandClass = command.second;

                try {
                    final var cmdInstance = (FeatherCommand<?>) Class.forName(commandClass)
                            .getConstructor(FeatherCommand.InitData.class)
                            .newInstance(new FeatherCommand.InitData(
                                    this.dependenciesMapBuilder.getMap()));
                    final var cmd = plugin.getCommand(commandName);

                    cmd.setExecutor(cmdInstance);
                    cmd.setTabCompleter(cmdInstance);
                } catch (final Exception e) {
                    throw new FeatherSetupException(
                            "Could not setup command " + commandName + "\nReason: "
                                    + StringUtils.exceptionToStr(e));
                }
            }

            // register listeners
            for (final var listenerClass : module.listeners) {
                try {
                    final var listenerInstance = (FeatherListener) Class.forName(listenerClass)
                            .getConstructor(FeatherListener.InitData.class)
                            .newInstance(new FeatherListener.InitData(
                                    this.dependenciesMapBuilder.getMap()));

                    pluginManager.registerEvents(listenerInstance, plugin);
                } catch (final Exception e) {
                    throw new FeatherSetupException(
                            "Could not setup listener " + listenerClass + "\nReason: "
                                    + StringUtils.exceptionToStr(e));
                }
            }

        }
    }

    private void doPostEnableCleanup() {
        final var iterator = this.modules.entrySet().iterator();
        while (iterator.hasNext()) {
            final var entry = iterator.next();
            final var moduleName = entry.getKey();

            if (!this.init.enabledModules.contains(moduleName)) {
                this.dependenciesMapBuilder
                        .removeDependency(
                                this.init.moduleConfigs.get(moduleName).instance.getClass());
                iterator.remove();
                this.enableOrder.remove(moduleName);
            }
        }

        this.init.moduleConfigs.clear();
    }

    private void disableModules(final IFeatherLogger logger) {
        for (int index = this.enableOrder.size() - 1; index >= 0; --index) {
            if (this.init.enabledModules.contains(this.enableOrder.get(index))) {
                this.modules.get(this.enableOrder.get(index)).onDisable(logger);
            }
        }
    }
}
