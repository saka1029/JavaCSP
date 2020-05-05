package jp.saka1029.csp.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import jp.saka1029.csp.Domain;
import jp.saka1029.csp.Problem;
import jp.saka1029.csp.Solver;
import jp.saka1029.csp.Variable;

class Test数独 {
    
    static Logger logger = Logger.getLogger(Test数独.class.toString());
    
    static int SIZE = 9;
    static int SSIZE = 3;
    static Domain DIGITS = Domain.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    static String 名前(int r, int c) {
        return r + "@" + c;
    }

    static Variable[][] 変数定義(Problem 問題, int[][] 入力) {
        Variable[][] 変数 = new Variable[SIZE][SIZE];
        for (int r = 0; r < SIZE; ++r)
            for (int c = 0; c < SIZE; ++c)
                変数[r][c] = 問題.variable(名前(r, c),
                    入力[r][c] == 0 ? DIGITS : Domain.of(入力[r][c]));
        return 変数;
    }

    static void 制約定義(Problem 問題, Variable[][] 変数) {
        List<Variable[]> 制約変数 = new ArrayList<>();
        for (int r = 0; r < SIZE; ++r)
            制約変数.add(変数[r]);
        for (int c = 0; c < SIZE; ++c) {
            Variable[] va = new Variable[SIZE];
            制約変数.add(va);
            for (int r = 0; r < SIZE; ++r)
                va[r] = 変数[r][c];
        }
        for (int r = 0; r < SIZE; r += SSIZE)
            for (int c = 0; c < SIZE; c += SSIZE) {
                Variable[] va = new Variable[SIZE];
                制約変数.add(va);
                for (int i = 0, p = 0; i < SSIZE; ++i)
                    for (int j = 0; j < SSIZE; ++j, ++p)
                        va[p] = 変数[r + i][c + j];
            }
        for (Variable[] va : 制約変数)
            問題.allDifferent(va);
    }

    static void 答(Variable[][] 変数, Map<Variable, Integer> 解答) {
        for (int r = 0; r < SIZE; ++r) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < SIZE; ++c)
                sb.append(String.format("%2d", 解答.get(変数[r][c])));
            logger.info(sb.toString());
        }
    }

    static void 数独(int[][] 入力) {
        Problem 問題 = new Problem();
        Variable[][] 変数 = 変数定義(問題, 入力);
        制約定義(問題, 変数);
        Solver 解決 = new Solver();
        解決.solve(問題, m -> 答(変数, m));
    }

    @Test
	void testWikipedia() {
		// Wikipedia 数独 の例題
		// https://ja.wikipedia.org/wiki/%E6%95%B0%E7%8B%AC
		int[][] 入力 = {
			{ 5, 3, 0, 0, 7, 0, 0, 0, 0 },
			{ 6, 0, 0, 1, 9, 5, 0, 0, 0 },
			{ 0, 9, 8, 0, 0, 0, 0, 6, 0 },
			{ 8, 0, 0, 0, 6, 0, 0, 0, 3 },
			{ 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 },
			{ 0, 6, 0, 0, 0, 0, 2, 8, 0 },
			{ 0, 0, 0, 4, 1, 9, 0, 0, 5 },
			{ 0, 0, 0, 0, 8, 0, 0, 7, 9 },
		};
		logger.info("test wikipedia");
		数独(入力);
	}

}
