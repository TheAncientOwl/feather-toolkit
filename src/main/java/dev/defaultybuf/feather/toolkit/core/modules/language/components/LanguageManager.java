/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file LanguageManager.java
 * @author Alexandru Delegeanu
 * @version 0.11
 * @description Module responsible for managing plugin messages translations
 */

package dev.defaultybuf.feather.toolkit.core.modules.language.components;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;
import dev.defaultybuf.feather.toolkit.api.interfaces.IPlayerLanguageAccessor;
import dev.defaultybuf.feather.toolkit.core.configuration.bukkit.BukkitConfigFile;
import dev.defaultybuf.feather.toolkit.core.modules.language.interfaces.ILanguage;
import dev.defaultybuf.feather.toolkit.exceptions.FeatherSetupException;
import dev.defaultybuf.feather.toolkit.util.java.Pair;
import dev.defaultybuf.feather.toolkit.util.java.StringUtils;

public class LanguageManager extends FeatherModule implements ILanguage {
    private final Map<String, IConfigFile> translations = new HashMap<>();

    public LanguageManager(final InitData data) {
        super(data);
    }

    @Override
    protected void onModuleEnable() throws FeatherSetupException {
        this.translations.put("en", loadTranslation("en"));
    }

    @Override
    protected void onModuleDisable() {}

    public IConfigFile getTranslation(final String language) {
        IConfigFile translation = this.translations.get(language);

        if (translation == null) {
            translation = loadTranslation(language);
            if (translation != null) {
                this.translations.put(language, translation);
            } else {
                translation = this.translations.get("en");
            }
        }

        return translation;
    }

    @Override
    public IConfigFile getTranslation(final CommandSender sender) {
        return getTranslation(
                sender instanceof Player
                        ? getInterface(IPlayerLanguageAccessor.class)
                                .getPlayerLanguageCode((OfflinePlayer) sender)
                        : "en");
    }

    private IConfigFile loadTranslation(final String language) {
        IConfigFile translation = null;

        try {
            translation = new BukkitConfigFile(getPlugin(),
                    Path.of("language", language + ".yml").toString());
        } catch (IOException e) {
            getLogger().error(e.getStackTrace().toString());
        }

        return translation;
    }

    public void reloadTranslations() {
        this.translations.forEach((name, config) -> {
            config.reloadConfig();
        });
    }

    @Override
    public void message(final CommandSender receiver, final String key) {
        assert getTranslation(receiver).getString(
                key) != null : "[modules.language.components]@LanguageManager.message(CommandSender, String): Null entry for key: "
                        + key;

        receiver.sendMessage(StringUtils.translateColors(getTranslation(receiver).getString(key)));
    }

    @Override
    public void message(final CommandSender receiver, String... keys) {
        final StringBuilder sb = new StringBuilder();

        final var translation = getTranslation(receiver);

        for (final var key : keys) {
            assert translation.getString(
                    key) != null : "[modules.language.components]@LanguageManager.message(CommandSender, String...): Null entry for key: "
                            + key;

            sb.append(translation.getString(key)).append('\n');
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }

        receiver.sendMessage(StringUtils.translateColors(sb.toString()));
    }

    @Override
    public void message(final CommandSender receiver, final String key,
            Pair<String, Object> placeholder) {
        assert placeholder.second != null : "[modules.language.components]@LanguageManager.message(CommandSender, String, Pair<String, Object>): Null placeholder value for key: "
                + placeholder.first;

        assert getTranslation(receiver).getString(
                key) != null : "[modules.language.components]@LanguageManager.message(CommandSender, String, Pair<String, Object>): Null translation entry for key "
                        + key;

        receiver
                .sendMessage(StringUtils
                        .translateColors(
                                getTranslation(receiver).getString(key).replace(placeholder.first,
                                        placeholder.second.toString())));
    }

    @Override
    public final void message(final CommandSender receiver, final String key,
            final List<Pair<String, Object>> placeholders) {
        receiver
                .sendMessage(StringUtils
                        .translateColors(StringUtils.replacePlaceholders(
                                getTranslation(receiver).getString(key), placeholders)));
    }
}
