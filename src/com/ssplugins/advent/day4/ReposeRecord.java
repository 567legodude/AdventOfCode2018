package com.ssplugins.advent.day4;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ReposeRecord {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ReposeRecord.class);
        ReposeRecord rr = new ReposeRecord(input.orElseThrow(Util.noInput()));
//        rr.part1();
        rr.part2();
    }
    
    private List<String> input;
    
    public ReposeRecord(List<String> input) {
        this.input = input;
    }
    
    private List<Sleep> getSleeps() {
        List<Entry> entries = input.stream()
                                   .map(Entry::new)
                                   .sorted(Comparator.comparing(Entry::getDate))
                                   .collect(Collectors.toList());
        List<Sleep> sleeps = new ArrayList<>();
        Sleep sleep = null;
        int last = -1;
        for (Entry entry : entries) {
            if (entry.isNewShift()) {
                if (sleep != null) {
                    sleeps.add(sleep);
                    sleep = null;
                }
                last = entry.getShift();
            }
            else if (entry.isSleepState()) {
                if (entry.getSleepState()) {
                    if (sleep != null) {
                        sleeps.add(sleep);
                    }
                    sleep = new Sleep(last, entry.getDate());
                }
                else {
                    if (sleep != null) {
                        sleep.setTo(entry.getDate());
                    }
                }
            }
        }
        return sleeps;
    }
    
    public void part1() {
        List<Sleep> sleeps = getSleeps();
        Map<Integer, Integer> time = new HashMap<>();
        sleeps.forEach(sleep -> {
            time.compute(sleep.getId(), (id, len) -> len == null ? sleep.length() : len + sleep.length());
        });
        time.entrySet().stream().max(Map.Entry.comparingByValue()).ifPresent(entry -> {
            int[] frequency = new int[60];
            sleeps.stream().filter(sleep -> sleep.getId() == entry.getKey()).forEach(sleep -> {
                for (int i = sleep.getFrom(); i < sleep.getTo(); i++) {
                    frequency[i]++;
                }
            });
            int maxM = 0;
            for (int m = 0; m < 60; m++) {
                if (frequency[m] > frequency[maxM]) {
                    maxM = m;
                }
            }
            Util.log("Guard: " + entry.getKey());
            Util.log("Minute: " + maxM);
            Util.log("Key: " + (entry.getKey() * maxM));
        });
    }
    
    public void part2() {
        List<Sleep> sleeps = getSleeps();
        List<Integer> ids = sleeps.stream().map(Sleep::getId).distinct().collect(Collectors.toList());
        Map<Integer, Pair<Integer, Integer>> freqs = new HashMap<>();
        ids.forEach(id -> {
            int[] frequency = new int[60];
            sleeps.stream().filter(sleep -> sleep.getId() == id).forEach(sleep -> {
                for (int i = sleep.getFrom(); i < sleep.getTo(); i++) {
                    frequency[i]++;
                }
            });
            int max = -1;
            int maxM = 0;
            for (int m = 0; m < 60; m++) {
                if (frequency[m] > max) {
                    max = frequency[m];
                    maxM = m;
                }
            }
            freqs.put(id, new Pair<>(maxM, max));
        });
        freqs.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue().getValue())).ifPresent(entry -> {
            Util.log("Guard: " + entry.getKey());
            Util.log("Minute: " + entry.getValue().getValue());
            Util.log("Key: " + (entry.getKey() * entry.getValue().getKey()));
        });
    }
    
}
