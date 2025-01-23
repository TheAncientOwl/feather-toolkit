/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
*
* @file Pair.java
* @author Alexandru Delegeanu
* @version 0.1
* @description Utility class
*/

package dev.defaultybuf.feather.toolkit.util.java;

public class Pair<First, Second> {
    public First first = null;
    public Second second = null;

    public Pair(final First first, final Second second) {
        this.first = first;
        this.second = second;
    }

    public static <First, Second> Pair<First, Second> of(final First first, final Second second) {
        return new Pair<First, Second>(first, second);
    }
}
