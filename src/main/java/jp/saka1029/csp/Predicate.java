package jp.saka1029.csp;

@FunctionalInterface
public interface Predicate {

    boolean test(int... values);

}
