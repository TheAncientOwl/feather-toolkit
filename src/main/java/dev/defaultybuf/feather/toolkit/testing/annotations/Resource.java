/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file Resource.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Helper annotation for loading resources in actual modules 
 *              injected as dependencies
 */
package dev.defaultybuf.feather.toolkit.testing.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Resource {
    String path();

    String content();
}
