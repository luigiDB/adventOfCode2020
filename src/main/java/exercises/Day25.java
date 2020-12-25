package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day25 {
    public static BigInteger es1(Stream<BigInteger> rawInput) {
        List<BigInteger> publicKeys = rawInput.collect(Collectors.toList());

        List<Tuple2<BigInteger, Integer>> loopSizes = new ArrayList<>();
        BigInteger subjectNumber = BigInteger.valueOf(7);
        BigInteger divider = BigInteger.valueOf(20201227);
        BigInteger value = BigInteger.valueOf(1);
        int loopCounter = 0;
        while (true) {
            loopCounter++;
            value = value.multiply(subjectNumber).mod(divider);
            if (publicKeys.contains(value)) {
                publicKeys.remove(value);
                loopSizes.add(Tuple.tuple(value, loopCounter));
                if (loopSizes.size() == 2)
                    break;
            }
        }

        value = BigInteger.valueOf(1);
        subjectNumber = loopSizes.get(0).v1;
        for (int i = 0; i < loopSizes.get(1).v2; i++) {
            value = value.multiply(subjectNumber).mod(divider);
        }

        return value;
    }

    public static BigInteger es2(Stream<BigInteger> rawInput) {
        return BigInteger.ZERO;
    }
}
