/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file ThrowableDummyModule.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Dummy module class that throws on enable for testing
 */
package dev.defaultybuf.feather.toolkit.api.dummies;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.exceptions.FeatherSetupException;

public class ThrowableDummyModule extends FeatherModule {

    public ThrowableDummyModule(final FeatherModule.InitData data) {
        super(data);
    }

    @Override
    protected void onModuleEnable() throws FeatherSetupException {
        throw new FeatherSetupException("[Test] Failed to setup ThrowableDummyModule");
    }

    @Override
    protected void onModuleDisable() {}

}
