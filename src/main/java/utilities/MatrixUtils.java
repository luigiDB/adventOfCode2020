package utilities;

import java.lang.reflect.Array;

public class MatrixUtils {

    public static <T> void printMatrix(T[][] a) {
        for (T[] row : a) {
            for (T elem : row)
                System.out.print(elem.toString());
            System.out.println();
        }
        System.out.println();
    }

    public static <T> T[][] cloneMatrix(T[][] matrix, Class<T> clazz) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        T[][] clone = (T[][]) Array.newInstance(clazz, rows, columns);
        for (int i = 0; i < matrix.length; i++)
            clone[i] = matrix[i].clone();
        return clone;
    }

    public static <T> boolean compareMatrix(T[][] a, T[][] b) {
        int height = a.length;
        int wide = a[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                if (!a[x][y].equals(b[x][y]))
                    return false;
            }
        }
        return true;
    }

    public static <T>int countOccurencies(T[][] matrix, T match) {
        int occurencies = 0;
        for (T[] row : matrix) {
            for (T elem : row)
                if (elem.equals(match))
                    occurencies++;
        }
        return occurencies;
    }
}
