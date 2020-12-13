package exercises;

import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import utilities.ChineseRemainderTheorem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Day13 {
    public static int es1(Stream<String> input) {
        List<String> collect = input.collect(Collectors.toList());
        Integer timestamp = Integer.valueOf(collect.get(0));
        IntStream ferries = stream(collect.get(1).split(","))
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
        List<Tuple2<BigInteger, BigInteger>> ferries = Seq.seq(stream(collect.get(1).split(",")))
                .zipWithIndex()
                .filter(ferry -> !ferry.v1.equals("x"))
                .map(ferry -> Tuple.tuple(new BigInteger(ferry.v1), BigInteger.valueOf(ferry.v2)))
                .collect(Collectors.toList());


        // sorting is not needed it works also with the natural order it just converge faster
        List<Tuple2<BigInteger, BigInteger>> sorted = Seq.seq(ferries)
                .sorted(Comparator.comparing(Tuple2::v1))
                .reverse()
                .collect(Collectors.toList());

        Tuple2<BigInteger, BigInteger> pop = sorted.remove(0);
        for (int i = 0; i < sorted.size(); i++) {
            Tuple2<BigInteger, BigInteger> elem = sorted.get(i);
            sorted.set(i, Tuple.tuple(elem.v1, elem.v2.subtract(pop.v2)));
        }

        BigInteger current = BigInteger.ZERO;
        BigInteger increment = pop.v1;
        BigInteger solution;

        while (true) {
            current = current.add(increment);
            Tuple2<Optional<BigInteger>, BigInteger> result = checkIfCurrentIsValid(sorted, current, increment);
            increment = result.v2;
            if (result.v1.isPresent()) {
                solution = result.v1.get();
                break;
            }
        }
        return solution.subtract(pop.v2);
    }

    private static Tuple2<Optional<BigInteger>, BigInteger> checkIfCurrentIsValid(List<Tuple2<BigInteger, BigInteger>> sorted,
                                                                                  BigInteger current,
                                                                                  BigInteger increment) {
        List<Tuple2<BigInteger, BigInteger>> support = new ArrayList<>(sorted);
        for (int i = 0; i < support.size(); i++) {
            Tuple2<BigInteger, BigInteger> ferry = support.get(i);
            if (current.add(ferry.v2).mod(ferry.v1).compareTo(BigInteger.ZERO) != 0) {
                // In this case the current step is not a valid multiplier thus try next
                return Tuple.tuple(Optional.empty(), increment);
            } else {
                if (i == support.size() - 1) {
                    // We are matching the last ferry thus we found the result we need
                    // The result is relative to the first offset.
                    return Tuple.tuple(Optional.of(current), increment);
                } else {
                    // In case current is valid but it's not the last we increase the step of the current ring. This way
                    // we found all the multiplier of the previous increment and the current.
                    Tuple2<BigInteger, BigInteger> pop = sorted.remove(0);
                    increment = increment.multiply(pop.v1);
                }
            }
        }
        return Tuple.tuple(Optional.empty(), increment);
    }

    public static BigInteger es2WithChineseRemainder(Stream<String> input) {
        List<String> collect = input.collect(Collectors.toList());
        Seq<Tuple2<BigInteger, BigInteger>> ferries = Seq.seq(stream(collect.get(1).split(",")))
                .zipWithIndex()
                .filter(ferry -> !ferry.v1.equals("x"))
                .map(ferry -> Tuple.tuple(new BigInteger(ferry.v1), BigInteger.valueOf(-ferry.v2)));

        Tuple2<Seq<BigInteger>, Seq<BigInteger>> chineseInput = Seq.unzip(ferries);

        return ChineseRemainderTheorem.chineseRemainder(chineseInput.v1.toArray(BigInteger[]::new), chineseInput.v2.toArray(BigInteger[]::new));
    }

}
