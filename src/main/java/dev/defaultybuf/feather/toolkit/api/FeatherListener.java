/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherListener.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @description Listener interface for paper events
 */

package dev.defaultybuf.feather.toolkit.api;

import java.util.Map;

import org.bukkit.event.Listener;

public abstract class FeatherListener extends FeatherDependencyAccessor implements Listener {
    public static final record InitData(Map<Class<?>, Object> modules) {
    }

    public FeatherListener(final InitData data) {
        super(data.modules);
    }
}
