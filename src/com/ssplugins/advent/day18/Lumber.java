package com.ssplugins.advent.day18;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lumber {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(Lumber.class);
        Lumber l = new Lumber(input.orElseThrow(Input.noInput()));
        l.part2();
    }
    
    private List<String> input;
    
    private char[][] map;
    private int[][][][] surround;
    
    public Lumber(List<String> input) {
        this.input = input;
    }
    
    private void iterate() {
        char[][] next = new char[map.length][map[0].length];
        for (int y = 0; y < map.length; y++) {
            char[] row = map[y];
            for (int x = 0; x < row.length; x++) {
                char c = row[x];
                if (c == '.') {
                    long trees = Stream.of(surround[y][x]).filter(ints -> map[ints[1]][ints[0]] == '|').count();
                    if (trees >= 3) next[y][x] = '|';
                    else next[y][x] = '.';
                }
                else if (c == '|') {
                    long yards = Stream.of(surround[y][x]).filter(ints -> map[ints[1]][ints[0]] == '#').count();
                    if (yards >= 3) next[y][x] = '#';
                    else next[y][x] = '|';
                }
                else if (c == '#') {
                    List<Character> around = Stream.of(surround[y][x]).map(ints -> map[ints[1]][ints[0]]).collect(Collectors.toList());
                    if (around.contains('|') && around.contains('#')) next[y][x] = '#';
                    else next[y][x] = '.';
                }
            }
        }
        map = next;
    }
    
    private int[][] surround(int x, int y) {
        List<int[]> points = new ArrayList<>(8);
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i == x && j == y) || (i < 0 || i >= map[0].length || j < 0 || j >= map.length)) {
                    continue;
                }
                points.add(new int[] {i, j});
            }
        }
        return points.toArray(new int[0][]);
    }
    
    private void simulate(long minutes) {
        map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        surround = new int[map.length][map[0].length][][];
        for (int y = 0; y < map.length; y++) {
            char[] row = map[y];
            for (int x = 0; x < row.length; x++) {
                surround[y][x] = surround(x, y);
            }
        }
        List<String> seen = new ArrayList<>();
        seen.add(visual(map));
        String end = null;
        for (long i = 0; i < minutes; i++) {
            iterate();
            String state = visual(map);
            int ind = seen.indexOf(state);
            if (ind > -1) {
                List<String> loop = seen.subList(ind, seen.size());
                end = loop.get((int) ((minutes - i - 1) % loop.size()));
                break;
            }
            seen.add(state);
        }
    
        if (end == null) {
            end = visual(map);
        }
        int tree = 0;
        int lumber = 0;
        for (char c : end.toCharArray()) {
            if (c == '|') tree++;
            else if (c == '#') lumber++;
        }
        Util.log(tree * lumber);
    }
    
    private String visual(char[][] map) {
        return Stream.of(map).map(String::new).collect(Collectors.joining("\n"));
    }
    
    public void part1() {
        simulate(10);
    }
    
    public void part2() {
        simulate(1000000000);
    }
    
}
