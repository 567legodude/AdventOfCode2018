package com.ssplugins.advent.day6;

import java.util.List;

public class MPoint {
    
    private int x, y;
    
    public MPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int distance(MPoint other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
    
    public List<MPoint> circle(int radius) {
        return null;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MPoint && ((MPoint) obj).x == x && ((MPoint) obj).y == y;
    }
    
}
