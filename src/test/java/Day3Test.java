import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class Day3Test {
    private String testInput =
            "..##.......\n" +
                    "#...#...#..\n" +
                    ".#....#..#.\n" +
                    "..#.#...#.#\n" +
                    ".#...##..#.\n" +
                    "..#.##.....\n" +
                    ".#.#.#....#\n" +
                    ".#........#\n" +
                    "#.##...#...\n" +
                    "#...##....#\n" +
                    ".#..#...#.#\n";

    @Test
    public void es1Test() {
        assertEquals(7, Day3.es1(AOCTestFramework.parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es1() {
        Character[][] input = AOCTestFramework.parseMatrix(AOCTestFramework.getTestInput("3"), Character.class, Character::valueOf);
        System.out.println(Day3.es1(input));
    }

    @Test
    public void es2Test() {
        assertEquals(BigInteger.valueOf(336), Day3.es2(AOCTestFramework.parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es2() {
        Character[][] input = AOCTestFramework.parseMatrix(AOCTestFramework.getTestInput("3"), Character.class, Character::valueOf);
        System.out.println(Day3.es2(input));
    }
}