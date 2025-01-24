/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file ReloadModuleTest.java
 * @author Alexandru Delegeanu
 * @version 0.10
 * @test_unit ReloadModule#0.4
 * @description Unit tests for ReloadModule
 */

package dev.defaultybuf.feather.toolkit.core.modules.reload.components;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import dev.defaultybuf.feather.toolkit.testing.core.FeatherModuleTest;
import dev.defaultybuf.feather.toolkit.testing.core.FeatherToolkitDependencyFactory;

class ReloadModuleTest extends FeatherModuleTest<ReloadModule> {

    @Override
    protected Class<ReloadModule> getModuleClass() {
        return ReloadModule.class;
    }

    @Override
    protected String getModuleName() {
        return FeatherToolkitDependencyFactory.getReloadFactory().name();
    }

    @Test
    void testModuleBasics() {
        assertDoesNotThrow(() -> moduleInstance.onModuleEnable());
        assertDoesNotThrow(() -> moduleInstance.onModuleDisable());
    }

}
