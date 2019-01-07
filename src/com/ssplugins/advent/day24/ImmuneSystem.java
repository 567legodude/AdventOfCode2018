package com.ssplugins.advent.day24;

import com.ssplugins.advent.util.Comparing;
import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImmuneSystem {
    
    public static void main(String[] args) {
        Optional<List<String>> immuneInput = Input.get(ImmuneSystem.class, "immune.txt");
        Optional<List<String>> infectionInput = Input.get(ImmuneSystem.class, "infection.txt");
        ImmuneSystem is = new ImmuneSystem(immuneInput.orElseThrow(Input.noInput()), infectionInput.orElseThrow(Input.noInput()));
        is.part2();
    }
    
    private List<String> immune;
    private List<String> infection;
    
    private List<Group> groups;
    
    public ImmuneSystem(List<String> immune, List<String> infection) {
        this.immune = immune;
        this.infection = infection;
    }
    
    private void setup() {
        groups = new ArrayList<>();
        immune.stream().map(Group::immune).forEach(groups::add);
        infection.stream().map(Group::infection).forEach(groups::add);
    }
    
    private void fight() {
        while (shouldFight(groups)) {
            boolean stalemate = true;
            for (Group group : groups) {
                if (!groups.stream().filter(group::isEnemy).allMatch(g -> g.isImmune(group.getDamageType()) || group.potentialDeaths(g) == 0)) {
                    stalemate = false;
                }
            }
            if (stalemate) return;
            // Target selection
            groups.sort(Group::compareTo);
            groups.forEach(group -> {
                Optional<Group> target = groups.stream()
                                               .filter(group::isEnemy)
                                               .filter(Group::canTarget)
                                               .max(Comparing.value(group::potentialDamage)
                                                             .then(Group::effectivePower)
                                                             .then(Group::getInitiative));
//                                               .max((o1, o2) -> {
//                                                   int pd1 = group.potentialDamage(o1);
//                                                   int pd2 = group.potentialDamage(o2);
//                                                   if (pd1 != pd2) return Integer.compare(pd1, pd2);
//                                                   int ep1 = o1.effectivePower();
//                                                   int ep2 = o2.effectivePower();
//                                                   if (ep1 != ep2) return Integer.compare(ep1, ep2);
//                                                   return Integer.compare(o1.getInitiative(), o2.getInitiative());
//                                               });
                target.ifPresent(g -> {
                    if (group.potentialDamage(g) == 0) return;
                    group.target(g);
                });
            });
            // Attack
            groups.sort(Comparing.reverse(Group::getInitiative));
            for (int i = 0; i < groups.size(); i++) {
                Group attacker = groups.get(i);
                if (attacker.getTarget() == null) continue;
                Group target = attacker.getTarget();
                attacker.attack(target);
                if (target.getUnits() <= 0) {
                    int ind = groups.indexOf(target);
                    if (ind <= i) i--;
                    groups.remove(target);
                }
            }
            groups.forEach(Group::reset);
        }
    }
    
    private boolean shouldFight(List<Group> groups) {
        Group g = groups.get(0);
        return groups.stream().anyMatch(g::isEnemy);
    }
    
    public void part1() {
        setup();
        fight();
        int units = groups.stream().mapToInt(Group::getUnits).sum();
        Util.log(units);
    }
    
    public void part2() {
        do {
            Group.BOOST++;
            setup();
            fight();
        } while (groups.get(0).getSide() != 0);
        int units = groups.stream().mapToInt(Group::getUnits).sum();
        Util.log(units);
    }
    
}
