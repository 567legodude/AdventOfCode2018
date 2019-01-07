package com.ssplugins.advent.day12;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pots {
    
    private boolean[] pots;
    private List<Rule> rules = new ArrayList<>();
    private int offset = 0;
    private int gen = 0;
    
    public Pots(boolean[] pots) {
        this.pots = pots;
    }
    
    public static Pots fromInput(List<String> input) {
        String state = input.get(0);
        state = state.substring(state.lastIndexOf(':') + 1).trim();
        Pots pots = new Pots(growthArray(state));
        for (int i = 2; i < input.size(); i++) {
            pots.addRule(Rule.fromInput(input.get(i)));
        }
        return pots;
    }
    
    public static boolean[] growthArray(String pots) {
        boolean[] p = new boolean[pots.length()];
        char[] chars = pots.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '#') {
                p[i] = true;
            }
        }
        return p;
    }
    
    public void addRule(Rule rule) {
        rules.add(rule);
    }
    
    public void next() {
        List<Boolean> n = new ArrayList<>(pots.length);
        for (int i = 0; i < pots.length; i++) {
            int finalI = i;
            Optional<Rule.Result> result = rules.stream()
                                                .map(rule -> rule.apply(pots, finalI))
                                                .filter(Rule.Result::applies)
                                                .findFirst();
            result.ifPresent(rule -> {
                n.add(rule.getOutput());
            });
        }
        for (int i = -3; i < 0; i++) {
            int finalI = i;
            Optional<Rule.Result> result = rules.stream()
                                                .map(rule -> rule.apply(pots, finalI))
                                                .filter(Rule.Result::applies)
                                                .findFirst();
            result.ifPresent(rule -> {
                n.add(finalI + 3, rule.getOutput());
            });
            offset++;
        }
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Optional<Rule.Result> result = rules.stream()
                                                .map(rule -> rule.apply(pots, pots.length + finalI))
                                                .filter(Rule.Result::applies)
                                                .findFirst();
            result.ifPresent(rule -> {
                n.add(rule.getOutput());
            });
        }
        pots = new boolean[n.size()];
        for (int i = 0; i < n.size(); i++) {
            pots[i] = n.get(i);
        }
        gen++;
    }
    
    public int sum() {
        int total = 0;
        for (int i = 0; i < pots.length; i++) {
            if (pots[i]) {
                total += i - offset;
            }
        }
        return total;
    }
    
    public boolean[] getPots() {
        return pots;
    }
    
    public int getGen() {
        return gen;
    }
    
}
