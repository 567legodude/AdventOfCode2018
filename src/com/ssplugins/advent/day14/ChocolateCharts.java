package com.ssplugins.advent.day14;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChocolateCharts {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ChocolateCharts.class);
        ChocolateCharts cc = new ChocolateCharts(input.orElseThrow(Input.noInput()));
        cc.part1();
    }
    
    private List<String> input;
    
    public ChocolateCharts(List<String> input) {
        this.input = input;
    }
    
    public void part1Old() { // Not designed well to work with part 2.
        int make = 10;
        int after = input.get(1).length() - input.get(0).length();
        List<Integer> scores = new ArrayList<>(input.get(0).length() + after + make);
        for (char c : input.get(0).toCharArray()) {
            scores.add(Integer.parseInt(String.valueOf(c)));
        }
        int[] current = new int[2];
        for (int i = 0; i < current.length; i++) {
            current[i] = i;
        }
        while (scores.size() < input.get(0).length() + after + make) {
            int sum = Arrays.stream(current).map(scores::get).sum();
            for (char c : String.valueOf(sum).toCharArray()) {
                scores.add(Integer.parseInt(String.valueOf(c)));
            }
            for (int j = 0; j < current.length; j++) {
                current[j] = (current[j] + scores.get(current[j]) + 1) % scores.size();
            }
        }
        String s = scores.subList(input.get(1).length(), input.get(1).length() + make)
                         .stream()
                         .map(String::valueOf)
                         .collect(Collectors.joining(""));
        Util.log(s);
    }
    
    public void part1() {
        StringBuilder scores = new StringBuilder(input.get(0));
        String in = input.get(1);
        int a = 0;
        int b = 1;
        while (scores.length() < in.length() + 1 || !scores.substring(scores.length() - (in.length() + 1)).contains(in)) {
            scores.append(Integer.parseInt(scores.substring(a, a + 1)) + Integer.parseInt(scores.substring(b, b + 1)));
            a = (a + Integer.parseInt(scores.substring(a, a + 1)) + 1) % scores.length();
            b = (b + Integer.parseInt(scores.substring(b, b + 1)) + 1) % scores.length();
        }
        String recipes = scores.substring(in.length(), in.length() + 10);
        Util.log(recipes);
        int index = scores.indexOf(in);
        Util.log(index);
    }
    
}
