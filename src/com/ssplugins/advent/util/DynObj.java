package com.ssplugins.advent.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynObj {
    
    private Map<Integer, String> values = new HashMap<>();
    
    public static Function<String, DynObj> regex(String regex) {
        return s -> {
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(s);
            DynObj dyn = new DynObj();
            if (!m.find()) return dyn;
            for (int i = 1; i <= m.groupCount(); i++) {
                dyn.setValue(i, m.group(i));
            }
            return dyn;
        };
    }
    
    private <T> Function<String, T> safe(Function<String, T> function) {
        return s -> {
            try {
                return function.apply(s);
            } catch (Throwable t) {
                return null;
            }
        };
    }
    
    public void setValue(int key, String value) {
        values.put(key, value);
    }
    
    public int size() {
        return values.size();
    }
    
    public boolean isNull(int index) {
        return get(index) == null;
    }
    
    public boolean isPresent(int index) {
        return !isNull(index);
    }
    
    public Optional<String> op(int index) {
        return Optional.ofNullable(get(index));
    }
    
    public String get(int index) {
        return values.get(index);
    }
    
    public Optional<Character> opChar(int index) {
        return op(index).filter(s -> s.length() == 1).map(s -> s.charAt(0));
    }
    
    public char getChar(int index) {
        return opChar(index).orElseThrow(DynObjException::new);
    }
    
    public Optional<Integer> opInt(int integer) {
        return op(integer).map(safe(Integer::parseInt));
    }
    
    public int getInt(int index) {
        return opInt(index).orElseThrow(DynObjException::new);
    }
    
    public Optional<Double> opDouble(int index) {
        return op(index).map(safe(Double::parseDouble));
    }
    
    public double getDouble(int index) {
        return opDouble(index).orElseThrow(DynObjException::new);
    }
    
    public Optional<Long> opLong(int index) {
        return op(index).map(safe(Long::parseLong));
    }
    
    public long getLong(int index) {
        return opLong(index).orElseThrow(DynObjException::new);
    }
    
    public Optional<Float> opFloat(int index) {
        return op(index).map(safe(Float::parseFloat));
    }
    
    public float getFloat(int index) {
        return opFloat(index).orElseThrow(DynObjException::new);
    }
    
    public Optional<String[]> opSplit(int index, String regex) {
        return op(index).map(s -> s.split(regex));
    }
    
    public String[] getSplit(int index, String regex) {
        return opSplit(index, regex).orElseThrow(DynObjException::new);
    }
    
    private class DynObjException extends RuntimeException {
    }
    
}
