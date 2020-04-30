package jp.saka1029.csp;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Domain {

    public final List<Integer> elements;

    Domain(List<Integer> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

    public static Domain of(int... elements) {
        return new Domain(IntStream.of(elements).boxed().collect(toList()));
    }

    @Override
    public String toString() {
        return elements.toString();
    }

}
