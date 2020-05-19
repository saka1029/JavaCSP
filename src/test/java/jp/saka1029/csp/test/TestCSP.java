package jp.saka1029.csp.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import jp.saka1029.csp.Constraint;
import jp.saka1029.csp.Domain;
import jp.saka1029.csp.Problem;
import jp.saka1029.csp.Solver;
import jp.saka1029.csp.Variable;

class TestCSP {

    @Test
    void testAll() {
        Problem problem = new Problem();
        Domain domain = Domain.of(1, 2, 3);
        Variable A = problem.variable("A", domain);
        Variable B = problem.variable("B", domain);
        Variable C = problem.variable("C", domain);
        Constraint X = problem.constraint(a -> a[0] + a[1] == a[2], A, B, C);
        Constraint Y = problem.constraint(a -> a[0] < a[1], A, B);
        Solver solver = new Solver();
        Set<Map<Variable, Integer>> actualResult = new HashSet<>();
        solver.solve(problem, r -> System.out.println(r));
        solver.solve(problem, r -> actualResult.add(Map.copyOf(r)));
        assertEquals("[1, 2, 3]", domain.toString());
        assertEquals(A, problem.variable("A"));
        assertEquals("A", A.name);
        assertEquals("A", A.toString());
        assertEquals("制約[A, B, C]", X.toString());
        Set<Map<Variable, Integer>> expectedResult = Set.of(
            Map.of(A, 1, B, 2, C, 3));
        assertEquals(expectedResult, actualResult);
        try {
            problem.variable("A", Domain.of(0));
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

}
