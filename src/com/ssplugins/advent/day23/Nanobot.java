package com.ssplugins.advent.day23;

import com.ssplugins.advent.day16.Sample;

public class Nanobot {
    
    private MPoint3D position;
    private int radius;
    
    public Nanobot(MPoint3D position, int radius) {
        this.position = position;
        this.radius = radius;
    }
    
    public static Nanobot fromInput(String input) {
        int[] pos = Sample.toIntArray(input.substring(input.indexOf('<') + 1, input.indexOf('>')).split(","));
        int r = Integer.parseInt(input.split(" ")[1].substring(2));
        return new Nanobot(new MPoint3D(pos[0], pos[1], pos[2]), r);
    }
    
    public boolean inRange(Nanobot other) {
        return inRange(other.position);
    }
    
    public boolean inRange(MPoint3D point) {
        return position.distance(point) <= radius;
    }
    
    public MPoint3D getPosition() {
        return position;
    }
    
    public int getRadius() {
        return radius;
    }
    
    public int x() {
        return position.getX();
    }
    
    public int y() {
        return position.getY();
    }
    
    public int z() {
        return position.getZ();
    }
    
}
