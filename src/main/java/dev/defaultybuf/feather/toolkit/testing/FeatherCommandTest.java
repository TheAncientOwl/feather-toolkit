/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file CommandDependencyAccessorMocker.java
 * @author Alexandru Delegeanu
 * @version 0.4
 * @description Utility class for developing commands unit tests that use modules
 */

package dev.defaultybuf.feather.toolkit.testing;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;

import dev.defaultybuf.feather.toolkit.api.FeatherCommand;

public abstract class FeatherCommandTest<CommandType extends FeatherCommand<?>>
        extends FeatherUnitTest {
    protected CommandType commandInstance;

    @BeforeEach
    void setUpCommandTest()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        commandInstance = getCommandClass().getConstructor(FeatherCommand.InitData.class)
                .newInstance(new FeatherCommand.InitData(dependenciesMap));
    }

    protected abstract Class<CommandType> getCommandClass();
}
