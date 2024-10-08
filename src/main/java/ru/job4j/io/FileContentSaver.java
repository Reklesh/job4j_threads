package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;

public class FileContentSaver {
    private final File file;

    public FileContentSaver(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, Charset.forName("WINDOWS-1251")))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
