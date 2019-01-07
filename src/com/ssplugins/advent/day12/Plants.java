package com.ssplugins.advent.day12;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.List;
import java.util.Optional;

public class Plants {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(Plants.class);
        Plants p = new Plants(input.orElseThrow(Input.noInput()));
        p.part2();
    }
    
    private List<String> input;
    
    public Plants(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        Pots pots = Pots.fromInput(input);
        for (int i = 0; i < 20; i++) {
            pots.next();
        }
        int total = pots.sum();
        Util.log(total);
    }
    
    public void part2() {
        Pots pots = Pots.fromInput(input);
        long old = pots.sum();
        long limit = 50000000000L;
        for (long i = 0; i < limit; i++) {
            pots.next();
            int sum = pots.sum();
            if (sum - old == 53) { // Eventually converges to +53 per iteration.
                old = (limit - i - 1) * 53 + sum;
                break;
            }
            old = sum;
        }
        Util.log(old);
    }
    
}
