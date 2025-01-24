/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherModuleTest.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @description Utility class for developing module unit tests that use modules
 */

package dev.defaultybuf.feather.toolkit.testing.core;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;

public abstract class FeatherModuleTest<ModuleType extends FeatherModule>
        extends FeatherUnitTest {
    @Mock protected IConfigFile mockModuleConfig;
    protected ModuleType moduleInstance;

    @BeforeEach
    void setUpModuleTest()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        moduleInstance = getModuleClass().getConstructor(FeatherModule.InitData.class).newInstance(
                new FeatherModule.InitData(getModuleName(), () -> mockModuleConfig,
                        dependenciesMap));

        dependenciesMap.put(getModuleClass(), moduleInstance);
    }

    protected abstract Class<ModuleType> getModuleClass();

    protected abstract String getModuleName();
}
