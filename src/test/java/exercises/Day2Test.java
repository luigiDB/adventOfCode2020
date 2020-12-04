package exercises;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;


public class Day2Test {

    private String testInput = "1-3 a: abcde\n" +
            "1-3 b: cdefg\n" +
            "2-9 c: ccccccccc";

    @Test
    public void es1Test() {
        assertEquals(2, Day2.es1(parseStrings(testInput)));
    }

    @Test
    public void es1() {
        System.out.println(Day2.es1(parseStrings(getTestInput("2"))));
    }

    @Test
    public void es2Test() {
        assertEquals(1, Day2.es2(parseStrings(testInput)));
    }

    @Test
    public void es2() {
        System.out.println(Day2.es2(parseStrings(getTestInput("2"))));
    }
}