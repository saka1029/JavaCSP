package jp.saka1029.csp.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import jp.saka1029.csp.Domain;
import jp.saka1029.csp.Problem;
import jp.saka1029.csp.Solver;
import jp.saka1029.csp.Variable;

class Test数独 {

    static Logger logger = Logger.getLogger(Test数独.class.toString());

    static int 辺の長さ = 9;
    static int 小四角形の辺の長さ = 3;
    static Domain 定義域 = Domain.rangeClosed(1, 9);

    static String 名前(int r, int c) {
        return r + "@" + c;
    }

    static Variable[][] 変数定義(Problem 問題, int[][] 入力) {
        Variable[][] 変数 = new Variable[辺の長さ][辺の長さ];
        for (int r = 0; r < 辺の長さ; ++r)
            for (int c = 0; c < 辺の長さ; ++c)
                変数[r][c] = 問題.variable(名前(r, c),
                    入力[r][c] == 0 ? 定義域 : Domain.of(入力[r][c]));
        return 変数;
    }

    static List<Variable[]> 制約定義(Problem 問題, Variable[][] 変数) {
        List<Variable[]> 制約変数 = new ArrayList<>();
        for (int r = 0; r < 辺の長さ; ++r)
            制約変数.add(変数[r]);
        for (int c = 0; c < 辺の長さ; ++c) {
            Variable[] va = new Variable[辺の長さ];
            制約変数.add(va);
            for (int r = 0; r < 辺の長さ; ++r)
                va[r] = 変数[r][c];
        }
        for (int r = 0; r < 辺の長さ; r += 小四角形の辺の長さ)
            for (int c = 0; c < 辺の長さ; c += 小四角形の辺の長さ) {
                Variable[] va = new Variable[辺の長さ];
                制約変数.add(va);
                for (int i = 0, p = 0; i < 小四角形の辺の長さ; ++i)
                    for (int j = 0; j < 小四角形の辺の長さ; ++j, ++p)
                        va[p] = 変数[r + i][c + j];
            }
        for (Variable[] va : 制約変数)
            問題.allDifferent(va);
        return 制約変数;
    }

    static void 答(Variable[][] 変数, Map<Variable, Integer> 解答) {
        for (int r = 0; r < 辺の長さ; ++r) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < 辺の長さ; ++c)
                sb.append(String.format("%2d", 解答.get(変数[r][c])));
            logger.info(sb.toString());
        }
    }

    static List<Variable> 束縛順序定義(List<Variable> 変数, List<Variable[]> 制約変数) {
        Set<Variable> 束縛順序 = new LinkedHashSet<>();
        for (Variable v : 変数)
            if (v.domain.size() == 1)
                束縛順序.add(v);
        Collections.sort(制約変数,
            Comparator.comparingInt(a -> Arrays.stream(a).mapToInt(v -> v.domain.size()).sum()));
        for (Variable[] a : 制約変数)
            for (Variable v : a)
                束縛順序.add(v);
        return new ArrayList<>(束縛順序);
    }

    static void 数独(int[][] 入力) {
        Problem 問題 = new Problem();
        Variable[][] 変数 = 変数定義(問題, 入力);
        List<Variable[]> 制約変数 = 制約定義(問題, 変数);
        List<Variable> 束縛順序 = 束縛順序定義(問題.variables, 制約変数);
        Solver 解決 = new Solver();
        解決.solve(問題, 束縛順序, m -> 答(変数, m));
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

    @Test
    void testGood_at_Sudoku_Heres_some_youll_never_complete() {
        // http://theconversation.com/good-at-sudoku-heres-some-youll-never-complete-5234
        int[][] 入力 = {
            { 0, 0, 0, 7, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 4, 3, 0, 2, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 6 },
            { 0, 0, 0, 5, 0, 9, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 4, 1, 8 },
            { 0, 0, 0, 0, 8, 1, 0, 0, 0 },
            { 0, 0, 2, 0, 0, 0, 0, 5, 0 },
            { 0, 4, 0, 0, 0, 0, 3, 0, 0 },
        };
        logger.info("Good at Sudoku Heres some youll never complete");
        数独(入力);
    }

}
