package com.ssplugins.advent.day25;

import com.ssplugins.advent.day16.Sample;

import java.util.Arrays;

public class MPointND {
    
    private int[] values;
    
    public MPointND(int[] values) {
        this.values = values;
    }
    
    public static MPointND fromInput(String input) {
        return new MPointND(Sample.toIntArray(input.split(",")));
    }
    
    public int distance(MPointND other) {
        if (values.length != other.values.length) return -1;
        int d = 0;
        for (int i = 0; i < values.length; i++) {
            d += Math.abs(values[i] - other.values[i]);
        }
        return d;
    }
    
    public int[] getValues() {
        return values;
    }
    
    @Override
    public int hashCode() {
        return Arrays.stream(values).map(i -> i * i).sum();
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MPointND && Arrays.equals(values, ((MPointND) obj).values);
    }
    
}
