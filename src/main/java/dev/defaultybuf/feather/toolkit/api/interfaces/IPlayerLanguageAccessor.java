/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file IPlayerLanguageAccessor.java
 * @author Alexandru Delegeanu
 * @version 0.1
 * @description Accessor interface for players language data
 */

package dev.defaultybuf.feather.toolkit.api.interfaces;

import org.bukkit.OfflinePlayer;

public interface IPlayerLanguageAccessor {
    public String getPlayerLanguageCode(final OfflinePlayer player);

    public void setPlayerLanguageCode(final OfflinePlayer player, final String lang);
}
