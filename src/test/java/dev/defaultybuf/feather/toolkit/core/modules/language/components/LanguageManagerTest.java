/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file LanguageManagerTest.java
 * @author Alexandru Delegeanu
 * @version 0.17
 * @test_unit LanguageManager#0.8
 * @description Unit tests for LanguageManager
 */

package dev.defaultybuf.feather.toolkit.core.modules.language.components;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import dev.defaultybuf.feather.toolkit.testing.FeatherModuleTest;
import dev.defaultybuf.feather.toolkit.testing.FeatherToolkitDependencyFactory;
import dev.defaultybuf.feather.toolkit.testing.utils.TestUtils;
import dev.defaultybuf.feather.toolkit.util.java.Pair;

public class LanguageManagerTest extends FeatherModuleTest<LanguageManager> {
    public static final record TranslationTestConfig(String shortName, Path actualPath,
            String content, String testKey, String testValue) {
    }

    public static final TranslationTestConfig EN = new TranslationTestConfig("en",
            Paths.get("language", "en.yml"),
            "test-key: 'test-string-en'\ntest-key2: 'test-string-en2'\ntest-key-placeholder: 'Hello {placeholder1} {placeholder2} {placeholder2}!'",
            "test-key",
            "test-string-en");

    public static final TranslationTestConfig FR = new TranslationTestConfig("fr",
            Paths.get("language", "fr.yml"), "test-key: 'test-string-fr'", "test-key",
            "test-string-fr");

    @Mock World mockWorld;
    @Mock Player mockPlayer;
    @Mock ConsoleCommandSender mockConsole;
    @Mock YamlConfiguration mockLanguageConfig;

    @Override
    protected Class<LanguageManager> getModuleClass() {
        return LanguageManager.class;
    }

    @Override
    protected String getModuleName() {
        return FeatherToolkitDependencyFactory.getLanguageFactory().name();
    }

    @Override
    protected void setUp() {
        lenient().when(mockPlayer.getLocation()).thenReturn(new Location(mockWorld, 0, 0, 0));

        lenient().when(mockPlayersLanguageAccessor.getPlayerLanguageCode(mockPlayer))
                .thenReturn(EN.shortName);
    }

    @Test
    void testModuleBasics() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            assertDoesNotThrow(() -> moduleInstance.onModuleEnable());

            assertDoesNotThrow(() -> {
                var enTranslation = moduleInstance.getTranslation(EN.shortName);

                assertNotNull(enTranslation, "Translation should not be null");
                assertEquals(enTranslation.getString(EN.testKey), EN.testValue);
            });

            assertDoesNotThrow(() -> moduleInstance.onModuleDisable());
        }
    }

    @Test
    void getTranslation_LanguageStringValidCache() {
        assertDoesNotThrow(() -> {
            try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
                // get translation first time to get loaded
                var enTranslation01 = moduleInstance.getTranslation(EN.shortName);

                assertNotNull(enTranslation01, "Translation should not be null");
                assertEquals(EN.testValue, enTranslation01.getString(EN.testKey));

                // get already loaded translation
                var enTranslation02 = moduleInstance.getTranslation(EN.shortName);

                assertNotNull(enTranslation02, "Translation should not be null");
                assertEquals(EN.testValue, enTranslation02.getString(EN.testKey));
            }
        });

    }

    @Test
    void getTranslation_LanguageStringNotValid() {
        assertDoesNotThrow(() -> {
            try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
                moduleInstance.onModuleEnable();

                // get 'en' translation
                var enTranslation = moduleInstance.getTranslation(EN.shortName);

                assertNotNull(enTranslation, "'en' translation should not be null");
                assertEquals(enTranslation.getString(EN.testKey), EN.testValue);

                // get 'fr' translation
                var frTranslation = moduleInstance.getTranslation(FR.shortName);

                assertNotNull(frTranslation,
                        "'fr' translation should be the same as 'en' translation");
                assertEquals(frTranslation.getString(EN.testKey), EN.testValue);

            }
        });
    }

    @Test
    void getTranslation_ConsoleEnTranslation() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            assertDoesNotThrow(() -> {
                var enTranslation01 = moduleInstance.getTranslation(mockConsole);

                assertNotNull(enTranslation01, "'en' translation should not be null");
                assertEquals(EN.testValue, enTranslation01.getString(EN.testKey));

                var enTranslation02 = moduleInstance.getTranslation(mockConsole);

                assertNotNull(enTranslation02, "'en' translation should not be null");
                assertEquals(EN.testValue, enTranslation02.getString(EN.testKey));
            });
        }
    }

    @Test
    void getTranslation_SenderValidTranslation() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            lenient().when(mockPlayersLanguageAccessor.getPlayerLanguageCode(mockPlayer))
                    .thenReturn(EN.shortName);

            assertDoesNotThrow(() -> {
                var enTranslation01 = moduleInstance.getTranslation(mockPlayer);

                assertNotNull(enTranslation01, "'en' translation should not be null");
                assertEquals(EN.testValue, enTranslation01.getString(EN.testKey));

                var enTranslation02 = moduleInstance.getTranslation(mockPlayer);

                assertNotNull(enTranslation02, "'en' translation should not be null");
                assertEquals(EN.testValue, enTranslation02.getString(EN.testKey));
            });
        }
    }

    @Test
    void getTranslation_SenderNotValidTranslation() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            lenient().when(mockPlayersLanguageAccessor.getPlayerLanguageCode(mockPlayer))
                    .thenReturn(EN.shortName);

            assertDoesNotThrow(() -> {
                var enTranslation = moduleInstance.getTranslation(mockPlayer);

                assertNotNull(enTranslation, "'en' translation should not be null");
                assertEquals(enTranslation.getString(EN.testKey), EN.testValue);

                lenient().when(mockPlayersLanguageAccessor.getPlayerLanguageCode(mockPlayer))
                        .thenReturn(FR.shortName);
                var frTranslation = moduleInstance.getTranslation(mockPlayer);

                assertTrue(enTranslation == frTranslation,
                        "'fr' translation should be the same as 'en' translation");
                assertNotNull(frTranslation, "'fr' translation should not be null, same as 'en'");
                assertEquals(frTranslation.getString(EN.testKey), EN.testValue);
            });
        }
    }

    @Test
    void reloadTranslations() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content);
                var frTempFile = TestUtils.makeTempFile(FR.actualPath, FR.content)) {
            assertDoesNotThrow(() -> {
                var en = moduleInstance.getTranslation(EN.shortName);
                var fr = moduleInstance.getTranslation(FR.shortName);

                assertEquals(EN.testValue, en.getString(EN.testKey));
                assertEquals(FR.testValue, fr.getString(FR.testKey));

                Files.write(enTempFile.getPath(), "test-key: 'test-string-en-MODIFIED'".getBytes());
                Files.write(frTempFile.getPath(), "test-key: 'test-string-fr-MODIFIED'".getBytes());

                moduleInstance.reloadTranslations();

                assertEquals("test-string-en-MODIFIED", en.getString(EN.testKey));
                assertEquals("test-string-fr-MODIFIED", fr.getString(FR.testKey));
            });

        }
    }

    @Test
    void message_NoKey() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            moduleInstance.message(mockPlayer);

            var messageCaptor = ArgumentCaptor.forClass(String.class);
            verify(mockPlayer).sendMessage(messageCaptor.capture());

            assertEquals("", messageCaptor.getValue());
        }
    }

    @Test
    void message_Key() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            moduleInstance.message(mockPlayer, EN.testKey);

            var messageCaptor = ArgumentCaptor.forClass(String.class);
            verify(mockPlayer).sendMessage(messageCaptor.capture());

            assertEquals(EN.testValue, messageCaptor.getValue());
        }
    }

    @Test
    void message_Keys() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            moduleInstance.message(mockPlayer, EN.testKey, EN.testKey + "2");

            var messageCaptor = ArgumentCaptor.forClass(String.class);
            verify(mockPlayer).sendMessage(messageCaptor.capture());

            assertEquals(EN.testValue + "\n" + EN.testValue + "2", messageCaptor.getValue());
        }
    }

    @Test
    void message_KeyPlaceholder() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            moduleInstance.message(mockPlayer, "test-key-placeholder",
                    Pair.of("{placeholder1}", "Steve"));

            var messageCaptor = ArgumentCaptor.forClass(String.class);
            verify(mockPlayer).sendMessage(messageCaptor.capture());

            assertEquals("Hello Steve {placeholder2} {placeholder2}!", messageCaptor.getValue());
        }
    }

    @Test
    void message_KeyPlaceholders() {
        try (var enTempFile = TestUtils.makeTempFile(EN.actualPath, EN.content)) {
            moduleInstance.message(mockPlayer, "test-key-placeholder",
                    List.of(Pair.of("{placeholder1}", "Steve"), Pair.of("{placeholder2}", "xD")));

            var messageCaptor = ArgumentCaptor.forClass(String.class);
            verify(mockPlayer).sendMessage(messageCaptor.capture());

            assertEquals("Hello Steve xD xD!", messageCaptor.getValue());
        }
    }

}
