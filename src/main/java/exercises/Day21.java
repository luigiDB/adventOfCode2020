package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21 {
    public static long es1(Stream<String> rawInput) {
        List<Tuple2<List<String>, List<String>>> input = parseInput(rawInput);

        Map<String, Set<String>> allergens = initAllergensMap(input);
        determineAllergens(input, allergens);

        Map<String, Long> countOccurrencesOfEachIngredient = input
                .stream()
                .flatMap(tuple -> tuple.v1.stream())
                .collect(
                        Collectors.groupingBy(Function.identity(),
                                Collectors.counting())
                );

        Long totalNumberOfIngredients = countOccurrencesOfEachIngredient.values()
                .stream()
                .reduce(1L, Long::sum);

        Long countOfAllergensOccurrences = allergens.values()
                .stream()
                .flatMap(Collection::stream)
                .map(countOccurrencesOfEachIngredient::get)
                .reduce(1L, Long::sum);
        return totalNumberOfIngredients - countOfAllergensOccurrences;
    }

    public static String es2(Stream<String> rawInput) {
        List<Tuple2<List<String>, List<String>>> input = parseInput(rawInput);

        Map<String, Set<String>> allergens = initAllergensMap(input);
        determineAllergens(input, allergens);

        return allergens.keySet()
                .stream()
                .sorted()
                .map(key -> allergens.get(key).iterator().next())
                .collect(Collectors.joining(","));
    }

    private static void determineAllergens(List<Tuple2<List<String>, List<String>>> input, Map<String, Set<String>> allergens) {
        for (String allergen : allergens.keySet()) {
            Set<String> possible = allergens.get(allergen);
            for (Tuple2<List<String>, List<String>> tuple : input) {
                if (tuple.v2.contains(allergen)) {
                    if (possible.isEmpty())
                        possible.addAll(tuple.v1);
                    else
                        possible.retainAll(tuple.v1);
                }
            }
        }

        while (true) {
            List<String> sureAllergens = new ArrayList<>();
            for (Map.Entry<String, Set<String>> allergen : allergens.entrySet()) {
                Set<String> possible = allergen.getValue();
                if (possible.size() == 1)
                    sureAllergens.add(possible.iterator().next());
            }
            if (sureAllergens.size() == allergens.size())
                break;
            for (String allergenToBeDeleted : sureAllergens) {
                for (Map.Entry<String, Set<String>> allergen : allergens.entrySet()) {
                    if (allergen.getValue().size() != 1)
                        allergen.getValue().remove(allergenToBeDeleted);
                }
            }
        }
    }

    private static Map<String, Set<String>> initAllergensMap(List<Tuple2<List<String>, List<String>>> input) {
        Map<String, Set<String>> allergenes = new HashMap<>();
        input
                .stream()
                .flatMap(tuple -> tuple.v2.stream())
                .forEach(allergen -> allergenes.put(allergen, new HashSet<>()));
        return allergenes;
    }

    private static List<Tuple2<List<String>, List<String>>> parseInput(Stream<String> input) {
        return input
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
    }
}
