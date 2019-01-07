package com.ssplugins.advent.util;

import java.util.List;
import java.util.Optional;

// Auto-loading class that was created around day 20.
public abstract class Advent {
    
    public static String INPUT = "input.txt";
    public static boolean PART_2 = false;
    
    protected List<String> input;
    
    public Advent() {
        Optional<List<String>> input = Input.get(this.getClass(), Advent.INPUT);
        this.input = input.orElseThrow(Input.noInput());
        if (Advent.PART_2) this.part2();
        else this.part1();
    }
    
    public abstract void part1();
    
    public void part2() {}
    
}
