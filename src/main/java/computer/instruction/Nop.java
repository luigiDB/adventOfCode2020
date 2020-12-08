package computer.instruction;

import computer.Computer;

import java.util.List;

public class Nop implements Instruction {

    private int value;

    public Nop(int value) {
        this.value = value;
    }

    @Override
    public void apply(Computer state) {
        state.updateInstructionPointer(1);
    }

    @Override
    public Instruction copy() {
        return new Nop(this.value);
    }

    @Override
    public List<Integer> parameters() {
        return List.of(value);
    }
}
