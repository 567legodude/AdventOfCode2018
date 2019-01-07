package com.ssplugins.advent.day20;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Room {
    
    private Room n, e, s, w;
    private int distance;
    
    public Room() {
        this(0);
    }
    
    public Room(int distance) {
        this.distance = distance;
    }
    
    public Set<Room> allRooms() {
        Set<Room> rooms = new HashSet<>();
        Deque<Room> check = new ArrayDeque<>();
        check.add(this);
        while (!check.isEmpty()) {
            Room room = check.remove();
            for (Room r : room.surround()) {
                if (r == null) continue;
                boolean add = rooms.add(r);
                if (add) check.add(r);
            }
        }
        return rooms;
    }
    
    public Room[] surround() {
        return new Room[] {n, e, s, w};
    }
    
    public Room north() {
        if (n == null) {
            n = new Room(distance + 1);
            n.s = this;
        }
        return n;
    }
    
    public Room east() {
        if (e == null) {
            e = new Room(distance + 1);
            e.w = this;
        }
        return e;
    }
    
    public Room south() {
        if (s == null) {
            s = new Room(distance + 1);
            s.n = this;
        }
        return s;
    }
    
    public Room west() {
        if (w == null) {
            w = new Room(distance + 1);
            w.e = this;
        }
        return w;
    }
    
    public int getDistance() {
        return distance;
    }
    
}
