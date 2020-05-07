package jp.saka1029.csp;

import java.util.AbstractList;

public class Domain extends AbstractList<Integer> {

    private final int[] elements;

    private Domain(int... elements) {
        this.elements = elements;
    }

    public static Domain of(int... elements) {
        return new Domain(elements);
    }

    @Override public Integer get(int index) { return elements[index]; }
    @Override public int size() { return elements.length; }
<<<<<<< HEAD
=======

>>>>>>> branch 'master' of https://github.com/saka1029/JavaCSP.git
}
