/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file TestUtils.java
 * @author Alexandru Delegeanu
 * @version 0.4
 * @description Testing utilities
 */
package dev.defaultybuf.feather.toolkit.testing.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;

import dev.defaultybuf.feather.toolkit.util.java.Pair;
import dev.defaultybuf.feather.toolkit.util.java.StringUtils;

public class TestUtils {
    public static Path getTestDataFolderPath() {
        return Paths.get(System.getProperty("user.home"), "tmp-feather-toolkit");
    }

    public static File getTestDataFolder() {
        return getTestDataFolderPath().toFile();
    }

    public static TempFile makeTempFile(final String path, final String content) {
        return makeTempFile(Paths.get(path), content);
    }

    public static TempFile makeTempFile(final Path path) {
        final var filePath = getTestDataFolderPath().resolve(path);

        try {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new TempFile(filePath);
    }

    public static TempFile makeRandomTempFile(final String extension) {
        return makeTempFile(getTestDataFolderPath().resolve(getRandomStringFile(16, extension)));
    }

    public static TempFile makeTempFile(final Path path, final String content) {
        final var tempFile = makeTempFile(getTestDataFolderPath().resolve(path));
        try {
            Files.write(tempFile.getPath(), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static TempFile makeRandomTempFile(final String extension, final String content) {
        final var tempFile = makeTempFile(
                getTestDataFolderPath().resolve(getRandomStringFile(16, extension)));
        try {
            Files.write(tempFile.getPath(), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static final SecureRandom RANDOM = new SecureRandom();

    static String getRandomStringFile(int length, final String extension) {
        StringBuilder randomString = new StringBuilder();

        // Generate random characters and add them to the string
        for (int i = 0; i < length; i++) {
            char randomChar = CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()));
            randomString.append(randomChar);

            // Add hyphen every 4 characters, except after the last one
            if ((i + 1) % 4 == 0 && i < length - 1) {
                randomString.append('-');
            }
        }

        return randomString.toString() + "." + extension;
    }

    public static String placeholderize(String message,
            final Pair<String, Object> replacement) {
        return StringUtils.replacePlaceholders(message, replacement).replace("&", "§");
    }

    public static String placeholderize(String message,
            final List<Pair<String, Object>> replacements) {
        return StringUtils.replacePlaceholders(message, replacements).replace("&", "§");
    }

}
