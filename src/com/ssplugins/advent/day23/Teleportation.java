package com.ssplugins.advent.day23;

import com.microsoft.z3.*;
import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Teleportation {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(Teleportation.class);
        Teleportation t = new Teleportation(input.orElseThrow(Input.noInput()));
        t.part2();
    }
    
    private List<String> input;
    
    public Teleportation(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        List<Nanobot> bots = input.stream().map(Nanobot::fromInput).collect(Collectors.toList());
        Optional<Nanobot> max = bots.stream().max(Comparator.comparing(Nanobot::getRadius));
        Nanobot n = max.orElseThrow(RuntimeException::new);
        long count = bots.stream().filter(n::inRange).count();
        Util.log(count);
    }
    
    public void part2() {
        System.load(new File("libz3.dll").getAbsolutePath());
        List<Nanobot> bots = input.stream().map(Nanobot::fromInput).collect(Collectors.toList());
    
        Map<String, String> cfg = new HashMap<>();
        cfg.put("model", "true");
        Context c = new Context(cfg);
    
        IntExpr x = c.mkIntConst("x");
        IntExpr y = c.mkIntConst("y");
        IntExpr z = c.mkIntConst("z");
        Optimize o = c.mkOptimize();
        ArithExpr[] inRange = new ArithExpr[bots.size()];
        for (int i = 0; i < inRange.length; i++) {
            Nanobot n = bots.get(i);
            inRange[i] = (ArithExpr) c.mkITE(c.mkLe(c.mkAdd(abs(c, c.mkSub(x, c.mkInt(n.x()))), abs(c, c.mkSub(y, c.mkInt(n.y()))), abs(c, c.mkSub(z, c.mkInt(n.z())))), c.mkInt(n.getRadius())), c.mkInt(1), c.mkInt(0));
        }
        IntExpr sum = c.mkIntConst("sum");
        IntExpr dist = c.mkIntConst("dist");
        o.Add(c.mkEq(sum, c.mkAdd(inRange)));
        o.Add(c.mkEq(dist, c.mkAdd(abs(c, x), abs(c, y), abs(c, z))));
    
        Optimize.Handle maxSum = o.MkMaximize(sum);
        Optimize.Handle minDist = o.MkMinimize(dist);
    
        Util.log(o.Check());
        Util.log(maxSum);
        Util.log(minDist);
    }
    
    private ArithExpr abs(Context c, ArithExpr expr) {
        return (ArithExpr) c.mkITE(c.mkGe(expr, c.mkInt(0)), expr, c.mkSub(c.mkInt(0), expr));
    }
    
}
