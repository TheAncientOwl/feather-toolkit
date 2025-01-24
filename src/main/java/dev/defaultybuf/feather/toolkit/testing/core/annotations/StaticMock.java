/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file StaticMock.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Marker annotation for static mocked classes, used for testing
 */
package dev.defaultybuf.feather.toolkit.testing.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StaticMock {
    Class<?> of();
}
