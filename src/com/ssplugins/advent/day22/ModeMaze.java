package com.ssplugins.advent.day22;

import com.ssplugins.advent.day15.MPoint;
import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModeMaze {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ModeMaze.class);
        ModeMaze mm = new ModeMaze(input.orElseThrow(Input.noInput()));
        mm.part2();
    }
    
    private List<String> input;
    
    private byte[][] map;
    private int tx, ty, depth;
    private Map<String, Integer> geos = new HashMap<>();
    private Map<String, Integer> eros = new HashMap<>();
    
    public ModeMaze(List<String> input) {
        this.input = input;
    }
    
    private void setup() {
        depth = Integer.parseInt(input.get(0).split(" ")[1]);
        String[] pos = input.get(1).split(" ")[1].split(",");
        tx = Integer.parseInt(pos[0]);
        ty = Integer.parseInt(pos[1]);
        map = new byte[ty + 1][tx + 1];
        for (int j = 0; j < map.length; j++) {
            byte[] row = map[j];
            for (int i = 0; i < row.length; i++) {
                map[j][i] = getType(i, j);
            }
        }
    }
    
    private String coord(int x, int y) {
        return x + "," + y;
    }
    
    private int getGeologic(int x, int y) {
        if (x == 0 && y == 0) return 0;
        if (x == tx && y == ty) return 0;
        if (y == 0) return x * 16807;
        if (x == 0) return y * 48271;
        return geos.compute(coord(x, y), (s, integer) -> integer == null ? getErosion(x - 1, y) * getErosion(x, y - 1) : integer);
    }
    
    private int getErosion(int x, int y) {
        return eros.compute(coord(x, y), (s, integer) -> integer == null ? (getGeologic(x, y) + depth) % 20183 : integer);
    }
    
    private byte getType(int x, int y) {
        return (byte) (getErosion(x, y) % 3);
    }
    
    public void part1() {
        setup();
        map[0][0] = 3;
        map[ty][tx] = 4;
        List<String> collect = Stream.of(map).map(bytes -> {
            StringBuilder b = new StringBuilder();
            for (byte t : bytes) {
                if (t == 0) b.append("|");
                else if (t == 1) b.append("=");
                else if (t == 2) b.append(".");
                else if (t == 3) b.append("M");
                else if (t == 4) b.append("T");
            }
            return b.toString();
        }).collect(Collectors.toList());
        Input.write(collect, ModeMaze.class, "out.txt");
        int risk = 0;
        for (byte[] row : map) {
            for (byte b : row) {
                risk += b;
            }
        }
        Util.log(risk);
    }
    
    public void part2() {
        setup();
        // 0=nothing, 1=torch, 2=climbing
    
        Map<Spot, Integer> time = new HashMap<>();
        Queue<Move> check = new PriorityQueue<>(Comparator.comparing(Move::getMinutes));
        MPoint start = new MPoint(0, 0);
        Spot end = new Spot(new MPoint(tx, ty), 1);
        check.add(new Move(start, 0, 1));
        while (!check.isEmpty()) {
            Move move = check.remove();
            Spot spot = move.spot();
            if (time.containsKey(spot) && time.get(spot) <= move.getMinutes()) {
                continue;
            }
            time.put(spot, move.getMinutes());
            if (spot.equals(end)) {
                Util.log(move.getMinutes());
                break;
            }
            // Consider tool swap
            byte type = getType(move.x(), move.y());
            if (type == 0) {
                if (move.getTool() == 1) type = 2;
                else if (move.getTool() == 2) type = 1;
            }
            else if (type == 1) {
                if (move.getTool() == 0) type = 2;
                else if (move.getTool() == 2) type = 0;
            }
            else if (type == 2) {
                if (move.getTool() == 0) type = 1;
                else if (move.getTool() == 1) type = 0;
            }
            check.add(new Move(move.getPoint(), move.getMinutes() + 7, type));
            for (MPoint point : move.getPoint().surround()) {
                if (point.getX() < 0 || point.getY() < 0) continue;
                byte t = getType(point.getX(), point.getY());
                if (t == move.getTool()) continue;
                check.add(new Move(point, move.getMinutes() + 1, move.getTool()));
            }
        }
        
    }
    
}
