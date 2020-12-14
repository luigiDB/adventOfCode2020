package exercises;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Assert;
import org.junit.Test;
import utilities.MatrixUtils;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.AOCTestFramework.getTestInput;
import static utilities.AOCTestFramework.parseStrings;
import static utilities.MatrixUtils.cardinalNeighbors;
import static utilities.MatrixUtils.intGenerator;

public class Day14Test {
    private String testInput = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
            "mem[8] = 11\n" +
            "mem[7] = 101\n" +
            "mem[8] = 0";

    @Test
    public void es1Test() {
        Assert.assertEquals(BigInteger.valueOf(165), Day14.es1(parseStrings(testInput)));
    }

    @Test
    public void es1() {
        BigInteger x = Day14.es1(parseStrings(getTestInput("14")));
        assertEquals(new BigInteger("8570568288597"), x);
        System.out.println(x);
    }

    @Test
    public void es2Test() {
        String input = "mask = 000000000000000000000000000000X1001X\n" +
                "mem[42] = 100\n" +
                "mask = 00000000000000000000000000000000X0XX\n" +
                "mem[26] = 1";
        assertEquals(BigInteger.valueOf(208), Day14.es2(parseStrings(input)));
    }

    @Test
    public void es2() {
        BigInteger x = Day14.es2(parseStrings(getTestInput("14")));
//        assertEquals(1, x);
        System.out.println(x);
    }

}