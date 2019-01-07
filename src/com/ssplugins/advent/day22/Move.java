package com.ssplugins.advent.day22;

import com.ssplugins.advent.day15.MPoint;

public class Move {
    
    private MPoint point;
    private int minutes;
    private int tool;
    
    public Move(MPoint point, int minutes, int tool) {
        this.point = point;
        this.minutes = minutes;
        this.tool = tool;
    }
    
    public int x() {
        return getPoint().getX();
    }
    
    public int y() {
        return getPoint().getY();
    }
    
    public Spot spot() {
        return new Spot(point, tool);
    }
    
    public MPoint getPoint() {
        return point;
    }
    
    public int getMinutes() {
        return minutes;
    }
    
    public int getTool() {
        return tool;
    }
}
