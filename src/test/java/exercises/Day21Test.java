package exercises;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;

public class Day21Test {
    private String testInput = "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n" +
            "trh fvjkl sbzzf mxmxvkd (contains dairy)\n" +
            "sqjhc fvjkl (contains soy)\n" +
            "sqjhc mxmxvkd sbzzf (contains fish)";

    @Test
    public void es1Test() {
        Assert.assertEquals(5, Day21.es1(parseStrings(testInput)));
    }

    @Test
    public void es1() {
        long x = Day21.es1(parseStrings(getTestInput("21")));
        assertEquals(1930, x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        assertEquals("mxmxvkd,sqjhc,fvjkl", Day21.es2(parseStrings(testInput)));
    }

    @Test
    public void es2() {
        String x = Day21.es2(parseStrings(getTestInput("21")));
//        assertEquals(1, x);
        System.out.println(x);
    }
}