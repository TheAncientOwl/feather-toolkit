/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file Cache.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @description Utility class for caching result of a given supplier
 */

package dev.defaultybuf.feather.toolkit.util.java;

import java.util.function.Supplier;

public class Cache<T> {
    private T obj = null;
    private final Supplier<T> supplier;

    public static <T> Cache<T> of(final Supplier<T> supplier) {
        return new Cache<T>(supplier);
    }

    public Cache(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (this.obj == null) {
            this.obj = this.supplier.get();
        }
        return obj;
    }
}
