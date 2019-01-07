package com.ssplugins.advent.day8;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.List;
import java.util.Optional;

public class MemoryManeuver {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(MemoryManeuver.class);
        MemoryManeuver mm = new MemoryManeuver(input.orElseThrow(Input.noInput()));
        mm.part2();
    }
    
    private List<String> input;
    
    public MemoryManeuver(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        Node root = Node.fromInput(input.get(0));
        long sum = sum(root);
        Util.log(sum);
    }
    
    private long sum(Node node) {
        return node.getMetadata().stream().mapToLong(value -> value).sum() + node.getChildren().stream().mapToLong(this::sum).sum();
    }
    
    public void part2() {
        Node root = Node.fromInput(input.get(0));
        long value = value(root);
        Util.log(value);
    }
    
    private long value(Node node) {
        if (node.getChildren().size() == 0) {
            return node.getMetadata().stream().mapToLong(value -> value).sum();
        }
        return node.getMetadata()
                   .stream()
                   .map(b -> --b)
                   .filter(b -> b >= 0 && b < node.getChildren().size())
                   .map(b -> node.getChildren().get(b))
                   .mapToLong(this::value)
                   .sum();
    }
    
}
