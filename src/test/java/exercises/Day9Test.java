package exercises;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.*;
import static utilities.AOCTestFramework.getTestInput;

public class Day9Test {
    private String testInput = "35\n" +
            "20\n" +
            "15\n" +
            "25\n" +
            "47\n" +
            "40\n" +
            "62\n" +
            "55\n" +
            "65\n" +
            "95\n" +
            "102\n" +
            "117\n" +
            "150\n" +
            "182\n" +
            "127\n" +
            "219\n" +
            "299\n" +
            "277\n" +
            "309\n" +
            "576";

    @Test
    public void es1Test() {
        assertEquals(BigInteger.valueOf(127), Day9.es1(parseBigInteger(testInput), 5));
    }

    @Test
    public void es1() {
        BigInteger x = Day9.es1(parseBigInteger(getTestInput("9")), 25);
        assertEquals(new BigInteger("144381670"), x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals(BigInteger.valueOf(62), Day9.es2(parseBigInteger(testInput), 5));
    }

    @Test
    public void es2() {
        BigInteger x = Day9.es2(parseBigInteger(getTestInput("9")), 25);
        assertEquals(new BigInteger("20532569"), x);
        System.out.println(x);
    }
}