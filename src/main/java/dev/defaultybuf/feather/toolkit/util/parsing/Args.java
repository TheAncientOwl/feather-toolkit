/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file Args.java
 * @author Alexandru Delegeanu
 * @version 0.7
 * @description Utility for parsing objects from command string args
 */

package dev.defaultybuf.feather.toolkit.util.parsing;

import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Args {
    public static final record ParseResult(Object[] args, int failIndex) {
        public static final int PARSE_SUCCESS_INDEX = -1;

        public boolean success() {
            return this.failIndex == ParseResult.PARSE_SUCCESS_INDEX;
        }

        public String getString(int index) {
            assert args[index] instanceof String : "[api.common.util]@Args.ParseResult.getString(): Argument at index "
                    + index
                    + " is not a string";

            return (String) args[index];
        }

        public double getDouble(int index) {
            assert args[index] instanceof Double : "[api.common.util]@Args.ParseResult.getString(): Argument at index "
                    + index
                    + " is not a double";

            return (double) args[index];
        }

        public int getInt(int index) {
            assert args[index] instanceof Integer : "[api.common.util]@Args.ParseResult.getString(): Argument at index "
                    + index
                    + " is not an integer";

            return (int) args[index];
        }

        public Player getPlayer(int index) {
            assert args[index] instanceof Player : "[api.common.util]@Args.ParseResult.getString(): Argument at index "
                    + index
                    + " is not a player";

            return (Player) args[index];
        }

        public OfflinePlayer getOfflinePlayer(int index) {
            assert args[index] instanceof OfflinePlayer : "[api.common.util]@Args.ParseResult.getString(): Argument at index "
                    + index
                    + " is not an offline player";

            return (OfflinePlayer) args[index];
        }

        public World getWorld(int index) {
            assert args[index] instanceof World : "[api.common.util]@Args.ParseResult.getString(): Argument at index "
                    + index + " is not a world";

            return (World) args[index];
        }
    }

    @SafeVarargs
    public static ParseResult parse(final String[] args, Function<String, Object>... parsers) {
        final var values = new Object[args.length];

        int index = 0;
        for (final var parser : parsers) {
            values[index] = parser.apply(args[index]);
            if (values[index] == null) {
                return new ParseResult(null, index);
            }
            index++;
        }

        return new ParseResult(values, -1);
    }

    public static Double getDouble(final String value) {
        Double out = null;
        try {
            out = Double.parseDouble(value);
        } catch (final Exception e) {
        }
        return out;
    }

    public static Integer getInt(final String value) {
        Integer out = null;
        try {
            out = Integer.parseInt(value);
        } catch (final Exception e) {
        }
        return out;
    }

    public static String getString(final String str) {
        return str;
    }

    public static Player getOnlinePlayer(final String name) {
        return Bukkit.getPlayerExact(name);
    }

    public static OfflinePlayer getOfflinePlayer(final String name) {
        var player = Bukkit.getOfflinePlayer(name);
        return player.hasPlayedBefore() ? player : null;
    }

    public static World getWorld(final String name) {
        return Bukkit.getWorld(name);
    }
}
