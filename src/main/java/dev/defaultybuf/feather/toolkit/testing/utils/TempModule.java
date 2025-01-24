/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file TempModule.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @description Helper class to delete given module files when no longer needed
 */
package dev.defaultybuf.feather.toolkit.testing.utils;

import java.util.List;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;

public class TempModule<T extends FeatherModule> implements AutoCloseable {
    final List<TempFile> resources;
    final T module;

    public TempModule(T module, List<TempFile> resources) {
        this.module = module;
        this.resources = resources;
    }

    @Override
    public void close() {
        for (var resource : resources) {
            resource.close();
        }
    }

    public T module() {
        return module;
    }
}
