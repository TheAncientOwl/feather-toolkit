/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file JavaExt.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description General algorithms
 */

package dev.defaultybuf.feather.toolkit.util.java;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JavaExt {
    public static <T> Optional<T> findIf(final List<T> list, final Predicate<T> predicate) {
        if (list == null) {
            return Optional.empty();
        }

        for (final var value : list) {
            if (predicate.test(value)) {
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

    public static <T> Optional<Integer> findIndex(final List<T> list,
            final Predicate<T> predicate) {
        if (list == null) {
            return Optional.empty();
        }

        for (int index = 0; index < list.size(); ++index) {
            if (predicate.test(list.get(index))) {
                return Optional.of(index);
            }
        }

        return Optional.empty();
    }

    public static <T> Optional<Integer> findLastIndex(final List<T> list,
            final Predicate<T> predicate) {
        if (list == null) {
            return Optional.empty();
        }

        int lastIndex = -1;

        for (int index = 0; index < list.size(); ++index) {
            if (predicate.test(list.get(index))) {
                lastIndex = index;
            }
        }

        return lastIndex == -1 ? Optional.empty() : Optional.of(lastIndex);
    }

    public static <T> boolean contains(final List<T> list, final Predicate<T> what) {
        if (list == null) {
            return false;
        }

        for (final var value : list) {
            if (what.test(value)) {
                return true;
            }
        }

        return false;
    }
}
