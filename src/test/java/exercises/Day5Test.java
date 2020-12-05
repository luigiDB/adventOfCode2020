package exercises;

import org.junit.Test;

import java.util.OptionalInt;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.*;

public class Day5Test {

    @Test
    public void es1BaseTest() {
        OptionalInt result = Day5.es1(Stream.of("FBFBBFFRLR"));
        assertEquals(357, result.orElse(-1));
    }


    @Test
    public void es1Test() {
        String testInput = "BFFFBBFRRR\n" +
                "FFFBBBFRRR\n" +
                "BBFFBBFRLL";
        OptionalInt result = Day5.es1(parseStrings(testInput));
        assertEquals(820, result.orElse(-1));
    }

    @Test
    public void es1() {
        Stream<String> input = parseStrings(getTestInput("5"));
        OptionalInt result = Day5.es1(input);
        System.out.println(result.orElse(-1));
    }

    @Test
    public void es2() {
        Stream<String> input = parseStrings(getTestInput("5"));
        System.out.println(Day5.es2(input));
    }
}