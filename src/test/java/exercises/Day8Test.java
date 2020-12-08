package exercises;

import computer.InstructionParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.*;
import static utilities.AOCTestFramework.parseStrings;

public class Day8Test {
    private String testInput = "nop +0\n" +
            "acc +1\n" +
            "jmp +4\n" +
            "acc +3\n" +
            "jmp -3\n" +
            "acc -99\n" +
            "acc +1\n" +
            "jmp -4\n" +
            "acc +6";

    @Test
    public void es1Test() {
        assertEquals(5, Day8.es1(parseInput(testInput, InstructionParser::parser)));
    }

    @Test
    public void es1() {
        int x = Day8.es1(parseInput(getTestInput("8"), InstructionParser::parser));
        assertEquals(1723, x);
        System.out.println(x);
    }

    @Test
    public void testSafeTermination() {
        String terminatingProgram = "nop +0\n" +
                "acc +1\n" +
                "jmp +4\n" +
                "acc +3\n" +
                "jmp -3\n" +
                "acc -99\n" +
                "acc +1\n" +
                "nop -4\n" +
                "acc +6";
        assertEquals(8, Day8.es1(parseInput(terminatingProgram, InstructionParser::parser)));
    }


    @Test
    public void es2Test() {
        assertEquals(8, Day8.es2(parseInput(testInput, InstructionParser::parser)));
    }

    @Test
    public void es2() {
        int x = Day8.es2(parseInput(getTestInput("8"), InstructionParser::parser));
        assertEquals(846, x);
        System.out.println(x);
    }
}