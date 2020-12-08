package exercises;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day6Test {
    private String testInput = "abc\n" +
            "\n" +
            "a\n" +
            "b\n" +
            "c\n" +
            "\n" +
            "ab\n" +
            "ac\n" +
            "\n" +
            "a\n" +
            "a\n" +
            "a\n" +
            "a\n" +
            "\n" +
            "b";

    @Test
    public void es1Test() {
        assertEquals(11, Day6.es1(parseStrings(testInput, "\n\n")));
    }

    @Test
    public void es1() {
        Stream<String> input = parseStrings(getTestInput("6"), "\n\n");
        System.out.println(Day6.es1(input));
    }

    @Test
    public void es2Test() {
        assertEquals(6L, (long) Day6.es2(parseStrings(testInput, "\n\n")));
    }

    @Test
    public void es2() {
        Stream<String> input = parseStrings(getTestInput("6"), "\n\n");
        System.out.println(Day6.es2(input));
    }
}