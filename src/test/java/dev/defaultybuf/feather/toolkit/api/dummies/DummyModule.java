/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file DummyModule.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Dummy module class for testing
 */
package dev.defaultybuf.feather.toolkit.api.dummies;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.exceptions.FeatherSetupException;

public class DummyModule extends FeatherModule {

    public DummyModule(final FeatherModule.InitData data) {
        super(data);
    }

    @Override
    protected void onModuleEnable() throws FeatherSetupException {}

    @Override
    protected void onModuleDisable() {}

}
