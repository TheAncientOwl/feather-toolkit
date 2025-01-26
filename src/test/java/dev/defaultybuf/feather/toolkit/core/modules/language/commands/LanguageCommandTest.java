/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file LanguageCommandTest.java
 * @author Alexandru Delegeanu
 * @version 0.21
 * @test_unit LanguageCommand#0.9
 * @description Unit tests for LanguageCommand
 */

package dev.defaultybuf.feather.toolkit.core.modules.language.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import dev.defaultybuf.feather.toolkit.core.Message;
import dev.defaultybuf.feather.toolkit.core.Placeholder;
import dev.defaultybuf.feather.toolkit.core.modules.language.commands.LanguageCommand.CommandType;
import dev.defaultybuf.feather.toolkit.core.modules.language.components.LanguageManager;
import dev.defaultybuf.feather.toolkit.core.modules.language.components.LanguageManagerTest;
import dev.defaultybuf.feather.toolkit.core.modules.language.events.LanguageChangeEvent;
import dev.defaultybuf.feather.toolkit.core.modules.language.interfaces.ILanguage;
import dev.defaultybuf.feather.toolkit.testing.FeatherCommandTest;
import dev.defaultybuf.feather.toolkit.testing.annotations.ActualModule;
import dev.defaultybuf.feather.toolkit.testing.annotations.Resource;
import dev.defaultybuf.feather.toolkit.testing.utils.TempModule;
import dev.defaultybuf.feather.toolkit.testing.utils.TestUtils;
import dev.defaultybuf.feather.toolkit.util.java.Pair;

class LanguageCommandTest extends FeatherCommandTest<LanguageCommand> {
    static final String LANGUAGE_CONFIG_CONTENT = "languages:\n en: English\n de: Deutsch";

    // @formatter:off
     static final String EN_LANGUAGE_FILE_CONTENT =
            "language:\n" +
            "  usage: '&8[&6Usage&8] &e/language info/list/[language]'\n" +
            "  change-success: '&8[&6Language&8] &eChanged successfully&8!'\n" +
            "  unknown: '&8[&4Language&8] &cInvalid value&8!'\n" +
            "  info: '&8[&6Language&8] &e{language}&8.'\n" +
            "  list: '&8[&6Language&8] &7Available languages&8: &e{language}&8.'\n";

     static final String DE_LANGUAGE_FILE_CONTENT =
            "language:\n" +
            "  usage: '&8[&6Verwendung&8] &e/language info/list/[sprache]'\n" +
            "  change-success: '&8[&6Sprache&8] &eErfolgreich geändert&8!'\n" +
            "  unknown: '&8[&4Sprache&8] &cUngültiger Wert&8!'\n" +
            "  info: '&8[&6Sprache&8] &e{sprache}&8.'\n" +
            "  list: '&8[&6Sprache&8] &7Verfügbare Sprachen&8: &e{sprache}&8.'\n";
    // @formatter:on

    @Mock World mockWorld;
    @Mock Player mockPlayer;
    @Mock CommandSender mockSender;

    @ActualModule(
            of = ILanguage.class,
            resources = {
                    @Resource(path = "config.yml", content = LANGUAGE_CONFIG_CONTENT),
                    @Resource(path = "en.yml", content = EN_LANGUAGE_FILE_CONTENT),
                    @Resource(path = "de.yml", content = DE_LANGUAGE_FILE_CONTENT),
            }) TempModule<LanguageManager> actualLanguage;

    ArgumentCaptor<String> messageCaptor;

    @Override
    protected Class<LanguageCommand> getCommandClass() {
        return LanguageCommand.class;
    }

    @Override
    protected void setUp() {
        lenient().when(mockPlayer.getLocation()).thenReturn(new Location(mockWorld, 0, 0, 0));

        lenient().when(mockPlayersLanguageAccessor.getPlayerLanguageCode(mockPlayer))
                .thenReturn(LanguageManagerTest.EN.shortName());

        messageCaptor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void testHasPermission_PermissionINFO() {
        var commandData =
                new LanguageCommand.CommandData(LanguageCommand.CommandType.INFO,
                        LanguageManagerTest.EN.shortName());
        assertTrue(commandInstance.hasPermission(mockSender, commandData));

        verifyNoInteractions(mockSender);
    }

    @Test
    void testHasPermission_PermissionLIST() {
        var commandData =
                new LanguageCommand.CommandData(LanguageCommand.CommandType.LIST,
                        LanguageManagerTest.EN.shortName());
        assertTrue(commandInstance.hasPermission(mockSender, commandData));

        verifyNoInteractions(mockSender);
    }

    @Test
    void testHasPermission_PermissionCHANGE() {
        var commandData =
                new LanguageCommand.CommandData(LanguageCommand.CommandType.CHANGE,
                        LanguageManagerTest.EN.shortName());
        assertTrue(commandInstance.hasPermission(mockSender, commandData));

        verifyNoInteractions(mockSender);
    }

    @Test
    void execute_INFO() {
        var commandData = new LanguageCommand.CommandData(
                LanguageCommand.CommandType.INFO, null);

        assertDoesNotThrow(() -> {
            commandInstance.execute(mockPlayer, commandData);
        });

        when(mockPlayersLanguageAccessor.getPlayerLanguageCode(mockPlayer)).thenReturn("fr");

        assertDoesNotThrow(() -> {
            commandInstance.execute(mockPlayer, commandData);
        });

        verify(mockPlayer, times(2)).sendMessage(messageCaptor.capture());

        assertEquals(2, messageCaptor.getAllValues().size());

        var rawMessage =
                actualLanguage.module().getTranslation("en").getString(Message.Language.INFO);
        assertEquals(
                TestUtils.placeholderize(rawMessage, Pair.of(Placeholder.LANGUAGE, "English")),
                messageCaptor.getAllValues().get(0));
        assertEquals(
                TestUtils.placeholderize(rawMessage, Pair.of(Placeholder.LANGUAGE, "")),
                messageCaptor.getAllValues().get(1));
    }

    @Test
    void execute_LIST() {

        var commandData = new LanguageCommand.CommandData(
                LanguageCommand.CommandType.LIST, null);

        assertDoesNotThrow(() -> {
            commandInstance.execute(mockPlayer, commandData);
        });

        when(mockPlayersLanguageAccessor.getPlayerLanguageCode(mockPlayer))
                .thenReturn(LanguageManagerTest.FR.shortName());

        assertDoesNotThrow(() -> {
            commandInstance.execute(mockPlayer, commandData);
        });

        verify(mockPlayer, times(2)).sendMessage(messageCaptor.capture());

        assertEquals(2, messageCaptor.getAllValues().size());

        var rawMessage =
                actualLanguage.module().getTranslation("en").getString(Message.Language.LIST);
        assertEquals(
                TestUtils.placeholderize(rawMessage,
                        Pair.of(Placeholder.LANGUAGE, "\n   en: English\n   de: Deutsch")),
                messageCaptor.getAllValues().get(0));
        assertEquals(
                TestUtils.placeholderize(rawMessage,
                        Pair.of(Placeholder.LANGUAGE, "\n   en: English\n   de: Deutsch")),
                messageCaptor.getAllValues().get(1));
    }

    @Test
    void execute_CHANGE() {
        var mockPluginManager = mock(PluginManager.class);
        when(mockServer.getPluginManager()).thenReturn(mockPluginManager);

        var commandData = new LanguageCommand.CommandData(
                LanguageCommand.CommandType.CHANGE, "de");

        assertDoesNotThrow(() -> {
            commandInstance.execute(mockPlayer, commandData);
        });

        verify(mockPlayersLanguageAccessor).setPlayerLanguageCode(eq(mockPlayer), eq("de"));
        verify(mockPlayer).sendMessage(anyString());
        verify(mockPluginManager).callEvent(any(LanguageChangeEvent.class));
    }

    @Test
    void execute_NULL() {
        var commandData = new LanguageCommand.CommandData(null, "de");

        assertThrows(NullPointerException.class, () -> {
            commandInstance.execute(mockPlayer, commandData);
        });

        verify(mockPlayersLanguageAccessor, never()).setPlayerLanguageCode(eq(mockPlayer),
                eq("de"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"info", "INFO", "iNfO", "iNFO", "inFO", "infO", "inFo"})
    void parse_INFO(String arg) {
        var args = new String[] {arg};

        assertDoesNotThrow(() -> {
            var commandData = commandInstance.parse(mockSender, args);

            assertEquals(CommandType.INFO, commandData.commandType(),
                    "Command type should be info");
            assertNull(commandData.language());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"list", "LIST", "lIsT", "liST", "lisT", "liSt"})
    void parse_LIST(String arg) {
        var args = new String[] {arg};

        assertDoesNotThrow(() -> {
            var commandData = commandInstance.parse(mockSender, args);

            assertNotNull(commandData, "Command data should be parsed successfully");
            assertEquals(CommandType.LIST, commandData.commandType(),
                    "Command type should be list");
            assertNull(commandData.language());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "de"})
    void parse_CHANGE_EXIST(String arg) {
        var args = new String[] {arg};

        assertDoesNotThrow(() -> {
            var commandData = commandInstance.parse(mockSender, args);

            assertNotNull(commandData, "Command data should be parsed successfully");
            assertEquals(CommandType.CHANGE, commandData.commandType(),
                    "Command type should be change");
            assertNotNull(commandData.language());
            assertEquals(arg, commandData.language());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"fr", "ro"})
    void parse_CHANGE_NOT_EXIST(String arg) {
        var args = new String[] {arg};

        assertDoesNotThrow(() -> {
            var commandData = commandInstance.parse(mockSender, args);

            assertNull(commandData, "Command data should be null");
            verify(mockSender).sendMessage(anyString());
        });
    }

    @ParameterizedTest
    @MethodSource("getInvalidArguments")
    void parse_InvalidArguments(String[] args) {
        assertDoesNotThrow(() -> {
            var commandData = commandInstance.parse(mockSender, args);

            assertNull(commandData, "Command data should be null");
            verify(mockSender).sendMessage(anyString());
        });
    }

    static Stream<Arguments> getInvalidArguments() {
        return Stream.of(
                Arguments.of((Object) new String[] {"info", "arg1"}),
                Arguments.of((Object) new String[] {"info", "de"}),
                Arguments.of((Object) new String[] {"list", "arg1"}),
                Arguments.of((Object) new String[] {"change", "arg1"}),
                Arguments.of((Object) new String[] {"change", "de"}),
                Arguments.of((Object) new String[] {"arg0", "arg1", "arg3"}));
    }

    @ParameterizedTest
    @MethodSource("getTabCompletions")
    void onTabComplete(String[] args, String[] expectedCompletions) {
        assertDoesNotThrow(() -> {
            var completions = commandInstance.onTabComplete(args);

            assertNotNull(completions, "Completions should not be null");
            assertArrayEquals(expectedCompletions, completions.toArray());
        });
    }

    static Stream<Arguments> getTabCompletions() {
        return Stream.of(
                Arguments.of((Object) new String[] {},
                        (Object) new String[] {"en", "de", "info", "list"}),
                Arguments.of((Object) new String[] {"e"}, (Object) new String[] {"en"}),
                Arguments.of((Object) new String[] {"en"}, (Object) new String[] {"en"}),
                Arguments.of((Object) new String[] {"d"}, (Object) new String[] {"de"}),
                Arguments.of((Object) new String[] {"i"}, (Object) new String[] {"info"}),
                Arguments.of((Object) new String[] {"in"}, (Object) new String[] {"info"}),
                Arguments.of((Object) new String[] {"inf"}, (Object) new String[] {"info"}),
                Arguments.of((Object) new String[] {"info"}, (Object) new String[] {"info"}),
                Arguments.of((Object) new String[] {"l"}, (Object) new String[] {"list"}),
                Arguments.of((Object) new String[] {"li"}, (Object) new String[] {"list"}),
                Arguments.of((Object) new String[] {"lis"}, (Object) new String[] {"list"}),
                Arguments.of((Object) new String[] {"list"}, (Object) new String[] {"list"}));
    }

}
