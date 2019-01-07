package com.ssplugins.advent.day19;

import com.ssplugins.advent.day16.ChronalClassification;
import com.ssplugins.advent.day16.Op;
import com.ssplugins.advent.day16.Sample;
import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GoWithTheFlow {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(GoWithTheFlow.class);
        GoWithTheFlow gwtf = new GoWithTheFlow(input.orElseThrow(Input.noInput()));
        gwtf.part2();
    }
    
    private List<String> input;
    
    private List<Op> ops;
    
    public GoWithTheFlow(List<String> input) {
        this.input = input;
    }
    
    public void part1() {
        ops = ChronalClassification.createOps();
    
        Op[] ops = this.ops.stream().sorted(Comparator.comparing(Op::getCode)).toArray(Op[]::new);
        int[][] program = input.subList(1, input.size()).stream().map(s -> s.split(" ")).map(Sample::toIntArray).toArray(int[][]::new);
        int[] reg = new int[6];
        reg[0] = 1; // Comment out for part 1.
        int pointer = 0;
        int bind = Integer.parseInt(input.get(0).split(" ")[1]);
        while (pointer > -1 && pointer < program.length) {
            reg[bind] = pointer;
            int[] inst = program[pointer];
            Op op = ops[inst[0]];
            op.getAction().execute(reg, inst[1], inst[2], inst[3]);
            pointer = reg[bind];
            pointer++;
        }
        Util.log(reg[0]);
    }
    
    public void part2() {
        // State of registers after initialization.
        int[] reg = new int[] {0, 1, 10550400, 10551330, 3, 1};
    
        // Loop instructions converted to array operations.
//        do {
//            reg[1] = 1;
//            do {
//                reg[2] = reg[1] * reg[5];
//                reg[2] = reg[2] == reg[3] ? 1 : 0;
//                if (reg[2] == 1) {
//                    reg[0] += reg[5];
//                }
//                reg[1]++;
//                reg[2] = reg[1] > reg[3] ? 1 : 0;
//            } while (reg[2] == 0);
//            reg[5]++;
//            reg[2] = reg[5] > reg[3] ? 1 : 0;
//        } while (reg[2] == 0);
        
        // Simplified: Sum of all factors of register 3.
        for (int i = 1; i <= reg[3]; i++) {
            if (reg[3] % i == 0) reg[0] += i;
        }
        
        Util.log(reg[0]);
        
    }
    
}
