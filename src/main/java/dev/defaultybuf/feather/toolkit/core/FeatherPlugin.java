/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherPlugin.java
 * @author Alexandru Delegeanu
 * @version 0.8
 * @description Plugin entry point
 */

package dev.defaultybuf.feather.toolkit.core;

import java.io.IOException;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.api.interfaces.IEnabledModulesProvider;
import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;
import dev.defaultybuf.feather.toolkit.exceptions.FeatherSetupException;
import dev.defaultybuf.feather.toolkit.exceptions.ModuleNotEnabledException;
import dev.defaultybuf.feather.toolkit.util.java.Clock;
import dev.defaultybuf.feather.toolkit.util.java.StringUtils;
import dev.defaultybuf.feather.toolkit.util.java.TimeUtils;

public class FeatherPlugin extends JavaPlugin implements IEnabledModulesProvider {
    public static final String FEATHER_TOOLKIT_CONFIG_YML = "feather-toolkit-config.yml";
    public static final String ENABLED_MODULES_YML = "enabled-modules.yml";

    private FeatherModulesManager modulesManager = new FeatherModulesManager();
    private IFeatherLogger featherLogger = null;

    @Override
    public void onEnable() {
        final var enableStartTime = Clock.currentTimeMillis();

        try {
            this.featherLogger = new FeatherLogger(this);

            this.featherLogger.info("Info message");
            this.featherLogger.warn("Warn message");
            this.featherLogger.error("Error message");
            this.featherLogger.debug("Debug message");

            this.modulesManager.onEnable(this);

            final var enableFinishTime = Clock.currentTimeMillis();

            getFeatherLogger().info("Successfully enabled&8. (&btook&8: &b"
                    + TimeUtils.formatElapsed(enableStartTime, enableFinishTime) + "&8)");
        } catch (final FeatherSetupException | ModuleNotEnabledException | IOException e) {
            this.featherLogger.error(StringUtils.exceptionToStr(e));
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        this.modulesManager.onDisable(getFeatherLogger());
        getFeatherLogger().info("Goodbye&8!");
    }

    public IFeatherLogger getFeatherLogger() {
        return this.featherLogger;
    }

    public List<FeatherModule> getEnabledModules() {
        return this.modulesManager.getEnabledModules();
    }
}
