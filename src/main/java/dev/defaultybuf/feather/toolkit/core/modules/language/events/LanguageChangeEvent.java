/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file LanguageChangeEvent.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @description Language change event data
 */

package dev.defaultybuf.feather.toolkit.core.modules.language.events;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import dev.defaultybuf.feather.toolkit.api.configuration.IConfigFile;

public class LanguageChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private final Player player;
    private final String language;
    private final IConfigFile translation;

    public LanguageChangeEvent(final Player player, final String language,
            final IConfigFile translation) {
        this.cancelled = false;

        this.player = player;
        this.language = language;
        this.translation = translation;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getLanguage() {
        return this.language;
    }

    public IConfigFile getTranslation() {
        return this.translation;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        LanguageChangeEvent that = (LanguageChangeEvent) obj;
        return Objects.equals(player, that.player) &&
                Objects.equals(language, that.language) &&
                Objects.equals(translation, that.translation);
    }
}
