package com.ssplugins.advent.day15;

import com.ssplugins.advent.util.Comparing;
import com.ssplugins.advent.util.Input;
import com.ssplugins.advent.util.Util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeverageBandits {
    
    public static void main(String[] args) {
        Optional<List<String>> input = Input.get(BeverageBandits.class);
        BeverageBandits bb = new BeverageBandits(input.orElseThrow(Input.noInput()));
        bb.part2();
    }
    
    private List<String> input;
    
    private char[][] map;
    private List<Unit> units;
    
    public BeverageBandits(List<String> input) {
        this.input = input;
    }
    
    private void setup() {
        map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        units = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            char[] row = map[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x] == 'E') {
                    units.add(Unit.elf(x, y));
                    row[x] = '.';
                }
                else if (row[x] == 'G') {
                    units.add(Unit.goblin(x, y));
                    row[x] = '.';
                }
            }
        }
    }
    
    private boolean round() {
        units.sort(Unit::compareTo);
        for (int i = 0; i < units.size(); i++) {
            Unit unit = units.get(i);
            MPoint point = unit.getPoint();
            List<Unit> targets = units.stream().filter(unit::isEnemy).collect(Collectors.toList());
            if (targets.isEmpty()) {
                return false;
            }
            List<MPoint> hitpoints = targets.stream().map(Unit::getPoint).map(MPoint::surround).flatMap(Stream::of).filter(this::canMove).collect(Collectors.toList());
            Optional<Unit> enemy = enemyInRange(unit);
            if (!enemy.isPresent() && hitpoints.isEmpty()) {
                continue;
            }
            if (!enemy.isPresent()) {
                Map<MPoint, Integer> dist = getDistanceMap(point);
                List<MPoint> reachable = intersect(dist.keySet(), hitpoints);
                if (reachable.isEmpty()) {
                    continue;
                }
                MPoint target = reachable.stream().min(closest(dist)).get();
                Map<MPoint, Integer> targetDistance = getDistanceMap(target);
                MPoint to = Stream.of(point.surround()).filter(this::canMove).min(closest(targetDistance)).orElseThrow(RuntimeException::new);
                unit.setPoint(to);
            }
            Unit target = enemy.orElseGet(() -> enemyInRange(unit).orElse(null));
            if (target == null) {
                continue;
            }
            target.hit(unit.getAttack());
            if (target.getHealth() <= 0) {
                if (units.indexOf(target) < i) {
                    i--;
                }
                units.remove(target);
            }
        }
        return true;
    }
    
    private Comparator<MPoint> closest(Map<MPoint, Integer> dist) {
        return (o1, o2) -> {
            int d1 = dist.getOrDefault(o1, -1);
            int d2 = dist.getOrDefault(o2, -1);
            if (d1 == -1) {
                return 1;
            }
            if (d2 == -1) {
                return -1;
            }
            if (d1 != d2) {
                return Integer.compare(d1, d2);
            }
            return o1.compareTo(o2);
        };
    }
    
    private boolean canMove(MPoint point) {
        return isOpen(point) && !findUnit(point).isPresent();
    }
    
    private boolean isOpen(MPoint point) {
        return map[point.getY()][point.getX()] == '.';
    }
    
    private Optional<Unit> findUnit(MPoint point) {
        return units.stream().filter(unit -> unit.getPoint().equals(point)).findFirst();
    }
    
    private Optional<Unit> enemyInRange(Unit unit) {
        return Stream.of(unit.getPoint().surround())
                     .map(this::findUnit)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .filter(unit::isEnemy)
                     .min(Comparing.value(Unit::getHealth).then(Unit::compareTo));
//                     .min((o1, o2) -> {
//                         int h1 = o1.getHealth();
//                         int h2 = o2.getHealth();
//                         if (h1 != h2) {
//                             return Integer.compare(h1, h2);
//                         }
//                         return o1.compareTo(o2);
//                     });
    }
    
    // Distance map of reachable spaces using flood fill.
    private Map<MPoint, Integer> getDistanceMap(MPoint mp) {
        Map<MPoint, Integer> dists = new HashMap<>();
        Deque<MPoint> queue = new ArrayDeque<>();
        queue.add(mp);
        dists.put(mp, 0);
        while (!queue.isEmpty()) {
            MPoint current = queue.pollFirst();
            Integer d = dists.get(current);
            for (MPoint point : current.surround()) {
                if (dists.keySet().stream().noneMatch(point::equals) && canMove(point)) {
                    queue.addLast(point);
                    dists.put(point, d + 1);
                }
            }
        }
        return dists;
    }
    
    private <T> List<T> intersect(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<>();
        for (T t : a) {
            for (T t1 : b) {
                if (t.equals(t1)) {
                    list.add(t);
                }
            }
        }
        return list;
    }
    
    public void part1() {
        setup();
        int rounds = 0;
        while (round()) {
            rounds++;
        }
        int health = units.stream().mapToInt(Unit::getHealth).sum();
        int result = health * rounds;
        Util.log("Elves: " + units.stream().filter(Unit::isElf).count());
        Util.log("Goblins: " + units.stream().filter(Unit::isGoblin).count());
        Util.log(result);
    }
    
    public void part2() {
        int power = 3;
        int rounds = 0;
        long elves = 0;
        do {
            setup();
            power++;
            int finalPower = power;
            elves = units.stream().filter(Unit::isElf).peek(unit -> unit.setAttack(finalPower)).count();
            rounds = 0;
            while (round()) {
                rounds++;
            }
        } while (units.stream().filter(Unit::isElf).count() != elves);
        int health = units.stream().mapToInt(Unit::getHealth).sum();
        int result = health * rounds;
        Util.log(result);
    }
    
}
