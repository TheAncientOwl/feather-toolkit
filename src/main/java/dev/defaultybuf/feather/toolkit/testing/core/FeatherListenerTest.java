/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherListenerTest.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @description Utility class for developing listeners unit tests that use modules
 */

package dev.defaultybuf.feather.toolkit.testing.core;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;

import dev.defaultybuf.feather.toolkit.api.FeatherListener;

public abstract class FeatherListenerTest<ListenerType extends FeatherListener>
        extends FeatherUnitTest {
    protected ListenerType listenerInstance;

    @BeforeEach
    void setUpListenerTest()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        listenerInstance = getListenerClass().getConstructor(FeatherListener.InitData.class)
                .newInstance(new FeatherListener.InitData(dependenciesMap));
    }

    protected abstract Class<ListenerType> getListenerClass();
}
