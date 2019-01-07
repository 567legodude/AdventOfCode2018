package com.ssplugins.advent.day23;

import com.ssplugins.advent.day15.MPoint;

public class MPoint3D extends MPoint {
    
    private int z;
    
    public MPoint3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }
    
    public int distance(MPoint3D other) {
        return super.distance(other) + Math.abs(z - other.z);
    }
    
    @Override
    public MPoint3D up() {
        return new MPoint3D(getX(), getY() - 1, getZ());
    }
    
    @Override
    public MPoint3D down() {
        return new MPoint3D(getX(), getY() + 1, getZ());
    }
    
    @Override
    public MPoint3D right() {
        return new MPoint3D(getX() + 1, getY(), getZ());
    }
    
    @Override
    public MPoint3D left() {
        return new MPoint3D(getX() - 1, getY(), getZ());
    }
    
    public MPoint3D top() {
        return new MPoint3D(getX(), getY(), getZ() + 1);
    }
    
    public MPoint3D bottom() {
        return new MPoint3D(getX(), getY(), getZ() - 1);
    }
    
    @Override
    public MPoint3D[] surround() {
        return new MPoint3D[] {up(), right(), down(), left(), top(), bottom()};
    }
    
    public int getZ() {
        return z;
    }
    
    public void setZ(int z) {
        this.z = z;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MPoint3D && super.equals(obj) && z == ((MPoint3D) obj).z;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + z * z;
    }
}
