package computer.instruction;

import computer.Computer;

public class Acc implements Instruction {

    private int value;

    public Acc(int value) {
        this.value = value;
    }

    @Override
    public void apply(Computer state) {
        state.updateAccumulator(value);
        state.updateInstructionPointer(1);
    }

    @Override
    public Instruction copy() {
        return new Acc(this.value);
    }
}
