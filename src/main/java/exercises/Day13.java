package exercises;

import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day13 {
    public static int es1(Stream<String> input) {
        List<String> collect = input.collect(Collectors.toList());
        Integer timestamp = Integer.valueOf(collect.get(0));
        IntStream ferries = Arrays.stream(collect.get(1).split(","))
                .filter(c -> !c.equals("x"))
                .mapToInt(Integer::valueOf);

        Optional<Tuple2<Integer, Integer>> min = Seq.seq(ferries)
                .map(ferry -> Tuple.tuple(ferry, ferry))
                .map(ferry -> Seq.iterateWhilePresent(ferry.v2, current -> Optional.of(current + ferry.v1))
                        .skipWhile(f -> f < timestamp)
                        .findFirst()
                        .map(x -> Tuple.tuple(ferry.v1, x))
                )
                .flatMap(Optional::stream)
                .min(Comparator.comparingInt(a -> a.v2));

        if (min.isPresent()) {
            Tuple2<Integer, Integer> target = min.get();
            return (target.v2 - timestamp) * target.v1;
        }
        throw new UnsupportedOperationException();
    }

    public static BigInteger es2(Stream<String> input, long startMultiplier) {
        List<String> collect = input.collect(Collectors.toList());
        List<Tuple2<BigInteger, BigInteger>> ferries = Seq.seq(Arrays.stream(collect.get(1).split(",")))
                .zipWithIndex()
                .filter(ferry -> !ferry.v1.equals("x"))
                .map(ferry -> Tuple.tuple(new BigInteger(ferry.v1), BigInteger.valueOf(ferry.v2)))
                .collect(Collectors.toList());

        Tuple2<BigInteger, BigInteger> max = ferries.stream()
                .max(Comparator.comparing(a -> a.v1)).get();

        Optional<BigInteger> first = generateSequence(max.v1, startMultiplier)
                .findFirst(elem -> checkIfSequenceStartingHereIsValid(ferries, elem, max.v2));

        if (first.isPresent())
            return first.get().subtract(max.v2);
        throw new UnsupportedOperationException();
    }

    private static boolean checkIfSequenceStartingHereIsValid(List<Tuple2<BigInteger, BigInteger>> ferries,
                                                              BigInteger elem,
                                                              BigInteger pivot) {
        for (int i = 0; i < ferries.size(); i++) {
            Tuple2<BigInteger, BigInteger> ferry = ferries.get(i);
            BigInteger displacement = pivot.subtract(ferry.v2);
            if (elem.add(displacement).mod(ferry.v1).compareTo(BigInteger.ZERO) != 0)
                return false;
            if (i == ferries.size() - 1)
                return true;
        }
        throw new UnsupportedOperationException();
    }

    private static Seq<BigInteger> generateSequence(BigInteger start, long startMultiplier) {
        return Seq.iterateWhilePresent(
                start.multiply(BigInteger.valueOf(startMultiplier)),
                current -> Optional.of(current.add(start))
        );
    }
}
