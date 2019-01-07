package com.ssplugins.advent.day25;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FourDAdventure {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(FourDAdventure.class);
        FourDAdventure fda = new FourDAdventure(input.orElseThrow(Input.noInput()));
        fda.part1();
    }
    
    private List<String> input;
    
    public FourDAdventure(List<String> input) {
        this.input = input;
    }
    
    private int distance(int[] a, int[] b) {
        int t = 0;
        for (int i = 0; i < a.length; i++) {
            t += Math.abs(a[i] - b[i]);
        }
        return t;
    }
    
    public void part1() {
        List<MPointND> points = input.stream().map(MPointND::fromInput).collect(Collectors.toList());
        List<List<MPointND>> consts = new ArrayList<>();
        points.forEach(p -> {
            List<MPointND> c = new ArrayList<>();
            c.add(p);
            consts.add(c);
        });
        Util.compareEach(points, (p, p2) -> {
            if (p.distance(p2) <= 3) {
                List<MPointND> con = consts.stream().filter(list -> list.contains(p)).findFirst().orElse(null);
                List<MPointND> con2 = consts.stream().filter(list -> list.contains(p2)).findFirst().orElse(null);
                if (con == con2) return;
                con.addAll(con2);
                consts.remove(con2);
            }
        });
        Util.log(consts.size());
    }
    
}
