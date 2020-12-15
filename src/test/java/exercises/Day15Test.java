package exercises;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day15Test {
    private final String testInput = "0,3,6";

    @Test
    public void es1Test() {
        Assert.assertEquals(436, Day15.es1(parseStrings(testInput)));
    }

    @Test
    public void es1AdditionalTests() {
        Assert.assertEquals(1, Day15.es1(parseStrings("1,3,2")));
        Assert.assertEquals(10, Day15.es1(parseStrings("2,1,3")));
        Assert.assertEquals(27, Day15.es1(parseStrings("1,2,3")));
        Assert.assertEquals(78, Day15.es1(parseStrings("2,3,1")));
        Assert.assertEquals(438, Day15.es1(parseStrings("3,2,1")));
        Assert.assertEquals(1836, Day15.es1(parseStrings("3,1,2")));
    }

    @Test
    public void es1() {
        long x = Day15.es1(parseStrings(getTestInput("15")));
        assertEquals(257, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals(175594, Day15.es2(parseStrings("0,3,6")));
        assertEquals(2578, Day15.es2(parseStrings("1,3,2")));
        assertEquals(3544142, Day15.es2(parseStrings("2,1,3")));
        assertEquals(261214, Day15.es2(parseStrings("1,2,3")));
        assertEquals(6895259, Day15.es2(parseStrings("2,3,1")));
        assertEquals(18, Day15.es2(parseStrings("3,2,1")));
        assertEquals(362, Day15.es2(parseStrings("3,1,2")));
    }

    @Test
    public void es2() {
        long x = Day15.es2(parseStrings(getTestInput("15")));
//        assertEquals(1, x);
        System.out.println(x);
    }
}