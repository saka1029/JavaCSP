package jp.saka1029.csp;

@FunctionalInterface
public interface Predicate5 extends Predicate {

    default boolean test(int... values) {
        return test(values[0], values[1], values[2], values[3], values[4]);
    }

    boolean test(int a, int b, int c, int d, int e);

}
