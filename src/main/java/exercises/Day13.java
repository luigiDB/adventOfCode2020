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

    public static BigInteger es2(Stream<String> input) {
        List<String> collect = input.collect(Collectors.toList());
        List<Tuple2<BigInteger, BigInteger>> ferries = Seq.seq(Arrays.stream(collect.get(1).split(",")))
                .zipWithIndex()
                .filter(ferry -> !ferry.v1.equals("x"))
                .map(ferry -> Tuple.tuple(new BigInteger(ferry.v1), BigInteger.valueOf(ferry.v2)))
                .collect(Collectors.toList());

        Optional<BigInteger> first = generateSequence(ferries.get(0).v1)
                .findFirst(elem -> checkIfSequenceStartingHereIsValid(ferries, elem));

        if(first.isPresent())
            return first.get();
        throw new UnsupportedOperationException();
    }

    private static boolean checkIfSequenceStartingHereIsValid(List<Tuple2<BigInteger, BigInteger>> ferries, BigInteger elem) {
        for (int i = 1; i < ferries.size(); i++) {
            Tuple2<BigInteger, BigInteger> ferry = ferries.get(i);
            if (!contains(ferry.v1, elem.add(ferry.v2)))
                return false;
            if (i == ferries.size() - 1)
                return true;
        }
        throw new UnsupportedOperationException();
    }

    private static Seq<BigInteger> generateSequence(BigInteger start) {
        return Seq.iterateWhilePresent(start, current -> Optional.of(current.add(start)));
    }

    private static boolean contains(BigInteger start, BigInteger target) {
        BigInteger current = start;
        while (current.compareTo(target) < 0) {
            current = current.add(start);
        }
        return current.compareTo(target) == 0;
//        return Seq.iterateWhilePresent(start, current -> Optional.of(current.add(start)))
//                .skipWhile(num -> num.compareTo(target) < 0)
//                .contains(target);
    }
}
