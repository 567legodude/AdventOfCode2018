package com.ssplugins.advent.day13;

import com.ssplugins.advent.util.Util;

public class Cart implements Comparable<Cart> {
    
    private int x, y;
    private Direction facing;
    private Turn turn;
    private Track on;
    
    public Cart(int x, int y, Direction facing, Track on) {
        this.x = x;
        this.y = y;
        this.facing = facing;
        turn = Turn.LEFT;
        this.on = on;
    }
    
    public void traverse(char[][] map) {
        x = facing.moveX(x);
        y = facing.moveY(y);
        on = Track.fromChar(map[y][x]);
        if (on == Track.INTERSECTION) {
            facing = turn.from(facing);
            turn = turn.next();
        }
        else if (on.isCurve()) {
            facing = Turn.curve(on, facing);
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Direction getFacing() {
        return facing;
    }
    
    public Track getOn() {
        return on;
    }
    
    @Override
    public int compareTo(Cart o) {
        return Util.compareReadOrder(x, y, o.x, o.y);
    }
    
    public enum Direction {
        NORTH('^'),
        EAST('>'),
        SOUTH('v'),
        WEST('<');
        
        private char c;
    
        Direction(char c) {
            this.c = c;
        }
    
        public static Direction fromChar(char c) {
            switch (c) {
                case '^':
                    return NORTH;
                case '>':
                    return EAST;
                case 'v':
                    return SOUTH;
                case '<':
                    return WEST;
            }
            return NORTH;
        }
    
        public char getChar() {
            return c;
        }
    
        public int moveX(int current) {
            switch (this) {
                case EAST:
                    return current + 1;
                case WEST:
                    return current - 1;
            }
            return current;
        }
    
        public int moveY(int current) {
            switch (this) {
                case NORTH:
                    return current - 1;
                case SOUTH:
                    return current + 1;
            }
            return current;
        }
    }
    
    private enum Turn {
        LEFT,
        STRAIGHT,
        RIGHT;
    
        public static Direction curve(Track track, Direction direction) {
            if (track == Track.FORWARD_CURVE) {
                switch (direction) {
                    case NORTH:
                        return Direction.EAST;
                    case EAST:
                        return Direction.NORTH;
                    case SOUTH:
                        return Direction.WEST;
                    case WEST:
                        return Direction.SOUTH;
                }
            }
            else if (track == Track.BACKWARD_CURVE) {
                switch (direction) {
                    case NORTH:
                        return Direction.WEST;
                    case EAST:
                        return Direction.SOUTH;
                    case SOUTH:
                        return Direction.EAST;
                    case WEST:
                        return Direction.NORTH;
                }
            }
            return direction;
        }
    
        public Turn next() {
            switch (this) {
                case LEFT:
                    return STRAIGHT;
                case STRAIGHT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
            }
            return STRAIGHT;
        }
    
        public Direction from(Direction old) {
            if (this == Turn.LEFT) {
                switch (old) {
                    case NORTH:
                        return Direction.WEST;
                    case EAST:
                        return Direction.NORTH;
                    case SOUTH:
                        return Direction.EAST;
                    case WEST:
                        return Direction.SOUTH;
                }
            }
            else if (this == Turn.RIGHT) {
                switch (old) {
                    case NORTH:
                        return Direction.EAST;
                    case EAST:
                        return Direction.SOUTH;
                    case SOUTH:
                        return Direction.WEST;
                    case WEST:
                        return Direction.NORTH;
                }
            }
            return old;
        }
    }
    
}
