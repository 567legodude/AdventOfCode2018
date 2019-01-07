package com.ssplugins.advent.day11;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.List;
import java.util.Optional;

public class ChronalCharge {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ChronalCharge.class);
        ChronalCharge cc = new ChronalCharge(input.orElseThrow(Input.noInput()));
        cc.part2();
    }
    
    private List<String> input;
    
    public ChronalCharge(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        int size = 300;
        int serial = Integer.parseInt(input.get(0));
        int highest = -5 * 9;
        int hx = 0, hy = 0;
        for (int x = 1; x <= size - 2; x++) {
            for (int y = 1; y <= size - 2; y++) {
                int power = 0;
                for (int j = x; j < x + 3; j++) {
                    for (int k = y; k < y + 3; k++) {
                        power += power(j, k, serial);
                    }
                }
                if (power > highest) {
                    highest = power;
                    hx = x;
                    hy = y;
                }
            }
        }
        Util.log(hx + "," + hy);
    }
    
    public void part2() {
        int size = 300;
        int serial = Integer.parseInt(input.get(0));
        long highest = -5 * 9;
        int hx = 0, hy = 0, hs = 0;
        for (int sq = 1; sq <= 13; sq++) {
            for (int x = 1; x <= size - sq + 1; x++) {
                for (int y = 1; y <= size - sq + 1; y++) {
                    long power = 0;
                    for (int j = x; j < x + sq; j++) {
                        for (int k = y; k < y + sq; k++) {
                            power += power(j, k, serial);
                        }
                    }
                    if (power > highest) {
                        highest = power;
                        hx = x;
                        hy = y;
                        hs = sq;
                    }
                }
            }
        }
        Util.log(hx + "," + hy + "," + hs);
    }
    
    private int power(int x, int y, int serial) {
        int rack = x + 10;
        int power = rack * y + serial;
        power *= rack;
        if (power >= 100) {
            String v = String.valueOf(power);
            power = Integer.parseInt(v.substring(v.length() - 3, v.length() - 2));
        }
        else {
            power = 0;
        }
        return power - 5;
    }
    
}
