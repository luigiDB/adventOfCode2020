package computer.instruction;

import computer.Computer;

public class Jmp implements Instruction {
    private int value;

    public Jmp(int value) {
        this.value = value;
    }

    @Override
    public void apply(Computer state) {
        state.updateInstructionPointer(value);
    }

    @Override
    public Instruction copy() {
        return new Jmp(this.value);
    }
}
