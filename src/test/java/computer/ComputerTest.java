package computer;

import computer.instruction.Instruction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static utilities.AOCTestFramework.parseInput;

public class ComputerTest {

    @Test
    public void es1Test() {
        String testInput = "nop +0\n" +
                "acc +1\n" +
                "jmp +4\n" +
                "acc +3\n" +
                "jmp -3\n" +
                "acc -99\n" +
                "acc +1\n" +
                "jmp -4\n" +
                "acc +6";
        Computer computer = new Computer(parseInput(testInput, InstructionParser::parser).collect(Collectors.toList()));
        Status result = computer.run();
        assertFalse(result.isTerminated());
        assertEquals(5, result.getAccumulator());
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
        Computer computer = new Computer(parseInput(terminatingProgram, InstructionParser::parser).collect(Collectors.toList()));
        Status result = computer.run();
        assertTrue(result.isTerminated());
        assertEquals(8, result.getAccumulator());
    }

    @Test
    public void thatCopyConstructorWorks() {
        String instructions = "nop +0\n" +
                "acc +1\n" +
                "jmp +4\n";
        List<Instruction> collect = parseInput(instructions, InstructionParser::parser).collect(Collectors.toList());
        List<Instruction> clone = new ArrayList<>();
        for(Instruction i: collect) {
            clone.add(i.copy());
        }

        for (int i = 0; i < collect.size(); i++) {
            assertNotEquals(collect.get(i), clone.get(i));
            Status originalRun = Computer.of(Collections.singletonList(collect.get(i))).run();
            Status cloneRun = Computer.of(Collections.singletonList(clone.get(i))).run();
            assertEquals(originalRun, cloneRun);
        }
        Status originalRun = Computer.of(collect).run();
        Status cloneRun = Computer.of(clone).run();
        assertEquals(originalRun, cloneRun);
    }
}