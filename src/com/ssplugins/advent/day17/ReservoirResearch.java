package com.ssplugins.advent.day17;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class ReservoirResearch {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ReservoirResearch.class);
        ReservoirResearch rr = new ReservoirResearch(input.orElseThrow(Input.noInput()));
        rr.part1();
    }
    
    private List<String> input;
    
    private int minX;
    private int minY;
    private byte[][] map;
    
    public ReservoirResearch(List<String> input) {
        this.input = input;
    }
    
    private void setup() {
        List<Line> lines = input.stream().map(Line::fromInput).collect(Collectors.toList());
        Integer minX = lines.stream().map(Line::getStartX).min(Integer::compareTo).orElse(-1);
        Integer maxX = lines.stream().map(line -> line.isVertical() ? line.getStartX() : line.getEndX()).max(Integer::compareTo).orElse(-1);
        Integer minY = lines.stream().map(Line::getStartY).min(Integer::compareTo).orElse(-1);
        Integer maxY = lines.stream().map(line -> line.isVertical() ? line.getEndY() : line.getStartY()).max(Integer::compareTo).orElse(-1);
        this.minX = minX - 1;
        this.minY = minY;
        map = new byte[maxY - minY + 1][maxX - minX + 3];
        lines.forEach(line -> {
            if (line.isVertical()) {
                for (int y = line.getStartY(); y <= line.getEndY(); y++) {
                    map[y - this.minY][line.getStartX() - this.minX] = 1;
                }
            }
            else {
                for (int x = line.getStartX(); x <= line.getEndX(); x++) {
                    map[line.getStartY() - this.minY][x - this.minX] = 1;
                }
            }
        });
    }
    
    private void fall(int x, int y, Deque<int[]> check) {
        int spy = y;
        do {
            int ty = spy++;
            map[ty][x] = 2; // Comment out this line for part 2.
        } while (spy < map.length && map[spy][x] == 0);
        if (spy >= map.length || (map[spy][x] == 2 && (!inContainer(x, spy, -1) || !inContainer(x, spy, 1)))) {
            return;
        }
        spy--;
        while (!(spread(x, spy, check, -1) | spread(x, spy, check, 1))) {
            spy--;
        }
    }
    
    private boolean spread(int x, int y, Deque<int[]> check, int dir) {
        int cx = x;
        map[y][cx] = 2;
        while (true) {
            cx += dir;
            if (map[y][cx] == 1) {
                return false;
            }
            else if (map[y][cx] == 2) {
                continue;
            }
            if (map[y + 1][cx] == 0) {
                check.addLast(new int[] {cx, y});
                return true;
            }
            map[y][cx] = 2;
        }
    }
    
    private boolean inContainer(int x, int y, int dir) {
        int cx = x;
        while (true) {
            cx += dir;
            if (cx < 0 || cx >= map[0].length) {
                return false;
            }
            if (map[y][cx] == 1) {
                return true;
            }
            if (map[y + 1][cx] == 0) {
                return false;
            }
        }
    }
    
    public void part1() {
        setup();
        Deque<int[]> check = new ArrayDeque<>();
        check.add(new int[] {500 - minX, 0});
        while (!check.isEmpty()) {
            int[] pos = check.pollFirst();
            if (pos == null) {
                throw new IllegalStateException("Null coordinate.");
            }
            fall(pos[0], pos[1], check);
        }
        
        int spaces = Stream.of(map).mapToInt(value -> {
            int c = 0;
            for (byte b : value) {
                if (b == 2) {
                    c++;
                }
            }
            return c;
        }).sum();
        Util.log(spaces);
    }
    
    public void part2() {
        setup();
        Deque<int[]> check = new ArrayDeque<>();
        check.add(new int[] {500 - minX, 0});
        while (!check.isEmpty()) {
            int[] pos = check.pollFirst();
            if (pos == null) {
                throw new IllegalStateException("Null coordinate.");
            }
            fall(pos[0], pos[1], check);
            // If this point is not fully inside a container, scrape the water off the top.
            if (!inContainer(pos[0], pos[1], -1) || !inContainer(pos[0], pos[1], 1)) {
                int cx = pos[0];
                while (true) {
                    cx--;
                    if (cx < 0 || map[pos[1]][cx] == 1) {
                        break;
                    }
                    map[pos[1]][cx] = 0;
                }
                cx = pos[0];
                while (true) {
                    cx++;
                    if (cx >= map[0].length || map[pos[1]][cx] == 1) {
                        break;
                    }
                    map[pos[1]][cx] = 0;
                }
            }
        }
    
        int spaces = Stream.of(map).mapToInt(value -> {
            int c = 0;
            for (byte b : value) {
                if (b == 2) {
                    c++;
                }
            }
            return c;
        }).sum();
        Util.log(spaces);
    }
    
}
