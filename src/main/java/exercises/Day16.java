package exercises;

import com.google.common.collect.Range;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {
    public static long es1(Stream<String> input) {
        List<String> setup = input.collect(Collectors.toList());
        Map<String, Tuple2<Range<Long>, Range<Long>>> ranges = parseRanges(setup.get(0));
        List<List<Long>> tickets = parseTickets(setup.get(2));

        return tickets.stream()
                .flatMap(Collection::stream)
                .filter(value -> {
                    return !valueInAnyRange(ranges, value);
                })
                .reduce(0L, Long::sum);

    }

    private static boolean valueInAnyRange(Map<String, Tuple2<Range<Long>, Range<Long>>> ranges, Long value) {
        for (Tuple2<Range<Long>, Range<Long>> range : ranges.values()) {
            if (range.v1.contains(value))
                return true;
            if (range.v2.contains(value))
                return true;
        }
        return false;
    }

    public static long es2(Stream<String> input) {
        List<String> setup = input.collect(Collectors.toList());
        Map<String, Tuple2<Range<Long>, Range<Long>>> ranges = parseRanges(setup.get(0));
        List<Long> myTicket = parseMyTicket(setup.get(1));
        List<List<Long>> tickets = parseTickets(setup.get(2));

        List<List<Long>> validTickets = tickets.stream()
                .filter(ticket -> {
                    for (Long value : ticket) {
                        if (!valueInAnyRange(ranges, value))
                            return false;
                    }
                    return true;
                }).collect(Collectors.toList());

        Set<String>[] plausible = new Set[ranges.size()];
        HashSet<String> originalSetOfPositions = new HashSet<>(ranges.keySet());
        for (int i = 0; i < plausible.length; i++) {
            plausible[i] = new HashSet<>(originalSetOfPositions);
        }

        while (!canTerminate(plausible)) {
            for (int i = 0; i < plausible.length; i++) {
                for (List<Long> ticket : validTickets) {
                    List<String> possible = valueInWhichRange(ranges, ticket.get(i));
                    plausible[i].retainAll(possible);
                }
            }

            for (int cycle = 0; cycle < plausible.length; cycle++) {
                List<Tuple2<String, Integer>> uniqueness = new ArrayList<>();
                for (int i = 0; i < plausible.length; i++) {
                    if (plausible[i].size() == 1) {
                        uniqueness.add(Tuple.tuple(plausible[i].iterator().next(), i));
                    }
                }

                for (Tuple2<String, Integer> unique : uniqueness) {
                    for (int i = 0; i < plausible.length; i++) {
                        if (i != unique.v2)
                            plausible[i].remove(unique.v1);
                    }
                }
            }
        }

        long result = 1;
        for (int i = 0; i < plausible.length; i++) {
            String key = plausible[i].iterator().next();
            if (key.contains("departure")) {
                result *= myTicket.get(i);
            }

        }

        return result;
    }

    private static boolean canTerminate(Set<String>[] plausible) {
        for (Set<String> strings : plausible) {
            if (strings.size() != 1)
                return false;
        }
        return true;
    }

    private static List<String> valueInWhichRange(Map<String, Tuple2<Range<Long>, Range<Long>>> ranges, Long value) {
        List<String> possible = new ArrayList<>();
        for (Map.Entry<String, Tuple2<Range<Long>, Range<Long>>> entry : ranges.entrySet()) {
            if (entry.getValue().v1.contains(value) || entry.getValue().v2.contains(value))
                possible.add(entry.getKey());
        }
        return possible;
    }

    private static Map<String, Tuple2<Range<Long>, Range<Long>>> parseRanges(String in) {
        Map<String, Tuple2<Range<Long>, Range<Long>>> map = new HashMap<>();
        Arrays.stream(in.split("\n"))
                .forEach(entry -> {
                    String[] a = entry.split(": ");
                    String[] b = a[1].split(" or ");
                    String[] range1 = b[0].split("-");
                    String[] range2 = b[1].split("-");

                    map.put(a[0], Tuple.tuple(
                            Range.closed(Long.valueOf(range1[0]), Long.valueOf(range1[1])),
                            Range.closed(Long.valueOf(range2[0]), Long.valueOf(range2[1]))
                    ));
                });
        return map;
    }

    private static List<Long> parseMyTicket(String in) {
        return parseTicket(in.split("\n")[1]);
    }

    private static List<Long> parseTicket(String in) {
        return Arrays.stream(in.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    private static List<List<Long>> parseTickets(String in) {
        return Arrays.stream(in.split("\n"))
                .skip(1)
                .map(Day16::parseTicket)
                .collect(Collectors.toList());
    }

}
