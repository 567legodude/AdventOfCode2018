package com.ssplugins.advent.day15;

import com.ssplugins.advent.util.Util;

// Represents a point using Manhattan (taxicab) geometry.
public class MPoint implements Comparable<MPoint> {
    
    private int x, y;
    
    public MPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int distance(MPoint other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
    
    public MPoint up() {
        return new MPoint(x, y - 1);
    }
    
    public MPoint down() {
        return new MPoint(x, y + 1);
    }
    
    public MPoint right() {
        return new MPoint(x + 1, y);
    }
    
    public MPoint left() {
        return new MPoint(x - 1, y);
    }
    
    public MPoint[] surround() {
        return new MPoint[] {up(), right(), down(), left()};
    }
    
    @Override
    public int compareTo(MPoint o) {
        return Util.compareReadOrder(x, y, o.x, o.y);
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MPoint && x == ((MPoint) obj).x && y == ((MPoint) obj).y;
    }
    
    @Override
    public int hashCode() {
        return x * x + y * y;
    }
}
