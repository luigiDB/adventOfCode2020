package computer;

import computer.instruction.Instruction;

import java.util.List;

public class Computer {

    private int accumulator;
    private int instructionPointer;
    private final List<Instruction> program;
    private final boolean[] dirty;

    public static Computer of(List<Instruction> program) {
        return new Computer(program);
    }

    public Computer(List<Instruction> program) {
        accumulator = 0;
        instructionPointer = 0;
        this.program = program;
        dirty = new boolean[program.size()];
    }

    public Status run() {
        while (!dirty[instructionPointer]) {

            Instruction current = program.get(instructionPointer);
            dirty[instructionPointer] = true;
            current.apply(this);

            if (instructionPointer >= program.size())
                return Status.of(true, accumulator, instructionPointer);
        }
        return Status.of(false, accumulator, instructionPointer);
    }

    public void updateAccumulator(int value) {
        accumulator += value;
    }

    public void updateInstructionPointer(int value) {
        instructionPointer += value;
    }
}
