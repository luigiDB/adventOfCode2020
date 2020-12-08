package computer.instruction;

import computer.Computer;

public class Nop implements Instruction {

    @Override
    public void apply(Computer state) {
        state.updateInstructionPointer(1);
    }

    @Override
    public Instruction copy() {
        return new Nop();
    }
}
