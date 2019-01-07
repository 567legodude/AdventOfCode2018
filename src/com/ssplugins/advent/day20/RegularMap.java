package com.ssplugins.advent.day20;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.*;

public class RegularMap {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(RegularMap.class);
        RegularMap rm = new RegularMap(input.orElseThrow(Input.noInput()));
        rm.part2();
    }
    
    private List<String> input;
    
    public RegularMap(List<String> input) {
        this.input = input;
    }
    
    private Set<Room> explore(Path path, Room current) {
        Set<Room> ends = new HashSet<>();
        if (path.isRoute()) {
            ends.add(walk(path.getRoute(), current));
        }
        else if (path.isSequence()) {
            Set<Room> explore = new HashSet<>();
            Set<Room> explored = new HashSet<>();
            explore.add(current);
            for (Path p : path.getParts()) {
                explore.forEach(room -> {
                    explored.addAll(explore(p, room));
                });
                explore.clear();
                explore.addAll(explored);
                explored.clear();
            }
            ends.addAll(explore);
        }
        else {
            for (Path p : path.getParts()) {
                ends.addAll(explore(p, current));
            }
        }
        return ends;
    }
    
    private Room walk(String route, Room r) {
        for (char c : route.toCharArray()) {
            if (c == 'N') r = r.north();
            else if (c == 'E') r = r.east();
            else if (c == 'S') r = r.south();
            else if (c == 'W') r = r.west();
        }
        return r;
    }
    
    public void part1() {
        Path path = Path.fromInput(input.get(0));
        Room location = new Room();
        Set<Room> ends = explore(path, location);
        Optional<Room> farthest = ends.stream().max(Comparator.comparing(Room::getDistance));
        Room room = farthest.orElseThrow(RuntimeException::new);
        Util.log(room.getDistance());
    }
    
    public void part2() {
        Path path = Path.fromInput(input.get(0));
        Room location = new Room();
        explore(path, location);
        Set<Room> rooms = location.allRooms();
        long count = rooms.stream().filter(room -> room.getDistance() >= 1000).count();
        Util.log(count);
    }
    
}
