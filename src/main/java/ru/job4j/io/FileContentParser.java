package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class FileContentParser {
    private final File file;

    public FileContentParser(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String getContent() {
        return content(data -> true);
    }

    public String getContentWithoutUnicode() {
        return content(data -> data < 0x80);
    }
}
