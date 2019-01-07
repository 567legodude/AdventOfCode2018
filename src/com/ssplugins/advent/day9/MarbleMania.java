package com.ssplugins.advent.day9;

import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarbleMania {
    
    public static void main(String[] args) {
        MarbleMania mm = new MarbleMania();
        long start = System.currentTimeMillis();
        mm.part1();
        long end = System.currentTimeMillis();
        Util.log(end - start);
    }
    
    public void part1Old() {
        final int players = 463;
        final int marbles = 71787 * 100; // * 100 for part 2.
        List<Integer> state = new ArrayList<>(marbles - marbles / 23 * 2);
        Map<Integer, Long> scores = new HashMap<>(players);
        state.add(0);
        
        int current = 0;
        int turn = 1;
        int action = 23;
        for (int m = 1; m <= marbles; m++) {
            if (--action == 0) {
                action = 23;
                current -= 7;
                if (current < 0) {
                    current += state.size();
                }
                Integer removed = state.remove(current);
                int finalM = m;
                scores.compute(turn, (i1, i2) -> i2 == null ? finalM + removed : i2 + finalM + removed);
                if (current == state.size()) {
                    current = 0;
                }
            }
            else {
                current += 2;
                if (current >= state.size()) {
                    current -= state.size();
                }
                state.add(current, m);
            }
            turn++;
            if (turn > players) {
                turn = 1;
            }
        }
        scores.values().stream().max(Long::compareTo).ifPresent(Util::log);
    }
    
    public void part1() {
        final int players = 463;
        final int marbles = 71787 * 100; // * 100 for part 2.
        Map<Integer, Long> scores = new HashMap<>(players);
    
        Marble current = new Marble(0);
        int turn = 1;
        int action = 23;
        for (int m = 1; m <= marbles; m++) {
            if (--action == 0) {
                action = 23;
                Marble toRemove = current;
                for (int i = 0; i < 7; i++) {
                    toRemove = toRemove.prev();
                }
                int finalM = m;
                Marble finalRemoved = toRemove;
                scores.compute(turn, (i1, i2) -> i2 == null ? finalM + finalRemoved.getValue() : i2 + finalM + finalRemoved.getValue());
                current = toRemove.next();
                toRemove.remove();
            }
            else {
                Marble prev = current.next();
                current = new Marble(m, prev, prev.next());
            }
            turn++;
            if (turn > players) {
                turn = 1;
            }
        }
        scores.values().stream().max(Long::compareTo).ifPresent(Util::log);
    }
    
}
