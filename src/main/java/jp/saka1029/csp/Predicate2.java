package jp.saka1029.csp;

@FunctionalInterface
public interface Predicate2 extends Predicate {

    default boolean test(int... values) {
        return test(values[0], values[1]);
    }

    boolean test(int a, int b);

}
