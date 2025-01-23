/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file Dummy1.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @description Dummy class for testing
 */
package dev.defaultybuf.feather.toolkit.api.dummies;

public class Dummy1 {
    String data;

    public Dummy1() {
        this.data = "nodata1";
    }

    public Dummy1(String data) {
        this.data = data;
    }

    public void foo() {}

    public String bar() {
        return "bar1";
    };

    public String get() {
        return this.data;
    }
}
