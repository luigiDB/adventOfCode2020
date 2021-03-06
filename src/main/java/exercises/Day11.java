package exercises;

import org.apache.commons.lang3.tuple.Pair;
import org.jooq.lambda.tuple.Tuple;

import java.util.function.BiFunction;

import static utilities.MatrixUtils.*;

public class Day11 {
    public static int es1(Character[][] matrix) {
        return gameOfSeat(matrix, Day11::occupiedNeighbors, 4);
    }

    public static int es2(Character[][] matrix) {
        return gameOfSeat(matrix, Day11::occupiedNeighborsInLine, 5);
    }

    private static int gameOfSeat(Character[][] matrix, BiFunction<Character[][], Pair<Integer, Integer>, Integer> neighborCounter, int tolerance) {
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

    private static Character[][] cycle(Character[][] matrix, BiFunction<Character[][], Pair<Integer, Integer>, Integer> neighborCounter, int tolerance) {
        int height = matrix.length;
        int wide = matrix[0].length;
        Character[][] clone = cloneMatrix(matrix, Character.class);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                int occupiedNeighbors = neighborCounter.apply(matrix, Pair.of(x, y));
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

    private static int occupiedNeighborsInLine(Character[][] matrix, Pair<Integer, Integer> pos) {
        int[] direction = new int[]{-1, 0, 1};
        int counter = 0;
        for (int directionX : direction) {
            for (int directionY : direction) {
                if (directionX == 0 && directionY == 0)
                    continue;
                if (occupiedIsSight(matrix, directionX, directionY, pos.getLeft(), pos.getRight()))
                    counter++;
            }
        }
        return counter;
    }

    private static boolean occupiedIsSight(Character[][] matrix, int xDirection, int yDirection, int x, int y) {
        int height = matrix.length;
        int wide = matrix[0].length;
        int currentx = x;
        int currenty = y;

        while (true) {
            currentx += xDirection;
            currenty += yDirection;
            if (currentx >= 0 && currentx < height && currenty >= 0 && currenty < wide) {
                if (matrix[currentx][currenty] == '#')
                    return true;
                if (matrix[currentx][currenty] == 'L')
                    return false;
            } else
                return false;
        }
    }

    private static int occupiedNeighbors(Character[][] matrix, Pair<Integer, Integer> pos) {
        int height = matrix.length;
        int wide = matrix[0].length;
        int[] displacement = new int[]{-1, 0, 1};
        int counter = 0;
        for (int displacementX : displacement) {
            for (int displacementY : displacement) {
                if (displacementX == 0 && displacementY == 0)
                    continue;
                int neiX = pos.getLeft() + displacementX;
                int neiY = pos.getRight() + displacementY;
                if (neiX >= 0 && neiX < height && neiY >= 0 && neiY < wide) {
                    if (matrix[neiX][neiY] == '#')
                        counter++;
                }
            }
        }
        return counter;
    }

    private static int countOccupied(Character[][] current) {
        return countOccurrences(current, '#');
    }
}
