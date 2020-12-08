package computer.instruction;

import computer.Computer;

import java.util.List;

public interface Instruction {
    void apply(Computer state);
    Instruction copy();
    List<Integer> parameters();
}

