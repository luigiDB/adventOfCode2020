import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class Day1Test {

    private String testInput = "1721\n" +
            "979\n" +
            "366\n" +
            "299\n" +
            "675\n" +
            "1456";

    @Test
    public void es1Test() {
        List<Integer> input = AOCTestFramework.parseInteger(testInput).collect(Collectors.toList());
        assertEquals(514579, Day1.es1(input));
    }

    @Test
    public void es1() {
        List<Integer> input = AOCTestFramework.parseInteger(AOCTestFramework.getTestInput("1")).collect(Collectors.toList());
        System.out.println(Day1.es1(input));
    }

    @Test
    public void es2Test() {
        List<Integer> input = AOCTestFramework.parseInteger(testInput).collect(Collectors.toList());
        assertEquals(241861950, Day1.es2(input));
    }

    @Test
    public void es2() {
        List<Integer> input = AOCTestFramework.parseInteger(AOCTestFramework.getTestInput("1")).collect(Collectors.toList());
        System.out.println(Day1.es2(input));
    }
}