package jp.saka1029.csp;

@FunctionalInterface
public interface Predicate4 extends Predicate {

    default boolean test(int... values) {
        return test(values[0], values[1], values[2], values[3]);
    }

    boolean test(int a, int b, int c, int d);

}
