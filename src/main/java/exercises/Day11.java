package exercises;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

public class Day11 {
    public static int es1(Character[][] matrix) {
        return gameOfSeat(matrix, Day11::cycle);
    }

    public static int es2(Character[][] matrix) {
        return gameOfSeat(matrix, Day11::cycle2);
    }

    private static int gameOfSeat(Character[][] matrix, Function<Character[][], Character[][]> cycle) {
        Character[][] current = cloneMatrix(matrix);
        while (true) {
            Character[][] clone = cycle.apply(current);
//            print(clone);

            if (compare(clone, current)) {
                break;
            } else {
                current = clone;
            }
        }

        return countOccupied(current);
    }

    private static Character[][] cycle(Character[][] matrix) {
        int height = matrix.length;
        int wide = matrix[0].length;
        Character[][] clone = cloneMatrix(matrix);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                int occupiedNeighbors = occupiedNeighbors(matrix, Pair.of(x, y));
                if (matrix[x][y] == 'L' && occupiedNeighbors == 0) {
                    clone[x][y] = '#';
                }
                if (matrix[x][y] == '#' && occupiedNeighbors >= 4) {
                    clone[x][y] = 'L';
                }
            }
        }
        return clone;
    }

    private static Character[][] cycle2(Character[][] matrix) {
        int height = matrix.length;
        int wide = matrix[0].length;
        Character[][] clone = cloneMatrix(matrix);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                int occupiedNeighbors = occupiedNeighborsInLine(matrix, Pair.of(x, y));
                if (matrix[x][y] == 'L' && occupiedNeighbors == 0) {
                    clone[x][y] = '#';
                }
                if (matrix[x][y] == '#' && occupiedNeighbors >= 5) {
                    clone[x][y] = 'L';
                }
            }
        }
        return clone;
    }

    private static int occupiedNeighborsInLine(Character[][] matrix, Pair<Integer, Integer> pos) {
        int height = matrix.length;
        int wide = matrix[0].length;
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

    private static Character[][] cloneMatrix(Character[][] matrix) {
        Character[][] copy = new Character[matrix.length][];
        for (int i = 0; i < matrix.length; i++)
            copy[i] = matrix[i].clone();
        return copy;
    }

    private static int countOccupied(Character[][] current) {
        int occupied = 0;
        for (Character[] characters : current) {
            for (Character c: characters)
                if (c == '#')
                    occupied++;
            }
        return occupied;
    }

    private static void print(Character[][] a) {
        for (Character[] characters : a) {
            for (Character c: characters)
                System.out.print(c);
            System.out.println("");
        }
        System.out.println("__________________________________");
    }

    private static boolean compare(Character[][] a, Character[][] b) {
        int height = a.length;
        int wide = a[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                if (a[x][y] != b[x][y])
                    return false;
            }
        }
        return true;
    }
}
