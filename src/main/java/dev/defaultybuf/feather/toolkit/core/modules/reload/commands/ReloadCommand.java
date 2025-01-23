/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file ReloadCommand.java
 * @author Alexandru Delegeanu
 * @version 0.8
 * @description Reload configurations command
 */

package dev.defaultybuf.feather.toolkit.core.modules.reload.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import dev.defaultybuf.feather.toolkit.api.FeatherCommand;
import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.core.Message;
import dev.defaultybuf.feather.toolkit.core.Placeholder;
import dev.defaultybuf.feather.toolkit.core.modules.language.components.LanguageManager;
import dev.defaultybuf.feather.toolkit.util.java.Pair;
import dev.defaultybuf.feather.toolkit.util.java.StringUtils;

public class ReloadCommand extends FeatherCommand<ReloadCommand.CommandData> {
    public ReloadCommand(final InitData data) {
        super(data);
    }

    public static record CommandData(List<FeatherModule> modules) {
    }

    @Override
    protected boolean hasPermission(final CommandSender sender, final CommandData data) {
        if (!sender.hasPermission("featherplugin.reload")) {
            getLanguage().message(sender, Message.GeneralCore.PERMISSION_DENIED);
            return false;
        }
        return true;
    }

    @Override
    protected void execute(final CommandSender sender, final CommandData data) {
        for (final var module : data.modules) {
            final var config = module.getConfig();
            if (config != null) {
                config.reloadConfig();
            }

            if (module instanceof LanguageManager) {
                ((LanguageManager) module).reloadTranslations();
            }
        }

        getLanguage().message(sender,
                data.modules.size() == 1 ? Message.Reload.CONFIG_RELOADED
                        : Message.Reload.CONFIGS_RELOADED);
    }

    protected CommandData parse(final CommandSender sender, final String[] args) {
        if (args.length != 1) {
            getLanguage().message(sender, Message.Reload.USAGE,
                    Pair.of(Placeholder.STRING, getEnabledModulesNames()));
            return null;
        }

        List<FeatherModule> modules = new ArrayList<FeatherModule>();

        final var arg0 = args[0].toLowerCase();
        if (arg0.equals("all")) {
            modules = getEnabledModules();
        } else {
            for (final var module : getEnabledModules()) {
                if (module.getModuleName().toLowerCase().equals(arg0)) {
                    modules.add(module);
                    break;
                }
            }
        }

        if (modules.isEmpty()) {
            getLanguage().message(sender, Message.Reload.USAGE,
                    Pair.of(Placeholder.STRING, getEnabledModulesNames()));
            return null;
        }

        return new CommandData(modules);
    }

    @Override
    public List<String> onTabComplete(final String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions = StringUtils.filterStartingWith(getEnabledModulesNames(), args[0]);
        } else {
            completions = getEnabledModulesNames();
        }

        return completions;
    }

    private List<String> getEnabledModulesNames() {
        final var modules = new ArrayList<String>();
        modules.add("all");
        getEnabledModules().forEach(module -> modules.add(module.getModuleName()));
        return modules;
    }
}
