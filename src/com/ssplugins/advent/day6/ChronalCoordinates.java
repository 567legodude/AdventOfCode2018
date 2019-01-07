package com.ssplugins.advent.day6;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;
import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChronalCoordinates {
    
    public static void main(String[] args) {
                Optional<List<String>> input = Input.get(ChronalCoordinates.class);
                ChronalCoordinates cc = new ChronalCoordinates(input.orElseThrow(Util.noInput()));
                cc.part2();
    }
    
    private List<String> input;
    
    public ChronalCoordinates(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        Pattern pattern = Pattern.compile("(\\d+), (\\d+)");
        List<MPoint> points = input.stream()
                                   .map(pattern::matcher)
                                   .filter(Matcher::find)
                                   .map(matcher -> new MPoint(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))))
                                   .collect(Collectors.toList());
        int minX = -1;
        int minY = -1;
        int maxX = -1;
        int maxY = -1;
        for (MPoint point : points) {
            if (minX == -1 || point.getX() < minX) {
                minX = point.getX();
            }
            if (minY == -1 || point.getY() < minY) {
                minY = point.getY();
            }
            if (point.getX() > maxX) {
                maxX = point.getX();
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
            }
        }
        
        Map<MPoint, Integer> area = new HashMap<>();
        List<MPoint> infinite = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Optional<MPoint> p = findClosest(new MPoint(i, j), points);
                if (!p.isPresent()) continue;
                if (i == minX || i == maxX || j == minY || j == maxY) {
                    p.ifPresent(infinite::add);
                }
                p.ifPresent(mPoint -> {
                    area.compute(mPoint, (mPoint1, integer) -> integer == null ? 1 : integer + 1);
                });
            }
        }
        infinite.forEach(area::remove);
        area.entrySet().stream().max(Map.Entry.comparingByValue()).ifPresent(entry -> {
            Util.log(entry.getValue());
        });
    }
    
    private Optional<MPoint> findClosest(MPoint point, List<MPoint> points) {
        List<Pair<MPoint, Integer>> dists = points.stream().map(mPoint -> new Pair<>(mPoint, mPoint.distance(point))).collect(Collectors.toList());
        List<Integer> u = dists.stream().map(Pair::getValue).collect(Collectors.toList());
        Optional<Pair<MPoint, Integer>> min = dists.stream().min(Comparator.comparing(Pair::getValue));
        if (!min.isPresent()) return Optional.empty();
        if (Collections.frequency(u, min.get().getValue()) > 1) {
            return Optional.empty();
        }
        return min.map(Pair::getKey);
    }
    
    public void part2() {
        int range = 10000;
        Pattern pattern = Pattern.compile("(\\d+), (\\d+)");
        List<MPoint> points = input.stream()
                                   .map(pattern::matcher)
                                   .filter(Matcher::find)
                                   .map(matcher -> new MPoint(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))))
                                   .collect(Collectors.toList());
        int minX = -1;
        int minY = -1;
        int maxX = -1;
        int maxY = -1;
        for (MPoint point : points) {
            if (minX == -1 || point.getX() < minX) {
                minX = point.getX();
            }
            if (minY == -1 || point.getY() < minY) {
                minY = point.getY();
            }
            if (point.getX() > maxX) {
                maxX = point.getX();
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
            }
        }
    
        minX = Math.min(minX, maxX - range / points.size());
        minY = Math.min(minY, maxY - range / points.size());
        maxX = Math.max(maxX, minX + range / points.size());
        maxY = Math.max(maxY, minY + range / points.size());
        List<MPoint> potential = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                potential.add(new MPoint(i, j));
            }
        }
        long count = potential.stream().map(mPoint -> points.stream().mapToInt(mPoint::distance).sum()).filter(integer -> integer < range).count();
        Util.log(count);
    }
    
}
