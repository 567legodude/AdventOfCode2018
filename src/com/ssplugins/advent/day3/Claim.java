package com.ssplugins.advent.day3;

import javafx.geometry.BoundingBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Claim {
    
    private int id;
    private int x, y, w, h;
    
    public Claim(String claim) {
        Matcher m = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)").matcher(claim);
        if (!m.find()) throw new IllegalArgumentException("Invalid claim.");
        id = Integer.parseInt(m.group(1));
        x = Integer.parseInt(m.group(2));
        y = Integer.parseInt(m.group(3));
        w = Integer.parseInt(m.group(4));
        h = Integer.parseInt(m.group(5));
    }
    
    private Claim(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public BoundingBox toBounds() {
        return new BoundingBox(x, y, w, h);
    }
    
    public int getId() {
        return id;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getMaxX() {
        return x + w;
    }
    
    public int getMaxY() {
        return y + h;
    }
    
    public int getWidth() {
        return w;
    }
    
    public int getHeight() {
        return h;
    }
    
    public boolean intersects(Claim other) {
        BoundingBox bb = this.toBounds();
        BoundingBox obb = other.toBounds();
        return bb.intersects(obb);
    }
    
    public void overlay(Boolean[][] claims) {
        for (int i = x; i < getMaxX(); i++) {
            for (int j = y; j < getMaxY(); j++) {
                claims[i][j] = true;
            }
        }
    }
    
    public static Claim overlap(BoundingBox a, BoundingBox b) {
        int x = (int) Math.max(a.getMinX(), b.getMinX());
        int y = (int) Math.max(a.getMinY(), b.getMinY());
        int w = (int) (Math.min(a.getMaxX(), b.getMaxX()) - x);
        int h = (int) (Math.min(a.getMaxY(), b.getMaxY()) - y);
        return new Claim(x, y, w, h);
    }
    
}
