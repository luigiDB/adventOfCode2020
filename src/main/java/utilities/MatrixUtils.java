package utilities;

public class MatrixUtils {

    public static <T> void print(T[][] a) {
        for (T[] row : a) {
            for (T elem : row)
                System.out.print(elem.toString());
            System.out.println();
        }
        System.out.println();
    }
}
