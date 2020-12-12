package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Test;
import utilities.AOCTestFramework;

import java.util.function.Function;
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
        Stream<Tuple2<Day12.Movement, Integer>> parseInput = parseInput(testInput, parser());
        assertEquals(25, Day12.es1(parseInput));
    }

    @Test
    public void es1() {
        int x = Day12.es1(parseInput(getTestInput("12"), parser()));
        assertEquals(1032, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        Stream<Tuple2<Day12.Movement, Integer>> parseInput = parseInput(testInput, parser());
        assertEquals(286, Day12.es2(parseInput));
    }

    @Test
    public void es2() {
        int x = Day12.es2(parseInput(getTestInput("12"), parser()));
        assertEquals(156735, x);
        System.out.println(x);
    }

    private Function<String, Tuple2<Day12.Movement, Integer>> parser() {
        return s -> {
            Day12.Movement movement = Day12.Movement.valueOf(s.substring(0, 1));
            Integer amuunt = Integer.valueOf(s.substring(1));
            return Tuple.tuple(movement, amuunt);
        };
    }
}