package com.ssplugins.advent.day10;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Point {

    private IntegerProperty x, y;
    private IntegerProperty vx, vy;
    
    public Point(int x, int y, int vx, int vy) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.vx = new SimpleIntegerProperty(vx);
        this.vy = new SimpleIntegerProperty(vy);
    }
    
    public static Point fromInput(String input) {
        int x = Integer.parseInt(input.substring(10, 16).trim());
        int y = Integer.parseInt(input.substring(18, 24).trim());
        int vx = Integer.parseInt(input.substring(36, 38).trim());
        int vy = Integer.parseInt(input.substring(40, 42).trim());
        return new Point(x, y, vx, vy);
    }
    
    public void update() {
        x.set(x.get() + vx.get());
        y.set(y.get() + vy.get());
    }
    
    public int getX() {
        return x.get();
    }
    
    public int getY() {
        return y.get();
    }
    
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
    
}
