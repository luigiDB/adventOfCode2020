package utilities;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
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
    private static final Function<String, BigInteger> BigIntegerTransformer = (String x) -> new BigInteger(x.replace('+', ' ').trim());
    private static final Function<String, String> stringTransformer = x -> x;

    public static Stream<Integer> parseInteger(String input) {
        return parseInput(input, integerTransformer);
    }

    public static Stream<Integer> parseInteger(Path fileName) {
        return parseInput(fileName, integerTransformer);
    }

    public static Stream<BigInteger> parseBigInteger(String input) {
        return parseInput(input, BigIntegerTransformer);
    }

    public static Stream<BigInteger> parseBigInteger(Path fileName) {
        return parseInput(fileName, BigIntegerTransformer);
    }

    public static Stream<String> parseStrings(String input) {
        return parseInput(input, stringTransformer);
    }

    public static Stream<String> parseStrings(Path fileName) {
        return parseInput(fileName, stringTransformer);
    }

    public static Stream<String> parseStrings(String input, String separator) {
        return parseInput(input, stringTransformer, separator);
    }

    public static Stream<String> parseStrings(Path fileName, String separator) {
        return parseInput(fileName, stringTransformer, separator);
    }

    public static <T> T[][] parseMatrix(String input, Class<T> clazz, Function<Character, T> transformer) {
        List<String> fileContent = Arrays.asList(input.split("\n"));
        return compileMatrix(fileContent, clazz, transformer);
    }

    public static <T> T[][] parseMatrix(Path fileName, Class<T> clazz, Function<Character, T> transformer) {
        List<String> lines = getListFile(fileName);
        return compileMatrix(lines, clazz, transformer);
    }

    private static <T> T[][] compileMatrix(List<String> fileContent, Class<T> clazz, Function<Character, T> transformer) {
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

    public static <T> Stream<T> parseInput(Path fileName, Function<String, T> lineTransformer) {
        return parseInput(getStringFile(fileName), lineTransformer, "\n");
    }

    public static <T> Stream<T> parseInput(String content, Function<String, T> lineTransformer) {
        return parseInput(content, lineTransformer, "\n");
    }

    public static <T> Stream<T> parseInput(Path fileName, Function<String, T> lineTransformer, String separator) {
        return parseInput(getStringFile(fileName), lineTransformer, separator);
    }

    public static <T> Stream<T> parseInput(String content, Function<String, T> lineTransformer, String separator) {
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
