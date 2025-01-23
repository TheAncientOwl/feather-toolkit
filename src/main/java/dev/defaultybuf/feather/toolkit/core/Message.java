/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file Message.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Message keys from language yml files
 */

package dev.defaultybuf.feather.toolkit.core;

public class Message {
    public static final class GeneralCore {
        public static final String PERMISSION_DENIED = "general.command.no-permission";
        public static final String USAGE_INVALID = "general.command.invalid";
        public static final String NO_PERMISSION = "general.no-permission";
        public static final String PLAYERS_ONLY = "general.command.players-only";
        public static final String NAN = "general.not-valid-number";
        public static final String NOT_ONLINE_PLAYER = "general.not-online-player";
        public static final String NOT_VALID_PLAYER = "general.not-player";
        public static final String NOT_VALID_WORLD = "general.not-valid-world";
        public static final String NOT_VALID_VALUE = "general.invalid-value";
        public static final String NOT_VALID_NUMBER = "general.not-valid-number";
        public static final String WORLD_NO_LONGER_AVAILABLE = "general.world-no-longer-available";
    }

    public static final class Reload {
        public static final String USAGE = "configuration.usage";
        public static final String CONFIG_RELOADED = "configuration.reload.single";
        public static final String CONFIGS_RELOADED = "configuration.reload.multiple";
    }

    public static final class Language {
        public static final String USAGE = "language.usage";
        public static final String CHANGE_SUCCESS = "language.change-success";
        public static final String UNKNOWN = "language.unknown";
        public static final String INFO = "language.info";
        public static final String LIST = "language.list";
    }

}
