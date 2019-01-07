package com.ssplugins.advent.day19;

import com.ssplugins.advent.day16.Sample;

public class Instruction {
    
    private String op;
    private int[] payload;
    
    public Instruction(String op, int[] payload) {
        this.op = op;
        this.payload = payload;
    }
    
    public static Instruction fromInput(String input) {
        String op = input.substring(0, input.indexOf(' '));
        int[] payload = Sample.toIntArray(input.substring(op.length() + 1).split(" "));
        return new Instruction(op, payload);
    }
    
    public String op() {
        return op;
    }
    
    public int a() {
        return payload[0];
    }
    
    public int b() {
        return payload[1];
    }
    
    public int c() {
        return payload[2];
    }
    
    public int[] getPayload() {
        return payload;
    }
    
}
