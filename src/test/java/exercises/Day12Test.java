package exercises;

import org.jooq.lambda.tuple.Tuple2;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseInput;

public class Day12Test {
    private String testInput = "F10\n" +
            "N3\n" +
            "F7\n" +
            "R90\n" +
            "F11";

    @Test
    public void es1Test() {
        Stream<Tuple2<Day12.Movement, Integer>> parseInput = parseInput(testInput, Day12::parser);
        assertEquals(25, Day12.es1(parseInput));
    }

    @Test
    public void es1() {
        int x = Day12.es1(parseInput(getTestInput("12"), Day12::parser));
        assertEquals(1032, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        Stream<Tuple2<Day12.Movement, Integer>> parseInput = parseInput(testInput, Day12::parser);
        assertEquals(286, Day12.es2(parseInput));
    }

    @Test
    public void es2() {
        int x = Day12.es2(parseInput(getTestInput("12"), Day12::parser));
        assertEquals(156735, x);
        System.out.println(x);
    }
}