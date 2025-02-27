/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherLogger.java
 * @author Alexandru Delegeanu
 * @version 0.4
 * @description Plugin logger
 */

package dev.defaultybuf.feather.toolkit.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import dev.defaultybuf.feather.toolkit.api.interfaces.IFeatherLogger;

public class FeatherLogger implements IFeatherLogger {
    public class MinecraftAnsiColors {
        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String DARK_BLUE = "\u001B[34m";
        public static final String DARK_GREEN = "\u001B[32m";
        public static final String DARK_AQUA = "\u001B[36m";
        public static final String DARK_RED = "\u001B[31m";
        public static final String DARK_PURPLE = "\u001B[35m";
        public static final String GOLD = "\u001B[33m";
        public static final String GRAY = "\u001B[37m";
        public static final String DARK_GRAY = "\u001B[90m";
        public static final String BLUE = "\u001B[94m";
        public static final String GREEN = "\u001B[92m";
        public static final String AQUA = "\u001B[96m";
        public static final String RED = "\u001B[91m";
        public static final String LIGHT_PURPLE = "\u001B[95m";
        public static final String YELLOW = "\u001B[93m";
        public static final String WHITE = "\u001B[97m";

        public static String translateColors(final String message) {
            final StringBuilder translated = new StringBuilder();
            boolean colorCode = false;

            for (char c : message.toCharArray()) {
                if (colorCode) {
                    colorCode = false;
                    translated.append(getAnsiColor(c));
                } else if (c == '&') {
                    colorCode = true;
                } else {
                    translated.append(c);
                }
            }
            return translated.append(RESET).toString();
        }

        private static String getAnsiColor(char code) {
            return switch (code) {
                case '0' -> BLACK;
                case '1' -> DARK_BLUE;
                case '2' -> DARK_GREEN;
                case '3' -> DARK_AQUA;
                case '4' -> DARK_RED;
                case '5' -> DARK_PURPLE;
                case '6' -> GOLD;
                case '7' -> GRAY;
                case '8' -> DARK_GRAY;
                case '9' -> BLUE;
                case 'a' -> GREEN;
                case 'b' -> AQUA;
                case 'c' -> RED;
                case 'd' -> LIGHT_PURPLE;
                case 'e' -> YELLOW;
                case 'f' -> WHITE;
                case 'r' -> RESET;
                default -> "";
            };
        }
    }

    private final Logger logger;

    public FeatherLogger(final JavaPlugin plugin) {
        this.logger = plugin.getLogger();
    }

    public void info(final String message) {
        this.logger.log(Level.INFO, MinecraftAnsiColors.translateColors("&3" + message));
    }

    public void warn(final String message) {
        this.logger.log(Level.WARNING, MinecraftAnsiColors.translateColors(message));
    }

    public void error(final String message) {
        this.logger.log(Level.SEVERE, MinecraftAnsiColors.translateColors(message));
    }

    public void debug(final String message) {
        this.logger.log(Level.INFO,
                MinecraftAnsiColors.translateColors("&8[&2Debug&8]&r &a" + message));
    }
}
