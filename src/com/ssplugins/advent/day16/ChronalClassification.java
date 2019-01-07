package com.ssplugins.advent.day16;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ChronalClassification {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(ChronalClassification.class);
        Optional<List<String>> input2 = Input.get(ChronalClassification.class, "input2.txt");
        ChronalClassification cc = new ChronalClassification(input.orElseThrow(Input.noInput()), input2.orElseThrow(Input.noInput()));
        cc.part2();
    }
    
    private List<String> input;
    private List<String> input2;
    
    private List<Op> ops;
    private List<Sample> samples;
    
    public ChronalClassification(List<String> input, List<String> input2) {
        this.input = input;
        this.input2 = input2;
    }
    
    public static List<Op> createOps() {
        List<Op> ops = new ArrayList<>();
        ops.add(new Op("addr", 9, (reg, a, b, c) -> reg[c] = reg[a] + reg[b]));
        ops.add(new Op("addi", 6, (reg, a, b, c) -> reg[c] = reg[a] + b));
        ops.add(new Op("mulr", 8, (reg, a, b, c) -> reg[c] = reg[a] * reg[b]));
        ops.add(new Op("muli", 0, (reg, a, b, c) -> reg[c] = reg[a] * b));
        ops.add(new Op("banr", 14, (reg, a, b, c) -> reg[c] = reg[a] & reg[b]));
        ops.add(new Op("bani", 11, (reg, a, b, c) -> reg[c] = reg[a] & b));
        ops.add(new Op("borr", 1, (reg, a, b, c) -> reg[c] = reg[a] | reg[b]));
        ops.add(new Op("bori", 10, (reg, a, b, c) -> reg[c] = reg[a] | b));
        ops.add(new Op("setr", 7, (reg, a, b, c) -> reg[c] = reg[a]));
        ops.add(new Op("seti", 12, (reg, a, b, c) -> reg[c] = a));
        ops.add(new Op("gtir", 15, (reg, a, b, c) -> reg[c] = a > reg[b] ? 1 : 0));
        ops.add(new Op("gtri", 2, (reg, a, b, c) -> reg[c] = reg[a] > b ? 1 : 0));
        ops.add(new Op("gtrr", 4, (reg, a, b, c) -> reg[c] = reg[a] > reg[b] ? 1 : 0));
        ops.add(new Op("eqir", 5, (reg, a, b, c) -> reg[c] = a == reg[b] ? 1 : 0));
        ops.add(new Op("eqri", 3, (reg, a, b, c) -> reg[c] = reg[a] == b ? 1 : 0));
        ops.add(new Op("eqrr", 13, (reg, a, b, c) -> reg[c] = reg[a] == reg[b] ? 1 : 0));
        return ops;
    }
    
    private void setup() {
        ops = createOps();
        samples = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 4) {
            samples.add(Sample.fromInput(input.subList(i, Math.min(i + 3, input.size()))));
        }
    }
    
    public void part1() {
        setup();
        long match = samples.stream()
                            .mapToLong(sample -> ops.stream()
                                                    .map(op -> op.execute(sample.copyBefore(), sample.a(), sample.b(), sample.c()))
                                                    .filter(ints -> Arrays.equals(ints, sample.getAfter()))
                                                    .count())
                            .filter(value -> value >= 3)
                            .count();
        Util.log(match);
    }
    
    public void part2() {
        setup();
        // Find potential op codes.
        for (int o = 0; o < 16; o++) {
            int op = o;
            for (Sample sample : samples) {
                ops.stream()
                   .filter(opc -> Arrays.equals(opc.execute(sample.copyBefore(), sample.a(), sample.b(), sample.c()), sample.getAfter()))
                   .forEach(opc -> {
                       opc.potential(sample.op());
                   });
            }
        }
        // Determine which code goes to which Op
        while (!ops.stream().allMatch(Op::hasCode)) {
            ops.stream()
               .filter(op -> op.getPotential().size() == 1)
               .forEach(op -> {
                   int code = op.getPotential().get(0);
                   ops.forEach(opc -> {
                       opc.getPotential().remove((Integer) code);
                   });
                   op.setCode(code);
               });
        }
        // Execute test program
        int[] register = new int[4];
        input2.stream()
              .map(s -> Sample.toIntArray(s.split(" ")))
              .forEach(payload -> {
                  Optional<Op> op = ops.stream().filter(Op.code(payload[0])).findFirst();
                  if (!op.isPresent()) {
                      throw new IllegalStateException("No OP for code " + payload[0]);
                  }
                  op.get().getAction().execute(register, payload[1], payload[2], payload[3]);
              });
        Util.log(register[0]);
    }
    
}
