package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21 {
    public static long es1(Stream<String> input) {
        List<Tuple2<List<String>, List<String>>> collect = input
                .map(s -> {
                    String[] split = s.split("\\(");
                    return Tuple.tuple(
                            Arrays.asList(split[0].split(" ")),
                            Arrays.asList(
                                    split[1].replaceAll("contains ", "").replaceAll("\\)", "").split(", ")
                            )
                    );
                })
                .collect(Collectors.toList());
        Map<String, Set<String>> allergenes = new HashMap<>();
        collect
                .stream()
                .flatMap(tuple -> tuple.v2.stream())
                .forEach(allergen -> allergenes.put(allergen, new HashSet<>()));

        for (String allergen : allergenes.keySet()) {
            Set<String> possible = allergenes.get(allergen);
            for (Tuple2<List<String>, List<String>> tuple : collect) {
                if (tuple.v2.contains(allergen)) {
                    if (possible.isEmpty()) {
                        possible.addAll(tuple.v1);
                    } else {
                        possible.retainAll(tuple.v1);
                    }
                }
            }
        }

        while (true) {
            List<String> sureAllergenes = new ArrayList<>();
            for (Map.Entry<String, Set<String>> allergen : allergenes.entrySet()) {
                Set<String> possible = allergen.getValue();
                if (possible.size() == 1) {
                    sureAllergenes.add(possible.iterator().next());
                }
            }
            if (sureAllergenes.size() == allergenes.size())
                break;
            for (String allergenToBeDeleted : sureAllergenes) {
                for (Map.Entry<String, Set<String>> allergen : allergenes.entrySet()) {
                    if (allergen.getValue().size() != 1) {
                        allergen.getValue().remove(allergenToBeDeleted);
                    }
                }
            }
        }

        Map<String, Long> countOccurences = collect
                .stream()
                .flatMap(tuple -> tuple.v1.stream())
                .collect(
                        Collectors.groupingBy(Function.identity(),
                                Collectors.counting())
                );

        Long countOfIngredients = countOccurences.values()
                .stream()
                .reduce(1L, Long::sum);

        Long countOfAllergenesOccurrences = allergenes.values()
                .stream()
                .flatMap(Collection::stream)
                .map(countOccurences::get)
                .reduce(1L, Long::sum);
        return countOfIngredients - countOfAllergenesOccurrences;
    }

    public static String es2(Stream<String> input) {
        List<Tuple2<List<String>, List<String>>> collect = input
                .map(s -> {
                    String[] split = s.split("\\(");
                    return Tuple.tuple(
                            Arrays.asList(split[0].split(" ")),
                            Arrays.asList(
                                    split[1].replaceAll("contains ", "").replaceAll("\\)", "").split(", ")
                            )
                    );
                })
                .collect(Collectors.toList());
        Map<String, Set<String>> allergenes = new HashMap<>();
        collect
                .stream()
                .flatMap(tuple -> tuple.v2.stream())
                .forEach(allergen -> allergenes.put(allergen, new HashSet<>()));

        for (String allergen : allergenes.keySet()) {
            Set<String> possible = allergenes.get(allergen);
            for (Tuple2<List<String>, List<String>> tuple : collect) {
                if (tuple.v2.contains(allergen)) {
                    if (possible.isEmpty()) {
                        possible.addAll(tuple.v1);
                    } else {
                        possible.retainAll(tuple.v1);
                    }
                }
            }
        }

        while (true) {
            List<String> sureAllergenes = new ArrayList<>();
            for (Map.Entry<String, Set<String>> allergen : allergenes.entrySet()) {
                Set<String> possible = allergen.getValue();
                if (possible.size() == 1) {
                    sureAllergenes.add(possible.iterator().next());
                }
            }
            if (sureAllergenes.size() == allergenes.size())
                break;
            for (String allergenToBeDeleted : sureAllergenes) {
                for (Map.Entry<String, Set<String>> allergen : allergenes.entrySet()) {
                    if (allergen.getValue().size() != 1) {
                        allergen.getValue().remove(allergenToBeDeleted);
                    }
                }
            }
        }

        return allergenes.keySet()
                .stream()
                .sorted()
                .map(key -> allergenes.get(key).iterator().next())
                .collect(Collectors.joining(","));
    }
}
