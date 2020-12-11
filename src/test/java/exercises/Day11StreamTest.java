package exercises;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseMatrix;

public class Day11StreamTest {
    private String testInput = "L.LL.LL.LL\n" +
            "LLLLLLL.LL\n" +
            "L.L.L..L..\n" +
            "LLLL.LL.LL\n" +
            "L.LL.LL.LL\n" +
            "L.LLLLL.LL\n" +
            "..L.L.....\n" +
            "LLLLLLLLLL\n" +
            "L.LLLLLL.L\n" +
            "L.LLLLL.LL";

    @Test
    public void es1Test() {
        assertEquals(37, Day11Stream.es1(parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es1() {
        Character[][] input = parseMatrix(getTestInput("11"), Character.class, Character::valueOf);
        int x = Day11Stream.es1(input);
        assertEquals(2277, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals(26, Day11Stream.es2(parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es2() {
        Character[][] input = parseMatrix(getTestInput("11"), Character.class, Character::valueOf);
        int x = Day11Stream.es2(input);
        assertEquals(2066, x);
        System.out.println(x);
    }
}