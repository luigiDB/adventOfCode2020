import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class Day1Test {

    private List<Integer> testInput = Stream.of(
            1721,
            979,
            366,
            299,
            675,
            1456).collect(Collectors.toList());

    @Test
    public void es1Test() {
        assertEquals(514579, Day1.es1(testInput));
    }

    @Test
    public void es1() throws IOException {
        List<Integer> input = AOCTestFramework.parseInteger("1").collect(Collectors.toList());
        System.out.println(Day1.es1(input));
    }

    @Test
    public void es2Test() {
        assertEquals(241861950, Day1.es2(testInput));
    }

    @Test
    public void es2() throws IOException {
        List<Integer> input = AOCTestFramework.parseInteger("1").collect(Collectors.toList());
        System.out.println(Day1.es2(input));
    }
}