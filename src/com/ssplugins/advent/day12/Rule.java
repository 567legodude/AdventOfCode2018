package com.ssplugins.advent.day12;

public class Rule {
    
    private boolean[] rule;
    private boolean output;
    
    public Rule(boolean[] rule, boolean output) {
        if (rule.length % 2 == 0) {
            throw new IllegalArgumentException("Rule must have odd number of pots.");
        }
        this.rule = rule;
        this.output = output;
    }
    
    public static Rule fromInput(String input) {
        return new Rule(Pots.growthArray(input.substring(0, 5)), input.charAt(9) == '#');
    }
    
    public boolean getResult(boolean[] pots, int index) {
        int d = rule.length / 2;
        for (int i = 0; i < rule.length; i++) {
            int ind = index - d + i;
            if (ind < 0 || ind >= pots.length) {
                continue;
            }
            if (rule[i] != pots[ind]) {
                return pots[index];
            }
        }
        return output;
    }
    
    public Result apply(boolean[] pots, int index) {
        int d = rule.length / 2;
        for (int i = 0; i < rule.length; i++) {
            int ind = index - d + i;
            boolean value = false;
            if (ind >= 0 && ind < pots.length) {
                value = pots[ind];
            }
            if (rule[i] != value) {
                return new Result(false, false);
            }
        }
        return new Result(true, output);
    }
    
    public class Result {
        private boolean applies, output;
    
        public Result(boolean applies, boolean output) {
            this.applies = applies;
            this.output = output;
        }
    
        public boolean applies() {
            return applies;
        }
    
        public boolean getOutput() {
            return output;
        }
    
        @Override
        public String toString() {
            return applies + "," + output;
        }
    }
    
}
