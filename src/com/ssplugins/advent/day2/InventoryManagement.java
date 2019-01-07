package com.ssplugins.advent.day2;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InventoryManagement {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(InventoryManagement.class);
        InventoryManagement im = new InventoryManagement(input.orElseThrow(Util.noInput()));
        im.part2();
    }
    
    private List<String> input;
    
    public InventoryManagement(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        int twos = 0;
        int threes = 0;
        for (String id : input) {
            List<Integer> chars = id.chars().boxed().collect(Collectors.toList());
            List<Integer> counts = chars.stream().map(i -> Collections.frequency(chars, i)).collect(Collectors.toList());
            if (counts.contains(2)) {
                twos++;
            }
            if (counts.contains(3)) {
                threes++;
            }
        }
        int checksum = twos * threes;
        Util.log(checksum);
    }
    
    public void part2() {
        for (String id1 : input) {
            for (String id2 : input) {
                if (id1.equals(id2)) {
                    continue;
                }
                int d = difference(id1, id2);
                if (d > -1) {
                    Util.log(id1.substring(0, d) + id1.substring(d + 1));
                    return;
                }
            }
        }
    }
    
    private int difference(String a, String b) {
        if (a.length() != b.length()) {
            return -1;
        }
        int d = -1;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (d != -1) {
                    return -1;
                }
                d = i;
            }
        }
        return d;
    }
    
}
