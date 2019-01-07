package com.ssplugins.advent.day13;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class MineCartMadness {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(MineCartMadness.class);
        MineCartMadness mcm = new MineCartMadness(input.orElseThrow(Input.noInput()));
        mcm.part2();
    }
    
    private List<String> input;
    
    private char[][] map;
    private List<Cart> carts;
    
    public MineCartMadness(List<String> input) {
        this.input = input;
    }
    
    private void setup() {
        map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        carts = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            char[] chars = map[y];
            for (int x = 0; x < chars.length; x++) {
                char c = chars[x];
                if (Stream.of(Cart.Direction.values()).map(Cart.Direction::getChar).anyMatch(character -> character == c)) {
                    Cart cart = new Cart(x, y, Cart.Direction.fromChar(c), Track.fromChar(c));
                    carts.add(cart);
                    map[y][x] = cart.getOn().getChar();
                }
            }
        }
    }
    
    public void part1() {
        setup();
        AtomicReference<Cart> col = new AtomicReference<>();
        while (!Util.anyEqual(carts, Cart::compareTo, (cart, cart2) -> col.set(cart))) {
            carts.sort(Cart::compareTo);
            carts.forEach(cart -> cart.traverse(map));
        }
        Util.log(col.get().getX() + "," + col.get().getY());
    }
    
    public void part2() {
        setup();
        while (carts.size() > 1) {
            carts.sort(Cart::compareTo);
            for (int i = 0; i < carts.size(); i++) {
                Cart cart = carts.get(i);
                cart.traverse(map);
                for (int j = 0; j < carts.size(); j++) {
                    if (j == i) {
                        continue;
                    }
                    Cart other = carts.get(j);
                    if (cart.compareTo(other) == 0) {
                        carts.remove(cart);
                        carts.remove(other);
                        i--;
                        if (j <= i) {
                            i--;
                        }
                    }
                }
            }
        }
        Util.log(carts.get(0).getX() + "," + carts.get(0).getY());
    }
    
}
