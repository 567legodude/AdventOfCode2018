package com.ssplugins.advent.day16;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Op {
    
    private String name;
    private int code;
    private Action action;
    
    private List<Integer> potential;
    
    public Op(String name, int code, Action action) {
        this.name = name;
        this.code = code;
        this.action = action;
        potential = new ArrayList<>();
    }
    
    public static Predicate<Op> code(int opcode) {
        return op -> op.getCode() == opcode;
    }
    
    public static Predicate<Op> name(String name) {
        return op -> op.getName().equals(name);
    }
    
    public int[] execute(int[] reg, int a, int b, int c) {
        action.execute(reg, a, b, c);
        return reg;
    }
    
    public void potential(int code) {
        if (!potential.contains(code)) {
            potential.add(code);
        }
    }
    
    public List<Integer> getPotential() {
        return potential;
    }
    
    public boolean hasCode() {
        return code != -1;
    }
    
    public String getName() {
        return name;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public Action getAction() {
        return action;
    }
    
    public interface Action {
        void execute(int[] reg, int a, int b, int c);
    }
    
}
