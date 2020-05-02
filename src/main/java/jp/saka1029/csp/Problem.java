package jp.saka1029.csp;

import java.util.ArrayList;
import java.util.Arrays;
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
        Arrays.stream(variables).forEach(v -> v.add(c));
        this._constraints.add(c);
        return c;
    }

    public void allDifferent(Variable... variables) {
        for (int i = 0, size = variables.length; i < size; ++i)
            for (int j = i + 1; j < size; ++j)
                constraint(a -> a[0] != a[1], variables[i], variables[j]);
    }

}
