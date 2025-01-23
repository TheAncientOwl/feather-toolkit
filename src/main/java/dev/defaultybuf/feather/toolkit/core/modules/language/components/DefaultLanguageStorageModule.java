/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file DefaultLanguageStorageModule.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Default storage option for language module
 */

package dev.defaultybuf.feather.toolkit.core.modules.language.components;

import java.io.IOException;

import org.bukkit.OfflinePlayer;

import dev.defaultybuf.feather.toolkit.api.FeatherModule;
import dev.defaultybuf.feather.toolkit.api.interfaces.IPlayerLanguageAccessor;

public class DefaultLanguageStorageModule extends FeatherModule implements IPlayerLanguageAccessor {

    public DefaultLanguageStorageModule(final InitData data) {
        super(data);
    }

    @Override
    public String getPlayerLanguageCode(final OfflinePlayer player) {
        return getConfig().getString(player.getUniqueId().toString(), "en");
    }

    @Override
    public void setPlayerLanguageCode(final OfflinePlayer player, String lang) {
        getConfig().setString(player.getUniqueId().toString(), lang);
        try {
            getConfig().saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
