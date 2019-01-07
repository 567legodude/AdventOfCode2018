package com.ssplugins.advent.day3;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Slicing {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(Slicing.class);
        Slicing slicing = new Slicing(input.orElseThrow(Input.noInput()));
        slicing.part2();
    }
    
    private List<String> input;
    
    public Slicing(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        List<Claim> claims = input.stream().map(Claim::new).collect(Collectors.toList());
        Boolean[][] fabric = new Boolean[1000][1000];
        Util.compareEach(claims, (claim, claim2) -> {
            if (claim.intersects(claim2)) {
                Claim.overlap(claim.toBounds(), claim2.toBounds()).overlay(fabric);
            }
        });
        int area = Stream.of(fabric).flatMap(Stream::of).mapToInt(value -> value == null ? 0 : (value ? 1 : 0)).sum();
        Util.log(area);
    }
    
    public void part2() {
        List<Claim> claims = input.stream().map(Claim::new).collect(Collectors.toList());
        claims.stream()
              .filter(c -> claims.stream().noneMatch(claim -> claim != c && claim.intersects(c)))
              .findFirst()
              .ifPresent(c -> Util.log(c.getId()));
    }
    
}
