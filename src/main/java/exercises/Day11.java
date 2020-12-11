package exercises;

public class Day11 {
    public static int es1(Character[][] matrix) {
        int height = matrix.length;
        int wide = matrix[0].length;

        Character[][] current = cloneMatrix(matrix);
        while (true) {
            Character[][] clone = cycle(current, 4);
//            print(clone);

            if (compare(clone, current)) {
                break;
            } else {
                current = clone;
            }
        }

        return countOccupied(height, wide, current);
    }

    public static int es2(Character[][] matrix) {
        int height = matrix.length;
        int wide = matrix[0].length;

        Character[][] current = cloneMatrix(matrix);
        while (true) {
            Character[][] clone = cycle2(current, 5);
//            print(clone);

            if (compare(clone, current)) {
                break;
            } else {
                current = clone;
            }
        }

        return countOccupied(height, wide, current);
    }

    private static Character[][] cycle2(Character[][] matrix, int minTolerance) {
        int height = matrix.length;
        int wide = matrix[0].length;
        Character[][] clone = cloneMatrix(matrix);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                int occupiedNeigbours = occupiedNeigboursInLine(matrix, x, y);
                if (matrix[x][y] == 'L' && occupiedNeigbours == 0) {
                    clone[x][y] = '#';
                }
                if (matrix[x][y] == '#' && occupiedNeigbours >= minTolerance) {
                    clone[x][y] = 'L';
                }
            }
        }
        return clone;
    }

    private static int occupiedNeigboursInLine(Character[][] matrix, int x, int y) {
        int height = matrix.length;
        int wide = matrix[0].length;
        int[] direction = new int[]{-1, 0, 1};
        int counter = 0;
        for (int i = 0; i < direction.length; i++) {
            for (int j = 0; j < direction.length; j++) {
                if (direction[i] == 0 && direction[j] == 0)
                    continue;
                if (occupiedIsSigth(matrix, direction[i], direction[j], x, y))
                    counter++;
            }
        }
        return counter;
    }

    private static boolean occupiedIsSigth(Character[][] matrix, int xDirection, int yDirection, int x, int y) {
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

    private static Character[][] cycle(Character[][] matrix, int minTolerance) {
        int height = matrix.length;
        int wide = matrix[0].length;
        Character[][] clone = cloneMatrix(matrix);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                int occupiedNeigbours = occupiedNeigbours(matrix, x, y);
                if (matrix[x][y] == 'L' && occupiedNeigbours == 0) {
                    clone[x][y] = '#';
                }
                if (matrix[x][y] == '#' && occupiedNeigbours >= minTolerance) {
                    clone[x][y] = 'L';
                }
            }
        }
        return clone;
    }

    private static int occupiedNeigbours(Character[][] matrix, int x, int y) {
        int height = matrix.length;
        int wide = matrix[0].length;
        int[] disp = new int[]{-1, 0, 1};
        int counter = 0;
        for (int i = 0; i < disp.length; i++) {
            for (int j = 0; j < disp.length; j++) {
                if (disp[i] == 0 && disp[j] == 0)
                    continue;
                int neiX = x + disp[i];
                int neiY = y + disp[j];
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

    private static int countOccupied(int height, int wide, Character[][] current) {
        int occ = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                if (current[x][y] == '#')
                    occ++;
            }
        }
        return occ;
    }

    private static void print(Character[][] a) {
        int height = a.length;
        int wide = a[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                System.out.print(a[x][y]);
            }
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
