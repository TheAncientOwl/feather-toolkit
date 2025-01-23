/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file IEnabledModulesProvider.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Helper interface
 */

package dev.defaultybuf.feather.toolkit.api.interfaces;

import java.util.List;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;

public interface IEnabledModulesProvider {
    public List<FeatherModule> getEnabledModules();
}
