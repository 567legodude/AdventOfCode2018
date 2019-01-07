package com.ssplugins.advent.util;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

public class Util {
    
    public static void log(Object object) {
        System.out.println(object);
    }
    
    public static <T> boolean anyEqual(List<T> list, Comparator<T> comparator, BiConsumer<T, T> consumer) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (comparator.compare(list.get(i), list.get(j)) == 0) {
                    if (consumer != null) {
                        consumer.accept(list.get(i), list.get(j));
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static <T> void compareEach(List<T> list, BiConsumer<T, T> consumer) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                consumer.accept(list.get(i), list.get(j));
            }
        }
    }
    
    public static int compareReadOrder(int x, int y, int ox, int oy) {
        int c = Integer.compare(y, oy);
        if (c != 0) {
            return c;
        }
        return Integer.compare(x, ox);
    }
}
