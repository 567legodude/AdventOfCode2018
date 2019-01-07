package com.ssplugins.advent.day24;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Group implements Comparable<Group> {
    
    public static int BOOST = 0;
    
    private int side;
    
    private int units;
    private int hp;
    
    private List<String> immune;
    private List<String> weak;
    
    private int attack;
    private String damageType;
    private int initiative;
    
    private boolean targeted;
    private Group target;
    
    public Group(int units, int hp, List<String> immune, List<String> weak, int attack, String damageType, int initiative) {
        this.units = units;
        this.hp = hp;
        this.immune = immune;
        this.weak = weak;
        this.attack = attack;
        this.damageType = damageType;
        this.initiative = initiative;
    }
    
    public static Group immune(String input) {
        return fromInput(input, 0);
    }
    
    public static Group infection(String input) {
        return fromInput(input, 1);
    }
    
    public static Group fromInput(String input, int side) {
        Pattern pattern = Pattern.compile("(\\d+) units each with (\\d+) hit points (?:\\((.+)\\) )?with an attack that does (\\d+) (\\w+) damage at initiative (\\d+)");
        Matcher m = pattern.matcher(input);
        if (!m.find()) throw new IllegalArgumentException("Invalid input.");
        int units = Integer.parseInt(m.group(1));
        int hp = Integer.parseInt(m.group(2));
        List<String> immune = null;
        List<String> weak = null;
        if (m.group(3) != null) {
            for (String s : m.group(3).split("; ")) {
                String type = s.split(" ")[0];
                String[] arr = s.substring(s.indexOf("to ") + 3).split(", ");
                if (type.equals("immune")) {
                    immune = Arrays.asList(arr);
                }
                else if (type.equals("weak")) {
                    weak = Arrays.asList(arr);
                }
            }
        }
        int attack = Integer.parseInt(m.group(4));
        String damageType = m.group(5);
        int initiative = Integer.parseInt(m.group(6));
        Group group = new Group(units, hp, immune, weak, attack, damageType, initiative);
        group.side = side;
        return group;
    }
    
    public int effectivePower() {
        return units * getAttack();
    }
    
    public int potentialDamage(Group to) {
        int dmg = effectivePower();
        if (to.isImmune(damageType)) return 0;
        else if (to.isWeak(damageType)) return dmg * 2;
        return dmg;
    }
    
    public int potentialDeaths(Group to) {
        return potentialDamage(to) / to.hp;
    }
    
    public boolean isImmune(String type) {
        return immune != null && immune.contains(type);
    }
    
    public boolean isWeak(String type) {
        return weak != null && weak.contains(type);
    }
    
    public void attack(Group other) {
        int dmg = potentialDamage(other);
        other.units -= dmg / other.hp;
    }
    
    public boolean isEnemy(Group other) {
        return side != other.side;
    }
    
    public void target(Group other) {
        this.target = other;
        other.targeted = true;
    }
    
    public void reset() {
        target = null;
        targeted = false;
    }
    
    @Override
    public int compareTo(Group o) {
        int e = Integer.compare(o.effectivePower(), effectivePower());
        if (e != 0) return e;
        return Integer.compare(initiative, o.initiative);
    }
    
    public int getUnits() {
        return units;
    }
    
    public int getHp() {
        return hp;
    }
    
    public List<String> getImmune() {
        return immune;
    }
    
    public List<String> getWeak() {
        return weak;
    }
    
    public int getAttack() {
        return attack + (side == 0 ? BOOST : 0);
    }
    
    public String getDamageType() {
        return damageType;
    }
    
    public int getInitiative() {
        return initiative;
    }
    
    public boolean canTarget() {
        return !isTargeted();
    }
    
    public boolean isTargeted() {
        return targeted;
    }
    
    public Group getTarget() {
        return target;
    }
    
    public int getSide() {
        return side;
    }
    
}
