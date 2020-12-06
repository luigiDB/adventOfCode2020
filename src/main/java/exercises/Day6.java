package exercises;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Day6 {
    public static int es1(Stream<String> input) {
        return input
                .map(Day6::stringToSet)
                .mapToInt(Set::size)
                .sum();
    }

    private static Set<Character> stringToSet(String form) {
        Set<Character> positive = new HashSet<>();
        for (int i = 0; i < form.length(); i++) {
            char c = form.charAt(i);
            if (c >= +'a' && c <= 'z')
                positive.add(c);
        }
        return positive;
    }

    public static Long es2(Stream<String> input) {
        return input
                .map(group -> {
                    Map<Character, Integer> occurrenceCounter = new HashMap<>();
                    String[] people = group.split("\n");
                    for (String form : people) {
                        Set<Character> personSet = stringToSet(form);
                        for (Character c : personSet) {
                            occurrenceCounter.compute(c, ((key, value) -> value != null ? value + 1 : 1));
                        }
                    }

                    return occurrenceCounter.entrySet().stream()
                            .filter(entry -> entry.getValue() == people.length)
                            .count();
                })
                .reduce(0L, (Long::sum));
    }
}
