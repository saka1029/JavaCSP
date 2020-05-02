package jp.saka1029.csp.test;

import java.util.logging.Logger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import jp.saka1029.csp.Domain;
import jp.saka1029.csp.Predicate;
import jp.saka1029.csp.Problem;
import jp.saka1029.csp.Solver;
import jp.saka1029.csp.Variable;

class TestSendMoreMoney {

    static Logger logger = Logger.getLogger(TestSendMoreMoney.class.getName());

    static int number(int... digits) {
        return IntStream.of(digits).reduce(0, (t, d) -> t * 10 + d);
    }

	@Test
    public void test単一制約() {
        Problem problem = new Problem();
        Domain first = Domain.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Domain rest = Domain.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Variable S = problem.variable("S", first);
        Variable E = problem.variable("E", rest);
        Variable N = problem.variable("N", rest);
        Variable D = problem.variable("D", rest);
        Variable M = problem.variable("M", first);
        Variable O = problem.variable("O", rest);
        Variable R = problem.variable("R", rest);
        Variable Y = problem.variable("Y", rest);
        Variable[] variables = {S, E, N, D, M, O, R, Y};
        for (int i = 0, size =variables.length; i < size; ++i)
            for (int j = i + 1; j < size; ++j)
                problem.constraint(a -> a[0] != a[1], variables[i], variables[j]);
        problem.constraint(a -> number(a[0], a[1], a[2], a[3]) + number(a[4], a[5], a[6], a[1])
            == number(a[4], a[5], a[2], a[1], a[7]), variables);
	    Solver solver = new Solver();
        solver.solve(problem, m -> logger.info(m.toString()));
    }

    @Test
    public void test桁ごとの制約() {
        Domain zero = Domain.of(0);
        Domain first = Domain.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Domain rest = Domain.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Domain carry = Domain.of(0, 1);
        Problem problem = new Problem();
        Variable Z = problem.variable("Z", zero);
        Variable C1 = problem.variable("C1", carry);
        Variable C2 = problem.variable("C2", carry);
        Variable C3 = problem.variable("C3", carry);
        Variable C4 = problem.variable("C4", carry);
        Variable S = problem.variable("S", first);
        Variable E = problem.variable("E", rest);
        Variable N = problem.variable("N", rest);
        Variable D = problem.variable("D", rest);
        Variable M = problem.variable("M", first);
        Variable O = problem.variable("O", rest);
        Variable R = problem.variable("R", rest);
        Variable Y = problem.variable("Y", rest);
        Variable[] variables = {S, E, N, D, M, O, R, Y};
        for (int i = 0, size =variables.length; i < size; ++i)
            for (int j = i + 1; j < size; ++j)
                problem.constraint(a -> a[0] != a[1], variables[i], variables[j]);
        //  C4 C3 C2 C1  Z
        //   Z  S  E  N  D
        // + Z  M  O  R  E
        // ---------------
        //   M  O  N  E  Y
        Predicate digitPredicate = a -> a[0] + a[1] + a[2] == a[3] + a[4] * 10;
        problem.constraint(digitPredicate, Z, D, E, Y, C1);
        problem.constraint(digitPredicate, C1, N, R, E, C2);
        problem.constraint(digitPredicate, C2, E, O, N, C3);
        problem.constraint(digitPredicate, C3, S, M, O, C4);
        problem.constraint(digitPredicate, C4, Z, Z, M, Z);
	    Solver solver = new Solver();
        solver.solve(problem, m -> logger.info(m.toString()));
    }

}