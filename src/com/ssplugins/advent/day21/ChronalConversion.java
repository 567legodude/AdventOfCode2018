package com.ssplugins.advent.day21;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChronalConversion {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ChronalConversion.class);
        ChronalConversion cc = new ChronalConversion(input.orElseThrow(Input.noInput()));
        cc.part2();
    }
    
    private List<String> input;
    
    public ChronalConversion(List<String> input) {
        this.input = input;
    }
    
    // elfcode converted to array operations
    public void part2() {
        List<Integer> seen = new ArrayList<>();
        int[] reg = new int[6];
        boolean b1;
        do {
            reg[3] = reg[4] | 65536;
            reg[4] = 4332021;
            do {
                reg[2] = reg[3] & 255;
                reg[4] += reg[2];
                reg[4] &= 16777215;
                reg[4] *= 65899;
                reg[4] &= 16777215;
                b1 = false;
                if (256 <= reg[3]) {
                    int i = 1;
                    while (i * 256 <= reg[3]) i++;
                    reg[3] = i - 1;
                    b1 = true;
                }
            } while (b1);
            if (seen.contains(reg[4])) {
                break;
            }
            seen.add(reg[4]);
        } while (reg[4] != reg[0]); // for part 1, remove while loop
        Util.log(seen.get(seen.size() - 1));
    }
    
}
