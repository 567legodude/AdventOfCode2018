package com.ssplugins.advent.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Input {
    
    public static Optional<List<String>> get(Class<?> clazz) {
        return get(clazz, "input.txt");
    }
    
    public static Optional<List<String>> get(Class<?> clazz, String fileName) {
        String path = clazz.getName();
        path = "/" + path.substring(0, path.length() - clazz.getSimpleName().length()).replace('.', '/');
        path += fileName;
        return Optional.ofNullable(clazz.getResourceAsStream(path)).map(inputStream -> {
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

}
