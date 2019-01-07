package com.ssplugins.advent.day15;

public class Unit implements Comparable<Unit> {
    
    public static final int ELF = 0;
    public static final int GOBLIN = 1;
    
    private MPoint point;
    private int type;
    
    private int health, attack;
    
    public Unit(int x, int y, int type, int health, int attack) {
        point = new MPoint(x, y);
        this.type = type;
        this.health = health;
        this.attack = attack;
    }
    
    public static Unit elf(int x, int y) {
        return new Unit(x, y, ELF, 200, 3);
    }
    
    public static Unit goblin(int x, int y) {
        return new Unit(x, y, GOBLIN, 200, 3);
    }
    
    public static boolean isElf(Unit unit) {
        return unit.getType() == ELF;
    }
    
    public static boolean isGoblin(Unit unit) {
        return unit.getType() == GOBLIN;
    }
    
    public boolean isEnemy(Unit unit) {
        return type != unit.getType();
    }
    
    public void hit(int amount) {
        health -= amount;
    }
    
    @Override
    public int compareTo(Unit o) {
        return point.compareTo(o.point);
    }
    
    public int getX() {
        return point.getX();
    }
    
    public int getY() {
        return point.getY();
    }
    
    public MPoint getPoint() {
        return point;
    }
    
    public void setPoint(MPoint point) {
        this.point = point;
    }
    
    public int getType() {
        return type;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public void setAttack(int attack) {
        this.attack = attack;
    }
}
