package jp.saka1029.csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem {

    private List<Variable> _variables = new ArrayList<>();
    public List<Variable> variables = Collections.unmodifiableList(_variables);
    private Map<String, Variable> variableMap = new HashMap<>();

    private List<Constraint> _constraints = new ArrayList<>();
    public List<Constraint> constraints = Collections.unmodifiableList(_constraints);

    public Variable variable(String name, Domain domain) {
        if (variableMap.containsKey(name))
            throw new IllegalArgumentException("変数名が重複: " + name);
        Variable v = new Variable(name, domain);
        this._variables.add(v);
        this.variableMap.put(name, v);
        return v;
    }

    public Variable variable(String name) {
        return variableMap.get(name);
    }

    public Constraint constraint(Predicate predicate, Variable... variables) {
        Constraint c = new Constraint(predicate, variables);
        for (Variable v : variables)
            v.add(c);
        this._constraints.add(c);
        return c;
    }

    public Constraint constraint(Predicate1 predicate, Variable a) {
        return constraint((Predicate)predicate, a);
    }

    public Constraint constraint(Predicate2 predicate, Variable a, Variable b) {
        return constraint((Predicate)predicate, a, b);
    }

    public Constraint constraint(Predicate3 predicate, Variable a, Variable b, Variable c) {
        return constraint((Predicate)predicate, a, b, c);
    }

    public Constraint constraint(Predicate4 predicate, Variable a, Variable b, Variable c, Variable d) {
        return constraint((Predicate)predicate, a, b, c, d);
    }

    public Constraint constraint(Predicate5 predicate, Variable a, Variable b, Variable c, Variable d,
        Variable e) {
        return constraint((Predicate)predicate, a, b, c, d, e);
    }

    public Constraint constraint(Predicate6 predicate, Variable a, Variable b, Variable c, Variable d,
        Variable e, Variable f) {
        return constraint((Predicate)predicate, a, b, c, d, e, f);
    }

    public Constraint constraint(Predicate7 predicate, Variable a, Variable b, Variable c, Variable d,
        Variable e, Variable f, Variable g) {
        return constraint((Predicate)predicate, a, b, c, d, e, f, g);
    }

    public Constraint constraint(Predicate8 predicate, Variable a, Variable b, Variable c, Variable d,
        Variable e, Variable f, Variable g, Variable h) {
        return constraint((Predicate)predicate, a, b, c, d, e, f, g, h);
    }

    public Constraint constraint(Predicate9 predicate, Variable a, Variable b, Variable c, Variable d,
        Variable e, Variable f, Variable g, Variable h, Variable i) {
        return constraint((Predicate)predicate, a, b, c, d, e, f, g, h, i);
    }

    public Constraint constraint(Predicate10 predicate, Variable a, Variable b, Variable c, Variable d,
        Variable e, Variable f, Variable g, Variable h, Variable i, Variable j) {
        return constraint((Predicate)predicate, a, b, c, d, e, f, g, h, i, j);
    }

    public void allDifferent(Variable... variables) {
        for (int i = 0, size = variables.length; i < size; ++i)
            for (int j = i + 1; j < size; ++j)
                constraint(a -> a[0] != a[1], variables[i], variables[j]);
    }

}
