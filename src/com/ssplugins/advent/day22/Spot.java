package com.ssplugins.advent.day22;

import com.ssplugins.advent.day15.MPoint;

public class Spot {
    
    private MPoint point;
    private int tool;
    
    public Spot(MPoint point, int tool) {
        this.point = point;
        this.tool = tool;
    }
    
    public MPoint getPoint() {
        return point;
    }
    
    public int getTool() {
        return tool;
    }
    
    @Override
    public int hashCode() {
        return point.hashCode() * (tool + 1);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Spot && ((Spot) obj).point.equals(point) && tool == ((Spot) obj).tool;
    }
}
