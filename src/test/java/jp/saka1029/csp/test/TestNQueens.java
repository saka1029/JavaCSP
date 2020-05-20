package jp.saka1029.csp.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import jp.saka1029.csp.Domain;
import jp.saka1029.csp.Problem;
import jp.saka1029.csp.Solver;
import jp.saka1029.csp.Variable;

class TestNQueens {

    static Logger logger = Logger.getLogger(TestNQueens.class.getName());

    static int nQueens(final int n) {
        long start = System.currentTimeMillis();
        Problem problem = new Problem();
        Domain domain = Domain.range(0, n);
        Variable[] rows = IntStream.range(0, n)
            .mapToObj(i -> problem.variable("R" + i, domain))
            .toArray(Variable[]::new);
        for (int i = 0; i < n; ++i)
            for (int j = i + 1; j < n; ++j) {
                int distance = j - i;
                problem.constraint(
                   (x, y) -> x != y && Math.abs(x - y) != distance, rows[i], rows[j]);
            }
        Solver solver = new Solver();
//        logger.info(n + "-Queens");
//        int[] count = {0};
//        int answers = solver.solve(problem, m -> {
//            logger.info("** answer " + (++count[0]));
//            for (int i = 0; i < n; ++i) {
//                int c = m.get(rows[i]);
//                logger.info(".".repeat(c) + "X" + ".".repeat(n - c -1));
//            }
//        });
        int[] answers = {0};
        solver.solve(problem, m -> ++answers[0]);
        logger.info("n=" + n + " : answers=" + answers[0]
            + " : elapse=" + (System.currentTimeMillis() - start) + "ms.");
        return answers[0];
    }

    @Test
    void test() {
        assertEquals(1, nQueens(1));
        assertEquals(0, nQueens(2));
        assertEquals(0, nQueens(3));
        assertEquals(2, nQueens(4));
        assertEquals(10, nQueens(5));
        assertEquals(4, nQueens(6));
        assertEquals(40, nQueens(7));
        assertEquals(92, nQueens(8));
        assertEquals(352, nQueens(9));
        assertEquals(724, nQueens(10));
    }

}
