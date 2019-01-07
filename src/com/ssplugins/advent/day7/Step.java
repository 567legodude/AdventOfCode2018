package com.ssplugins.advent.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Step {
    
    private String letter;
    private List<String> required = new ArrayList<>();
    private int time;
    
    public Step(String letter) {
        this.letter = letter;
        String[] times = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        time = 60 + Arrays.asList(times).indexOf(letter) + 1;
    }
    
    public int getTime() {
        return time;
    }
    
    public void useTime() {
        time--;
    }
    
    public String getLetter() {
        return letter;
    }
    
    public List<String> getRequired() {
        return required;
    }
    
    public void addRequired(String step) {
        required.add(step);
    }
    
    @Override
    public String toString() {
        return getLetter();
    }
    
}
