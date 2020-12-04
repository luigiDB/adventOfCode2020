import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class AOCTestFramework {

    public static Stream<Integer> parseInteger(String fileName) {
        return parseInput(fileName, x -> Integer.valueOf(x.replace('+', ' ').trim()));
    }

    public static Stream<String> parseStrings(String fileName) {
        return parseInput(fileName, x -> x);
    }

    public static <T> T[][] parseMatrix(List<String> fileContent, Class<T> clazz, Function<Character, T> transformer) {
        int rows = fileContent.size();
        int columns = fileContent.get(0).length();
        @SuppressWarnings("unchecked")
        T[][] matrix = (T[][]) Array.newInstance(clazz, rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = transformer.apply(fileContent.get(i).charAt(j));
            }
        }
        return matrix;
    }

    public static <T> T[][] parseMatrix(String fileName, Class<T> clazz, Function<Character, T> transformer) {
        List<String> lines = getListFile(fileName);
        return parseMatrix(lines, clazz, transformer);
    }

    public static String parseString(String fileName) {
        return getStringFile(fileName);
    }

    public static Stream<String> parseStrings(String fileName, String separator) {
        return Arrays.stream(getStringFile(fileName).split(separator));
    }



    // low level interfaces
    private static <T> Stream<T> parseInput(String fileName, Function<String, T> lineTransformer) {
        return getStreamFile(fileName).map(lineTransformer);
    }

    private static List<String> getListFile(String fileName) {
        try {
            return Files.readAllLines(getFile(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the file as list");
        }
    }

    private static Stream<String> getStreamFile(String fileName) {
        try {
            return Files.lines(getFile(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the file as stream");
        }
    }

    private static String getStringFile(String fileName) {
        try {
            return Files.readString(getFile(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the file as string");
        }
    }

    private static Path getFile(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName + ".txt");
        assert url != null;
        try {
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to locate the file");
        }
    }
}
