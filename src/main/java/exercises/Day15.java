package exercises;

import org.jooq.lambda.Seq;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 {
    public static long es1(Stream<String> input) {
        Map<Long, Long> memory = initMemmory(input);

        long lastSpoken = 0;
        lastSpoken = game(memory, lastSpoken, 2020);

        return lastSpoken;
    }

    private static Map<Long, Long> initMemmory(Stream<String> input) {
        String[] split = input
                .collect(Collectors.toList())
                .get(0)
                .split(",");

        Map<Long, Long> memory = new HashMap<>();

        Seq.seq(Arrays.stream(split)
                .map(Long::valueOf))
                .zipWithIndex()
                .forEach(tuple -> {
                    memory.put(tuple.v1, tuple.v2);
                });
        return memory;
    }

    private static long game(Map<Long, Long> memory, long lastSpoken, long turns) {
        for (long turn = memory.size(); turn < turns - 1; turn++) {
            if (memory.containsKey(lastSpoken)) {
                long newOccurence = turn - memory.get(lastSpoken);
                memory.put(lastSpoken, turn);
                lastSpoken = newOccurence;
            } else {
                memory.put(lastSpoken, turn);
                lastSpoken = 0;
            }
            System.out.println(lastSpoken);
        }
        return lastSpoken;
    }

    public static long es2(Stream<String> input) {
        Map<Long, Long> memory = initMemmory(input);

        long lastSpoken = 0;
        lastSpoken = game(memory, lastSpoken, 30000000);

        return lastSpoken;
    }
}
