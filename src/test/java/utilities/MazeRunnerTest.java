package utilities;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utilities.AOCTestFramework.parseMatrix;
import static utilities.MatrixUtils.matrixSet;
import static utilities.MatrixUtils.searchOccurrences;

public class MazeRunnerTest {
    private String maze =
            "###################################E#\n" +
            "# #       #       #     #         # #\n" +
            "# # ##### # ### ##### ### ### ### # #\n" +
            "#       #   # #     #     # # #   # #\n" +
            "##### # ##### ##### ### # # # ##### #\n" +
            "#   # #       #     # # # # #     # #\n" +
            "# # ####### # # ##### ### # ##### # #\n" +
            "# #       # # #   #     #     #   # #\n" +
            "# ####### ### ### # ### ##### # ### #\n" +
            "#     #   # #   # #   #     # #     #\n" +
            "# ### ### # ### # ##### # # # #######\n" +
            "#   #   # # #   #   #   # # #   #   #\n" +
            "####### # # # ##### # ### # ### ### #\n" +
            "#     # #     #   # #   # #   #     #\n" +
            "# ### # ##### ### # ### ### ####### #\n" +
            "# #   #     #     #   # # #       # #\n" +
            "# # ##### # ### ##### # # ####### # #\n" +
            "# #     # # # # #     #       # #   #\n" +
            "# ##### # # # ### ##### ##### # #####\n" +
            "# #   # # #     #     # #   #       #\n" +
            "# # ### ### ### ##### ### # ##### # #\n" +
            "# #         #     #       #       # #\n" +
            "#S###################################";

    private String donutMaze =
            "         A           \n" +
            "         A           \n" +
            "  #######.#########  \n" +
            "  #######.........#  \n" +
            "  #######.#######.#  \n" +
            "  #######.#######.#  \n" +
            "  #######.#######.#  \n" +
            "  #####  B    ###.#  \n" +
            "BC...##  C    ###.#  \n" +
            "  ##.##       ###.#  \n" +
            "  ##...DE  F  ###.#  \n" +
            "  #####    G  ###.#  \n" +
            "  #########.#####.#  \n" +
            "DE..#######...###.#  \n" +
            "  #.#########.###.#  \n" +
            "FG..#########.....#  \n" +
            "  ###########.#####  \n" +
            "             Z       \n" +
            "             Z       ";

    @Test
    public void testMinimalMatrixToGraph() {
        String minimalMaze =
                "#######\n" +
                "#  ####\n" +
                "# #####\n" +
                "#   ###\n" +
                "# # ###\n" +
                "# #   E\n" +
                "#S#####";
        Character[][] matrix = parseMatrix(minimalMaze, Character.class, Character::valueOf);
        Tuple2<Integer, Integer> start = searchOccurrences(matrix, 'S').iterator().next();
        matrixSet(matrix, start, ' ');
        Tuple2<Integer, Integer> end = searchOccurrences(matrix, 'E').iterator().next();
        matrixSet(matrix, end, ' ');
        Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> graph = MazeRunner.formatGraph(matrix, ' ', start);

        DijkstraShortestPath<Tuple2<Integer, Integer>, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Tuple2<Integer, Integer>, DefaultWeightedEdge> path = shortestPath.getPath(start, end);
        assertEquals(10.0, path.getWeight(), 1);
    }

    @Test
    public void testMatrixToGraph() {
        Character[][] matrix = parseMatrix(maze, Character.class, Character::valueOf);
        Tuple2<Integer, Integer> start = searchOccurrences(matrix, 'S').iterator().next();
        matrixSet(matrix, start, ' ');
        Tuple2<Integer, Integer> end = searchOccurrences(matrix, 'E').iterator().next();
        matrixSet(matrix, end, ' ');
        Graph<Tuple2<Integer, Integer>, DefaultWeightedEdge> graph = MazeRunner.formatGraph(matrix, ' ', start);

        DijkstraShortestPath<Tuple2<Integer, Integer>, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Tuple2<Integer, Integer>, DefaultWeightedEdge> path = shortestPath.getPath(start, end);
        assertEquals(280.0, path.getWeight(), 1);
    }
}