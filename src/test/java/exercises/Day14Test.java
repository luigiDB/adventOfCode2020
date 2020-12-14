package exercises;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day14Test {

    @Test
    public void es1Test() {
        String testInput = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                "mem[8] = 11\n" +
                "mem[7] = 101\n" +
                "mem[8] = 0";
        Assert.assertEquals(BigInteger.valueOf(165), Day14.es1(parseStrings(testInput)));
    }

    @Test
    public void es1() {
        BigInteger x = Day14.es1(parseStrings(getTestInput("14")));
        assertEquals(new BigInteger("8570568288597"), x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        String input = "mask = 000000000000000000000000000000X1001X\n" +
                "mem[42] = 100\n" +
                "mask = 00000000000000000000000000000000X0XX\n" +
                "mem[26] = 1";
        assertEquals(BigInteger.valueOf(208), Day14.es2(parseStrings(input)));
    }

    @Test
    public void es2() {
        BigInteger x = Day14.es2(parseStrings(getTestInput("14")));
        assertEquals(new BigInteger("3289441921203"), x);
        System.out.println(x);
    }

}