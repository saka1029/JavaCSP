package jp.saka1029.csp;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Domain extends AbstractList<Integer> {

    private final int[] elements;

    private Domain(int... elements) {
        this.elements = elements;
    }

    public static Domain of(int... elements) {
        return new Domain(Arrays.copyOf(elements, elements.length));
    }

    public static Domain range(int startInclusive, int endExclusive) {
        return new Domain(IntStream.range(startInclusive, endExclusive).toArray());
    }

    public static Domain rangeClosed(int startInclusive, int endInclusive) {
        return range(startInclusive, endInclusive + 1);
    }

    @Override public Integer get(int index) { return elements[index]; }
    @Override public int size() { return elements.length; }
}
