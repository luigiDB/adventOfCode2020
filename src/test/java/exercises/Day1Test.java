package exercises;

import org.junit.Test;
import utilities.AOCTestFramework;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseInteger;


public class Day1Test {

    private String testInput = "1721\n" +
            "979\n" +
            "366\n" +
            "299\n" +
            "675\n" +
            "1456";

    @Test
    public void es1Test() {
        List<Integer> input = parseInteger(testInput).collect(Collectors.toList());
        assertEquals(514579, Day1.es1(input));
    }

    @Test
    public void es1() {
        List<Integer> input = parseInteger(getTestInput("1")).collect(Collectors.toList());
        System.out.println(Day1.es1(input));
    }

    @Test
    public void es2Test() {
        List<Integer> input = parseInteger(testInput).collect(Collectors.toList());
        assertEquals(241861950, Day1.es2(input));
    }

    @Test
    public void es2() {
        List<Integer> input = parseInteger(getTestInput("1")).collect(Collectors.toList());
        System.out.println(Day1.es2(input));
    }
}