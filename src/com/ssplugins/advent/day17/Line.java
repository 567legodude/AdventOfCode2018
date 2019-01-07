package com.ssplugins.advent.day17;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Line {
    
    private int startX, endX, startY, endY;
    private boolean vertical;
    
    public Line(int startX, int endX, int startY, int endY, boolean vertical) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.vertical = vertical;
    }
    
    public static Line fromInput(String input) {
        Pattern pattern = Pattern.compile("(.)=(\\d+)(?:\\.\\.(\\d+))?");
        Matcher m = pattern.matcher(input);
        Line line = new Line(-1, -1, -1, -1, false);
        while (m.find()) {
            if (m.group(1).equals("x")) {
                line.startX = Integer.parseInt(m.group(2));
                if (m.group(3) != null) {
                    line.endX = Integer.parseInt(m.group(3));
                }
            }
            if (m.group(1).equals("y")) {
                line.startY = Integer.parseInt(m.group(2));
                if (m.group(3) != null) {
                    line.vertical = true;
                    line.endY = Integer.parseInt(m.group(3));
                }
            }
        }
        if (line.startX == -1 || line.startY == -1) {
            throw new IllegalArgumentException("Invalid input.");
        }
        return line;
    }
    
    public int getStartX() {
        return startX;
    }
    
    public int getEndX() {
        return endX;
    }
    
    public int getStartY() {
        return startY;
    }
    
    public int getEndY() {
        return endY;
    }
    
    public boolean isVertical() {
        return vertical;
    }
    
}
