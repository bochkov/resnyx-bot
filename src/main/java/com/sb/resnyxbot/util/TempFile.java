package com.sb.resnyxbot.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class TempFile {

    private static final String DIR = System.getProperty("java.io.tmpdir");

    private TempFile() {
    }

    public static File create(String name, byte[] bytes) throws IOException {
        File file = new File(DIR, name);
        file.deleteOnExit();
        try (FileOutputStream fout = new FileOutputStream(file)) {
            fout.write(bytes);
        }
        return file;
    }
}
