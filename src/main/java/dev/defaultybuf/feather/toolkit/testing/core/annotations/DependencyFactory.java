/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file DependencyFactory.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Test annotation used on methods to specify which dependency does it provide
 */

package dev.defaultybuf.feather.toolkit.testing.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DependencyFactory {
    Class<?> of();
}
