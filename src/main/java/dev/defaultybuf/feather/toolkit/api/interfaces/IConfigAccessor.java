/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file IConfigAccessor.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Config accessor interface
 */

package dev.defaultybuf.feather.toolkit.api.interfaces;

import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;

public interface IConfigAccessor {
    public IConfigFile getConfig();
}
