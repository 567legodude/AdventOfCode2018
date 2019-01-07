package com.ssplugins.advent.day16;

import java.util.Arrays;
import java.util.List;

public class Sample {
    
    private int[] before, after;
    private int[] payload;
    
    public Sample(int[] before, int[] payload, int[] after) {
        this.before = before;
        this.after = after;
        this.payload = payload;
    }
    
    public static Sample fromInput(List<String> list) {
        if (list.size() < 3) {
            throw new IllegalArgumentException("Invalid input");
        }
        String b = list.get(0);
        int[] before = toIntArray(b.substring(b.indexOf('[') + 1, b.length() - 1).split(", "));
    
        int[] payload = new int[4];
        String[] pa = list.get(1).split(" ");
        for (int i = 0; i < pa.length; i++) {
            payload[i] = Integer.parseInt(pa[i]);
        }
    
        String a = list.get(2);
        int[] after = toIntArray(a.substring(a.indexOf('[') + 1, a.length() - 1).split(", "));
        return new Sample(before, payload, after);
    }
    
    public static int[] toIntArray(String[] ia) {
        int[] arr = new int[4];
        for (int i = 0; i < ia.length; i++) {
            arr[i] = Integer.parseInt(ia[i]);
        }
        return arr;
    }
    
    public int op() {
        return payload[0];
    }
    
    public int a() {
        return payload[1];
    }
    
    public int b() {
        return payload[2];
    }
    
    public int c() {
        return payload[3];
    }
    
    public int[] copyBefore() {
        return Arrays.copyOf(before, before.length);
    }
    
    public int[] getBefore() {
        return before;
    }
    
    public int[] getAfter() {
        return after;
    }
    
    public int[] getPayload() {
        return payload;
    }
    
}
