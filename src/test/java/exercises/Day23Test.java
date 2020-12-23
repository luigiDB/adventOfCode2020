package exercises;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day23Test {

    @Test
    public void es1Test() {
        Assert.assertEquals("92658374", Day23.es1("389125467", 10));
    }

    @Test
    public void es1Test2() {
        Assert.assertEquals("67384529", Day23.es1("389125467", 100));
    }

    @Test
    public void es1() {
        String x = Day23.es1("327465189", 100);
        assertEquals("82934675", x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals(149245887792L, Day23.es2("389125467", 10000000, 1000000));
    }

    @Test
    public void es2() {
        long x = Day23.es2("327465189", 10000000, 1000000);
        assertEquals(474600314018L, x);
        System.out.println(x);
    }
}