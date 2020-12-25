package exercises;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.*;

public class Day25Test {
    private String testInput = "5764801\n" +
            "17807724";

    @Test
    public void es1Test() {
        Assert.assertEquals(BigInteger.valueOf(14897079), Day25.es1(parseBigInteger(testInput)));
    }

    @Test
    public void es1() {
        BigInteger x = Day25.es1(parseBigInteger(getTestInput("25")));
        assertEquals(BigInteger.valueOf(4968512), x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals(BigInteger.valueOf(0), Day25.es2(parseBigInteger(testInput)));
    }

    @Test
    public void es2() {
        BigInteger x = Day25.es2(parseBigInteger(getTestInput("25")));
//        assertEquals(1, x);
        System.out.println(x);
    }
}