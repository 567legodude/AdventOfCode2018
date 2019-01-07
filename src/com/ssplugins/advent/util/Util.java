package com.ssplugins.advent.util;

import java.util.function.Supplier;

public class Util {
    
    public static void log(Object object) {
        System.out.println(object);
    }
    
    public static Supplier<IllegalArgumentException> noInput() {
        return () -> new IllegalArgumentException("No input provided.");
    }
    
}
