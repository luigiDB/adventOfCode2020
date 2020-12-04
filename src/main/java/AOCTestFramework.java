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

    private static final Function<String, Integer> integerTransformer = x -> Integer.valueOf(x.replace('+', ' ').trim());
    private static final Function<String, String> stringTransformer = x -> x;

    public static Stream<Integer> parseInteger(String input) {
        return parseDirectInput(input, integerTransformer);
    }

    public static Stream<Integer> parseInteger(Path fileName) {
        return parseFileInput(fileName, integerTransformer);
    }

    public static Stream<String> parseStrings(String input) {
        return parseDirectInput(input, stringTransformer);
    }

    public static Stream<String> parseStrings(Path fileName) {
        return parseFileInput(fileName, stringTransformer);
    }

    public static Stream<String> parseStrings(String input, String separator) {
        return parseDirectInput(input, stringTransformer, separator);
    }

    public static Stream<String> parseStrings(Path fileName, String separator) {
        return parseFileInput(fileName, stringTransformer, separator);
    }

    public static <T> T[][] parseMatrix(String input, Class<T> clazz, Function<Character, T> transformer) {
        List<String> fileContent = Arrays.asList(input.split("\n"));
        return compileMatrix(clazz, transformer, fileContent);
    }

    public static <T> T[][] parseMatrix(Path fileName, Class<T> clazz, Function<Character, T> transformer) {
        List<String> lines = getListFile(fileName);
        return compileMatrix(clazz, transformer, lines);
    }

    private static <T> T[][] compileMatrix(Class<T> clazz, Function<Character, T> transformer, List<String> fileContent) {
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

    public static Path getTestInput(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName + ".txt");
        assert url != null;
        try {
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to locate the file");
        }
    }

    // low level interfaces
    public static <T> Stream<T> parseFileInput(Path fileName, Function<String, T> lineTransformer) {
        return parseDirectInput(getStringFile(fileName), lineTransformer, "\n");
    }

    public static <T> Stream<T> parseDirectInput(String content, Function<String, T> lineTransformer) {
        return parseDirectInput(content, lineTransformer, "\n");
    }

    public static <T> Stream<T> parseFileInput(Path fileName, Function<String, T> lineTransformer, String separator) {
        return parseDirectInput(getStringFile(fileName), lineTransformer, separator);
    }

    public static <T> Stream<T> parseDirectInput(String content, Function<String, T> lineTransformer, String separator) {
        return Stream.of(content.split(separator))
                .map(lineTransformer);
    }

    public static List<String> getListFile(Path fileName) {
        try {
            return Files.readAllLines(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the file as list");
        }
    }

    public static Stream<String> getStreamFile(Path fileName) {
        try {
            return Files.lines(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the file as stream");
        }
    }

    public static String getStringFile(Path fileName) {
        try {
            return Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the file as string");
        }
    }
}
