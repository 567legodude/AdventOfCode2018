package com.ssplugins.advent.day9;

public class Marble {
    
    private int value;
    private Marble prev, next;
    
    public Marble(int value) {
        this(value, null, null);
    }
    
    public Marble(int value, Marble after, Marble before) {
        this.value = value;
        if (value == 0) {
            prev = this;
            next = this;
        }
        else {
            prev = after;
            after.setNext(this);
            next = before;
            before.setPrev(this);
        }
    }
    
    public void remove() {
        prev.setNext(next);
        next.setPrev(prev);
    }
    
    public int getValue() {
        return value;
    }
    
    public Marble next() {
        return next;
    }
    
    public Marble prev() {
        return prev;
    }
    
    public void setNext(Marble next) {
        this.next = next;
    }
    
    public void setPrev(Marble prev) {
        this.prev = prev;
    }
    
    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
    
}
