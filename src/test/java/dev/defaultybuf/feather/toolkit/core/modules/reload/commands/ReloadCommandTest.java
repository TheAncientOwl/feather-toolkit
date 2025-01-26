/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file ReloadCommandTest.java
 * @author Alexandru Delegeanu
 * @version 0.20
 * @test_unit ReloadCommand#0.7
 * @description Unit tests for ReloadCommand
 */

package dev.defaultybuf.feather.toolkit.core.modules.reload.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import dev.defaultybuf.feather.toolkit.core.Message;
import dev.defaultybuf.feather.toolkit.core.modules.language.components.LanguageManager;
import dev.defaultybuf.feather.toolkit.testing.FeatherCommandTest;
import dev.defaultybuf.feather.toolkit.testing.FeatherToolkitDependencyFactory;

class ReloadCommandTest extends FeatherCommandTest<ReloadCommand> {
    @Mock CommandSender mockSender;

    @Override
    protected Class<ReloadCommand> getCommandClass() {
        return ReloadCommand.class;
    }

    @Test
    void testHasPermission_WithPermission() {
        when(mockSender.hasPermission("featherplugin.reload")).thenReturn(true);

        var commandData = new ReloadCommand.CommandData(new ArrayList<>());
        assertTrue(commandInstance.hasPermission(mockSender, commandData));

        verify(mockSender, times(1)).hasPermission("featherplugin.reload");
        verifyNoInteractions(mockLanguage);
    }

    @Test
    void testHasPermission_WithoutPermission() {
        when(mockSender.hasPermission("featherplugin.reload")).thenReturn(false);

        var commandData = new ReloadCommand.CommandData(new ArrayList<>());
        assertFalse(commandInstance.hasPermission(mockSender, commandData));

        verify(mockSender, times(1)).hasPermission("featherplugin.reload");
        verify(mockLanguage, times(1))
                .message(mockSender, Message.GeneralCore.PERMISSION_DENIED);
    }

    @Test
    void testExecute_ReloadsConfigsAndTranslations() {
        final var enabledModules = List.of(
                FeatherToolkitDependencyFactory.getReloadFactory().MockModule(dependenciesMap),
                mock(LanguageManager.class));

        var commandData = new ReloadCommand.CommandData(enabledModules);
        commandInstance.execute(mockSender, commandData);

        verify(enabledModules.get(0).getConfig(), times(1)).reloadConfig();
        verify((LanguageManager) enabledModules.get(1), times(1)).reloadTranslations();
        verify(mockLanguage, times(1)).message(mockSender, Message.Reload.CONFIGS_RELOADED);
    }

    @Test
    void testParse_ValidAllArgument() {
        final var enabledModules = List.of(
                FeatherToolkitDependencyFactory.getReloadFactory().MockModule(dependenciesMap),
                FeatherToolkitDependencyFactory.getLanguageFactory().MockModule(
                        dependenciesMap));
        when(mockEnabledModulesProvider.getEnabledModules()).thenReturn(enabledModules);

        var args =
                new String[] {
                        FeatherToolkitDependencyFactory.getReloadFactory()
                                .MockModule(dependenciesMap)
                                .getModuleName()};
        var result = commandInstance.parse(mockSender, args);

        assertNotNull(result);
        assertEquals(1, result.modules().size());
        assertEquals(enabledModules.get(0), result.modules().get(0));
    }

    @Test
    void testParse_ValidArgument() {
        final var enabledModules = List.of(
                FeatherToolkitDependencyFactory.getReloadFactory().MockModule(dependenciesMap),
                FeatherToolkitDependencyFactory.getLanguageFactory().MockModule(
                        dependenciesMap));
        when(mockEnabledModulesProvider.getEnabledModules()).thenReturn(enabledModules);

        var args = new String[] {"all"};
        var result = commandInstance.parse(mockSender, args);

        assertNotNull(result);
        assertEquals(2, result.modules().size());
        assertEquals(enabledModules.get(0), result.modules().get(0));
        assertEquals(enabledModules.get(1), result.modules().get(1));
    }

    @Test
    void testParse_InvalidArgument() {
        when(commandInstance.getEnabledModules()).thenReturn(List.of());

        var args = new String[] {"nonexistent-module"};
        var result = commandInstance.parse(mockSender, args);

        assertNull(result);
        verify(mockLanguage, times(1))
                .message(eq(mockSender), eq(Message.Reload.USAGE),
                        anyPlaceholder());
    }

    @Test
    void testParse_EmptyArgument() {
        var args = new String[] {};
        var result = commandInstance.parse(mockSender, args);

        assertNull(result);
        verify(mockLanguage, times(1))
                .message(eq(mockSender), eq(Message.Reload.USAGE),
                        anyPlaceholder());
    }

    @Test
    void testOnTabComplete_NoArgument() {
        final var enabledModules = List.of(
                FeatherToolkitDependencyFactory.getReloadFactory().MockModule(
                        dependenciesMap),
                FeatherToolkitDependencyFactory.getLanguageFactory().MockModule(dependenciesMap));
        when(mockEnabledModulesProvider.getEnabledModules()).thenReturn(enabledModules);

        var args = new String[] {};
        var completions = commandInstance.onTabComplete(args);

        assertEquals(3, completions.size());
        assertEquals("all", completions.get(0), "1st completion should be 'all'");
        assertEquals(FeatherToolkitDependencyFactory.getReloadFactory().name(), completions.get(1),
                "2nd completion should be '"
                        + FeatherToolkitDependencyFactory.getReloadFactory().name()
                        + "'");
        assertEquals(FeatherToolkitDependencyFactory.getLanguageFactory().name(),
                completions.get(2),
                "3rd completion should be '"
                        + FeatherToolkitDependencyFactory.getLanguageFactory().name()
                        + "'");
    }

    @Test
    void testOnTabComplete_SingleArgument() {
        final var enabledModules = List.of(
                FeatherToolkitDependencyFactory.getReloadFactory().MockModule(
                        dependenciesMap),
                FeatherToolkitDependencyFactory.getLanguageFactory().MockModule(
                        dependenciesMap));
        when(mockEnabledModulesProvider.getEnabledModules()).thenReturn(enabledModules);

        var args = new String[] {"re"};
        var completions = commandInstance.onTabComplete(args);

        assertEquals(1, completions.size());
        assertEquals(FeatherToolkitDependencyFactory.getReloadFactory().name(), completions.get(0),
                "1st completion should be '"
                        + FeatherToolkitDependencyFactory.getReloadFactory().name()
                        + "'");
    }

    @Test
    void testOnTabComplete_NoMatchingArgument() {
        final var enabledModules = List.of(
                FeatherToolkitDependencyFactory.getReloadFactory().MockModule(
                        dependenciesMap),
                FeatherToolkitDependencyFactory.getLanguageFactory().MockModule(dependenciesMap));
        when(mockEnabledModulesProvider.getEnabledModules()).thenReturn(enabledModules);

        var args = new String[] {"unknown"};
        var completions = commandInstance.onTabComplete(args);

        assertTrue(completions.isEmpty());
    }

}
