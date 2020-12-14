package utilities;

import org.apache.commons.lang3.SerializationUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixUtils {

    public static final Supplier<Seq<Integer>> intGenerator = () -> Seq.iterateWhilePresent(0, i -> Optional.of(i + 1));

    public static <T> void printMatrix(T[][] a) {
        for (T[] row : a) {
            for (T elem : row)
                System.out.print(elem.toString() + '\t');
            System.out.println();
        }
        System.out.println();
    }

    public static <T extends Serializable> void fillMatrix(T[][] matrix, T filler) {
        int height = matrix.length;
        int wide = matrix[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                matrix[x][y] = (T) SerializationUtils.clone(filler);
            }
        }
    }

    public static <T> void fillMatrix(T[][] matrix, Supplier<Seq<T>> generator) {
        int height = matrix.length;
        int wide = matrix[0].length;

        List<T> filling = generator.get()
                .slice(0, (long) (height * wide) + 1).collect(Collectors.toList());
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                matrix[x][y] = filling.get(x * height + y);
            }
        }
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

    public static <T> int countOccurrences(T[][] matrix, T match) {
        int occurencies = 0;
        for (T[] row : matrix) {
            for (T elem : row)
                if (elem.equals(match))
                    occurencies++;
        }
        return occurencies;
    }

    public static <T> List<Tuple2<Integer, Integer>> searchOccurrences(T[][] matrix, T match) {
        List<Tuple2<Integer, Integer>> results = new ArrayList<>();
        int height = matrix.length;
        int wide = matrix[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < wide; y++) {
                if (matrix[x][y].equals(match))
                    results.add(Tuple.tuple(x, y));
            }
        }
        return results;
    }

    public static <T> Stream<T> neighbors(T[][] matrix, Tuple2<Integer, Integer> pos) {
        int height = matrix.length;
        int wide = matrix[0].length;
        int[] displacement = new int[]{-1, 0, 1};
        List<T> neighbors = new ArrayList<>();
        for (int displacementX : displacement) {
            for (int displacementY : displacement) {
                if (displacementX == 0 && displacementY == 0)
                    continue;
                int neiX = pos.v1() + displacementX;
                int neiY = pos.v2() + displacementY;
                if (neiX >= 0 && neiX < height && neiY >= 0 && neiY < wide) {
                    neighbors.add(matrix[neiX][neiY]);
                }
            }
        }
        return neighbors.stream();
    }

    public static <T> Stream<Tuple3<Integer, Integer, T>> cardinalNeighbors(T[][] matrix, Tuple2<Integer, Integer> pos) {
        int height = matrix.length;
        int wide = matrix[0].length;
        List<Tuple2<Integer, Integer>> cardinalDirections = cardinalDirections();
        Seq<Optional<Tuple2<Integer, Integer>>> tuples = Seq.seq(cardinalDirections)
                .map(tuple -> {
                    int neiX = pos.v1() + tuple.v1;
                    int neiY = pos.v2() + tuple.v2;
                    if (neiX >= 0 && neiX < height && neiY >= 0 && neiY < wide) {
                        return Optional.of(Tuple.tuple(neiX, neiY));
                    }
                    return Optional.empty();
                });
        return tuples
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(tuple2 -> Tuple.tuple(tuple2.v1, tuple2.v2, matrix[tuple2.v1][tuple2.v1]));
    }

    public static <T> Stream<Tuple2<Integer, Integer>> clearDirections(T[][] matrix, Tuple2<Integer, Integer> pos, T clearPath) {
        int height = matrix.length;
        int wide = matrix[0].length;
        Seq<Optional<Tuple2<Integer, Integer>>> tuples = Seq.seq(cardinalDirections())
                .map(direction -> {
                    int neiX = pos.v1() + direction.v1;
                    int neiY = pos.v2() + direction.v2;
                    if (neiX >= 0 && neiX < height && neiY >= 0 && neiY < wide && matrix[neiX][neiY].equals(clearPath)) {
                        return Optional.of(direction);
                    }
                    return Optional.empty();
                });
        return tuples
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private static List<Tuple2<Integer, Integer>> cardinalDirections() {
        int[] directions = {-1, 0, 1, 0, -1};
        List<Tuple2<Integer, Integer>> cardinalDirections = new ArrayList<>();
        for (int i = 0; i < directions.length - 1; i++) {
            cardinalDirections.add(Tuple.tuple(directions[i], directions[i + 1]));
        }
        return cardinalDirections;
    }

    public static <T> T matrixGet(T[][] matrix, Tuple2<Integer, Integer> pos) {
        return matrix[pos.v1][pos.v2];
    }

    public static <T> void matrixSet(T[][] matrix, Tuple2<Integer, Integer> pos, T elem) {
        matrix[pos.v1][pos.v2] = elem;
    }

    /**
     * Return all the elements encounterd traversing the matrix from the {@code pos} following the {@code direction}.
     * The matrix is indexed by row and after by column de facto we are inverting x and y.
     * <pre>
     * # # #
     * # # # <- Tuple.tuple(1, 2)
     * # # #
     * </pre>
     * As a consequence also directions are handled by row first; for example the following example<p>
     * {@code look(matrix, Tuple.tuple(2, 4), Tuple.tuple(-1, 0))} <p>
     * Will traverse the matrix by moving -1 by row and 0 by column.
     * <pre>
     * # # # # # <- to here
     *         ^
     *         |
     * # # # # #
     *         ^
     *         |
     * # # # # # <- from here
     * </pre>
     *
     * @param matrix           input matrix
     * @param pos              starting point (row-column)
     * @param lookingDirection direction (row-column displacement)
     * @param <T>              the type of the matrix
     * @return
     */
    public static <T> Stream<T> look(T[][] matrix, Tuple2<Integer, Integer> pos, Tuple2<Integer, Integer> lookingDirection) {
        int height = matrix.length;
        int wide = matrix[0].length;
        List<T> look = new ArrayList<>();

        int currentX = pos.v1();
        int currentY = pos.v2();

        while (true) {
            currentX += lookingDirection.v1();
            currentY += lookingDirection.v2();
            if (currentX >= 0 && currentX < height && currentY >= 0 && currentY < wide) {
                T currentElem = matrix[currentX][currentY];
                look.add(currentElem);
            } else
                break;
        }

        return look.stream();
    }

    public static <T> boolean lookForFirstMatch(T[][] matrix,
                                                Tuple2<Integer, Integer> pos,
                                                Tuple2<Integer, Integer> lookingDirection,
                                                Predicate<T> match,
                                                Predicate<T> skip) {
        int height = matrix.length;
        int wide = matrix[0].length;

        int currentX = pos.v1();
        int currentY = pos.v2();

        while (true) {
            currentX += lookingDirection.v1();
            currentY += lookingDirection.v2();
            if (currentX >= 0 && currentX < height && currentY >= 0 && currentY < wide) {
                T currentElem = matrix[currentX][currentY];
                if (skip.negate().test(currentElem))
                    return match.test(currentElem);
            } else
                break;
        }
        return false;
    }

    /**
     * Important the given pos is never checked
     */
    public static <T> Tuple2<Integer, Integer> lookForLastMatch(T[][] matrix,
                                                                Tuple2<Integer, Integer> pos,
                                                                Tuple2<Integer, Integer> lookingDirection,
                                                                T match) {
        int height = matrix.length;
        int wide = matrix[0].length;

        int currentX = pos.v1();
        int currentY = pos.v2();

        Tuple2<Integer, Integer> lastMatch = pos;
        while (true) {
            currentX += lookingDirection.v1();
            currentY += lookingDirection.v2();
            if (currentX >= 0 && currentX < height && currentY >= 0 && currentY < wide) {
                Tuple2<Integer, Integer> currPos = Tuple.tuple(currentX, currentY);
                T currentElem = matrixGet(matrix, currPos);
                if (currentElem.equals(match))
                    lastMatch = currPos;
                else
                    return lastMatch;
            } else
                return lastMatch;
        }
    }

    public static <T> List<Tuple2<Integer, Integer>> lookWhileMatch(T[][] matrix,
                                                                    Tuple2<Integer, Integer> pos,
                                                                    Tuple2<Integer, Integer> lookingDirection,
                                                                    T match) {
        int height = matrix.length;
        int wide = matrix[0].length;

        int currentX = pos.v1();
        int currentY = pos.v2();

        List<Tuple2<Integer, Integer>> traversal = new ArrayList<>();
        traversal.add(pos);
        while (true) {
            currentX += lookingDirection.v1();
            currentY += lookingDirection.v2();
            if (currentX >= 0 && currentX < height && currentY >= 0 && currentY < wide) {
                Tuple2<Integer, Integer> currPos = Tuple.tuple(currentX, currentY);
                T currentElem = matrixGet(matrix, currPos);
                if (currentElem.equals(match))
                    traversal.add(currPos);
                else
                    return traversal;
            } else
                return traversal;
        }
    }

    public static Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> formatGraph(Character[][] matrix) {
        Tuple2<Integer, Integer> start = searchOccurrences(matrix, 'S').iterator().next();
        matrixSet(matrix, start, ' ');
        Tuple2<Integer, Integer> end = searchOccurrences(matrix, 'E').iterator().next();
        matrixSet(matrix, end, ' ');

        Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        Queue<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> bfsQueue = new LinkedList<>();
        bfsQueue.add(Tuple.tuple(start, Tuple.tuple(1, 0)));

        while (!bfsQueue.isEmpty()) {
            Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> extract = bfsQueue.poll();
            Tuple2<Integer, Integer> poll = extract.v1;
            Tuple2<Integer, Integer> banDirection = extract.v2;
            graph.addVertex(poll);
            clearDirections(matrix, poll, ' ')
                    .filter(direction -> !direction.equals(banDirection))
                    .forEach(direction -> {
                        List<Tuple2<Integer, Integer>> walk = lookWhileMatch(matrix, poll, direction, ' ');
                        walk.remove(0);
                        int counter = 1;
                        for (Tuple2<Integer, Integer> step : walk) {
                            List<Tuple2<Integer, Integer>> collect = clearDirections(matrix, step, ' ').collect(Collectors.toList());
                            if (collect.size() != 2) {
                                addEdge(graph, poll, counter, step);

                                if (collect.size() == 3 || collect.size() == 4) {
                                    bfsQueue.add(Tuple.tuple(step, reverseDirection(direction)));
                                }
                            } else {
                                Tuple2<Integer, Integer> second = collect.get(1);
                                if (!collect.get(0).equals(reverseDirection(second))) {
                                    addEdge(graph, poll, counter, step);
                                    bfsQueue.add(Tuple.tuple(step, reverseDirection(direction)));
                                }
                            }
                            counter++;
                        }
                    });
        }

        return graph;
    }

    private static Tuple2<Integer, Integer> reverseDirection(Tuple2<Integer, Integer> second) {
        return Tuple.tuple(-second.v1, -second.v2);
    }

    private static void addEdge(Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> graph,
                                Tuple2<Integer, Integer> start,
                                int counter,
                                Tuple2<Integer, Integer> end) {
        graph.addVertex(end);
        DefaultWeightedEdge edge = graph.addEdge(start, end);
        if (edge != null)
            graph.setEdgeWeight(edge, counter);
    }

}
