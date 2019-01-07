package com.ssplugins.advent.day20;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private String route;
    private List<Path> parts;
    private boolean sequence = true;
    
    public Path(String route) {
        this.route = route;
    }
    
    public Path(List<Path> branches) {
        this.parts = branches;
    }
    
    public static Path fromInput(String input) {
        if (input.isEmpty()) {
            return new Path("");
        }
        if (input.startsWith("^") && input.endsWith("$")) {
            input = input.substring(1, input.length() - 1);
        }
        List<Path> parts = new ArrayList<>();
        int last = 0;
        while (last < input.length()) {
            int op = input.indexOf('(', last);
            if (op == -1) break;
            int ep = findEndParen(input, '(', ')', op);
            if (ep == -1) throw new IllegalArgumentException("Unclosed parentheses.");
            parts.add(fromInput(input.substring(last, op)));
    
            List<Path> branches = new ArrayList<>();
            int i = op + 1;
            for (int j = i; j < ep; j++) {
                if (input.charAt(j) == '(') {
                    j = findEndParen(input, '(', ')', j);
                }
                if (input.charAt(j) == '|') {
                    branches.add(fromInput(input.substring(i, j)));
                    if (j == ep - 1) {
                        branches.add(new Path(""));
                    }
                    i = j + 1;
                }
            }
            if (i < ep) {
                branches.add(fromInput(input.substring(i, ep)));
            }
            Path path = new Path(branches);
            path.sequence = false;
            parts.add(path);
            
            last = ep + 1;
        }
        if (last < input.length()) {
            parts.add(new Path(input.substring(last)));
        }
        if (parts.size() == 1) return parts.get(0);
        else return new Path(parts);
    }
    
    public static int findEndParen(String s, char open, char close, int start) {
        int count = 0;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == open) count++;
            else if (c == close) count--;
            if (count < 0) return -1;
            else if (count == 0) return i;
        }
        return -1;
    }
    
    public boolean isRoute() {
        return route != null;
    }
    
    public boolean isComplex() {
        return parts != null;
    }
    
    public boolean isSequence() {
        return sequence;
    }
    
    public String getRoute() {
        return route;
    }
    
    public List<Path> getParts() {
        return parts;
    }
    
}
