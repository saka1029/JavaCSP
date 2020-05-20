package jp.saka1029.csp;

@FunctionalInterface
public interface Predicate1 extends Predicate {

    default boolean test(int... values) {
        return test(values[0]);
    }

    boolean test(int a);

}
