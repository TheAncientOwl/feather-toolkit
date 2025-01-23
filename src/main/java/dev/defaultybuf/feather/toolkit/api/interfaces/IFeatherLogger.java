/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file IFeatherLogger.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @description Plugin logger interface
 */

package dev.defaultybuf.feather.toolkit.api.interfaces;

public interface IFeatherLogger {
    public void info(final String message);

    public void warn(final String message);

    public void error(final String message);

    public void debug(final String message);
}
