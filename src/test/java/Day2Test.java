import org.junit.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class Day2Test {

    private Stream<String> testInput = Stream.of(
            "1-3 a: abcde",
            "1-3 b: cdefg",
            "2-9 c: ccccccccc");

    @Test
    public void es1Test() {
        assertEquals(2, Day2.es1(testInput));
    }

    @Test
    public void es1() throws IOException {
        System.out.println(Day2.es1(AOCTestFramework.parseStrings("2")));
    }

    @Test
    public void es2Test() {
        assertEquals(1, Day2.es2(testInput));
    }

    @Test
    public void es2() throws IOException {
        System.out.println(Day2.es2(AOCTestFramework.parseStrings("2")));
    }
}