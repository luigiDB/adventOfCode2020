package exercises;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day16Test {

    @Test
    public void es1Test() {
        Assert.assertEquals(71, Day16.es1(parseStrings("class: 1-3 or 5-7\n" +
                "row: 6-11 or 33-44\n" +
                "seat: 13-40 or 45-50\n" +
                "\n" +
                "your ticket:\n" +
                "7,1,14\n" +
                "\n" +
                "nearby tickets:\n" +
                "7,3,47\n" +
                "40,4,50\n" +
                "55,2,20\n" +
                "38,6,12", "\n\n")));
    }

    @Test
    public void es1() {
        long x = Day16.es1(parseStrings(getTestInput("16"), "\n\n"));
        assertEquals(21980, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        String input = "class: 0-1 or 4-19\n" +
                "row: 0-5 or 8-19\n" +
                "departure: 0-13 or 16-19\n" +
                "\n" +
                "your ticket:\n" +
                "11,12,13\n" +
                "\n" +
                "nearby tickets:\n" +
                "3,9,18\n" +
                "15,1,5\n" +
                "5,14,9";
        assertEquals(13, Day16.es2(parseStrings(input, "\n\n")));
    }

    @Test
    public void es2() {
        long x = Day16.es2(parseStrings(getTestInput("16"), "\n\n"));
        assertEquals(1439429522627L, x);
        System.out.println(x);
    }
}