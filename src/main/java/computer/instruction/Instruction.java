package computer.instruction;

import computer.Computer;

public interface Instruction {
    void apply(Computer state);
    Instruction copy();
}

