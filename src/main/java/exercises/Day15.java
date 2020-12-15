package exercises;

import org.jooq.lambda.Seq;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day15 {
    public static long es1(Stream<String> input) {
        Map<Long, Long> memory = initMemory(input);
        return game(memory, 2020);
    }

    public static long es2(Stream<String> input) {
        Map<Long, Long> memory = initMemory(input);
        return game(memory, 30000000);
    }

    private static long game(Map<Long, Long> memory, long turns) {
        long lastSpoken = 0;
        for (long turn = memory.size(); turn < turns - 1; turn++) {
            if (memory.containsKey(lastSpoken)) {
                long newOccurrence = turn - memory.get(lastSpoken);
                memory.put(lastSpoken, turn);
                lastSpoken = newOccurrence;
            } else {
                memory.put(lastSpoken, turn);
                lastSpoken = 0;
            }
//            System.out.println(lastSpoken);
        }
        return lastSpoken;
    }

    private static Map<Long, Long> initMemory(Stream<String> input) {
        Map<Long, Long> memory = new HashMap<>();

        Seq.seq(input
                .map(Long::valueOf))
                .zipWithIndex()
                .forEach(tuple -> {
                    memory.put(tuple.v1, tuple.v2);
                });
        return memory;
    }
}
