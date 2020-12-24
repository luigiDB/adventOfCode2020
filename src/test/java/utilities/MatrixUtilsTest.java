package utilities;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple5;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;
import static utilities.MatrixUtils.cardinalNeighbors;
import static utilities.MatrixUtils.intGenerator;

public class MatrixUtilsTest {

    private static Integer[][] originalMatrix;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final Tuple2<Integer, Integer> firstElem = Tuple.tuple(0, 0);

    @Before
    public void setUp() {
        originalMatrix = new Integer[5][5];
        MatrixUtils.fillMatrix(originalMatrix, intGenerator);

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printMatrix() {
        MatrixUtils.printMatrix(originalMatrix);
        String expecteprint = "0\t1\t2\t3\t4\t\r\n" +
                "5\t6\t7\t8\t9\t\r\n" +
                "10\t11\t12\t13\t14\t\r\n" +
                "15\t16\t17\t18\t19\t\r\n" +
                "20\t21\t22\t23\t24\t\r\n" +
                "\r\n";
        assertEquals(expecteprint, outContent.toString());
    }

    @Test
    public void fillMatrix() {
        MatrixUtils.fillMatrix(originalMatrix, 0);
        assertEquals(25, MatrixUtils.countOccurrences(originalMatrix, 0));
    }

    @Test
    public void cloneMatrix() {
        Integer[][] clone = MatrixUtils.cloneMatrix(originalMatrix, Integer.class);
        assertArrayEquals(clone, originalMatrix);
    }

    @Test
    public void compareMatrix() {
        Integer[][] clone = MatrixUtils.cloneMatrix(originalMatrix, Integer.class);
        assertTrue(MatrixUtils.compareMatrix(originalMatrix, clone));

        clone[2][2] = 10;
        assertFalse(MatrixUtils.compareMatrix(originalMatrix, clone));
    }

    @Test
    public void countOccurrences() {
        for (int i = 0; i < 24; i++) {
            assertEquals(1, MatrixUtils.countOccurrences(originalMatrix, i));
        }
        assertEquals(0, MatrixUtils.countOccurrences(originalMatrix, 25));
    }

    @Test
    public void neighbors() {
        Seq<Tuple2<Tuple2<Integer, Integer>, Seq<Integer>>> tests =
                Seq.of(
                        Tuple.tuple(firstElem, Seq.seq(IntStream.of(1, 5, 6).boxed())),
                        Tuple.tuple(Tuple.tuple(2, 4), Seq.seq(IntStream.of(8, 9, 13, 18, 19).boxed())),
                        Tuple.tuple(Tuple.tuple(3, 1), Seq.seq(IntStream.of(10, 11, 12, 15, 17, 20, 21, 22).boxed())),
                        Tuple.tuple(Tuple.tuple(4, 4), Seq.seq(IntStream.of(18, 19, 23).boxed()))
                );
        tests.forEach(test -> {
            Seq<Integer> neighbors = Seq.seq(MatrixUtils.neighbors(originalMatrix, test.v1()));
            assertTrue(neighbors.containsAll(test.v2));
        });
    }


    @Test
    public void look() {
        Seq<Tuple3<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Seq<Integer>>> tests =
                Seq.of(
                        Tuple.tuple(firstElem, Tuple.tuple(1, 1), Seq.seq(IntStream.of(6, 12, 18, 24).boxed())),
                        Tuple.tuple(Tuple.tuple(2, 4), Tuple.tuple(0, -1), Seq.seq(IntStream.of(13, 12, 11, 10).boxed())),
                        Tuple.tuple(Tuple.tuple(2, 4), Tuple.tuple(-1, 0), Seq.seq(IntStream.of(9, 4).boxed()))
                );
        tests.forEach(test -> {
            Seq<Integer> look = Seq.seq(MatrixUtils.look(originalMatrix, test.v1, test.v2));
            assertTrue(look.containsAll(test.v3));
        });
    }

    @Test
    public void lookForFirstMatch() {
        Seq<Tuple5<
                Tuple2<Integer, Integer>,
                Tuple2<Integer, Integer>,
                Predicate<Integer>,
                Predicate<Integer>,
                Boolean>> tests =
                Seq.of(
                        // never skip check if first elem is > 15
                        Tuple.tuple(firstElem, Tuple.tuple(1, 1), x -> x > 15, x -> false, false),
                        // never skip check if first elem is 6
                        Tuple.tuple(firstElem, Tuple.tuple(1, 1), x -> x == 6, x -> false, true),
                        // skip odd numbers check if first non skipped elem is 12
                        Tuple.tuple(Tuple.tuple(2, 4), Tuple.tuple(0, -1), x -> x == 12, x -> x % 2 != 0, true),
                        // skip all
                        Tuple.tuple(Tuple.tuple(2, 0), Tuple.tuple(-1, -1), x -> x < 10, x -> true, false)
                );
        tests.forEach(test -> assertEquals(test.v5, MatrixUtils.lookForFirstMatch(originalMatrix, test.v1, test.v2, test.v3, test.v4)));
    }

    @Test
    public void matrixGet() {
        assertEquals(Integer.valueOf(0), MatrixUtils.matrixGet(originalMatrix, firstElem));
        assertEquals(Integer.valueOf(7), MatrixUtils.matrixGet(originalMatrix, Tuple.tuple(1, 2)));
    }

    @Test
    public void matrixSet() {
        MatrixUtils.matrixSet(originalMatrix, firstElem, 10);
        assertEquals(Integer.valueOf(10), MatrixUtils.matrixGet(originalMatrix, firstElem));
        Tuple2<Integer, Integer> elem = Tuple.tuple(1, 2);
        MatrixUtils.matrixSet(originalMatrix, elem, 42);
        assertEquals(Integer.valueOf(42), MatrixUtils.matrixGet(originalMatrix, elem));
    }

    @Test
    public void searchOccurrences() {
        MatrixUtils.matrixSet(originalMatrix, Tuple.tuple(1, 2), 10);
        assertThat(MatrixUtils.searchOccurrences(originalMatrix, 10),
                containsInAnyOrder(
                        Tuple.tuple(1, 2),
                        Tuple.tuple(2, 0)
                ));
    }

    @Test
    public void graph() {
        Integer[][] matrix = new Integer[5][5];
        MatrixUtils.fillMatrix(matrix, intGenerator);

        Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                Tuple2<Integer, Integer> center = Tuple.tuple(row, column);
                cardinalNeighbors(matrix, center)
                        .forEach(neighbor -> {
                            graph.addVertex(center);
                            Tuple2<Integer, Integer> neighborCoordinate = Tuple.tuple(neighbor.v1, neighbor.v2);
                            graph.addVertex(neighborCoordinate);
                            DefaultWeightedEdge edge = graph.addEdge(center, neighborCoordinate);
                            if (edge != null)
                                graph.setEdgeWeight(edge, center.v1 * matrix.length + center.v2);
                        });
            }
        }
        Tuple2<Integer, Integer> startNode = firstElem;
        Tuple2<Integer, Integer> endNode = Tuple.tuple(1, 2);
        assertEquals(2, graph.degreeOf(startNode));
        assertEquals(2, graph.inDegreeOf(startNode));
        assertEquals(2, graph.edgesOf(startNode).size());

        DijkstraShortestPath<Tuple2<Integer, Integer>, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Tuple2<Integer, Integer>, DefaultWeightedEdge> path = shortestPath.getPath(startNode, endNode);
        assertTrue(path.getVertexList().containsAll(List.of(
                firstElem,
                Tuple.tuple(0, 1),
                Tuple.tuple(0, 2),
                Tuple.tuple(1, 2)
        )));
        assertEquals(3.0, path.getWeight(), 0.1);

        //verify that the path change with the graph
        graph.removeEdge(startNode, Tuple.tuple(0, 1));
        path = shortestPath.getPath(startNode, endNode);
        assertTrue(path.getVertexList().containsAll(List.of(
                firstElem,
                Tuple.tuple(1, 0),
                Tuple.tuple(1, 1),
                Tuple.tuple(1, 2)
        )));
    }

    @Test
    public void lookForLastMatch() {
        MatrixUtils.matrixSet(originalMatrix, Tuple.tuple(1, 0), 1);
        MatrixUtils.matrixSet(originalMatrix, Tuple.tuple(2, 0), 1);
        MatrixUtils.matrixSet(originalMatrix, Tuple.tuple(3, 0), 1);
        assertEquals(Tuple.tuple(1, 0), MatrixUtils.lookForLastMatch(
                originalMatrix,
                Tuple.tuple(3, 0),
                Tuple.tuple(-1, 0),
                1));
        assertEquals(Tuple.tuple(1, 0), MatrixUtils.lookForLastMatch(
                originalMatrix,
                Tuple.tuple(4, 0),
                Tuple.tuple(-1, 0),
                1));
    }

    @Test
    public void clearDirections() {
        assertThat( MatrixUtils.clearDirections(originalMatrix, firstElem, 5).collect(Collectors.toList()),
                contains(
                        Tuple.tuple(1, 0)
                ));
    }

    @Test
    public void lookForLastMatchList() {
        MatrixUtils.matrixSet(originalMatrix, Tuple.tuple(1, 0), 1);
        MatrixUtils.matrixSet(originalMatrix, Tuple.tuple(2, 0), 1);
        MatrixUtils.matrixSet(originalMatrix, Tuple.tuple(3, 0), 1);
        assertThat(MatrixUtils.lookWhileMatch(
                originalMatrix, Tuple.tuple(3, 0), Tuple.tuple(-1, 0), 1),
                contains(
                        Tuple.tuple(3, 0),
                        Tuple.tuple(2, 0),
                        Tuple.tuple(1, 0)
                ));
        assertThat(MatrixUtils.lookWhileMatch(
                originalMatrix, Tuple.tuple(4, 0), Tuple.tuple(-1, 0), 1),
                contains(
                        Tuple.tuple(4, 0),
                        Tuple.tuple(3, 0),
                        Tuple.tuple(2, 0),
                        Tuple.tuple(1, 0)
                ));
    }
}