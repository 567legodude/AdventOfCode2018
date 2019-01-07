package com.ssplugins.advent.day10;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StarsAlign {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(StarsAlign.class);
        StarsAlign sa = new StarsAlign(input.orElseThrow(Input.noInput()));
        sa.part1();
    }
    
    private List<String> input;
    
    public StarsAlign(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        List<Point> points = input.stream().map(Point::fromInput).collect(Collectors.toList());
        int time = 0;
        long distance = distanceSum(points);
        long newDist = distance;
        // Run until the sum of the distances to the origin isn't decreasing.
        while (newDist <= distance) {
            distance = newDist;
            points.forEach(Point::update);
            time++;
            newDist = distanceSum(points);
        }
        Util.log(time);
        Util.log(pointsToString(points));
    }
    
    private String pointsToString(List<Point> points) {
        return points.stream().map(Point::toString).collect(Collectors.joining(", "));
    }
    
    private long distanceSum(List<Point> points) {
        return points.stream().mapToLong(value -> {
            return (long) value.getX() * value.getX() + (long) value.getY() * value.getY();
        }).sum();
    }
    
}
