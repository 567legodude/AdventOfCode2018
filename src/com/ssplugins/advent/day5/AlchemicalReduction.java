package com.ssplugins.advent.day5;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AlchemicalReduction {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(AlchemicalReduction.class);
        AlchemicalReduction ar = new AlchemicalReduction(input.orElseThrow(Util.noInput()));
        ar.part2();
    }
    
    private List<String> input;
    
    public AlchemicalReduction(List<String> input) {
        this.input = input;
    }
    
    private String reduce(String input) {
        Matcher m = Pattern.compile("(.)(?!\\1)(?i:\\1)").matcher(input);
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(buffer, "");
        }
        m.appendTail(buffer);
        return buffer.toString();
    }
    
    private String reduceFully(String input) {
        int len;
        do {
            len = input.length();
            input = reduce(input);
        } while (input.length() != len);
        return input;
    }
    
    public void part1() {
        String input = this.input.get(0);
        input = reduceFully(input);
        Util.log(input.length());
    }
    
    public void part2() {
        String input = this.input.get(0);
        List<String> types = input.toLowerCase()
                                  .chars()
                                  .distinct()
                                  .mapToObj(value -> String.valueOf((char) value))
                                  .collect(Collectors.toList());
        types.stream()
             .map(s -> input.replace(s, "").replace(s.toUpperCase(), ""))
             .map(this::reduceFully)
             .map(String::length)
             .min(Integer::compareTo)
             .ifPresent(Util::log);
    }
    
}
