package jp.saka1029.csp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Solver {

    static Logger logger = Logger.getLogger(Solver.class.getName());

    static List<List<Constraint>> constraintOrder(final List<Variable> bindingOrder, final List<Constraint> constraints) {
        final int variableSize = bindingOrder.size();
        final int constraintSize = constraints.size();
        final List<List<Constraint>> result = new ArrayList<>(variableSize);
        final Set<Constraint> done = new HashSet<>(constraintSize);
        final Set<Variable> bound = new HashSet<>(variableSize);
        for (Variable v : bindingOrder) {
            bound.add(v);
            final List<Constraint> list = new ArrayList<>();
            result.add(list);
            for (Constraint c : constraints)
                if (!done.contains(c) && bound.containsAll(c.variables)) {
                    list.add(c);
                    done.add(c);
                }
        }
        return result;
    }

    public void solve(final Problem problem, final Answer answer) {
        final int variableSize = problem.variables.size();
        final List<Variable> bindingOrder = problem.variables;
        final List<List<Constraint>> constraintOrder = constraintOrder(bindingOrder, problem.constraints);
        final int[] arguments = new int[variableSize];
        final Map<Variable, Integer> result = new LinkedHashMap<>(variableSize);

        new Object() {

            boolean test(final int i) {
                for (Constraint c : constraintOrder.get(i)) {
                    int p = 0;
                    for (Variable v : c.variables)
                        arguments[p++] = result.get(v);
                    if (!c.predicate.test(arguments))
                        return false;
                }
                return true;
            }

            void solve(final int i) {
                if (i >= variableSize)
                    answer.answer(result);
                else {
                    final Variable v = bindingOrder.get(i);
                    final Domain d = v.domain;
                    for (int value : d) {
                        result.put(v, value);
                        if (test(i)) solve(i + 1);
                    }
                }
            }

        }.solve(0);
    }

}
