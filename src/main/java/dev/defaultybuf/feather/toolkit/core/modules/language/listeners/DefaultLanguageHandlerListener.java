/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file DefaultLanguageHandlerListener.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Set default language of players
 */

package dev.defaultybuf.feather.toolkit.core.modules.language.listeners;

import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.defaultybuf.feather.toolkit.api.FeatherListener;
import dev.defaultybuf.feather.toolkit.core.modules.language.components.DefaultLanguageStorageModule;

public class DefaultLanguageHandlerListener extends FeatherListener {

    public DefaultLanguageHandlerListener(final InitData data) {
        super(data);
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final var playerUUID = event.getPlayer().getUniqueId().toString();

        final var config = getInterface(DefaultLanguageStorageModule.class).getConfig();

        final var language = config.getString(playerUUID, "none");
        if (language.equals("none")) {
            config.setString(playerUUID, "en");
            try {
                config.saveConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
