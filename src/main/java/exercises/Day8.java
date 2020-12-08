package exercises;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {
    public static int es1(Stream<Instruction> input) {
        List<Instruction> instructions = input.collect(Collectors.toList());
        return driver(instructions).getRight();
    }

    public static int es2(Stream<Instruction> input) {
        List<Instruction> instructions = input.collect(Collectors.toList());
        for (int i = 0; i < instructions.size(); i++) {
            Instruction current = instructions.get(i);
            if(current.getCommand().equals(Command.JMP)) {
                instructions.set(i, Instruction.of(Command.NOP, current.getArgument()));
                Pair<Boolean, Integer> result = driver(instructions);
                if (result.getLeft())
                    return result.getRight();
                instructions.set(i, current);
            } else if(current.getCommand().equals(Command.NOP)) {
                instructions.set(i, Instruction.of(Command.JMP, current.getArgument()));
                Pair<Boolean, Integer> result = driver(instructions);
                if (result.getLeft())
                    return result.getRight();
                instructions.set(i, current);
            }
        }
        return 0;
    }

    private static Pair<Boolean, Integer> driver(List<Instruction> instructions) {
        boolean[] dirty = new boolean[instructions.size()];
        int accumulator = 0;
        int instructionCounter = 0;
        while (instructionCounter < instructions.size() && !dirty[instructionCounter]) {
            Instruction current = instructions.get(instructionCounter);
            dirty[instructionCounter] = true;
            switch (current.command) {
                case ACC:
                    accumulator += current.getArgument();
                case NOP:
                    instructionCounter++;
                    break;
                case JMP:
                    instructionCounter += current.getArgument();
                    break;
            }

        }
        if (instructionCounter >= instructions.size())
            return Pair.of(true, accumulator);
        else
            return Pair.of(false, accumulator);
    }

    public static Instruction parser(String input) {
        String[] split = input.split(" ");

        Command cmd = null;
        switch (split[0].trim()) {
            case "acc":
                cmd = Command.ACC;
                break;
            case "jmp":
                cmd = Command.JMP;
                break;
            case "nop":
                cmd = Command.NOP;
                break;
        }

        return Instruction.of(cmd, Integer.parseInt(split[1].replace('+', ' ').trim()));
    }

    public static class Instruction {
        private final Command command;

        private final int argument;

        public static Instruction of(Command command, int argument) {
            return new Instruction(command, argument);
        }

        private Instruction(Command command, int argument) {
            this.command = command;
            this.argument = argument;
        }

        public Command getCommand() {
            return command;
        }

        public int getArgument() {
            return argument;
        }
    }

    public enum Command {
        ACC, JMP, NOP
    }
}