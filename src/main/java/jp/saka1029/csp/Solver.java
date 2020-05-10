package jp.saka1029.csp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Solver {

    static final Logger logger = Logger.getLogger(Solver.class.getName());

    static List<List<Constraint>> constraintOrder(List<Variable> bindingOrder, List<Constraint> constraints) {
        int variableSize = bindingOrder.size();
        int constraintSize = constraints.size();
        List<List<Constraint>> result = new ArrayList<>(variableSize);
        Set<Constraint> done = new HashSet<>(constraintSize);
        Set<Variable> bound = new HashSet<>(variableSize);
        for (Variable v : bindingOrder) {
            bound.add(v);
            List<Constraint> list = new ArrayList<>();
            result.add(list);
            for (Constraint c : constraints)
                if (!done.contains(c) && bound.containsAll(c.variables)) {
                    list.add(c);
                    done.add(c);
                }
        }
        return result;
    }

    public void solve(Problem problem, List<Variable> bindingOrder, Answer answer) {
        int variableSize = problem.variables.size();
        List<List<Constraint>> constraintOrder = constraintOrder(bindingOrder, problem.constraints);
        int[] arguments = new int[variableSize];
        Map<Variable, Integer> result = new LinkedHashMap<>(variableSize);

        new Object() {

            boolean test(int i) {
                for (Constraint c : constraintOrder.get(i)) {
                    int p = 0;
                    for (Variable v : c.variables)
                        arguments[p++] = result.get(v);
                    if (!c.predicate.test(arguments))
                        return false;
                }
                return true;
            }

            void solve(int i) {
                if (i >= variableSize)
                    answer.answer(result);
                else {
                    Variable v = bindingOrder.get(i);
                    Domain d = v.domain;
                    for (int value : d) {
                        result.put(v, value);
                        if (test(i))
                            solve(i + 1);
                    }
                }
            }

        }.solve(0);
    }

    public void solve(Problem problem, Answer answer) {
        solve(problem, problem.variables, answer);
    }

}
