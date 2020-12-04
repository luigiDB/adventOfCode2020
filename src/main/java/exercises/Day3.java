package exercises;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.stream.Stream;

public class Day3 {
    public static int es1(Character[][] input) {
        return descendSlope(input, Pair.of(3, 1));
    }

    public static BigInteger es2(Character[][] input) {
        Stream<Pair<Integer, Integer>> slopes = Stream.of(
                Pair.of(1, 1),
                Pair.of(3, 1),
                Pair.of(5, 1),
                Pair.of(7, 1),
                Pair.of(1, 2)
        );
        Stream<Integer> integerStream = slopes.map(slope -> descendSlope(input, slope));
        return integerStream
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);
    }

    private static int descendSlope(Character[][] matrix, Pair<Integer, Integer> slope) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int row = 0;
        int column = 0;
        int trees = 0;
        while (row < rows - slope.getRight()) {
            row += slope.getRight();
            column = (column + slope.getLeft()) % columns;
            if (matrix[row][column] == '#')
                trees++;
        }
        return trees;
    }
}
