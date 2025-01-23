/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file Dummy2.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @description Dummy class for testing
 */
package dev.defaultybuf.feather.toolkit.api.dummies;

public class Dummy2 {
    String data;

    public Dummy2() {
        this.data = "nodata2";
    }

    public Dummy2(String data) {
        this.data = data;
    }

    public void foo() {}

    public String bar() {
        return "bar2";
    };

    public String get() {
        return this.data;
    }
}
