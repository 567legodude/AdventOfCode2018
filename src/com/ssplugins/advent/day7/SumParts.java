package com.ssplugins.advent.day7;

import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SumParts {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(SumParts.class);
        SumParts sp = new SumParts(input.orElseThrow(Util.noInput()));
        sp.part2();
    }
    
    private List<String> input;
    
    public SumParts(List<String> input) {
        this.input = input;
    }
    
    private void loadSteps(List<Step> steps) {
        input.forEach(s -> {
            Step step = getStep(steps, String.valueOf(s.charAt(36)));
            String other = String.valueOf(s.charAt(5));
            step.addRequired(other);
            getStep(steps, other);
        });
    }
    
    public void part1() {
        List<Step> steps = new ArrayList<>();
        loadSteps(steps);
        List<Step> finished = new ArrayList<>();
        while (steps.size() > 0) {
            steps.stream()
                 .filter(step -> step.getRequired().size() == 0)
                 .min(Comparator.comparing(Step::getLetter))
                 .ifPresent(step -> {
                     finished.add(step);
                     steps.forEach(s -> {
                         s.getRequired().remove(step.getLetter());
                     });
                     steps.remove(step);
                 });
        }
        String order = String.join("", finished.stream().map(Step::getLetter).collect(Collectors.toList()));
        Util.log(order);
    }
    
    private Step getStep(List<Step> steps, String step) {
        return steps.stream()
                    .filter(s -> s.getLetter().equals(step))
                    .findFirst()
                    .orElseGet(() -> {
                        Step s = new Step(step);
                        steps.add(s);
                        return s;
                    });
    }
    
    public void part2() {
        List<Step> steps = new ArrayList<>();
        loadSteps(steps);
        AtomicInteger workers = new AtomicInteger(5);
        int time = 0;
        List<Step> working = new ArrayList<>(workers.get());
        while (steps.size() > 0) {
            // employ
            long ready = steps.stream().filter(step -> step.getRequired().size() == 0 && !working.contains(step)).count();
            if (ready > 0 && workers.get() > 0) {
                long amount = Math.min(workers.get(), ready);
                for (long i = 0; i < amount; i++) {
                    steps.stream()
                         .filter(step -> step.getRequired().size() == 0 && !working.contains(step))
                         .min(Comparator.comparing(Step::getLetter))
                         .ifPresent(step -> {
                             working.add(step);
                             workers.getAndDecrement();
                         });
                }
            }
            // work
            working.forEach(Step::useTime);
            time++;
            // release
            working.removeIf(step -> {
                boolean done = step.getTime() == 0;
                if (done) {
                    workers.getAndIncrement();
                    steps.forEach(step1 -> {
                        step1.getRequired().remove(step.getLetter());
                    });
                    steps.removeIf(step1 -> step1.getLetter().equals(step.getLetter()));
                }
                return done;
            });
        }
        Util.log(time);
    }
    
}
