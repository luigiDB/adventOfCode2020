package computer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Status {
    private final boolean terminated;
    private final int accumulator;
    private final int instructionPointer;

    public static Status of(boolean terminated, int accumulator, int instructionPointer) {
        return new Status(terminated, accumulator, instructionPointer);
    }

    public Status(boolean terminated, int accumulator, int instructionPointer) {
        this.terminated = terminated;
        this.accumulator = accumulator;
        this.instructionPointer = instructionPointer;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public int getInstructionPointer() {
        return instructionPointer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Status status = (Status) o;
        return new EqualsBuilder()
                .append(terminated, status.terminated)
                .append(accumulator, status.accumulator)
                .append(instructionPointer, status.instructionPointer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(terminated)
                .append(accumulator)
                .append(instructionPointer)
                .toHashCode();
    }
}