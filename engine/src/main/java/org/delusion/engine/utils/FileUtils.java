package org.delusion.engine.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static String read(String path, boolean fromClasspath) throws IOException {
        if (fromClasspath) {
            return readFromClasspath(path);
        }
        return readFromExternal(path);
    }

    private static String readFromClasspath(String path) throws IOException {
        InputStream stream = FileUtils.class.getResourceAsStream(path);

        StringBuilder content = new StringBuilder();
        while (stream.available() > 0) {
            content.append((char)stream.read());
        }

        return content.toString();
    }

    private static String readFromExternal(String path) throws IOException {
        FileInputStream stream = new FileInputStream(path);

        StringBuilder content = new StringBuilder();
        while (stream.available() > 0) {
            content.append((char)stream.read());
        }

        return content.toString();
    }
}
