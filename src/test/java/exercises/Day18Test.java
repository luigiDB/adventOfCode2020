package exercises;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day18Test {
    private String testInput =
            "2 * 3 + (4 * 5)\n" +
            "5 + (8 * 3 + 9 + 3 * 4 * 3)\n" +
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))\n" +
            "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2";

    @Test
    public void es1Test() {
        Assert.assertEquals(26335, Day18.es1(parseStrings(testInput)));
    }

    @Test
    public void es1() {
        long x = Day18.es1(parseStrings(getTestInput("18")));
        assertEquals(1451467526514L, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        String input =
                "1 + (2 * 3) + (4 * (5 + 6))\n" +
                "2 * 3 + (4 * 5)\n" +
                "5 + (8 * 3 + 9 + 3 * 4 * 3)\n" +
                "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))\n" +
                "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2";
        assertEquals(693942L, Day18.es2(parseStrings(input)));
    }

    @Test
    public void es2() {
        long x = Day18.es2(parseStrings(getTestInput("18")));
        assertEquals(224973686321527L, x);
        System.out.println(x);
    }
}