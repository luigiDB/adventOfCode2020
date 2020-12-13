package exercises;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day13Test {
    private String testInput = "939\n7,13,x,x,59,x,31,19";

    @Test
    public void es1Test() {
        Assert.assertEquals(295, Day13.es1(parseStrings(testInput)));
    }

    @Test
    public void es1() {
        int x = Day13.es1(parseStrings(getTestInput("13")));
        assertEquals(205, x);
        System.out.println(x);
        System.out.println(Long.MAX_VALUE);
    }

    @Test
    public void es2MinExample() {
        assertEquals(new BigInteger("5"), Day14.es2(parseStrings("0\n5,3,x,4"), 1));
    }

    @Test
    public void es2AdditionalExamples1() {
        assertEquals(new BigInteger("3417"), Day14.es2(parseStrings("0\n17,x,13,19"), 1));
    }

    @Test
    public void es2AdditionalExamples2() {
        assertEquals(new BigInteger("754018"), Day14.es2(parseStrings("0\n67,7,59,61"), 1));
    }

    @Test
    public void es2AdditionalExamples3() {
        assertEquals(new BigInteger("779210"), Day14.es2(parseStrings("0\n67,x,7,59,61"), 1));
    }

    @Test
    public void es2AdditionalExamples4() {
        assertEquals(new BigInteger("1261476"), Day14.es2(parseStrings("0\n67,7,x,59,61"), 1));
    }

    @Test
    public void es2AdditionalExamples5() {
        assertEquals(new BigInteger("1202161486"), Day14.es2(parseStrings("0\n1789,37,47,1889"), 1));
    }

    @Test
    public void es2Test() {
        assertEquals(new BigInteger("1068781"), Day14.es2(parseStrings(testInput), 1));
    }

    @Test
    public void es2() {
        BigInteger x = Day14.es2(parseStrings(getTestInput("13")), 1000000000000L);
//        assertEquals(1, x);
        System.out.println(x);
    }
}