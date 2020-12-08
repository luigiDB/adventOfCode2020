package exercises;

import computer.Computer;
import computer.Status;
import computer.instruction.Instruction;
import computer.instruction.Jmp;
import computer.instruction.Nop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {
    public static int es1(Stream<Instruction> input) {
        List<Instruction> instructions = input.collect(Collectors.toList());
        return Computer.of(instructions).run().getAccumulator();
    }

    public static int es2(Stream<Instruction> input) {
        List<Instruction> instructions = input.collect(Collectors.toList());
        for (int i = 0; i < instructions.size(); i++) {
            if(instructions.get(i) instanceof Jmp || instructions.get(i) instanceof Nop) {
                Status result = invertCommand(instructions, i);
                if (result.isTerminated())
                    return result.getAccumulator();
            }
        }
        return 0;
    }

    private static Status invertCommand(List<Instruction> instructions, int i) {
        Instruction current = instructions.get(i);
        Status result = null;
        if (current instanceof Jmp) {
            result = setCommandAtIndex(instructions, i, new Nop(current.parameters().get(0)));
        } else if (current instanceof Nop) {
            result = setCommandAtIndex(instructions, i, new Jmp(current.parameters().get(0)));
        }
        return result;
    }

    private static Status setCommandAtIndex(List<Instruction> instructions, int i, Instruction exchange) {
        List<Instruction> clone = new ArrayList<>();
        for (Instruction instruction : instructions) {
            clone.add(instruction.copy());
        }

        clone.set(i, exchange);

        return Computer.of(clone).run();
    }

}
