/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherModuleHiddenTest.java
 * @author Alexandru Delegeanu
 * @version 0.6
 * @test_unit FeatherModuleHidden#0.6
 * @description Unit tests for FeatherModuleHidden
 */

package dev.defaultybuf.feather.toolkit.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import dev.defaultybuf.feather.toolkit.api.dummies.DummyModule;
import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;
import dev.defaultybuf.feather.toolkit.testing.mockers.FeatherModuleTest;

class FeatherModuleHiddenTest extends FeatherModuleTest<DummyModule> {

    @Override
    protected Class<DummyModule> getModuleClass() {
        return DummyModule.class;
    }

    @Override
    protected String getModuleName() {
        return FeatherModule.HIDE_LIFECYCLE_PREFIX + "DummyModule";
    }

    @Test
    void basics() {
        assertDoesNotThrow(() -> {
            assertEquals(FeatherModule.HIDE_LIFECYCLE_PREFIX
                    + "DummyModule", moduleInstance.getModuleName());
            assertNotNull(moduleInstance.getConfig());
            moduleInstance.onEnable();
            moduleInstance.onDisable(moduleInstance.getLogger());
            verifyNoInteractions(dependenciesMap.get(IFeatherLogger.class));
        });
    }
}
