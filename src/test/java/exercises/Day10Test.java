package exercises;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseInteger;

public class Day10Test {
    private String simpleInput = "16\n" +
            "10\n" +
            "15\n" +
            "5\n" +
            "1\n" +
            "11\n" +
            "7\n" +
            "19\n" +
            "6\n" +
            "12\n" +
            "4";

    private String mediumInput = "28\n" +
            "33\n" +
            "18\n" +
            "42\n" +
            "31\n" +
            "14\n" +
            "46\n" +
            "20\n" +
            "48\n" +
            "47\n" +
            "24\n" +
            "23\n" +
            "49\n" +
            "45\n" +
            "19\n" +
            "38\n" +
            "39\n" +
            "11\n" +
            "1\n" +
            "32\n" +
            "25\n" +
            "35\n" +
            "8\n" +
            "17\n" +
            "7\n" +
            "9\n" +
            "4\n" +
            "2\n" +
            "34\n" +
            "10\n" +
            "3";

    @Test
    public void es1SimpleTest() {
        assertEquals(35, Day10.es1(parseInteger(simpleInput)));
    }

    @Test
    public void es1MediumTest() {
        assertEquals(220, Day10.es1(parseInteger(mediumInput)));
    }

    @Test
    public void es1() {
        int x = Day10.es1(parseInteger(getTestInput("10")));
        assertEquals(1984, x);
        System.out.println(x);
    }

    @Test
    public void es2SimpleTest() {
        BigInteger dpSolution = Day10.es2Dp(parseInteger(simpleInput));
        BigInteger recursiveSolution = Day10.es2Recursive(parseInteger(simpleInput));
        assertEquals(0, dpSolution.compareTo(recursiveSolution));
        assertEquals(0, BigInteger.valueOf(8).compareTo(dpSolution));
    }

    @Test
    public void es2MediumTest() {
        BigInteger dpSolution = Day10.es2Dp(parseInteger(mediumInput));
        BigInteger recursiveSolution = Day10.es2Recursive(parseInteger(mediumInput));
        assertEquals(0, dpSolution.compareTo(recursiveSolution));
        assertEquals(0, BigInteger.valueOf(19208).compareTo(dpSolution));
    }

    @Test
    public void es2() {
        BigInteger dpSolution = Day10.es2Dp(parseInteger(getTestInput("10")));
        BigInteger recursiveSolution = Day10.es2Recursive(parseInteger(getTestInput("10")));
        assertEquals(0, dpSolution.compareTo(recursiveSolution));
        assertEquals(0, dpSolution.compareTo(new BigInteger("3543369523456")));
        System.out.println(dpSolution);
    }
}