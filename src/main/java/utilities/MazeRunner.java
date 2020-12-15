package utilities;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static utilities.MatrixUtils.*;

public class MazeRunner {


    public static Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> formatGraph(Character[][] matrix, Character emptyPath,
                                                                                   Tuple2<Integer, Integer> start) {

        Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        Queue<Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>> bfsQueue = new LinkedList<>();
        bfsQueue.add(Tuple.tuple(start, Tuple.tuple(1, 0)));

        while (!bfsQueue.isEmpty()) {
            Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> extract = bfsQueue.poll();
            Tuple2<Integer, Integer> currentStartingPos = extract.v1;
            Tuple2<Integer, Integer> arrivalDirection = extract.v2;
            graph.addVertex(currentStartingPos);
            clearDirections(matrix, currentStartingPos, ' ')
                    .filter(direction -> !direction.equals(arrivalDirection))
                    .forEach(direction -> {
                        List<Tuple2<Integer, Integer>> walk = lookWhileMatch(matrix, currentStartingPos, direction, emptyPath);
                        for (int counter = 1; counter < walk.size(); counter++) {
                            Tuple2<Integer, Integer> step = walk.get(counter);
                            List<Tuple2<Integer, Integer>> possibleDirectionsFromStep = clearDirections(matrix, step, emptyPath).collect(Collectors.toList());
                            if (possibleDirectionsFromStep.size() != 2) {
                                // step is a 3 way/4 way split or a dead end
                                addEdge(graph, currentStartingPos, step, counter);

                                if (possibleDirectionsFromStep.size() == 3 || possibleDirectionsFromStep.size() == 4) {
                                    bfsQueue.add(Tuple.tuple(step, reverseDirection(direction)));
                                } // dead end
                            } else {
                                //corridor where angle are considered vertexes
                                if (!possibleDirectionsFromStep.get(0).equals(reverseDirection(possibleDirectionsFromStep.get(1)))) {
                                    addEdge(graph, currentStartingPos, step, counter);
                                    bfsQueue.add(Tuple.tuple(step, reverseDirection(direction)));
                                }
                            }
                        }
                    });
        }
        return graph;
    }

    private static void addEdge(Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> graph,
                                Tuple2<Integer, Integer> startingVertex,
                                Tuple2<Integer, Integer> endingVertex, int edgeLen) {
        graph.addVertex(endingVertex);
        DefaultWeightedEdge edge = graph.addEdge(startingVertex, endingVertex);
        if (edge != null)
            graph.setEdgeWeight(edge, edgeLen);
    }

    public static void printPath(Character[][] matrix, GraphPath<Tuple2<Integer, Integer>, DefaultWeightedEdge> path) {
        Character[][] clone = MatrixUtils.cloneMatrix(matrix, Character.class);
        for (Tuple2<Integer, Integer> pos : path.getVertexList()) {
            MatrixUtils.matrixSet(clone, pos, '@');
        }
        MatrixUtils.printMatrix(clone);
    }
}
