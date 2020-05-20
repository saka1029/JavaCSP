package jp.saka1029.csp;

@FunctionalInterface
public interface Predicate3 extends Predicate {

    default boolean test(int... values) {
        return test(values[0], values[1], values[2]);
    }

    boolean test(int a, int b, int c);

}
