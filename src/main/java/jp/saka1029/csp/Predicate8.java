package jp.saka1029.csp;

@FunctionalInterface
public interface Predicate8 extends Predicate {

    default boolean test(int... values) {
        return test(values[0], values[1], values[2], values[3],
            values[4], values[5], values[6], values[7]);
    }

    boolean test(int a, int b, int c, int d, int e, int f, int g, int h);

}
