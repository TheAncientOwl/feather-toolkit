/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file TempFile.java
 * @author Alexandru Delegeanu
 * @version 0.3
 * @description Helper class to delete given file when no longer needed
 */
package dev.defaultybuf.feather.toolkit.testing.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;

public class TempFile implements AutoCloseable {
    final Path path;

    public TempFile(final Path path) {
        this.path = path;
    }

    @Override
    public void close() {
        try {
            if (Files.exists(path)) {
                FileUtils.delete(path.toFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File get() {
        return path.toFile();
    }

    public Path getPath() {
        return path;
    }
}
