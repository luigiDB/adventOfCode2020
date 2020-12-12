package utilities;

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
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static utilities.MatrixUtils.intGenerator;

public class MatrixUtilsTest {

    private static Integer[][] originalMatrix;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

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
        String expectedrint = "0\t1\t2\t3\t4\t\r\n" +
                "5\t6\t7\t8\t9\t\r\n" +
                "10\t11\t12\t13\t14\t\r\n" +
                "15\t16\t17\t18\t19\t\r\n" +
                "20\t21\t22\t23\t24\t\r\n" +
                "\r\n";
        assertEquals(expectedrint, outContent.toString());
    }

    @Test
    public void fillMatrix() {
        MatrixUtils.fillMatrix(originalMatrix, Integer.valueOf(0));
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
                        Tuple.tuple(Tuple.tuple(0, 0), Seq.seq(IntStream.of(1, 5, 6).boxed())),
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
                        Tuple.tuple(Tuple.tuple(0, 0), Tuple.tuple(1, 1), Seq.seq(IntStream.of(6, 12, 18, 24).boxed())),
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
                        Tuple.tuple(Tuple.tuple(0, 0), Tuple.tuple(1, 1), x -> x > 15, x -> false, false),
                        // never skip check if first elem is 6
                        Tuple.tuple(Tuple.tuple(0, 0), Tuple.tuple(1, 1), x -> x == 6, x -> false, true),
                        // skip odd numbers check if first non skipped elem is 12
                        Tuple.tuple(Tuple.tuple(2, 4), Tuple.tuple(0, -1), x -> x == 12, x -> x % 2 != 0, true),
                        // skip all
                        Tuple.tuple(Tuple.tuple(2, 0), Tuple.tuple(-1, -1), x -> x < 10, x -> true, false)
                );
        tests.forEach(test -> {
            assertEquals(test.v5, MatrixUtils.lookForFirstMatch(originalMatrix, test.v1, test.v2, test.v3, test.v4));
        });
    }
}