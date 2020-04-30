package jp.saka1029.csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Variable {

    public final String name;
    public final Domain domain;

    private final List<Constraint> _constraints = new ArrayList<Constraint>();
    public final List<Constraint> constraints = Collections.unmodifiableList(_constraints);

    Variable(String name, Domain domain) {
        this.name = name;
        this.domain = domain;
    }

    void add(Constraint constraint) {
        this._constraints.add(constraint);
    }

    @Override
    public String toString() {
        return name;
    }

}
