package com.ssplugins.advent.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Input {
    
    public static String getPath(Class<?> type, String fileName) {
        String path = type.getName();
        path = "/" + path.substring(0, path.lastIndexOf('.') + 1).replace('.', '/');
        path += fileName;
        return path;
    }
    
    public static Optional<List<String>> get(Class<?> type) {
        return get(type, "input.txt");
    }
    
    public static Optional<List<String>> get(Class<?> type, String fileName) {
        String path = getPath(type, fileName);
        return Optional.ofNullable(type.getResourceAsStream(path)).map(inputStream -> {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                return null;
            }
            return lines;
        });
    }
    
    public static void write(List<String> lines, Class<?> type, String fileName) {
        String path = getPath(type, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                if (i + 1 < lines.size()) {
                    writer.newLine();
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Supplier<IllegalArgumentException> noInput() {
        return () -> new IllegalArgumentException("No input provided.");
    }
    
}
