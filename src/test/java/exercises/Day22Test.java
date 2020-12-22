package exercises;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day22Test {
    private String testInput = "Player 1:\n" +
            "9\n" +
            "2\n" +
            "6\n" +
            "3\n" +
            "1\n" +
            "\n" +
            "Player 2:\n" +
            "5\n" +
            "8\n" +
            "4\n" +
            "7\n" +
            "10";

    @Test
    public void es1Test() {
        Assert.assertEquals(306, Day22.es1(parseStrings(testInput, "\n\n")));
    }

    @Test
    public void es1() {
        long x = Day22.es1(parseStrings(getTestInput("22"), "\n\n"));
        assertEquals(33473, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals(291, Day22.es2(parseStrings(testInput, "\n\n")));
    }

    @Test
    public void es2() {
        long x = Day22.es2(parseStrings(getTestInput("22"), "\n\n"));
//        assertEquals(1, x);
        System.out.println(x);
    }
}