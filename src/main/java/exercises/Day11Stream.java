package exercises;

import org.jooq.lambda.Seq;
import org.jooq.lambda.function.Function2;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Optional;
import java.util.stream.Stream;

import static utilities.MatrixUtils.*;

public class Day11Stream {
    public static int es1(Character[][] matrix) {
        return gameOfSeat(matrix, Day11Stream::occupiedNeighbors, 4);
    }

    public static int es2(Character[][] matrix) {
        return gameOfSeat(matrix, Day11Stream::occupiedNeighborsInLine, 5);
    }

    private static int gameOfSeat(Character[][] matrix, Function2<Character[][], Tuple2<Integer, Integer>, Long> neighborCounter, int tolerance) {
        Character[][] current = cloneMatrix(matrix, Character.class);
        while (true) {
            Character[][] clone = cycle(current, neighborCounter, tolerance);

            if (compareMatrix(clone, current)) {
                break;
            } else {
                current = clone;
            }
        }

        return countOccupied(current);
    }

    private static Character[][] cycle(Character[][] matrix, Function2<Character[][], Tuple2<Integer, Integer>, Long> neighborCounter, int tolerance) {
        int height = matrix.length;
        int wide = matrix[0].length;
        Character[][] clone = cloneMatrix(matrix, Character.class);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                long occupiedNeighbors = neighborCounter.apply(matrix, Tuple.tuple(x, y));
                if (matrix[x][y] == 'L' && occupiedNeighbors == 0) {
                    clone[x][y] = '#';
                }
                if (matrix[x][y] == '#' && occupiedNeighbors >= tolerance) {
                    clone[x][y] = 'L';
                }
            }
        }
        return clone;
    }

    private static Stream<Tuple2<Integer, Integer>> lookingDirections() {
        return Seq.of(-1, 0, 1)
                .crossSelfJoin()
                .remove(Tuple.tuple(0, 0));
    }

    private static long occupiedNeighbors(Character[][] matrix, Tuple2<Integer, Integer> pos) {
        int height = matrix.length;
        int wide = matrix[0].length;

        return lookingDirections()
                .map(tuple -> Tuple.tuple(pos.v1() + tuple.v1(), pos.v2() + tuple.v2()))
                .filter(tuple -> tuple.v1() >= 0 && tuple.v1() < height && tuple.v2() >= 0 && tuple.v2() < wide)
                .filter(tuple -> matrix[tuple.v1()][tuple.v2()] == '#')
                .count();
    }

    private static long occupiedNeighborsInLine(Character[][] matrix, Tuple2<Integer, Integer> pos) {
        Stream<Optional<Character>> firstsInSight = lookingDirections()
                .map(tuple -> occupationInSight(matrix, tuple, pos));
        return firstsInSight
                .flatMap(Optional::stream)
                .filter(character -> character == '#')
                .count();
    }

    private static Optional<Character> occupationInSight(Character[][] matrix, Tuple2<Integer, Integer> direction, Tuple2<Integer, Integer> position) {
        int height = matrix.length;
        int wide = matrix[0].length;

        Seq<Tuple2<Integer, Integer>> lineOfSight = Seq.iterateWhilePresent(position, currentPosition -> {
            int currentx = currentPosition.v1() + direction.v1();
            int currenty = currentPosition.v2() + direction.v2();
            if (currentx >= 0 && currentx < height && currenty >= 0 && currenty < wide) {
                return Optional.of(Tuple.tuple(currentx, currenty));
            } else
                return Optional.empty();
        }).skip(1); //we don' want the current pos in the sequence
        return lineOfSight
                .map(tuple -> matrix[tuple.v1()][tuple.v2()])
                .findFirst(character -> character != '.');
    }

    private static int countOccupied(Character[][] current) {
        return countOccurencies(current, '#');
    }
}
