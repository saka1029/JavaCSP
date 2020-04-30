package jp.saka1029.csp;

import java.util.List;

public class Constraint {

    public final Predicate predicate;
    public final List<Variable> variables;

    Constraint(Predicate predicate, Variable... variables) {
        this.predicate = predicate;
        this.variables = List.of(variables);
    }

    @Override
    public String toString() {
        return "制約" + variables;
    }
}
