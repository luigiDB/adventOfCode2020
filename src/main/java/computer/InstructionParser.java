package computer;

import computer.instruction.Acc;
import computer.instruction.Instruction;
import computer.instruction.Jmp;
import computer.instruction.Nop;

public class InstructionParser {
    public static Instruction parser(String input) {
        String[] split = input.split(" ");
        int parameter = Integer.parseInt(split[1].replace('+', ' ').trim());

        switch (split[0].trim()) {
            case "acc":
                return new Acc(parameter);
            case "jmp":
                return new Jmp(parameter);
            case "nop":
                return new Nop(parameter);
            default:
                throw new UnsupportedOperationException("Cannot parse " + split[0].trim());
        }
    }
}
