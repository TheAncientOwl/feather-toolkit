/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file DummyCommand.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Dummy command class for testing
 */
package dev.defaultybuf.feather.toolkit.api.dummies;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import dev.defaultybuf.feather.toolkit.api.FeatherCommand;

public class DummyCommand extends FeatherCommand<DummyCommand.CommandData> {
    public DummyCommand(final InitData data) {
        super(data);
    }

    public static record CommandData(String data) {
    }

    @Override
    protected CommandData parse(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return null;
        } else {
            var sb = new StringBuilder();
            for (var arg : args) {
                sb.append(arg).append(' ');
            }
            return new CommandData(sb.toString());
        }
    }

    @Override
    protected boolean hasPermission(CommandSender sender, CommandData data) {
        return data == null ? false : !data.data.startsWith("noperm");
    }

    @Override
    protected void execute(CommandSender sender, CommandData data) {
        sender.sendMessage(data.data);
    }

    @Override
    protected List<String> onTabComplete(String[] args) {
        return Arrays.asList(args);
    }
}
