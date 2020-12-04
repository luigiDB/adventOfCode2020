import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Day3Test {
    private List<String> testInput = Stream.of(
            "..##.......",
            "#...#...#..",
            ".#....#..#.",
            "..#.#...#.#",
            ".#...##..#.",
            "..#.##.....",
            ".#.#.#....#",
            ".#........#",
            "#.##...#...",
            "#...##....#",
            ".#..#...#.#"
    ).collect(Collectors.toList());

    @Test
    public void es1Test() {
        assertEquals(7, Day3.es1(AOCTestFramework.parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es1() {
        Character[][] input = AOCTestFramework.parseMatrix("3", Character.class, Character::valueOf);
        System.out.println(Day3.es1(input));
    }

    @Test
    public void es2Test() {
        assertEquals(BigInteger.valueOf(336), Day3.es2(AOCTestFramework.parseMatrix(testInput, Character.class, Character::valueOf)));
    }

    @Test
    public void es2() {
        Character[][] input = AOCTestFramework.parseMatrix("3", Character.class, Character::valueOf);
        System.out.println(Day3.es2(input));
    }
}