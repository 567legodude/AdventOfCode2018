package com.ssplugins.advent.day13;

public enum Track {
    
    VERTICAL('|', false),
    HORIZONTAL('-', false),
    INTERSECTION('+', false),
    FORWARD_CURVE('/', true),
    BACKWARD_CURVE('\\', true);
    
    private char c;
    private boolean curve;
    
    Track(char c, boolean curve) {
        this.c = c;
        this.curve = curve;
    }
    
    public static Track fromChar(char c) {
        switch (c) {
            case '|':
            case '^':
            case 'v':
                return VERTICAL;
            case '-':
            case '<':
            case '>':
                return HORIZONTAL;
            case '+':
                return INTERSECTION;
            case '/':
                return FORWARD_CURVE;
            case '\\':
                return BACKWARD_CURVE;
            default:
                return VERTICAL; // Shouldn't be reached.
        }
    }
    
    public char getChar() {
        return c;
    }
    
    public boolean isCurve() {
        return curve;
    }
    
}
