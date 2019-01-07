package com.ssplugins.advent.day1;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChronalCalibration {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ChronalCalibration.class);
        ChronalCalibration cc = new ChronalCalibration(input.orElseThrow(Util.noInput()));
        cc.part2();
    }
    
    private List<String> input;
    
    public ChronalCalibration(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        int result = input.stream().mapToInt(Integer::parseInt).sum();
        Util.log(result);
    }
    
    public void part2() {
        List<Integer> source = input.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> seen = new ArrayList<>();
        seen.add(0);
        int index = 0;
        int current = 0;
        while (true) {
            current += source.get(index);
            if (seen.contains(current)) {
                Util.log(current);
                break;
            }
            else {
                seen.add(current);
            }
            index++;
            if (index >= source.size()) {
                index = 0;
            }
        }
    }
    
}
