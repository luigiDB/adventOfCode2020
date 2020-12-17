package exercises;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseMatrix;

public class Day17MatrixTest {
    private String testInput =
            ".#.\n" +
            "..#\n" +
            "###";

    @Test
    public void es1Test() {
        Assert.assertEquals(112, Day17Matrix.es1(parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es1() {
        long x = Day17Matrix.es1(parseMatrix(getTestInput("17"), Character.class, Character::valueOf));
        assertEquals(424, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals(848, Day17Matrix.es2(parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es2() {
        long x = Day17Matrix.es2(parseMatrix(getTestInput("17"), Character.class, Character::valueOf));
        assertEquals(2460, x);
        System.out.println(x);
    }
}