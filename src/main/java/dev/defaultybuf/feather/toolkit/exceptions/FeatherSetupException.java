/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherSetupException.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Exception throw during plugin load
 */

package dev.defaultybuf.feather.toolkit.exceptions;

public class FeatherSetupException extends Exception {
    public FeatherSetupException() {
        super();
    }

    public FeatherSetupException(String message) {
        super(message);
    }

    public FeatherSetupException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeatherSetupException(Throwable cause) {
        super(cause);
    }
}
