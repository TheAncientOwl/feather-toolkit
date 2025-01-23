/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherModule.java
 * @author Alexandru Delegeanu
 * @version 0.7
 * @description Base class for plugin module
 */

package dev.defaultybuf.feather.toolkit.api;

import java.util.Map;
import java.util.function.Supplier;

import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;
import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;
import dev.defaultybuf.feather.toolkit.exceptions.FeatherSetupException;

public abstract class FeatherModule extends FeatherDependencyAccessor {
    public final static String HIDE_LIFECYCLE_PREFIX = "$";

    private final String name;
    protected final IConfigFile config;

    public static final record InitData(String name, Supplier<IConfigFile> configSupplier,
            Map<Class<?>, Object> modules) {
    }

    public FeatherModule(final InitData data) {
        super(data.modules);
        this.name = data.name;
        this.config = data.configSupplier.get();
    }

    public void onEnable() throws FeatherSetupException {
        logStatus(getLogger(), "&7setup started");
        onModuleEnable();
        logStatus(getLogger(), "&aenabled");
    }

    public void onDisable(final IFeatherLogger logger) {
        logStatus(logger, "&7disabling started");
        onModuleDisable();
        logStatus(logger, "&adisabled");
    }

    public String getModuleName() {
        return this.name;
    }

    public IConfigFile getConfig() {
        return this.config;
    }

    private void logStatus(final IFeatherLogger logger, final String message) {
        if (!name.startsWith(HIDE_LIFECYCLE_PREFIX)) {
            logger.info("&2" + this.name + "&8: &r" + message);
        }
    }

    protected void onModuleEnable() throws FeatherSetupException {};

    protected void onModuleDisable() {};
}
